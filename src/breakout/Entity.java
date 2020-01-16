package breakout;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/*
Currently unused because of unforeseen issues with moving Shapes.
Going to just do rectangular bricks for now, may refactor later.
Going to leave this code in here, just in case I do refactor later.
Will remove before final build. FIXME
 */

public class Entity extends ImageView {
    private double xVelocity;
    private double yVelocity;
    private Shape hitbox;

    public Entity(Image i) {
        this(i, "rect");
    }

    public Entity(Image i, String hitboxShape) {
        super(i);
        Bounds bounds = this.getBoundsInLocal();
        switch (hitboxShape) {
            case "rect":
                Rectangle rect = new Rectangle();
                rect.setWidth(bounds.getWidth());
                rect.setHeight(bounds.getHeight());
                hitbox = rect;
                break;
            case "circle":
                Circle circle = new Circle();
                circle.setRadius(Math.min(bounds.getHeight(), bounds.getWidth())/2);
                hitbox = circle;
                break;
            case "ellipse":
                Ellipse ellipse = new Ellipse();
                ellipse.setRadiusX(bounds.getWidth()/2);
                ellipse.setRadiusY(bounds.getHeight()/2);
                hitbox = ellipse;
                break;
        }
    }

    public Entity(Image i, Shape shape) {
        super(i);
        hitbox = shape;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public Shape getHitbox() {
        return hitbox;
    }

    public void update() {
        updateHitbox();
    }

    public void updateHitbox() {

    }
}
