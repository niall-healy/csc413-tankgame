package tankgame.gameobject.movable;

import tankgame.GameWorld;
import tankgame.gameobject.GameObject;

import java.awt.image.BufferedImage;

public abstract class Movable extends GameObject {

    private int vx;
    private int vy;

    private int prevX;
    private int prevY;

    private int R = 1;
    private final int ROTATION_SPEED = 2;

    public Movable(int x, int y, int vx, int vy, int angle, BufferedImage image) {
        super(x, y, angle, image);
        this.vx = vx;
        this.vy = vy;
    }

    protected void rotateLeft() {
        this.setAngle( this.getAngle() - this.ROTATION_SPEED );
    }

    protected void rotateRight() {
        this.setAngle( this.getAngle() + this.ROTATION_SPEED );
    }

    protected void moveBackwards() {
        this.prevX = this.getX();
        this.prevY = this.getY();

        vx = (int) Math.round(R * Math.cos(Math.toRadians( this.getAngle() )));
        vy = (int) Math.round(R * Math.sin(Math.toRadians( this.getAngle() )));
        this.setX( this.getX() - vx );
        this.setY( this.getY() - vy );
        checkBorder();
        this.setHitBoxLocation(this.getX(), this.getY());
    }

    protected void moveForwards() {
        this.prevX = this.getX();
        this.prevY = this.getY();

        vx = (int) Math.round(R * Math.cos(Math.toRadians( this.getAngle() )));
        vy = (int) Math.round(R * Math.sin(Math.toRadians( this.getAngle() )));
        this.setX( this.getX() + vx );
        this.setY( this.getY() + vy );
        checkBorder();
        this.setHitBoxLocation(this.getX(), this.getY());
    }

    private void checkBorder() {
        if (this.getX() < 30) {
            this.setX( 30 );
        }
        if (this.getX() >= GameWorld.WORLD_WIDTH - 88) {
            this.setX( GameWorld.WORLD_WIDTH - 88 );
        }
        if (this.getY() < 40) {
            this.setY( 40 );
        }
        if (this.getY() >= GameWorld.WORLD_HEIGHT - 70) {
            this.setY( GameWorld.WORLD_HEIGHT - 70 );
        }
    }

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setR(int r) {
        R = r;
    }

}
