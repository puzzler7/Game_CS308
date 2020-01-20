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
        setImage(i);
    }

    public RectBrick(double x, double y, int health, double height) {
        shape = new Rectangle(height*Main.BRICK_WIDTH_HEIGHT_RATIO, height);
        shape.setFill(Color.ORANGE);
        hp = health;
        setCenterX(x);
        setCenterY(y);
        updateImage();
    }

    @Override
    public void onHit(Ball b) {
        if (b.getCenterX()<getCenterX()-getWidth()/2 || b.getCenterX()>getCenterX()+getWidth()/2) {
            b.setXNegate(-1);
        }
        if (b.getCenterY()<getCenterY()-getHeight()/2 || b.getCenterY()>getCenterY()+getHeight()/2) {
            b.setYNegate(-1);
        }
        super.onHit(b);
    }

    @Override
    void setCenterX(double x) {
        ((Rectangle)shape).setX(x-((Rectangle) shape).getWidth()/2);
    }

    @Override
    void setCenterY(double y) {
        ((Rectangle)shape).setY(y-((Rectangle) shape).getHeight()/2);
    }

    @Override
    double getCenterX() {
        return ((Rectangle)shape).getX()+((Rectangle)shape).getWidth()/2;
    }

    @Override
    double getCenterY() {
        return ((Rectangle)shape).getY()+((Rectangle)shape).getHeight()/2;
    }

    double getWidth() {
        return ((Rectangle)shape).getWidth();
    }

    double getHeight() {
        return ((Rectangle)shape).getHeight();
    }
}
