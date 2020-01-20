package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Random;

public abstract class Brick {
    protected Shape shape;
    protected int hp;
    protected ImagePattern image;
    protected boolean mustBeHit = true;
    protected boolean dead = false;
    protected int score = 100;
    protected double powerupChance = .5;

    abstract void setCenterX(double x);
    abstract void setCenterY(double y);
    abstract double getCenterX();
    abstract double getCenterY();

    public void checkBallCollision(ArrayList<Ball> balls) {
        for (Ball b : balls) {
            Shape intersection = Shape.intersect(shape, b);
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                onHit(b);
            }
        }
    }

    public void setImage(Image i) {
        image = new ImagePattern(i);
        shape.setFill(image);
    }

    public void update(){}

    protected void onHit(Ball b) {
        checkForPowerup();
        Main.setScore((int)(Main.getScore()+getScore()*Main.getSpeedFactor()));
        hit(b.getDmg());
        updateImage();
    }

    protected void updateImage() {
        try {
            if (hp > 0) {
                Image i = new Image(Brick.class.getClassLoader().getResourceAsStream(Main.BRICK_IMAGE_PATH_START
                        + hp + Main.BRICK_IMAGE_PATH_END));

                setImage(i);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    protected void checkForPowerup(){
        Random rand = new Random();
        if (rand.nextDouble()<powerupChance) {
            PowerUp.generateRandomPowerup(getCenterX(), getCenterY());
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHP(int health) {
        hp = health;
    }

    public int getScore() {
        return score;
    }

    public Shape getShape() {
        return shape;
    }

    public void hit(int dmg) {
        hp -= dmg;
        if (hp<=0) die();
    }

    public void die() {
        setCenterX(-Main.WIDTH*2); //moves offscreen
        setCenterY(-Main.HEIGHT*2);
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean mustBeHit() {
        return mustBeHit;
    }

    public void setMustBeHit(boolean hit) {
        mustBeHit = hit;
    }
}
