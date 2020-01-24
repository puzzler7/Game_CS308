package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class for the powerup objects that fall. Extends RectBrick because it uses a lot of the same code (it's a
 * rectangle so it uses a lot of the same positioning code, and they die)
 *
 * Assumes that the images for all of the ids exist and are named appropriately.
 *
 * @author Maverick Chung mc608
 */
public class PowerUp extends RectBrick{
    private String id;
    private double yVelocity;
    private int duration;

    public PowerUp(double x, double y, String code) {
        this(x, y, code, Main.POWERUP_HEIGHT);
    }

    public PowerUp(double x, double y, String code, Image i) {
        this(x,y,code);
        image = new ImagePattern(i);
        shape.setFill(image);
    }

    public PowerUp(double x, double y, String code, double height) {
        super(x, y, 1, height);
        id = code;
        Image i = new Image(PowerUp.class.getClassLoader().getResourceAsStream(Main.POWERUP_PATH_START + id
                + Main.POWERUP_PATH_END));
        setImage(i);
        yVelocity = Main.POWERUP_FALL;
        duration = Main.POWERUP_DURATION;
    }

    /**
     * Returns a random powerup at the given location
     * @param x x location of the powerup
     * @param y y location of the powerup
     */
    public static void generateRandomPowerup(double x, double y) {
        Scene scene = Main.getDisplayScene();
        Random rand = new Random();
        PowerUp pow = new PowerUp(x, y, Main.POWERUP_CODES[rand.nextInt(Main.POWERUP_CODES.length)]);
        ((Group)scene.getRoot()).getChildren().add(pow.shape);
        Main.getPowerups().add(pow);
    }

    /**
     * These override the parent method so that balls don't collide with it
     * @param balls parameter given to match parent signature
     */
    @Override
    public void checkBallCollision(ArrayList<Ball> balls) {}
    @Override
    public void onHit(Ball b) {}

    /**
     * Updates the location of the powerup, and kills it if it touches the void
     * @param elapsedTime time since last frame/update
     */
    public void update(double elapsedTime) {
        setCenterY(getCenterY()+elapsedTime*yVelocity);
        if (getCenterY()+getHeight()/2 > Main.HEIGHT-Main.VOID_SIZE) {
            die();
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    /**
     * Checks to see if the powerup has been collected by a paddle.
     * @param paddles list of paddles to check collision with
     * @return the id and duration of the collected powerup, or "none" if it's not collected
     */
    public String checkPaddleCollision(ArrayList<Paddle> paddles) {
        for (Paddle p: paddles) {
            Shape intersection = Shape.intersect(shape, p);
            if (intersection.getBoundsInLocal().getWidth()!= -1) {
                die();
                return getId()+" "+getDuration();
            }
        }
        return "none";
    }
}
