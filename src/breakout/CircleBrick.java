package breakout;

import javafx.scene.shape.Circle;

/**
 * A circular brick that extends brick.
 *
 * Assumes that all inputs are positive and valid.
 *
 * @author Maverick Chung mc608
 */
public class CircleBrick extends Brick{
    public CircleBrick(double x, double y) {
        this(x, y, Main.BRICK_HEALTH, Main.BRICK_RADIUS);
    }

    public CircleBrick(double x, double y, int health) {
        this(x, y, health, Main.BRICK_RADIUS);
    }

    public CircleBrick(double x, double y, int health, double radius) {
        shape = new Circle(x, y, radius);
        hp = health;
        updateImage();
    }

    /**
     * Causes the ball to bounce back radially outwards from the brick.
     * @param b Ball that hit this brick
     */
    @Override
    public void onHit(Ball b) {
        double mag = b.getXVelocity()*b.getXVelocity()+b.getYVelocity()*b.getYVelocity();
        mag = Math.pow(mag, .5);
        double newX = b.getX()-getCenterX();
        double newY = b.getY()-getCenterY();
        double norm = newX*newX+newY*newY;
        norm = Math.pow(norm, .5);
        newX *= mag/norm;
        newY *= mag/norm;
        b.setXVelocity(newX);
        b.setYVelocity(newY);
        super.onHit(b);
    }

    @Override
    public void setCenterX(double x) {
        ((Circle)shape).setCenterX(x);
    }

    @Override
    public void setCenterY(double y) {
        ((Circle)shape).setCenterY(y);
    }

    @Override
    double getCenterX() {
        return ((Circle)shape).getCenterX();
    }

    @Override
    double getCenterY() {
        return ((Circle)shape).getCenterY();
    }
}