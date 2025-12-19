public class Pistol extends Weapon{
    public Pistol(String name,int damage,double projectileSpeed,double cooldownMilliSeconds){
        super("pistol",15,8.5,500.0);
    }
    @Override
    public double getProjectileWidth() {
        return 4;
    }

    @Override
    public double getProjectileHeight() {
        return 3;
    }
}