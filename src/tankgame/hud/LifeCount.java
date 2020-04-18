package tankgame.hud;

import tankgame.gameobject.movable.Tank;

import java.awt.*;

public class LifeCount extends HudElement {

    private int numLives;

    public LifeCount(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(this.color);
        g2d.setFont(new Font("Monospaced", Font.BOLD + Font.ITALIC, 75));
        g2d.drawString(String.valueOf(numLives), this.getX(), this.getY());
    }

    @Override
    public void update(Tank tank) {
        this.numLives = tank.getNumLives();
    }
}
