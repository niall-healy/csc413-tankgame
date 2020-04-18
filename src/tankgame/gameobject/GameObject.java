
package tankgame.gameobject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    private int x;
    private int y;

    private int angle;

    private BufferedImage image;

    Rectangle hitBox;

    public abstract void update();

    public GameObject(int x, int y, int angle, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.image = image;
        this.hitBox = new Rectangle(x, y, this.getImage().getWidth(), this.getImage().getHeight());
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getAngle() {
        return angle;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    public void setX(int a){
        this.x = a;
    }

    public void setY(int b){
        this.y = b;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setHitBoxLocation(int x, int y) {
        this.hitBox.setLocation(x, y);
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.image.getWidth() / 2.0, this.image.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, rotation, null);
        g2d.setColor(Color.CYAN);
        //g2d.drawRect(x, y, image.getWidth(), image.getHeight());
    }
}
