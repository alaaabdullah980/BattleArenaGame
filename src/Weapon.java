public class Weapon {
    private String name;
    private int damage;
    private double projectileSpeed;
    private double cooldownMilliSeconds;
    private double lastShotTime = 0;

    public Weapon(String name,int damage,double projectileSpeed,double cooldownMilliSeconds){
        this.name=name;
        this.damage=damage;
        this.projectileSpeed=projectileSpeed;
        this.cooldownMilliSeconds=cooldownMilliSeconds;
    }

    double getProjectileWidth() {
        return 0;
    }

    double getProjectileHeight() {
        return 0;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public int getDamage(){
        return damage;
    }
    public void setDamage(int damage){
        this.damage=damage;
    }
    public double getProjectileSpeed(){
        return projectileSpeed;
    }
    public void setProjectileSpeed(double projectileSpeed){
        this.projectileSpeed=projectileSpeed;
    }
    public double getCooldownMilliSeconds(){
        return cooldownMilliSeconds;
    }
    public void setCooldownMilliSeconds(double cooldownMilliSeconds){
        this.cooldownMilliSeconds=cooldownMilliSeconds;
    }
    public Boolean canshot(double currentMilliSeconds){
        if(currentMilliSeconds - lastShotTime >= cooldownMilliSeconds){
            return true;
        }
        else {
            return false;
        }
    }
    public void newShot(double currentMilliSeconds){
        lastShotTime = currentMilliSeconds;
    }

    public void recordShot(long nowMillis) {
        lastShotTime = nowMillis;
    }
    public boolean canShoot(double nowMillis){
        return nowMillis - lastShotTime >= cooldownMilliSeconds;
    }
}