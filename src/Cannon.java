public class Cannon extends Weapon{
    public Cannon(String name,int damage,double projectileSpeed,double cooldownMilliSeconds){
        super("cannon",23,8,1000.0);


    }
    @Override
    public double getProjectileWidth() {
        return 7;
    }

    @Override
    public double getProjectileHeight() {
        return 5;
    }
}
