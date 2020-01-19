package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;


public class Main extends Application {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final int[] SIZE = {WIDTH, HEIGHT};
    public static final int VOID_SIZE = 50;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLI_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    public static final int BALL_SIZE = 20;

    public static final int PADDLE_HEIGHT = 30;
    public static final int PADDLE_WIDTH = 150;

    public static final int BRICK_WIDTH_HEIGHT_RATIO = 2;
    public static final double BRICK_HEIGHT = 40;
    public static final int BRICK_HEALTH = 1;
    public static final double BRICK_RADIUS = 20;

    public static final String HEART_IMAGE = "gameheart.png";

    public static final Paint background1 = Color.BLUEVIOLET;
    public static final Paint background2 = Color.BLACK;
    public static final double POWERUP_WIDTH_HEIGHT_RATIO = 2;
    public static final double POWERUP_HEIGHT = 20;
    public static final double POWERUP_FALL = 400;
    public static final int POWERUP_DURATION = 300;

    public static final String BALL_IMAGE = "soccerball2.jpg";
    public static final double BUTTON_HEIGHT = 50;
    public static final double BUTTON_WIDTH_HEIGHT_RATIO = 4;
    public static final double BUTTON_SHRINK = 0.95;


    private static ArrayList<Ball> balls = new ArrayList<>();
    private static ArrayList<Paddle> paddles = new ArrayList<>();
    private static ArrayList<Brick> bricks = new ArrayList<>();
    private static HashMap<String,Integer> powerupTracker = new HashMap<>();
    private static ArrayList<PowerUp> powerups = new ArrayList<>();
    private static Scene displayScene;
    private static int lives = 1;


    @Override
    public void start(Stage stage) throws Exception {
        displayScene = SceneHandler.getLevelScene(1,balls, paddles, bricks, 1);
        stage.setScene(displayScene);
        stage.setTitle("My first test!");
        stage.show();
        KeyFrame frame = new KeyFrame(Duration.millis(MILLI_DELAY), e -> update(SECOND_DELAY, stage));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public void update(double elapsedTime, Stage stage) {
        stage.setScene(displayScene);
        //FIXME stop background physics
        boolean dead = false;
        for (Ball b : balls) {
            b.update(elapsedTime);
        }
        for (Paddle p : paddles) {
            p.update(elapsedTime);
            p.checkBallCollision(balls);
            //idea: satisfaction paddle bounce
        }
        for (PowerUp p: powerups) {
            p.update(elapsedTime);
            String code = p.checkPaddleCollision(paddles);
            if (!code.equals("none")){
                int duration = Integer.parseInt(code.split(" ")[1]);
                String id = code.split(" ")[0];
                powerupTracker.put(id, duration);
            }
        }
        handlePowerups();
        for (Brick brick : bricks) {
            brick.checkBallCollision(balls);
        }
        for (Ball b : balls) {
            dead = b.checkBounce();
            //System.out.println(b.getY());
        }

        if (dead) {
            //displayScene.setFill(background2);
            lives--;
        } else {
            displayScene.setFill(background1);
        }
        SceneHandler.getLifecount().setText("X" + lives);
        if (lives <= 0) {
            displayScene = SceneHandler.getDeathScene();
            lives = 3; //FIXME magic val
        }
        //System.out.println(bricks);
    }

    private void handlePowerups() {
        for (String id: powerupTracker.keySet()){
            if (powerupTracker.get(id)>0){
                powerupTracker.put(id, Math.min(powerupTracker.get(id)-1, 0));
                activatePowerup(id);
            }
        }
    }

    private void activatePowerup(String id) {
        //FIXME
    }

    private boolean hasWon() {
        for (Brick brick: bricks) {
            if (brick.mustBeHit()&&!brick.isDead()) return false;
        }
        return true;
    }

    public static ArrayList<Ball> getBalls() {
        return balls;
    }

    public static ArrayList<Paddle> getPaddles(){
        return paddles;
    }

    public static ArrayList<Brick> getBricks() {
        return bricks;
    }

    public static void setLives(int life) {
        lives = life;
    }

    public static void setDisplayScene(Scene scene) {
        displayScene = scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
