public class Sniper extends Weapon{
    public Sniper(String name,int damage,double projectileSpeed,double cooldownMilliSeconds){
        super("sniper",51,10.0,1500.0);
    }
    @Override
    public double getProjectileWidth() {
        return 10;
    }

    @Override
    public double getProjectileHeight() {
        return 8;
    }
}
