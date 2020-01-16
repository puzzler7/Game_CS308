package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    private int xVelocity;
    private int yVelocity;
    private int bounceX;
    private int bounceY;
    private ImagePattern image;

    public Ball() {
        this(10);
    }

    public Ball(double radius) {
        this(Main.WIDTH / 2, Main.HEIGHT / 2, radius);
        setFill(Color.GREEN);
    }

    public Ball(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        bounceX = Main.WIDTH;
        bounceY = Main.HEIGHT - Main.VOID_SIZE;
    }

    public Ball(double centerX, double centerY, double radius, Image i) {
        this(centerX, centerY, radius);
        image = new ImagePattern(i);
        this.setFill(image);
    }

    public boolean update(double elapsedTime) { //currently returns if the ball dies.
        setX(getX() + xVelocity * elapsedTime);
        setY(getY() + yVelocity * elapsedTime);
        if (bounceX != 0 && bounceY != 0) {
            return checkBounce();
        } else {
            System.out.println("Zero bounce!");
            return true;
        }
    }

    private boolean checkBounce() {
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

    public int getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(int yVelocity) {
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
