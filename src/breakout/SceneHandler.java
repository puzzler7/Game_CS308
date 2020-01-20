package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SceneHandler {
    private static Text lifecount;
    private static Text scorecount;
    private static PushButton deathButton;
    private static Image heartImage;
    private static Image ballImage;
    private static ArrayList<PushButton> menuButtons = new ArrayList<>();

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
        balls.clear();
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
        lifecount.setFont(Main.DISPLAY_FONT);
        root.getChildren().add(lifecount);

        scorecount = new Text();
        setScoreText(Main.STARTING_SCORE);
        scorecount.setX(Main.WIDTH-scorecount.getBoundsInLocal().getWidth());
        scorecount.setY(Main.HEIGHT-Main.VOID_SIZE + 40); //FIXME magic val
        scorecount.setFill(Color.WHITE);
        scorecount.setFont(Main.DISPLAY_FONT);
        root.getChildren().add(scorecount);

        Image ballImage = new Image(SceneHandler.class.getClassLoader().getResourceAsStream(Main.BALL_IMAGE));
        Ball b = new Ball(ballImage);
        b.setX(Main.BALL_X);
        b.setY(Main.BALL_Y);
        b.setXVelocity(Main.BALL_X_VELOCITY);
        b.setYVelocity(Main.BALL_Y_VELOCITY);
        root.getChildren().add(b);
        balls.add(b);

        Paddle p = new Paddle();
        root.getChildren().add(p);
        paddles.add(p);

        readBricks("levels/level" + level + ".txt", bricks, root);

        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT, Main.background1);
        scene.setOnMouseMoved(e -> handleMouseInput(e.getX(), e.getY()));
        Main.setScore(Main.STARTING_SCORE);
        Main.setCurrentSceneString("level"+level);
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
        deathText.setFont(Main.MAIN_FONT);
        deathText.setX(Main.WIDTH / 2 - deathText.getBoundsInLocal().getWidth() / 2);
        deathText.setY(Main.HEIGHT / 4);
        root.getChildren().add(deathText);

        //FIXME score indicator

        deathButton = new PushButton(0,0,Main.BUTTON_HEIGHT, "Menu");
        deathButton.setCenterX(Main.WIDTH / 2);
        deathButton.setCenterY(Main.HEIGHT / 2);
        deathButton.setFill(Color.WHITE);
        root.getChildren().addAll(deathButton.getObjects());

        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT, Main.background1);
        scene.setOnMouseMoved(e -> deathMouse(e.getX(), e.getY()));
        scene.setOnMouseClicked(e -> deathClick(e.getX(), e.getY()));
        Main.setCurrentSceneString("death");
        Main.clearScores();
        return scene;
    }

    static void deathMouse(double x, double y) {
        if (deathButton.contains(x, y)) {
            deathButton.onMouseover();
        } else {
            deathButton.onMouseoff();
        }
    }

    static void deathClick(double x, double y) {
        if (deathButton.contains(x, y)) {
            Main.setDisplayScene(getMenuScene());
        }
    }

    public static Scene getMenuScene() {
        Main.setLives(Integer.MAX_VALUE); //FIXME
        menuButtons.clear();
        Group root = new Group();
        Text titleText = new Text("Speedy Bricks");

        titleText.setFont(Main.MAIN_FONT);
        titleText.setX(Main.WIDTH / 2 - titleText.getBoundsInLocal().getWidth() / 2);
        titleText.setY(Main.HEIGHT / 4);
        root.getChildren().add(titleText);

        PushButton startButton = new PushButton(0,0,Main.BUTTON_HEIGHT, "Start");
        startButton.setCenterX(Main.WIDTH / 2);
        startButton.setCenterY(Main.HEIGHT / 2);
        root.getChildren().addAll(startButton.getObjects());
        menuButtons.add(startButton);

        PushButton rulesButton = new PushButton(0,0,Main.BUTTON_HEIGHT, "Rules");
        rulesButton.setCenterX(Main.WIDTH / 2);
        rulesButton.setCenterY(Main.HEIGHT *5/8);
        root.getChildren().addAll(rulesButton.getObjects());
        menuButtons.add(rulesButton);

        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT, Main.background1);
        scene.setOnMouseMoved(e -> menuMouse(e.getX(), e.getY()));
        scene.setOnMouseClicked(e -> menuClick(e.getX(), e.getY()));
        Main.setCurrentSceneString("menu");
        return scene;
    }

    static void menuMouse(double x, double y) {
        for (PushButton button: menuButtons) {
            if (button.contains(x, y)) {
                button.onMouseover();
            } else {
                button.onMouseoff();
            }
        }
    }

    static void menuClick(double x, double y) {
        if (menuButtons.get(0).contains(x, y)) {
            Main.setDisplayScene(getLevelScene(1, Main.getBalls(), Main.getPaddles(), Main.getBricks(),
                    Main.STARTING_LIVES)); //FIXME
        } else if (menuButtons.get(1).contains(x,y)) {
            Main.setDisplayScene(getRulesScene());
        }
    }

    private static Scene getRulesScene() {
        Main.setCurrentSceneString("rules");
        return null;
    }


    public Scene getVictoryScene() {
        Main.setCurrentSceneString("victory");
        return null;
    }

    public static Text getLifecount() {
        return lifecount;
    }

    public static Text getScoreText() {
        return scorecount;
    }

    public static void setScoreText(int sc) {
        scorecount.setText("Score: "+sc);
        scorecount.setX(Main.WIDTH-scorecount.getBoundsInLocal().getWidth());
        scorecount.setY(Main.HEIGHT-Main.VOID_SIZE + 40); //FIXME magic val
    }

    public static Rectangle getDeathButton() {
        return deathButton;
    }
}
