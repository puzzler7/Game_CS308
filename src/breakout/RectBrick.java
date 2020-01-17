package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class RectBrick extends Rectangle {
    private int hp;

    //Idea: Brick superclass (interface?) with a shape instance var and image/frame handling

    public RectBrick(double x, double y) {
        this(x, y, Main.BRICK_HEALTH, Main.BRICK_HEIGHT);
    }

    public RectBrick(double x, double y, int health) {
        this(x, y, health, Main.BRICK_HEIGHT);
    }

    public RectBrick(double x, double y, int health, double height) {
        super(height*Main.BRICK_WIDTH_HEIGHT_RATIO, height);
        setFill(Color.ORANGE);
        hp = health;
        setX(x);
        setY(y);
    }

    public boolean checkBallCollision(ArrayList<Ball> balls) {
        boolean ret = false;
        for (Ball b : balls) {
            Shape intersection = Shape.intersect(this, b);
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                b.setYVelocity(-b.getYVelocity());//FIXME side bounce
                ret = hit(1); //FIXME dmg numbers vary by ball?
            }
        }
        return ret;
    }

    public boolean hit(int dmg) {
        hp -= dmg;
        return hp<=0;
    }

    public void die() {
        setX(-999);
        setY(-999);
    }
}
