package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class PowerUp extends RectBrick{
    private String id;

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
    }

    @Override
    public void checkBallCollision(ArrayList<Ball> balls) {
        return;
    }
}
