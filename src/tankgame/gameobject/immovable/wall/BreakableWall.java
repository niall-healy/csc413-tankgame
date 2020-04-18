package tankgame.gameobject.immovable.wall;

import tankgame.GameWorld;
import tankgame.gameobject.immovable.wall.Wall;

import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {

    BufferedImage brokenImage;

    public BreakableWall(int x, int y, int angle, BufferedImage image, BufferedImage brokenImage) {
        super(x, y, angle, image);

        this.brokenImage = brokenImage;
    }

    public void shatter() {
        this.setImage(brokenImage);

        this.setHitBoxLocation(GameWorld.WORLD_WIDTH - 32, GameWorld.WORLD_HEIGHT - 32);
    }

}
