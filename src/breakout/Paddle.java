package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.*;

public class Paddle extends Rectangle {

    public Paddle()  {
        this(Main.PADDLE_WIDTH);
    }

    public Paddle(double width) {
        this(width, Main.PADDLE_HEIGHT);
    }

    public Paddle(double width, double height) {
        this(width, height, Main.HEIGHT - Main.VOID_SIZE-height);
    }

    public Paddle(double width, double height, double y) {
        super(Main.WIDTH/2-width/2, y, width, height);
        setFill(Color.PURPLE);
    }

    public void update(double x) {
        setX(x-getWidth()/2);
        if (getX()<0){
            setX(0);
        }
        if (getX()>Main.WIDTH-getWidth()) {
            setX(Main.WIDTH-getWidth());
        }

    }

    public void checkBallCollision(ArrayList<Ball> balls){
        for (Ball b: balls) {
            Shape intersection = Shape.intersect(this, b);
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                b.setYVelocity(-b.getYVelocity());
            }
        }
    }

}
