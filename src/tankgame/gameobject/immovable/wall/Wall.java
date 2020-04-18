package tankgame.gameobject.immovable.wall;

import tankgame.gameobject.immovable.Immovable;

import java.awt.image.BufferedImage;

public abstract class Wall extends Immovable {

    public Wall(int x, int y, int angle, BufferedImage image) {
        super(x, y, angle, image);
    }
}
