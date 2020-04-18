package tankgame.hud;

import tankgame.gameobject.movable.Tank;

import java.awt.*;

public abstract class HudBar extends HudElement {

    int width;
    int height;
    int data;

    public HudBar(int x, int y, int width, int height, Color color) {
        super(x, y, color);

        this.width = width;
        this.height = height;
    }

    public abstract void update(Tank tank);

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(data <= 40) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(this.color);
        }
        g2d.drawRect(this.getX(), this.getY(), this.width, this.height);
        g2d.fillRect(this.getX(), this.getY(), (this.width * data/100), this.height);
    }

}
