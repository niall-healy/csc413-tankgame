package tankgame.hud;

import tankgame.gameobject.movable.Tank;
import tankgame.GameWorld;

import java.awt.*;
import java.util.ArrayList;

public class Hud {

    Tank tank1;
    Tank tank2;

    private ArrayList<HudElement> tank1Elements;
    private ArrayList<HudElement> tank2Elements;

    //BEWARE: Magic numbers ahead
    public void init(Tank tank1, Tank tank2) {
        this.tank1Elements = new ArrayList<>();
        this.tank2Elements = new ArrayList<>();

        this.tank1 = tank1;
        this.tank2 = tank2;

        Color healthColor = new Color(180, 180, 180);
        Color ammoColor = new Color(70, 70, 70);

        this.tank1Elements.add(new AmmoBar(50, GameWorld.SCREEN_HEIGHT - 64, 200, 25, ammoColor));
        this.tank1Elements.add(new HealthBar(50, GameWorld.SCREEN_HEIGHT - 90, 200, 25, healthColor));
        this.tank1Elements.add(new LifeCount(260, GameWorld.SCREEN_HEIGHT - 35, Color.BLACK));

        tank2Elements.add(new AmmoBar(GameWorld.SCREEN_WIDTH - 250, GameWorld.SCREEN_HEIGHT - 64, 200, 25, ammoColor));
        tank2Elements.add(new HealthBar(GameWorld.SCREEN_WIDTH - 250, GameWorld.SCREEN_HEIGHT - 90, 200, 25, healthColor));
        tank2Elements.add(new LifeCount(GameWorld.SCREEN_WIDTH - 305, GameWorld.SCREEN_HEIGHT - 35, Color.BLACK));
    }

    public void update() {
        this.tank1Elements.forEach(hudElement -> hudElement.update(this.tank1));
        this.tank2Elements.forEach(hudElement -> hudElement.update(this.tank2));
    }

    public void drawImage(Graphics g) {
        this.tank1Elements.forEach(hudElement -> hudElement.drawImage(g));
        this.tank2Elements.forEach(hudElement -> hudElement.drawImage(g));
    }
}
