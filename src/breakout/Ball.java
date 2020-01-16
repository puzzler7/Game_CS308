package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    private double xVelocity;
    private double yVelocity;
    private int bounceX; //FIXME remove for Main consts?
    private int bounceY;
    private ImagePattern image;

    public Ball() {
        this(Main.BALL_SIZE);
    }

    public Ball(double radius) {
        this(Main.WIDTH / 2, Main.HEIGHT / 2, radius);
    }

    public Ball(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        setFill(Color.GREEN);
        bounceX = Main.WIDTH;
        bounceY = Main.HEIGHT - Main.VOID_SIZE;
    }

    public Ball(double centerX, double centerY, double radius, Image i) {
        this(centerX, centerY, radius);
        image = new ImagePattern(i);
        this.setFill(image);
    }

    public Ball (Image i) {
        this();
        image = new ImagePattern(i);
        this.setFill(image);
    }

    public void update(double elapsedTime) { //currently returns if the ball dies.
        setX(getX() + xVelocity * elapsedTime);
        setY(getY() + yVelocity * elapsedTime);
        double mag = xVelocity*xVelocity+yVelocity*yVelocity;
        mag = Math.pow(mag, .5);
        setRotate(getRotate()+mag/100);//FIXME
    }

    public boolean checkBounce() {
        if (getX() < getRadius()) {
            xVelocity *= -1;
            setX(getRadius());
        }
        if (getX() > bounceX - getRadius()) {
            xVelocity *= -1;
            setX(bounceX - getRadius());
        }
        if (getY() < getRadius()) {
            yVelocity *= -1;
            setY(getRadius());
        }
        if (getY() > bounceY - getRadius()) {
            yVelocity *= -1;
            setY(bounceY - getRadius());
            return true;
        }
        return false;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public int getBounceX() {
        return bounceX;
    }

    public void setBounceX(int bounceX) {
        this.bounceX = bounceX;
    }

    public int getBounceY() {
        return bounceY;
    }

    public void setBounceY(int bounceY) {
        this.bounceY = bounceY;
    }

    public void setX(double x) {
        setCenterX(x);
    }

    public double getX() {
        return getCenterX();
    }

    public void setY(double y) {
        setCenterY(y);
    }

    public double getY() {
        return getCenterY();
    }
}
