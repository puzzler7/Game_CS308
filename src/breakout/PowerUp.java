package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

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
        yVelocity = Main.POWERUP_FALL;
        duration = Main.POWERUP_DURATION;
    }

    @Override
    public void checkBallCollision(ArrayList<Ball> balls) {
        return;
    }

    public void update(double elapsedTime) {
        setCenterY(getCenterY()+elapsedTime*yVelocity);
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
