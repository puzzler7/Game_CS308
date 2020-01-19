package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SceneHandler {
    private static Text lifecount;
    private static Rectangle deathButton;
    private static Image heartImage;
    private static Image ballImage;

    public static ArrayList<String> readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        ArrayList<String> ret = new ArrayList<>();
        while (sc.hasNext()) {
            ret.add(sc.nextLine());
        }
        return ret;
    }

    public static Scene getLevelScene(int level, ArrayList<Ball> balls, ArrayList<Paddle> paddles, ArrayList<Brick> bricks, int lives) {
        //FIXME implement level selection - currently, level param is ignored

        balls.clear(); //FIXME better object clearing
        paddles.clear();
        bricks.clear();
        Main.setLives(lives);
        Group root = new Group();

        Image heartImage = new Image(SceneHandler.class.getClassLoader().getResourceAsStream(Main.HEART_IMAGE));
        ImageView heart = new ImageView(heartImage);
        heart.setPreserveRatio(true);
        heart.setFitHeight(Main.VOID_SIZE);
        heart.setX(0);
        heart.setY(Main.HEIGHT - Main.VOID_SIZE);
        root.getChildren().add(heart);

        lifecount = new Text("x" + lives);
        lifecount.setX(heart.getBoundsInLocal().getWidth());
        lifecount.setY(Main.HEIGHT - Main.VOID_SIZE + 40); //FIXME magic val
        lifecount.setFill(Color.WHITE);
        Font lifefont = new Font("Courier New", 48); //FIXME magic val
        lifecount.setFont(lifefont);
        root.getChildren().add(lifecount);

        Image ballImage = new Image(SceneHandler.class.getClassLoader().getResourceAsStream(Main.BALL_IMAGE));
        Ball b = new Ball(ballImage);
        b.setXVelocity(0);
        b.setYVelocity(250); //FIXME magic val
        root.getChildren().add(b);
        balls.add(b);

        Paddle p = new Paddle();
        root.getChildren().add(p);
        paddles.add(p);

        readBricks("levels/level" + level + ".txt", bricks, root);

        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT, Main.background1);
        scene.setOnMouseMoved(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    static void handleMouseInput(double x, double y) {
        ArrayList<Paddle> paddles = Main.getPaddles();
        for (Paddle p : paddles) {
            p.queueNewX(x);
        }
    }

    public static void readBricks(String path, ArrayList<Brick> bricks, Group root) {
        ArrayList<String> brickCodes = new ArrayList<>();
        try {
            brickCodes = readFile(path);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("bad file");
        }

        int brickWidth = Main.WIDTH / brickCodes.get(0).split(" ").length;
        brickCodes.remove(0);
        double x = 0;
        double y = 0;
        for (String brickCode : brickCodes) {
            x = 0;
            for (String code : brickCode.split(" ")) {
                Brick brick = null;
                switch (code.charAt(0)) {
                    case 'o':
                        brick = new CircleBrick((x + .5) * brickWidth, (y + .5) * brickWidth / Main.BRICK_WIDTH_HEIGHT_RATIO,
                                Integer.parseInt("" + code.charAt(1)), brickWidth /Main.BRICK_WIDTH_HEIGHT_RATIO/ 2);
                        break;
                    case 's':
                        brick = new RectBrick((x + .5) * brickWidth, (y + .5) * brickWidth / Main.BRICK_WIDTH_HEIGHT_RATIO,
                                Integer.parseInt("" + code.charAt(1)), brickWidth / Main.BRICK_WIDTH_HEIGHT_RATIO);
                        break;
                }
                if (brick != null) {
                    root.getChildren().add(brick.getShape());
                    bricks.add(brick);
                }
                x++;
            }
            y++;
        }
    }

    public static Scene getDeathScene() {
        Group root = new Group();
        Text deathText = new Text("You have died.");
        Font lifefont = new Font("Courier New", 48); //FIXME magic val
        deathText.setFont(lifefont);
        deathText.setX(Main.WIDTH / 2 - deathText.getBoundsInLocal().getWidth() / 2);
        deathText.setY(Main.HEIGHT / 4);
        root.getChildren().add(deathText);

        deathButton = new Rectangle(70, 20);
        deathButton.setArcHeight(5);
        deathButton.setArcWidth(5);
        deathButton.setX(Main.WIDTH / 2 - deathButton.getWidth() / 2);
        deathButton.setY(Main.HEIGHT / 2 - deathButton.getHeight() / 2);
        deathButton.setFill(Color.WHITE);
        root.getChildren().add(deathButton);

        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT, Main.background1);
        scene.setOnMouseMoved(e -> deathMouse(e.getX(), e.getY()));
        scene.setOnMouseClicked(e -> deathClick(e.getX(), e.getY()));
        return scene;
    }

    static void deathMouse(double x, double y) {
        if (deathButton.contains(x, y)) {
            deathButton.setHeight(15);
            deathButton.setWidth(60);//FIXME magic val all of this
        } else {
            deathButton.setHeight(20);
            deathButton.setWidth(70);
        }
        deathButton.setX(Main.WIDTH / 2 - deathButton.getWidth() / 2);
        deathButton.setY(Main.HEIGHT / 2 - deathButton.getHeight() / 2);
    }

    static void deathClick(double x, double y) {
        if (deathButton.contains(x, y)) {
            Main.setDisplayScene(getLevelScene(1, Main.getBalls(), Main.getPaddles(), Main.getBricks(), 1)); //FIXME
            Main.setLives(1);//FIXME
        }
    }

    public static Scene getMenuScene() {
        return null; //FIXME
    }
}
