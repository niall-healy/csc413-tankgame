package tankgame.gameobject.immovable;

import tankgame.gameobject.GameObject;

import java.awt.image.BufferedImage;

public abstract class Immovable extends GameObject {

    public Immovable(int x, int y, int angle, BufferedImage image) {
        super(x, y, angle, image);
    }

    public void update() { }

}
