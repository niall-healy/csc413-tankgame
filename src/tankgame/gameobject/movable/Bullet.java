package tankgame.gameobject.movable;

import tankgame.GameWorld;

import java.awt.image.BufferedImage;

public class Bullet extends Movable {

    //tracks this bullet's index on our list of all game objects
    //used to delete bullets
    private int bulletID;
    private boolean hasDestructed;
    //keeps track of the tank that made the bullet so tanks can't hit themselves
    private int tankID;

    public Bullet(int x, int y, int vx, int vy, int angle, int bulletID, int tankID, BufferedImage image) {
        super(x, y, vx, vy, angle, image);

        this.bulletID = bulletID;
        this.tankID = tankID;
        this.hasDestructed = false;

        this.setR(3);
    }

    public void selfDestruct() {
        if( !hasDestructed ) {
            GameWorld.gameObjectArrayListRemove(bulletID);
        }

        hasDestructed = true;
    }

    @Override
    public void update() {
        this.moveForwards();
    }

    public int getTankID() {
        return tankID;
    }

    public void setBulletID(int bulletID) {
        this.bulletID = bulletID;
    }
}
