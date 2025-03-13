package com.example.asteroids;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/*
I create the bullets at the start of the game and after this they won´t be deleted to safe some performance.
They just get invisible then...
That is why the field isActive exists.
Number of bullets active at the same time : 10
 */
public class Bullet implements iBullet {

    //fields
    private double coordX;
    private double coordY;
    private double speed = 30;
    private final double radius = 20;
    private double angle;
    private boolean isActive = true;
    private double angleInRadians;
    private static Player player;
    private Bounds bounds ;
    private static boolean shootableFlag = true;
    private static final long shootableInterval = 150;


    public Bullet(double coordX, double coordY, double angle){

        angleInRadians = Math.toRadians(player.getImageView().getRotate()) - (Math.PI / 2);
        this.coordX = coordX;
        this.coordY = coordY;
        this.angle = angle;
        this.bounds = new BoundingBox(
            this.coordX,
            this.coordY,
            40,
            40
        );

    }//end of constructor

    public void moveBullet() {

        // Calculate the bullet's velocity components
        double velocityX = Math.cos(angleInRadians) * this.getSpeed();
        double velocityY = Math.sin(angleInRadians) * this.getSpeed();

        // Update the bullet's position based on the velocity components
        this.setCoordX(this.getCoordX() + velocityX);
        this.setCoordY(this.getCoordY() + velocityY);

        this.bounds = new BoundingBox(
                this.coordX,
                this.coordY,
                40,
                40
        );

    }//end of moveBullet
    public boolean checkCollision() {

        for(Asteroid asteroid : Asteroid.getAsteroids()) {

            Bounds asteroidBounds = new BoundingBox(
                    asteroid.getAsteroidImage().getBoundsInParent().getMinX(),
                    asteroid.getAsteroidImage().getBoundsInParent().getMinY(),
                    asteroid.getAsteroidImage().getBoundsInParent().getWidth(),
                    asteroid.getAsteroidImage().getBoundsInParent().getHeight()
            );



            if(this.bounds.intersects(asteroidBounds)){

                asteroid.removeImage();
                asteroid.despawnAsteroid();
                player.getBullets().remove(asteroid);
                return true;
            }
        }
        return false;
    }//end of checkCollision
    public static void attachPlayer(Player p){player = p;
    }//end of attachPlayer
    public static void spawnBullet(){
        if(player.getBullets().size() > 9){ for(int i = 0; i <5;i++){player.getBullets().removeFirst();}}
        player.getBullets().push(new Bullet(
                player.getImageView().getBoundsInParent().getCenterX()-18,
                player.getImageView().getBoundsInParent().getCenterY()
                ,player.getImageView().getRotate()));

    }//end of spawnBullet

    //getters
    public double getCoordX() {return this.coordX;}

    public double getCoordY() {return this.coordY;}

    public double getAngle() {return this.angle;}

    public double getRadius() {return this.radius;}

    public boolean isActive(){return this.isActive;}

    public double getSpeed() {return this.speed;}

    public boolean isShootable(){return this.shootableFlag;}

    public static long getShootableInterval(){return shootableInterval;}

    public Bounds getBounds(){return this.bounds;}

    //setters
    public void setCoordX(double coordX) {this.coordX = coordX;}

    public void setCoordY(double coordY) {this.coordY = coordY;}

    public void toggleActive(){this.isActive = !this.isActive;}

    public static void setShootable(boolean s){shootableFlag = s;}


}//end of Bullet
