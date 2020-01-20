package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SceneHandler {
    public static final KeyCode[] digitCodes = {KeyCode.DIGIT0, KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3,
            KeyCode.DIGIT4, KeyCode.DIGIT5, KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9};
    public static final KeyCode[] numPadCodes = {KeyCode.NUMPAD0, KeyCode.NUMPAD1, KeyCode.NUMPAD2, KeyCode.NUMPAD3,
            KeyCode.NUMPAD4, KeyCode.NUMPAD5, KeyCode.NUMPAD6, KeyCode.NUMPAD7, KeyCode.NUMPAD8, KeyCode.NUMPAD9};

    private static Text lifecount;
    private static Text scorecount;
    private static PushButton deathButton;
    private static PushButton rulesButton;
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
        Main.clearObjects();
        Main.setLives(lives);
        Group root = new Group();

        Rectangle theVoid = new Rectangle(Main.WIDTH, Main.VOID_SIZE);
        theVoid.setFill(Color.BLACK);
        theVoid.setX(0);
        theVoid.setY(Main.HEIGHT-Main.VOID_SIZE);
        root.getChildren().add(theVoid);

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

        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT, Main.BACKGROUND);
        scene.setOnMouseMoved(e -> handleMouseInput(e.getX(), e.getY()));
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        Main.setScore(Main.STARTING_SCORE);
        Main.setCurrentSceneString("level"+level);
        return scene;
    }

    private static void handleKeyInput(KeyCode code) {
        if (code == KeyCode.L) {
            Main.setLives(Main.getLives()+1);
        } else if (code == KeyCode.R) {
            Main.resetBall();
        } else if (code == KeyCode.MINUS) {
            Main.setSpeedFactor(Main.getSpeedFactor()-0.1);
        } else if (code == KeyCode.EQUALS) {
            Main.setSpeedFactor(Main.getSpeedFactor()+0.1);
        }
        else {
            checkNumKey(code);
        }
    }

    private static void checkNumKey(KeyCode code) {
        for (int i = 1; i < digitCodes.length; i++) {
            if (code == digitCodes[i] || code == numPadCodes[i]){
                if (i > Main.MAX_LEVEL) {
                    i = Main.MAX_LEVEL;
                }
                Main.setDisplayScene(getLevelScene(i, Main.getBalls(), Main.getPaddles(), Main.getBricks(), Main.getLives()));
                return;
            }
        }
        if (code == digitCodes[0] || code == numPadCodes[0]){
            Main.setDisplayScene(getLevelScene(Math.min(10, Main.MAX_LEVEL), Main.getBalls(), Main.getPaddles(),
                    Main.getBricks(), Main.getLives()));
        }
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
        HashMap<Character, TeleBrick> teleTracker = new HashMap<>();
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
                    case 'b':
                        double size = Integer.parseInt("" + code.charAt(1));
                        brick = new BouncerBrick((x+size/2) * brickWidth,
                                (y +size/2) * brickWidth / Main.BRICK_WIDTH_HEIGHT_RATIO,
                                0, brickWidth /Main.BRICK_WIDTH_HEIGHT_RATIO/ 2*size);
                        break;
                    case 't':
                        brick = new TeleBrick((x + .5) * brickWidth, (y + .5) * brickWidth / Main.BRICK_WIDTH_HEIGHT_RATIO,
                                1, brickWidth /Main.BRICK_WIDTH_HEIGHT_RATIO/ 2);
                        TeleBrick other = teleTracker.getOrDefault(code.charAt(1),null);
                        if (other != null) {
                            TeleBrick.pair((TeleBrick)brick, other);
                        }
                        else {
                            teleTracker.put(code.charAt(1),(TeleBrick)brick);
                        }
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
        return getEndScene("death");
    }

    public static Scene getVictoryScene() {
        return getEndScene("victory");
    }

    public static Scene getEndScene(String type) {
        Group root = new Group();
        Text deathText = null;
        if (type.equals("death")) { //FIXME magic val
            deathText = new Text("You have died.");
        } else if (type.equals("victory")) {
            deathText = new Text("You have won!");
        }

        deathText.setFont(Main.MAIN_FONT);
        deathText.setX(Main.WIDTH / 2 - deathText.getBoundsInLocal().getWidth() / 2);
        deathText.setY(Main.HEIGHT / 4);
        root.getChildren().add(deathText);

        Text endScore = new Text();
        endScore.setText("Final Score: "+(int)Main.getTotalScore());
        endScore.setFont(Main.MAIN_FONT);
        endScore.setTextAlignment(TextAlignment.CENTER);
        endScore.setWrappingWidth(400); //FIXME magic val
        endScore.setX(Main.WIDTH / 2 - endScore.getBoundsInLocal().getWidth() / 2);
        endScore.setY(Main.HEIGHT * 3/ 8);
        root.getChildren().add(endScore);

        deathButton = new PushButton(0,0,Main.BUTTON_HEIGHT, "Menu");
        deathButton.setCenterX(Main.WIDTH / 2);
        deathButton.setCenterY(Main.HEIGHT * 2/3);
        deathButton.setFill(Color.WHITE);
        root.getChildren().addAll(deathButton.getObjects());

        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT, Main.BACKGROUND);
        scene.setOnMouseMoved(e -> deathMouse(e.getX(), e.getY()));
        scene.setOnMouseClicked(e -> deathClick(e.getX(), e.getY()));
        Main.setCurrentSceneString(type);
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

        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT, Main.BACKGROUND);
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
        Group root = new Group();
        Text rulesText = new Text();
        rulesText.setWrappingWidth(Main.WIDTH-50); //FIXME magic val
        rulesText.setTextAlignment(TextAlignment.CENTER);
        rulesText.setText("Move the mouse to control the paddle. " +
                "The faster you hit the ball with the paddle, " +
                "the faster it moves. \n\n" +
                "Bounce the ball off bricks to break them. " +
                "Collect the powerups that fall, " +
                "but avoid the harmful ones! ");
        rulesText.setFont(Main.RULES_FONT);
        rulesText.setX(Main.WIDTH / 2 - rulesText.getBoundsInLocal().getWidth() / 2);
        rulesText.setY(Main.HEIGHT / 8);
        root.getChildren().add(rulesText);

        rulesButton = new PushButton(0,0,Main.BUTTON_HEIGHT, "Menu");
        rulesButton.setCenterX(Main.WIDTH / 2);
        rulesButton.setCenterY(Main.HEIGHT * 3/4);
        rulesButton.setFill(Color.WHITE);
        root.getChildren().addAll(rulesButton.getObjects());

        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT, Main.BACKGROUND);
        scene.setOnMouseMoved(e -> rulesMouse(e.getX(), e.getY()));
        scene.setOnMouseClicked(e -> rulesClick(e.getX(), e.getY()));
        Main.setCurrentSceneString("rules");
        return scene;
    }

    static void rulesMouse(double x, double y) {
        if (rulesButton.contains(x, y)) {
            rulesButton.onMouseover();
        } else {
            rulesButton.onMouseoff();
        }
    }

    static void rulesClick(double x, double y) {
        if (rulesButton.contains(x, y)) {
            Main.setDisplayScene(getMenuScene());
        }
    }

    public static Text getLifecount() {
        return lifecount;
    }

    public static Text getScoreText() {
        return scorecount;
    }

    public static void setScoreText(double sco) {
        int sc = (int)sco;
        scorecount.setText("Score:"+sc);
        scorecount.setX(Main.WIDTH-scorecount.getBoundsInLocal().getWidth());
        scorecount.setY(Main.HEIGHT-Main.VOID_SIZE + 40); //FIXME magic val
    }

    public static Rectangle getDeathButton() {
        return deathButton;
    }
}
