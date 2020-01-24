package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

/**
 * Class for the paddle. Assumes all inputs are positive and valid.
 *
 * @author Maverick Chung mc608
 */
public class Paddle extends Rectangle {
    double lastX;
    double newX;
    double vel;

    public Paddle() {
        this(Main.PADDLE_WIDTH);
    }

    public Paddle(double width) {
        this(width, Main.PADDLE_HEIGHT);
    }

    public Paddle(double width, double height) {
        this(width, height, Main.HEIGHT - Main.VOID_SIZE - height);
    }

    public Paddle(double width, double height, double y) {
        super(Main.WIDTH / 2 - width / 2, y, width, height);
        lastX = Main.WIDTH / 2 - width / 2;
        newX = lastX;
        setArcHeight(getHeight()/2);
        setArcWidth(getWidth()/4);
        setFill(Color.PURPLE);
    }

    /**
     * Updates the position of the paddle and stores the velocity.
     * @param elapsedTime Time since last frame/update.
     */
    public void update(double elapsedTime) {
        vel = (newX - lastX) / elapsedTime;
        lastX = newX;
        setX(newX - getWidth() / 2);
        if (getX() < 0) {
            setX(0);
        }
        if (getX() > Main.WIDTH - getWidth()) {
            setX(Main.WIDTH - getWidth());
        }

    }

    /**
     * Checks to see if the balls have collided with the paddle. If so, updates their velocity accordingly.
     * @param balls list of balls to be checked
     */
    public void checkBallCollision(ArrayList<Ball> balls) {
        for (Ball b : balls) {
            Shape intersection = Shape.intersect(this, b);
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                b.setY(getY() - b.getRadius());
                b.setYVelocity(-b.getYVelocity());
                if (vel!=0) {
                    double ballXVel = b.getXVelocity();

                    //the paddle velocity is added to the ball velocity, but first it is scaled down
                    //it is raised to the .8 power, keeping small velocities about the same
                    //but lowering large velocities to reasonable numbers.
                    double newVel = ballXVel + vel / Math.abs(vel) * Math.pow(Math.abs(vel), .8);
                    double scale = newVel / ballXVel / 2;
                    b.setXVelocity(newVel);
                    b.setYVelocity(b.getYVelocity() + vel / Math.abs(vel) * Math.pow(Math.abs(vel), .8));
                }
            }
        }
    }

    /**
     * Set the next position of the paddle, to be updated on update() call.
     * @param x new position of the paddle
     */
    public void queueNewX(double x) {
        newX = x;
    }


    /**
     * Checks to see if the paddle has collected a powerup.
     * @param powerups list of powerups to check.
     */
    public void checkPowerupCollision(ArrayList<PowerUp> powerups) {
        for (PowerUp p: powerups) {
            Shape intersection = Shape.intersect(this, p.getShape());
            if (intersection.getBoundsInLocal().getWidth()!= -1) {
                Main.addPowerup(p.getId());
                p.die();
            }
        }
    }
}
