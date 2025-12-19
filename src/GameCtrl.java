import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class GameCtrl {
    private Pane root;
    private Fighter p1, p2;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Group entities = new Group();
    private AnimationTimer timer;
    private Text p1Health, p2Health;
    private Text p1Weapon, p2Weapon;  // New: Weapon display texts
    private Group p1Group, p2Group;
    private Set<String> keys = new HashSet<>();
    private Consumer<String> onGameEnd;
    private Text winnerText;
    private Button playAgainButton;  // New: Play Again button

    public GameCtrl(Fighter p1, Fighter p2, Consumer<String> onGameEnd) {
        this.p1 = p1;
        this.p2 = p2;
        this.onGameEnd = onGameEnd;
        root = new Pane();
        root.setPrefSize(900, 600);
        setupUI();
        setupInput();
        setupTimer();
        root.getChildren().add(entities);
    }

    private void setupUI() {
        Rectangle background = new Rectangle(0, 0, 900, 600);
        background.setFill(Color.BLACK);
        root.getChildren().add(background);

        Rectangle middle = new Rectangle(450, 0, 6, 600);
        middle.setFill(Color.LIME);  // Changed to white for visibility (vertical divider)
        root.getChildren().add(middle);

        // Removed: Horizontal divider line (replaced with visible vertical divider above)

        // Create composite shape for P1: Circle (head) + Triangle (body)
        p1Group = new Group();
        Circle p1Head = new Circle(p1.getWidth() / 4);
        p1Head.setFill(Color.RED);
        p1Head.setCenterX(p1.getWidth() / 2);
        p1Head.setCenterY(p1.getHeight() / 4);
        Polygon p1Body = new Polygon();
        p1Body.getPoints().addAll(
                p1.getWidth() / 2.0, p1.getHeight() / 2.0,
                0.0, p1.getHeight() * 1.0,
                p1.getWidth() * 1.0, p1.getHeight() * 1.0
        );
        p1Body.setFill(Color.RED);
        p1Group.getChildren().addAll(p1Head, p1Body);
        p1Group.setTranslateX(p1.getX());
        p1Group.setTranslateY(p1.getY());

        // Create composite shape for P2: Circle (head) + Triangle (body)
        p2Group = new Group();
        Circle p2Head = new Circle(p2.getWidth() / 4);
        p2Head.setFill(Color.BLUE);
        p2Head.setCenterX(p2.getWidth() / 2);
        p2Head.setCenterY(p2.getHeight() / 4);
        Polygon p2Body = new Polygon();
        p2Body.getPoints().addAll(
                p2.getWidth() / 2.0, p2.getHeight() / 2.0,
                0.0, p2.getHeight() * 1.0,
                p2.getWidth() * 1.0, p2.getHeight() * 1.0
        );
        p2Body.setFill(Color.BLUE);
        p2Group.getChildren().addAll(p2Head, p2Body);
        p2Group.setTranslateX(p2.getX());
        p2Group.setTranslateY(p2.getY());

        root.getChildren().addAll(p1Group, p2Group);

        p1Health = new Text(20, 20, "P1 HP: " + p1.getHealth());
        p1Health.setFill(Color.LIME);
        p2Health = new Text(750, 20, "P2 HP: " + p2.getHealth());
        p2Health.setFill(Color.LIME);

        // New: Weapon display texts below health
        p1Weapon = new Text(20, 40, "Weapon: " + p1.getWeapon().getName());
        p1Weapon.setFill(Color.LIME);
        p2Weapon = new Text(750, 40, "Weapon: " + p2.getWeapon().getName());
        p2Weapon.setFill(Color.LIME);

        root.getChildren().addAll(p1Health, p2Health, p1Weapon, p2Weapon);

        winnerText = new Text(300, 300, "");
        winnerText.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        winnerText.setFill(Color.DARKGOLDENROD);
        winnerText.
                setTextAlignment(TextAlignment.CENTER);
        winnerText.setVisible(false);
        root.getChildren().add(winnerText);

        // New: Play Again button, styled like Start Game button
        playAgainButton = new Button("Play Again");
        playAgainButton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-background-color: linear-gradient(to bottom, #00ff00, #32cd32); -fx-text-fill: white; -fx-border-color: #228b22; -fx-border-width: 3; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 5);");
        playAgainButton.setPrefSize(200, 60);
        playAgainButton.setLayoutX(350);  // Center horizontally
        playAgainButton.setLayoutY(400);  // Below winner text
        playAgainButton.setVisible(false);
        playAgainButton.setOnAction(e -> restartGame());
        root.getChildren().add(playAgainButton);
    }

    private void setupInput() {
        root.setOnKeyPressed(e -> {
            keys.add(e.getCode().toString());
        });
        root.setOnKeyReleased(e -> {
            keys.remove(e.getCode().toString());
        });
    }

    private void setupTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long nowMS = System.currentTimeMillis();
                processInput(nowMS);
                updateProjectiles();
                render();
                checkGameOver();
            }
        };
    }

    private void processInput(long nowMs) {
        // Only process if game is not over (winnerText not visible)
        if (winnerText.isVisible()) return;

        double dx1 = 0, dy1 = 0;
        if (keys.contains("W")) dy1 -= 1;
        if (keys.contains("S")) dy1 += 1;
        if (keys.contains("A")) dx1 -= 1;
        if (keys.contains("D")) dx1 += 1;
        p1.move(dx1, dy1);

        if (keys.contains("SPACE")) {
            Projectile p = p1.attack(nowMs, p2);
            if (p != null) {
                projectiles.add(p);
            }
        }

        // New: Check for weapon switch (Q for P1)
        if (keys.contains("Q")) {
            p1.switchWeapon();
            keys.remove("Q");
        }

        double dx2 = 0, dy2 = 0;
        if (keys.contains("UP")) dy2 -= 1;
        if (keys.contains("DOWN")) dy2 += 1;
        if (keys.contains("LEFT")) dx2 -= 1;
        if (keys.contains("RIGHT")) dx2 += 1;
        p2.move(dx2, dy2);

        if (keys.contains("ENTER")) {
            Projectile p = p2.attack(nowMs, p1);
            if (p != null) {
                projectiles.add(p);
            }
        }

        // New: Check for weapon switch (DIGIT1 for P2)
        if (keys.contains("DIGIT1")) {
            p2.switchWeapon();
            keys.remove("DIGIT1");
        }
    }

    private void updateProjectiles() {
        ArrayList<Projectile> toRemove = new ArrayList<>();
        for (Projectile p : projectiles) {
            p.update();

            if (p.getOwner() != p1 && p.getBounds().intersects(p1.getBounds())) {
                p1.takeDamage(p.getDamage());
                toRemove.add(p);
            } else if (p.getOwner() != p2 && p.getBounds().intersects(p2.getBounds())) {
                p2.takeDamage(p.getDamage());
                toRemove.add(p);
            } else if (p.isOffScreen()) {
                toRemove.add(p);
            }
        }
        projectiles.removeAll(toRemove);
    }

    private void render() {
        p1Group.setTranslateX(p1.getX());
        p1Group.setTranslateY(p1.getY());
        p1Group.setRotate(p1.getRotation());

        p2Group.setTranslateX(p2.getX());
        p2Group.setTranslateY(p2.getY());
        p2Group.setRotate(p2.getRotation());

        p1Health.setText("P1 HP: " + p1.getHealth());
        p2Health.setText("P2 HP: " + p2.getHealth());

        // New: Update weapon display texts
        p1Weapon.setText("Weapon: " + p1.getWeapon().getName());
        p2Weapon.setText("Weapon: " + p2.getWeapon().getName());

        entities.getChildren().clear();
        for (Projectile p : projectiles) {
            Rectangle r = new Rectangle(p.getX(), p.getY(), 10, 5);
            r.setFill(Color.LIME);
            entities.getChildren().add(r);
        }
    }

    public void stop() {
        timer.stop();
    }

    public void start() {
        root.requestFocus();
        timer.start();
    }

    private void checkGameOver() {
        if (p1.getHealth() <= 0) {
            winnerText.setText("Player 2 wins ⚔");
            winnerText.setVisible(true);
            playAgainButton.setVisible(true);  // Show Play Again button
            stop();
            onGameEnd.accept("Player 2 wins ⚔");
        } else if (p2.getHealth() <= 0) {
            winnerText.setText("Player 1 wins ⚔");
            winnerText.setVisible(true);
            playAgainButton.setVisible(true);  // Show Play Again button
            stop();
            onGameEnd.accept("Player 1 wins ⚔");
        }
    }

    // New: Restart the game
    private void restartGame() {
        // Reset health
        p1.setHealth(100);
        p2.setHealth(100);
        // Reset positions
        p1.setX(100);
        p1.setY(250);
        p2.setX(750);
        p2.setY(250);
        // Clear projectiles
        projectiles.clear();
        // Hide winner text and button
        winnerText.setVisible(false);
        playAgainButton.setVisible(false);
        // Restart the game
        start();
    }

    public Pane getRoot() {
        return root;
    }
}