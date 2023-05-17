package rpg;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import rpg.entities.Player;

public class RPG extends Application {
  private static final Color BACKGROUND_COLOR = Color.BLUE;
  private static final Color PLAYER_COLOR = Color.RED;
  private static final double FPS = 24;
  private static final double PLAYER_HEIGHT = 50;
  private static final double PLAYER_WIDTH = 50;
  private static final double PLAYER_X = 100;
  private static final double PLAYER_Y = 100;
  private static final int SCENE_HEIGHT = 800;
  private static final int SCENE_WIDTH = 800;

  private static final double MS_PER_UPDATE = 1000 / FPS;

  private Group root = new Group();
  private Player player = new Player(PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_COLOR);
  private Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, BACKGROUND_COLOR);
  private double lag = 0;
  private long previousTime = 0;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    this.root.getChildren().add(this.player);

    primaryStage.setTitle("RPG");
    primaryStage.setScene(this.scene);
    primaryStage.show();

    AnimationTimer gameLoop =
        new AnimationTimer() {
          @Override
          public void handle(long currentTime) {
            double elapsedMilliseconds = (currentTime - previousTime) / 1e6;
            previousTime = currentTime;
            lag += elapsedMilliseconds;

            processInput(elapsedMilliseconds);

            while (lag >= MS_PER_UPDATE) {
              update(elapsedMilliseconds);
              lag -= MS_PER_UPDATE;
            }

            render(elapsedMilliseconds);
          }
        };

    gameLoop.start();
  }

  private void processInput(double elapsedMilliseconds) {}

  private void render(double elapsedMilliseconds) {}

  private void update(double elapsedMilliseconds) {}
}
