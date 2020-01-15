package breakout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 800;
    public static final int[] SIZE = {WIDTH, HEIGHT};

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public Scene getMenuScene() {
        return null; //FIXME
    }

    public Scene getLevelScene(int level) {
        //FIXME implement level selection - currently, level param is ignored

    }

    public static void main (String[] args) {
        launch(args);
    }


}
