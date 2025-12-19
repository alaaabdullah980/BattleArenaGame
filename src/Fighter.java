import javafx.geometry.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Fighter {
    private double x, y;
    private int health;
    private int width;
    private int height;
    private List<Weapon> weapons = new ArrayList<>();
    private int currentWeaponIndex = 0;
    private Boolean isLeftSide;
    private double speed = 4;
    private double rotation = 0;

    public Fighter(double x, double y, int health, int width, int height, Weapon weapon, Boolean isLeftSide) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.width = width;
        this.height = height;
        this.isLeftSide = isLeftSide;
    }

    public void addWeapon(Weapon weapon) {
        this.weapons.add(weapon);
    }

    // (bouns) switch weapons
    public void switchWeapon() {
        if (!weapons.isEmpty()) {
            currentWeaponIndex = (currentWeaponIndex + 1) % weapons.size();
            System.out.println("Switched weapon to: " + getWeapon().getName());
        }
    }

    public Weapon getWeapon() {
        return weapons.get(currentWeaponIndex);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Boolean getIsLeftSide() {
        return isLeftSide;
    }

    public void setIsLeftSide(Boolean isLeftSide) {
        this.isLeftSide = isLeftSide;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
    }

    public Projectile attack(long nowMillis, Fighter opponent) {
        if (getWeapon().canShoot(nowMillis)) {
            getWeapon().recordShot(nowMillis);

            double dx = opponent.getX() - this.x;
            double angle = 0;

            if (dx > 0) {
                angle = 0;
            } else {
                angle = Math.PI;
            }

            double startX = x + width / 2;
            double startY = y + height / 2;

            return new Projectile(startX, startY, angle, getWeapon().getProjectileSpeed(), getWeapon().getDamage(), this, getWeapon().getProjectileWidth(), getWeapon().getProjectileHeight());
        }
        return null;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    public void move(double dx, double dy) {
        x += dx * speed;
        y += dy * speed;
        if (x < 0) x = 0;
        if (x + width > 900) x = 900 - width;
        if (y < 0) y = 0;
        if (y + height > 600) y = 600 - height;

    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height );
    }
}