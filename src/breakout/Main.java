package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;


public class Main extends Application {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final int[] SIZE = {WIDTH, HEIGHT};
    public static final int VOID_SIZE = 100;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLI_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint background1 = Color.BLUEVIOLET;
    public static final Paint background2 = Color.BLACK;

    private ArrayList<Ball> balls = new ArrayList<>();
    private int lives = 10;

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = getLevelScene(0);
        stage.setScene(scene);
        stage.setTitle("My first test!");
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.millis(MILLI_DELAY), e -> update(SECOND_DELAY, scene));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public void update(double elapsedTime, Scene scene) {
        boolean dead =  false;
        for (Ball b: balls) {
            dead = b.update(elapsedTime);
        }
        if (dead){
            scene.setFill(background2);
        } else {
            //scene.setFill(background1);
        }
    }

    public Scene getMenuScene() {
        return null; //FIXME
    }

    public Scene getLevelScene(int level) {
        //FIXME implement level selection - currently, level param is ignored
        Group root = new Group();
        Ball b = new Ball(10);
        b.setX(WIDTH/2);
        b.setY(HEIGHT/2);
        b.setXVelocity(50);
        b.setYVelocity(600);
        b.setBounceX(WIDTH);
        b.setBounceY(HEIGHT);
        root.getChildren().add(b);
        balls.add(b);
        Scene scene = new Scene(root, WIDTH, HEIGHT, background1);
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
