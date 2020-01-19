package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CircleBrick extends Brick{
    public CircleBrick(double x, double y) { //FIXME constructors here similar to rectbrick ones, can I get it in super?
        this(x, y, Main.BRICK_HEALTH, Main.BRICK_RADIUS);
    }

    public CircleBrick(double x, double y, int health) {
        this(x, y, health, Main.BRICK_RADIUS);
    }

    public CircleBrick(double x, double y, Image i) {
        this(x,y);
        image = new ImagePattern(i);
        shape.setFill(image);
    }

    public CircleBrick(double x, double y, int health, double radius) {
        shape = new Circle(x, y, radius);
        shape.setFill(Color.ORANGE);
        hp = health;
    }

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
        hit(b.getDmg());
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
