package tankgame;

import tankgame.gameobject.*;
import tankgame.gameobject.immovable.*;
import tankgame.gameobject.immovable.powerup.AmmoPack;
import tankgame.gameobject.immovable.powerup.HealthPack;
import tankgame.gameobject.immovable.powerup.LightningShot;
import tankgame.gameobject.immovable.powerup.PowerUp;
import tankgame.gameobject.immovable.wall.BreakableWall;
import tankgame.gameobject.immovable.wall.Wall;
import tankgame.gameobject.movable.Bullet;
import tankgame.gameobject.movable.Tank;

import java.util.ArrayList;

public class CollisionDetector {

    //This is some bad code so strap yourself in
    public static void detectCollisions(ArrayList<GameObject> gameObjectArrayList) {
        GameObject outerLoopObject, innerLoopObject;

        //Loop through all objects in the ArrayList
        for(int outerLoopIndex = 0; outerLoopIndex < gameObjectArrayList.size(); outerLoopIndex++) {
            outerLoopObject = gameObjectArrayList.get(outerLoopIndex);

            //Ignore Immovable's in first loop (Walls, PowerUps)
            if( !(outerLoopObject instanceof Immovable) ){

                //Loop through all objects again
                for(int innerLoopIndex = 0; innerLoopIndex < gameObjectArrayList.size(); innerLoopIndex++) {
                    innerLoopObject = gameObjectArrayList.get(innerLoopIndex);

                    //Don't check an object against its own hitBox
                    if(innerLoopIndex != outerLoopIndex) {

                        //If objects are colliding
                        if(outerLoopObject.getHitBox().intersects( innerLoopObject.getHitBox() )) {
                            if(outerLoopObject instanceof Tank) {

                                if(innerLoopObject instanceof Wall) {
                                    tankHitWall( (Tank)outerLoopObject, (Wall)innerLoopObject );
                                }
                                else if(innerLoopObject instanceof PowerUp) {
                                    tankHitPowerUp( (Tank)outerLoopObject, (PowerUp)innerLoopObject );
                                }
                                else if(innerLoopObject instanceof Bullet) {
                                    bulletHitTank( (Bullet)innerLoopObject, (Tank)outerLoopObject );
                                }
                                else if(innerLoopObject instanceof Tank) {
                                    tanksCollided( (Tank)outerLoopObject, (Tank)innerLoopObject );
                                }
                            }
                            else if(outerLoopObject instanceof Bullet) {
                                if(innerLoopObject instanceof Wall) {
                                    bulletHitWall( (Bullet)outerLoopObject, (Wall)innerLoopObject );
                                }
                                else if(innerLoopObject instanceof Tank) {
                                    bulletHitTank( (Bullet)outerLoopObject, (Tank)innerLoopObject );
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private static void bulletHitTank(Bullet bullet, Tank tank) {
        if(tank.getTankID() != bullet.getTankID()) {
            tank.takeDamage();
            bullet.selfDestruct();
            //System.out.println("Tank was hit with bullet.");
        }
    }

    private static void bulletHitWall(Bullet bullet, Wall wall) {
        //System.out.println("Bullet Hit Wall");
        bullet.selfDestruct();

        if(wall instanceof BreakableWall) {
            ((BreakableWall)wall).shatter();
        }
    }

    public static void tankHitWall(Tank tank, Wall wall) {
        tank.setX(tank.getPrevX());
        tank.setY(tank.getPrevY());

        //System.out.println("Tank hit Wall.");
    }

    public static void tankHitPowerUp(Tank tank, PowerUp powerUp) {
        if(powerUp instanceof HealthPack) {
            if(tank.getHealth() != 100) {
                tank.setHealth(100);
                powerUp.hide();
            }
        }
        else if(powerUp instanceof AmmoPack) {
            if(tank.getAmmo() != 100) {
                tank.setAmmo(100);
                powerUp.hide();
            }
        }
        else if(powerUp instanceof LightningShot) {
            tank.gotLightning();
            powerUp.hide();
        }
        //System.out.println("Tank hit PowerUp.");
    }

    public static void tanksCollided(Tank tank1, Tank tank2) {
        tank1.takeDamage();
        tank2.takeDamage();
        //System.out.println("Tanks Collided.");
    }

}
