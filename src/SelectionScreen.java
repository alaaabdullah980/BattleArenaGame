import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.util.function.BiConsumer;

public class SelectionScreen {
    private BorderPane root;
    private ChoiceBox<String> choice1, choice2;
    private BiConsumer<Fighter, Fighter> onStart;

    public SelectionScreen(BiConsumer<Fighter, Fighter> onStart) {
        this.onStart = onStart;
        root = new BorderPane();
        root.setStyle("-fx-background-color: black;");
        choice1 = new ChoiceBox<>();
        choice2 = new ChoiceBox<>();

        VBox box = new VBox(20);
        box.setPadding(new Insets(50));
        box.setStyle("-fx-alignment: center;");


        String labelStyle = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;";


        String choiceBoxStyle = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #333; -fx-text-fill: white; -fx-border-color: #666; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-radius: 5;";
        choice1.setStyle(choiceBoxStyle);
        choice2.setStyle(choiceBoxStyle);

        choice1.getItems().addAll("Warrior", "Mage", "Goblin");
        choice2.getItems().addAll("Warrior", "Mage", "Goblin");
        choice1.setValue("Warrior");
        choice2.setValue("Warrior");


        Label player1Label = new Label("Player 1");
        player1Label.setStyle(labelStyle);
        Label player2Label = new Label("Player 2");
        player2Label.setStyle(labelStyle);

        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-background-color: linear-gradient(to bottom, #00ff00, #32cd32); -fx-text-fill: white; -fx-border-color: #228b22; -fx-border-width: 3; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 5);");
        startButton.setPrefSize(200, 60);  // Increased size

        startButton.setOnAction(event -> {
            Fighter f1 = createFighter(choice1.getValue(), 100, 250, true);
            Fighter f2 = createFighter(choice2.getValue(), 750, 250, false);
            onStart.accept(f1, f2);
        });

        box.getChildren().addAll(startButton, player1Label, choice1, player2Label, choice2);
        root.setCenter(box);
    }

    private Fighter createFighter(String name, double x, double y, boolean isLeftSide) {
        Fighter fighter = null;
        switch (name) {
            case "Warrior":
                fighter = new Warrior(x, y, 100, 120, 60, null, isLeftSide);
                break;
            case "Mage":
                fighter = new Mage(x, y, 100, 100, 50, null, isLeftSide);
                break;
            case "Goblin":
                fighter = new Goblin(x, y, 100, 200, 70, null, isLeftSide);
                break;
            default:
                fighter = new Warrior(x, y, 100, 120, 60, null, isLeftSide);
        }

        fighter.addWeapon(new Cannon("cannon", 23, 8, 1000.0));
        fighter.addWeapon(new Pistol("pistol", 15, 8.5, 500.0));
        fighter.addWeapon(new Sniper("sniper", 51, 10, 1500.0));
        return fighter;
    }

    public Node getRoot() {
        return root;
    }
}