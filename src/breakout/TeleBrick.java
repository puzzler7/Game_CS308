package breakout;

/**
 * A circular brick that teleports the ball to its paired brick when hit (like a portal).
 *
 * Assumes the the portal has a non null pair, and that its pair's pair is this object.
 *
 * @author Maverick Chung mc608
 */
public class TeleBrick extends CircleBrick {
    private TeleBrick portal;

    public TeleBrick(double x, double y) {
        super(x, y);
    }

    public TeleBrick(double x, double y, int health, double radius) {
        super(x, y, health, radius);
    }

    /**
     * static method to pair two portals
     * @param a portal 1 to be paired
     * @param b portal 2 to be paired
     */
    public static void pair(TeleBrick a, TeleBrick b) {
        a.portal = b;
        b.portal = a;
    }

    /**
     * This brick and its pair die, and the ball is teleported to the location of the pair.
     * @param b Ball that hit this brick
     */
    @Override
    public void onHit(Ball b) {
        die();
        b.setCenterX(portal.getCenterX());
        b.setCenterY(portal.getCenterY());
        portal.die();
    }

    /**
     * Sets the image filepath to the correct picture so that Brick.updateImage() has the right image
     */
    @Override
    protected String getImageString(){
        return Main.PORTAL_PATH;
    }
}