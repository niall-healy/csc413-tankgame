package tankgame.hud;

import tankgame.gameobject.movable.Tank;

import java.awt.*;

public class AmmoBar extends HudBar {

    public AmmoBar(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    @Override
    public void update(Tank tank) {
        this.data = tank.getAmmo();
    }
}
