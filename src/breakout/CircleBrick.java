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
    void setCenterX(double x) {
        ((Circle)shape).setCenterX(x);
    }

    @Override
    void setCenterY(double y) {
        ((Circle)shape).setCenterY(y);
    }
}
