package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class Main extends Application {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final int[] SIZE = {WIDTH, HEIGHT};
    public static final int VOID_SIZE = 50;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLI_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    public static final int BALL_SIZE = 20;

    public static final int PADDLE_HEIGHT = 50;
    public static final int PADDLE_WIDTH = 75;

    public static final int BRICK_WIDTH_HEIGHT_RATIO = 2;
    public static final double BRICK_HEIGHT = 20;
    public static final int BRICK_HEALTH = 1;
    public static final double BRICK_RADIUS = 20;

    public static final String HEART_IMAGE = "gameheart.png";

    public static final Paint background1 = Color.BLUEVIOLET;
    public static final Paint background2 = Color.BLACK;

    private static final String BALL_IMAGE = "soccerball2.jpg";

    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Paddle> paddles = new ArrayList<>();
    private ArrayList<Brick> bricks = new ArrayList<>();
    private int lives = 10;
    private Text lifecount;

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = getLevelScene(0);
        stage.setScene(scene);
        stage.setTitle("My first test!");
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.millis(MILLI_DELAY), e -> update(SECOND_DELAY, scene, stage));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public void update(double elapsedTime, Scene scene, Stage stage) {
        boolean dead = false;
        for (Ball b : balls) {
            b.update(elapsedTime);
        }
        for (Paddle p : paddles) {
            p.update(elapsedTime);
            p.checkBallCollision(balls);
            //idea: satisfaction paddle bounce
        }
        for (Brick brick : bricks) {
            brick.checkBallCollision(balls);
        }
        for (Ball b : balls) {
            dead = b.checkBounce();
            //System.out.println(b.getY());
        }

        if (dead) {
            //scene.setFill(background2);
            lives--;
        } else {
            scene.setFill(background1);
        }
        lifecount.setText("X" + lives);
    }

    public Scene getMenuScene() {
        return null; //FIXME
    }

    public Scene getLevelScene(int level) {
        //FIXME implement level selection - currently, level param is ignored
        Group root = new Group();
        Image heartImage = new Image(this.getClass().getClassLoader().getResourceAsStream(HEART_IMAGE));
        ImageView heart = new ImageView(heartImage);
        heart.setPreserveRatio(true);
        heart.setFitHeight(VOID_SIZE);
        heart.setX(0);
        heart.setY(HEIGHT - VOID_SIZE);
        root.getChildren().add(heart);
        lifecount = new Text("x" + lives);
        lifecount.setX(heart.getBoundsInLocal().getWidth());
        lifecount.setY(HEIGHT - VOID_SIZE + 40); //FIXME magic val
        lifecount.setFill(Color.WHITE);
        Font lifefont = new Font("Courier New", 48); //FIXME magic val
        lifecount.setFont(lifefont);
        root.getChildren().add(lifecount);

        Image ballImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
        Ball b = new Ball(ballImage);
        b.setXVelocity(50);
        b.setYVelocity(200);
        root.getChildren().add(b);
        balls.add(b);
        Paddle p = new Paddle();
        root.getChildren().add(p);
        paddles.add(p);

        CircleBrick brick = new CircleBrick(WIDTH / 2, HEIGHT / 4);
        bricks.add(brick);
        root.getChildren().add(brick.shape);

        Scene scene = new Scene(root, WIDTH, HEIGHT, background1);
        scene.setOnMouseMoved(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    private void handleMouseInput(double x, double y) {
        for (Paddle p : paddles) {
            p.queueNewX(x);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
