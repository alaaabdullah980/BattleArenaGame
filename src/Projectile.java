import javafx.geometry.Rectangle2D;

public class Projectile {
    private double x;
    private double y;
    private double angle;  // Changed: Angle in radians instead of direction
    private double speed;
    private int damage;
    private Fighter owner;
    private double projectileWidth;
    private double projectileHeight;

    public Projectile(double x, double y, double angle, double speed, int damage, Fighter owner, double projectileWidth, double projectileHeight) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = speed;
        this.damage = damage;
        this.owner = owner;
        this.projectileWidth = projectileWidth;
        this.projectileHeight = projectileHeight;
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

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Fighter getOwner() {
        return owner;
    }

    public void setOwner(Fighter owner) {
        this.owner = owner;
    }

    public double getProjectileWidth() {
        return projectileWidth;
    }

    public void setProjectileWidth(double projectileWidth) {
        this.projectileWidth = projectileWidth;
    }

    public double getProjectileHeight() {
        return projectileHeight;
    }

    public void setProjectileHeight(double projectileHeight) {
        this.projectileHeight = projectileHeight;
    }

    public void update() {
        if (angle == 0) {  // Right
            x += speed;
        } else if (angle == Math.PI) {  // Left
            x -= speed;
        }
        // No vertical movement
    }

    public boolean isOffScreen() {
        // Updated: Check both x and y
        return x < 0y < 0 || y > 600;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, projectileWidth, projectileHeight);
    }
}
