package tankgame.gameobject.immovable.powerup;

import tankgame.GameWorld;
import tankgame.gameobject.immovable.Immovable;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUp extends Immovable {

    private int tickPickedUp;
    private boolean isHidden;

    public PowerUp(int x, int y, int angle, BufferedImage image) {
        super(x, y, angle, image);

        this.isHidden = false;
    }

    public void hide() {
        this.tickPickedUp = GameWorld.tickCount;

        this.setHitBoxLocation(GameWorld.WORLD_WIDTH - 32, GameWorld.WORLD_HEIGHT - 32);

        this.isHidden = true;
    }

    public void unHide() {
        this.setHitBoxLocation(this.getX(), this.getY());

        this.isHidden = false;
    }

    public void update() {
        if(GameWorld.tickCount - tickPickedUp > 2160) {
            this.unHide();
        }
    }

    public void drawImage(Graphics g) {
        if(!isHidden) {
            super.drawImage(g);
        }
    }

}
