package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.*;

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
        setArcWidth(getWidth()/4);//FIXME magic val
        setFill(Color.PURPLE);
    }

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

    public void checkBallCollision(ArrayList<Ball> balls) {
        for (Ball b : balls) {
            Shape intersection = Shape.intersect(this, b);
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                b.setY(getY() - b.getRadius());
                b.setYVelocity(-b.getYVelocity());
                if (vel!=0) {
                    double ballXVel = b.getXVelocity();
                    double newVel = ballXVel + vel / Math.abs(vel) * Math.pow(Math.abs(vel), .8);//FIXME magic val
                    double scale = newVel / ballXVel / 2;//FIXME magic val
                    b.setXVelocity(newVel);
                    b.setYVelocity(b.getYVelocity() + vel / Math.abs(vel) * Math.pow(Math.abs(vel), .8));
                }
            }
        }
    }

    public void queueNewX(double x) {
        newX = x;
    }

    public void checkPowerupCollision(ArrayList<PowerUp> powerups) {
        for (PowerUp p: powerups) {
            Shape intersection = Shape.intersect(this, p.getShape());
            if (intersection.getBoundsInLocal().getWidth()!= -1) {
                System.out.println("collide");
                Main.addPowerup(p.getId());
                p.die();
            }
        }
    }
}
