package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class BouncerBrick extends CircleBrick {
    public BouncerBrick(double x, double y) {
        this(x, y, 999, Main.BRICK_RADIUS);
    }

    public BouncerBrick(double x, double y, Image i) {
        super(x, y, i);
        hp = 999;
        mustBeHit = false;
    }

    public BouncerBrick(double x, double y, int health, double radius) {
        super(x, y, 999, radius);
        mustBeHit = false;
        shape.setFill(Color.RED);
    }

    @Override
    public void onHit(Ball b) {
        super.onHit(b);
        b.setXVelocity(b.getXVelocity()*Main.BOUNCER_SPEED);
        b.setYVelocity(b.getYVelocity()*Main.BOUNCER_SPEED);
    }

    @Override
    protected void updateImage() {

    }
}
