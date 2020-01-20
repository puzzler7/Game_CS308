package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
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
    public static final double BALL_X = WIDTH/2;
    public static final double BALL_Y = HEIGHT * 5 / 8;
    public static final double BALL_X_VELOCITY = 0;
    public static final double BALL_Y_VELOCITY = 200;

    public static final int PADDLE_HEIGHT = 30;
    public static final int PADDLE_WIDTH = 150;

    public static final int BRICK_WIDTH_HEIGHT_RATIO = 3;
    public static final double BRICK_HEIGHT = 40;
    public static final int BRICK_HEALTH = 1;
    public static final double BRICK_RADIUS = 20;

    public static final String HEART_IMAGE = "gameheart.png";

    public static final Paint BACKGROUND = Color.BLUEVIOLET;
    public static final double POWERUP_WIDTH_HEIGHT_RATIO = 3;
    public static final double POWERUP_HEIGHT = 20;
    public static final double POWERUP_FALL = 400;
    public static final int POWERUP_DURATION = 300;

    public static final String BALL_IMAGE = "soccerball2.jpg";
    public static final double BUTTON_HEIGHT = 50;
    public static final double BUTTON_WIDTH_HEIGHT_RATIO = 4;
    public static final double BUTTON_SHRINK = 0.95;
    public static final double BOUNCER_SPEED = 1.05;
    public static final String BRICK_IMAGE_PATH_START = "OpenGameArt/PNG/";
    public static final String BRICK_IMAGE_PATH_END = "-Breakout-Tiles.png";
    private static final int SCORE_PER_FRAME = 1;
    public static Font MAIN_FONT = new Font("Courier New", 48);
    public static Font DISPLAY_FONT = new Font("Courier New", 40);
    public static Font RULES_FONT = new Font("Courier New", 20);
    public static final int STARTING_LIVES = 5;
    public static final int SCORE_PER_LIFE = 500;
    public static final int STARTING_SCORE = 500;
    public static final int MAX_LEVEL = 4;

    public static final String[] POWERUP_CODES = {"newlife", "+1000", "-1000"};
    public static final String POWERUP_PATH_START = "powerups/";
    public static final String POWERUP_PATH_END = ".png";
    public static final String BOUNCER_PATH = "bumper.png";
    public static final String PORTAL_PATH = "portal.png";


    private static ArrayList<Ball> balls = new ArrayList<>();
    private static ArrayList<Paddle> paddles = new ArrayList<>();
    private static ArrayList<Brick> bricks = new ArrayList<>();
    private static HashMap<String,Integer> powerupTracker = new HashMap<>();
    private static ArrayList<PowerUp> powerups = new ArrayList<>();
    private static Scene displayScene;
    private static String currentScene;
    private static int lives;
    private static ArrayList<Double> scores = new ArrayList<Double>();
    private static double score;
    private static Stage myStage;
    private static double speedFactor = 1;

    @Override
    public void start(Stage stage) throws Exception {
        myStage = stage;
        displayScene = SceneHandler.getMenuScene();
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
        elapsedTime *= speedFactor;
        stage.setScene(displayScene);
        if(currentScene.length()>5 && currentScene.substring(0,5).equals("level")) {
            levelUpdate(elapsedTime);
        }

    }

    private void levelUpdate(double elapsedTime) {
        boolean dead = false;
        for (Ball b : balls) {
            b.update(elapsedTime);
        }
        for (Paddle p : paddles) {
            p.update(elapsedTime);
            p.checkBallCollision(balls);
            p.checkPowerupCollision(powerups);
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
        }

        if (dead) {
            lives--;
            resetBall();
        }
        score -= SCORE_PER_FRAME*speedFactor;
        if (score <= 0) {
            score += SCORE_PER_LIFE*speedFactor;
            lives--;
        }
        SceneHandler.setScoreText(score);
        SceneHandler.getLifecount().setText("X" + lives);
        if (lives <= 0) {
            displayScene = SceneHandler.getDeathScene();
        }
        if (hasWon()) {
            storeScore();
            int currentLevel = Integer.parseInt(""+currentScene.charAt(currentScene.length()-1));
            if (currentLevel==MAX_LEVEL){ //FIXME needs comment
                setDisplayScene(SceneHandler.getVictoryScene());
            } else {
                setDisplayScene(SceneHandler.getLevelScene(currentLevel+1, balls, paddles, bricks, lives+1));
                //FIXME gain life on level finish
            }
        }
    }

    static void resetBall() {
        if (balls.size() > 1) {
            balls.subList(1, balls.size()).clear();
        }
        Ball b = balls.get(0);
        b.setX(Main.BALL_X);
        b.setY(Main.BALL_Y);
        b.setXVelocity(Main.BALL_X_VELOCITY);
        b.setYVelocity(Main.BALL_Y_VELOCITY);//FIXME copied from scene handler
        /*for (Paddle p: paddles) {
            p.setX(WIDTH/2);
        }*/ //FIXME removed because mouse controls paddle
    }

    public static void addPowerup(String id) {
        powerupTracker.put(id, POWERUP_DURATION);
    }

    private void handlePowerups() {
        for (String id: powerupTracker.keySet()){
            if (powerupTracker.get(id)>0){
                if (powerupTracker.get(id) == POWERUP_DURATION) {
                    activatePowerup(id);
                    continue;
                }
                powerupTracker.put(id, Math.max(powerupTracker.get(id)-1, 0));
                if (powerupTracker.get(id) == 0) {
                    deactivatePowerup(id);
                }
            }
        }
    }

    private void activatePowerup(String id) {
        if (id.equals(POWERUP_CODES[0])) { //newlife
            setLives(lives+1);
            powerupTracker.put(id,0);
        } else if (id.equals(POWERUP_CODES[1])) { //+1000
            score += 1000;
            powerupTracker.put(id,0);
        }
        else if (id.equals(POWERUP_CODES[2])) { //-1000
            score -= 1000;
            powerupTracker.put(id,0);
        }
    }

    private void deactivatePowerup(String id) {
        //FIXME
    }

    private boolean hasWon() {
        for (Brick brick: bricks) {
            if (brick.mustBeHit()&&!brick.isDead()) return false;
        }
        return true;
    }

    public static void clearObjects() {
        balls.clear();
        paddles.clear();
        bricks.clear();
        powerups.clear();
    }

    public static void setSpeedFactor(double factor) {
        speedFactor = Math.min(Math.max(0.1,factor),5);
    }

    public static double getSpeedFactor() {
        return speedFactor;
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

    public static ArrayList<PowerUp> getPowerups() {
        return powerups;
    }

    public static void setLives(int life) {
        lives = Math.min(99,life);
    }

    public static int getLives() {
        return lives;
    }

    public static void setScore(double sc) {
        score = sc;
    }

    public static double getScore() {
        return score;
    }

    public static void storeScore() {
        scores.add(score);
    }

    public static void clearScores() {
        scores.clear();
    }

    public static double getTotalScore() {
        double ret = 0;
        for (double i: scores) {
            ret += i;
        }
        return ret+lives*SCORE_PER_LIFE;
    }

    public static void setDisplayScene(Scene scene) {
        displayScene = scene;
    }

    public static Scene getDisplayScene() {
        return displayScene;
    }

    public static void setCurrentSceneString(String str) {
        currentScene = str;
    }

    public static Stage getStage() {
        return myStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
