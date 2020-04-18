/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;


import tankgame.gameobject.*;
import tankgame.gameobject.immovable.powerup.AmmoPack;
import tankgame.gameobject.immovable.powerup.HealthPack;
import tankgame.gameobject.immovable.powerup.LightningShot;
import tankgame.gameobject.immovable.wall.BreakableWall;
import tankgame.gameobject.immovable.wall.UnbreakableWall;
import tankgame.gameobject.movable.Bullet;
import tankgame.gameobject.movable.Tank;
import tankgame.hud.Hud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

/**
 * Main driver class of Tank Example.
 * Class is responsible for loading resources and
 * initializing game objects. Once completed, control will
 * be given to infinite loop which will act as our game loop.
 * A very simple game loop.
 * @author anthony-pc
 */
public class GameWorld extends JPanel  {

    public static final int WORLD_WIDTH = 1920;
    public static final int WORLD_HEIGHT = 1472;
    public static final int SCREEN_WIDTH = 960;
    public static final int SCREEN_HEIGHT = 736;
    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jFrame;
    private Hud hud;
    private Tank tankOne;
    private Tank tankTwo;

    private static Boolean gameOver = false;
    private static Boolean isRunning = true;
    private static int winner = 0;

    private static ArrayList<GameObject> gameObjectArrayList;

    public static int tickCount = 0;

    public static void main(String[] args) {
        GameWorld gameWorld = new GameWorld();
        gameWorld.init();

        try {
            while (isRunning) {
                for(int i = 0; i < gameObjectArrayList.size(); i++) {
                    gameObjectArrayList.get(i).update();
                }

                CollisionDetector.detectCollisions(gameObjectArrayList);

                gameWorld.hud.update();

                gameWorld.repaint();
                tickCount++;
              //  System.out.println(gameWorld.t1);
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }

    }


    private void init() {
        this.jFrame = new JFrame("Tank Game");
        this.world = new BufferedImage(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        this.hud = new Hud();

        InputStream mapInStream;

        gameObjectArrayList = new ArrayList<>();

        try {
            //Using class loaders to read in resources
            mapInStream = GameWorld.class.getClassLoader().getResourceAsStream("map1.txt");

            buildMap(mapInStream);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        tankOne = new Tank(170, 170, 0, 0, 0, 1,  Resource.getResourceImage("tankOne"));
        gameObjectArrayList.add(tankOne);

        tankTwo = new Tank(1696, 1248, 0, 0, 0, 2,  Resource.getResourceImage("tankTwo"));
        gameObjectArrayList.add(tankTwo);

        TankControl tankTwoControl = new TankControl(tankTwo, KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_ENTER);

        TankControl tankOneControl = new TankControl(tankOne, KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_SPACE);

        hud.init(tankOne, tankTwo);

        this.jFrame.setLayout(new BorderLayout());
        this.jFrame.add(this);
        this.jFrame.addKeyListener(tankOneControl);
        this.jFrame.addKeyListener(tankTwoControl);
        this.jFrame.setSize(GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT + 30);
        this.jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        buffer = world.createGraphics();

        g2.setColor(Color.black);
        g2.fillRect(0, 0, GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT);

        if(gameOver) {
            String gameOverMessage = "Game Over! Player " + winner + " won!";
            String restartMessage = "Exit and reopen to play again";

            g2.setFont(new Font("Monospaced", Font.BOLD + Font.ITALIC, 50));
            g2.setColor(Color.GREEN);
            g2.drawString(gameOverMessage, 125, 300);
            g2.drawString(restartMessage, 50, 400);
            GameWorld.isRunning = false;
        }
        else {
            drawBackground(buffer);
            drawObjects(buffer);

            BufferedImage leftHalf = getSplitScreen(tankOne, world);
            BufferedImage rightHalf = getSplitScreen(tankTwo, world);
            BufferedImage miniMap = world.getSubimage(0, 0, GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT);

            g2.drawImage(leftHalf, 0, 0, null);
            g2.drawImage(rightHalf, GameWorld.SCREEN_WIDTH/2 + 2, 0, null);

            hud.drawImage(g);

            g2.scale(.15, .15);
            g2.drawImage(miniMap, (int) (2.35 * GameWorld.SCREEN_WIDTH), (int) (4.67 * GameWorld.SCREEN_HEIGHT), null);
        }
    }

    private void drawBackground(Graphics g) {
        BufferedImage backgroundImage = Resource.getResourceImage("background");

        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < WORLD_WIDTH; i += backgroundImage.getWidth()) {
            for(int j = 0; j < WORLD_HEIGHT; j += backgroundImage.getHeight()) {
                g2d.drawImage(backgroundImage, i, j, null);
            }
        }
    }

    private void drawObjects(Graphics g) {
        for(int i = 0; i < gameObjectArrayList.size(); i++) {
            gameObjectArrayList.get(i).drawImage(g);
        }
    }

    private void buildMap(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream) );

        String line;
        int lineCounter = 0;
        int imgSideLength = 32;

        while ( (line = bufferedReader.readLine()) != null ) {
            for(int i = 0; i < line.length(); i++) {
                if(line.charAt(i) == '1') {
                    gameObjectArrayList.add(new UnbreakableWall(i * imgSideLength, lineCounter * imgSideLength, 0, Resource.getResourceImage("unbreakableWall")) );
                    //System.out.println("UnbreakableWall added to ArrayList");
                }
                else if(line.charAt(i) == '2') {
                    gameObjectArrayList.add(new BreakableWall(i * imgSideLength, lineCounter * imgSideLength, 0, Resource.getResourceImage("breakableWall"), Resource.getResourceImage("brokenWall")) );
                    //System.out.println("BreakableWall added to ArrayList");
                }
                else if(line.charAt(i) == '4') {
                    gameObjectArrayList.add(new HealthPack(i * imgSideLength, lineCounter * imgSideLength, 0, Resource.getResourceImage("healthPack")) );
                    //System.out.println("HealthPack added to ArrayList");
                }
                else if(line.charAt(i) == '5') {
                    gameObjectArrayList.add(new AmmoPack(i * imgSideLength, lineCounter * imgSideLength, 0, Resource.getResourceImage("ammoPack")) );
                    //System.out.println("AmmoPack added to ArrayList");
                }
                else if(line.charAt(i) == '6') {
                    gameObjectArrayList.add(new LightningShot(i * imgSideLength, lineCounter * imgSideLength, 0, Resource.getResourceImage("lightningPowerUp")) );
                    //System.out.println("LightningShot added to ArrayList");
                }
            }
            lineCounter++;
        }
    }

    public BufferedImage getSplitScreen(Tank tank, BufferedImage world) {
        int x = tank.getX() - SCREEN_WIDTH/4;
        int y = tank.getY() - SCREEN_HEIGHT/3 ;

        if(x < 0) {
            x = 0;
        }
        if(x > WORLD_WIDTH - SCREEN_WIDTH/2) {
            x = WORLD_WIDTH - SCREEN_WIDTH/2;
        }
        if(y < 0) {
            y = 0;
        }
        if(y > WORLD_HEIGHT - SCREEN_HEIGHT) {
            y = (WORLD_HEIGHT - SCREEN_HEIGHT);
        }

        return world.getSubimage(x, y, SCREEN_WIDTH/2 - 2, SCREEN_HEIGHT);
    }

    public static void gameObjectArrayListAdd(GameObject object) {
        gameObjectArrayList.add(object);
    }

    public static void gameObjectArrayListRemove(int index) {
        gameObjectArrayList.remove(index);

        //update all subsequent bulletID's
        for(int i = index; i < gameObjectArrayList.size(); i++) {
            if(gameObjectArrayList.get(i) instanceof Bullet) {
                ((Bullet)gameObjectArrayList.get(i)).setBulletID(i);
            }
        }
    }

    public static int getObjectListSize() {
        return gameObjectArrayList.size();
    }

    public static void setGameOver(Boolean bool) {
        GameWorld.gameOver = bool;
    }

    public static void setWinner(int winner) {
        GameWorld.winner = winner;
    }
}