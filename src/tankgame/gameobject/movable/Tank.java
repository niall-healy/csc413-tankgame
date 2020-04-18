package tankgame.gameobject.movable;

import tankgame.GameWorld;
import tankgame.Resource;

import java.awt.image.BufferedImage;

/**
 *
 * @author anthony-pc
 */
public class Tank extends Movable {

    private int tankID;

    private int tickCountOfLastShot;
    private int tickCountOfLastDamage;
    private int tickCountGotLightning;

    private int health;
    private int ammo;
    private int fireRate;
    private int numLives;

    private int startingX;
    private int startingY;

    private boolean hasLightning;

    private boolean upPressed;
    private boolean downPressed;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean shootPressed;


    public Tank(int x, int y, int vx, int vy, int angle, int tankID, BufferedImage tankImage) {
        super(x, y, vx, vy, angle, tankImage);

        this.startingX = x;
        this.startingY = y;

        this.tankID = tankID;

        this.hasLightning = false;

        this.health = 100;
        this.ammo = 100;
        this.fireRate = 50;
        this.numLives = 5;
    }

    public void toggleUpPressed() {
        this.upPressed = true;
    }

    public void toggleDownPressed() {
        this.downPressed = true;
    }

    public void toggleRightPressed() {
        this.rightPressed = true;
    }

    public void toggleLeftPressed() {
        this.leftPressed = true;
    }

    public void toggleShootPressed() {
        this.shootPressed = true;
    }

    public void unToggleUpPressed() {
        this.upPressed = false;
    }

    public void unToggleDownPressed() {
        this.downPressed = false;
    }

    public void unToggleRightPressed() {
        this.rightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.leftPressed = false;
    }

    public void unToggleShootPressed() {
        this.shootPressed = false;
    }

    @Override
    public void update() {

        if(this.health <= 0) {
            numLives--;
            this.respawn();
        }

        if(numLives == 0) {
            GameWorld.setGameOver(true);
            if(this.tankID == 1) {
                GameWorld.setWinner(2);
            }
            else {
                GameWorld.setWinner(1);
            }
        }

        if (this.upPressed) {
            this.moveForwards();
        }
        if (this.downPressed) {
            this.moveBackwards();
        }

        if (this.leftPressed) {
            this.rotateLeft();
        }
        if (this.rightPressed) {
            this.rotateRight();
        }

        if ( (this.shootPressed && (GameWorld.tickCount - tickCountOfLastShot) >= fireRate && ammo > 0) || hasLightning) {
            Bullet b = new Bullet( this.getXOfBarrel(), this.getYOfBarrel(), 0, 0, this.getAngle(),
                    GameWorld.getObjectListSize(), this.tankID, Resource.getResourceImage("bullet"));

            GameWorld.gameObjectArrayListAdd(b);

            tickCountOfLastShot = GameWorld.tickCount;

            if(!hasLightning) {
                this.ammo -= 5;
            }
        }

        if(GameWorld.tickCount - tickCountGotLightning > 576) {
            hasLightning = false;
        }

    }

    @Override
    public String toString() {
        return "x=" + this.getX() + ", y=" + this.getY() + ", angle=" + this.getAngle();
    }

    public void takeDamage() {
        if(GameWorld.tickCount - tickCountOfLastDamage >= 45) {
            this.health -= 20;

            tickCountOfLastDamage = GameWorld.tickCount;

            //System.out.println("Tank " + tankID + " has taken damage. Health is " + health);
        }
    }

    public void gotLightning() {
        this.tickCountGotLightning = GameWorld.tickCount;
        this.hasLightning = true;
    }

    public void respawn() {
        this.setX(this.startingX);
        this.setY(this.startingY);

        this.health = 100;
        this.ammo = 100;

        this.setHitBoxLocation(this.startingX, this.startingY);
    }

    public int getTankID() {
        return tankID;
    }

    public int getHealth() {
        return health;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getNumLives() {
        return numLives;
    }

    public int getXOfBarrel() {
        return (int) Math.round((this.getX() + 25) + 35 * Math.cos(Math.toRadians( this.getAngle() )));
    }

    public int getYOfBarrel() {
        return (int) Math.round((this.getY() + 25) + 35 * Math.sin(Math.toRadians( this.getAngle() )));
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
