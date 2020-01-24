package breakout;

import javafx.scene.image.Image;

/**
 * A indestructible circular bouncer brick.
 *
 * @author Maverick Chung mc608
 */
public class BouncerBrick extends CircleBrick {
    public BouncerBrick(double x, double y) {
        this(x, y, 999, Main.BRICK_RADIUS);
    }

    public BouncerBrick(double x, double y, int health, double radius) {
        super(x, y, 999, radius);
        mustBeHit = false;
        Image i = new Image(BouncerBrick.class.getClassLoader().getResourceAsStream(Main.BOUNCER_PATH));
        setImage(i);
    }

    /**
     * In addition to bouncing the ball away radially, the bumper also speeds up the ball slightly.
     * @param b Ball that hit this brick
     */
    @Override
    public void onHit(Ball b) {
        super.onHit(b);
        b.setXVelocity(b.getXVelocity()*Main.BOUNCER_SPEED);
        b.setYVelocity(b.getYVelocity()*Main.BOUNCER_SPEED);
    }

    /**
     * Overrides the parent update image so that the bumper image isn't removed on hit.
     */
    @Override
    protected void updateImage() {

    }
}
