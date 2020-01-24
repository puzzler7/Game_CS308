package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Random;

/**
 * Abstract brick class to represent a brick of some shape. Created because the Shape superclass does not have its
 * own getter/setter for X and Y positions.
 *
 * Assumes that the brick images exist and are named appropriately. Also assumes all inputs are positive and valid.
 *
 * @author Maverick Chung mc608
 */
public abstract class Brick {
    protected Shape shape;
    protected int hp;
    protected ImagePattern image;
    protected boolean mustBeHit = true;
    protected boolean dead = false;
    protected int score = 100;
    protected double powerupChance = .5;

    /**
     * Abstract classes that control the position of the brick.
     */
    abstract void setCenterX(double x);
    abstract void setCenterY(double y);
    abstract double getCenterX();
    abstract double getCenterY();

    /**
     * Checks for collision with a ball
     * @param balls A list of balls to check collision with
     */
    public void checkBallCollision(ArrayList<Ball> balls) {
        for (Ball b : balls) {
            Shape intersection = Shape.intersect(shape, b);
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                onHit(b);
            }
        }
    }

    /**
     * Set the fill image of the brick
     * @param i the image to fill the brick
     */
    public void setImage(Image i) {
        image = new ImagePattern(i);
        shape.setFill(image);
    }

    /**
     * What should the brick do when hit? Default is to check for powerup spawn, take damage, increase score,
     * and then update the image to reflect the damaged state.
     * @param b
     */
    protected void onHit(Ball b) {
        checkForPowerup();
        Main.setScore((int)(Main.getScore()+getScore()*Main.getSpeedFactor()));
        hit(b.getDmg());
        updateImage();
    }

    /**
     * Updates the image to the next image according to the brick health (e.g. a brick with 2 hp might
     * have the image filename "brickimage2.png"
     */
    protected void updateImage() {
        try {
            if (hp > 0) {
                Image i = new Image(Brick.class.getClassLoader().getResourceAsStream(Main.BRICK_IMAGE_PATH_START
                        + hp + Main.BRICK_IMAGE_PATH_END));

                setImage(i);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Spawns a powerup with a random chance equal to powerupChance.
     */
    protected void checkForPowerup(){
        Random rand = new Random();
        if (rand.nextDouble()<powerupChance) {
            PowerUp.generateRandomPowerup(getCenterX(), getCenterY());
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHP(int health) {
        hp = health;
    }

    public int getScore() {
        return score;
    }

    public Shape getShape() {
        return shape;
    }

    /**
     * Takes an amount of damage. Block dies if hp drops to or below 0.
     * @param dmg Damage to be taken
     */
    public void hit(int dmg) {
        hp -= dmg;
        if (hp<=0) die();
    }

    /**
     * Kills the block by moving it very far offscreen and setting the death flag.
     */
    public void die() {
        setCenterX(-Main.WIDTH*2);
        setCenterY(-Main.HEIGHT*2);
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean mustBeHit() {
        return mustBeHit;
    }

    public void setMustBeHit(boolean hit) {
        mustBeHit = hit;
    }
}
