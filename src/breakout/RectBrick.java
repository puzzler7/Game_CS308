package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class RectBrick extends Brick {
    //Idea: Brick superclass (interface?) with a shape instance var and image/frame handling

    public RectBrick(double x, double y) {
        this(x, y, Main.BRICK_HEALTH, Main.BRICK_HEIGHT);
    }

    public RectBrick(double x, double y, int health) {
        this(x, y, health, Main.BRICK_HEIGHT);
    }

    public RectBrick(double x, double y, Image i) {
        this(x,y);
        image = new ImagePattern(i);
        shape.setFill(image);
    }

    public RectBrick(double x, double y, int health, double height) {
        shape = new Rectangle(height*Main.BRICK_WIDTH_HEIGHT_RATIO, height);
        shape.setFill(Color.ORANGE);
        hp = health;
        setCenterX(x);
        setCenterY(y);
    }

    @Override
    void setCenterX(double x) {
        ((Rectangle)shape).setX(x-((Rectangle) shape).getWidth()/2);
    }

    @Override
    void setCenterY(double y) {
        ((Rectangle)shape).setY(y-((Rectangle) shape).getHeight()/2);
    }
}
