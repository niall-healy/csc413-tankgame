package tankgame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import static javax.imageio.ImageIO.read;

public class Resource {

    private static HashMap<String, BufferedImage> resources;

    static {
        Resource.resources = new HashMap<>();

        try {
            Resource.resources.put("tankOne", read(GameWorld.class.getClassLoader().getResource("tank1.png")));
            Resource.resources.put("tankTwo", read(GameWorld.class.getClassLoader().getResource("tank2.png")));
            Resource.resources.put("background", read(GameWorld.class.getClassLoader().getResource("background.jpg")));
            Resource.resources.put("bullet", read(GameWorld.class.getClassLoader().getResource("bullet.png")));
            Resource.resources.put("breakableWall", read(GameWorld.class.getClassLoader().getResource("breakableWall.png")));
            Resource.resources.put("brokenWall", read(GameWorld.class.getClassLoader().getResource("wallCrumbs.png")));
            Resource.resources.put("unbreakableWall",read(GameWorld.class.getClassLoader().getResource("unbreakableWall.png")));
            Resource.resources.put("healthPack", read(GameWorld.class.getClassLoader().getResource("healthPack.png")));
            Resource.resources.put("ammoPack", read(GameWorld.class.getClassLoader().getResource("ammoPack.png")));
            Resource.resources.put("lightningPowerUp", read(GameWorld.class.getClassLoader().getResource("fireRateUp.png")));
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(-5);
        }
    }

    public static BufferedImage getResourceImage(String key) {
        return Resource.resources.get(key);
    }
}
