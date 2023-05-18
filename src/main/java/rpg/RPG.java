package rpg;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import rpg.command.Command;
import rpg.command.PlayerMoveDownCommand;
import rpg.command.PlayerMoveLeftCommand;
import rpg.command.PlayerMoveRightCommand;
import rpg.command.PlayerMoveUpCommand;
import rpg.entities.Player;
import rpg.input.InputHandler;

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
  private InputHandler inputHandler = new InputHandler();
  private Player player = new Player(PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_COLOR);
  private Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, BACKGROUND_COLOR);
  private double elapsedMilliseconds = 0;
  private double lag = 0;
  private long previousTime = 0;

  private Command playerMoveDownCommand = new PlayerMoveDownCommand(player);
  private Command playerMoveLeftCommand = new PlayerMoveLeftCommand(player);
  private Command playerMoveRightCommand = new PlayerMoveRightCommand(player);
  private Command playerMoveUpCommand = new PlayerMoveUpCommand(player);

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    setupDefaultKeyBindings();

    this.root.getChildren().add(this.player);

    primaryStage.setTitle("RPG");
    primaryStage.setScene(this.scene);

    this.scene.setOnKeyPressed(
        event -> {
          handleInput(event.getCode(), elapsedMilliseconds);
        });

    primaryStage.show();

    AnimationTimer gameLoop =
        new AnimationTimer() {
          @Override
          public void handle(long currentTime) {
            elapsedMilliseconds = (currentTime - previousTime) / 1e6;
            previousTime = currentTime;
            lag += elapsedMilliseconds;

            while (lag >= MS_PER_UPDATE) {
              update(MS_PER_UPDATE);
              lag -= MS_PER_UPDATE;
            }

            render(elapsedMilliseconds);
          }
        };

    gameLoop.start();
  }

  private void handleInput(KeyCode keycode, double elapsedMilliseconds) {
    Command command = this.inputHandler.handleInput(keycode);
    command.execute(elapsedMilliseconds);
  }

  private void render(double elapsedMilliseconds) {}

  private void setupDefaultKeyBindings() {
    this.inputHandler.mapInput(KeyCode.DOWN, this.playerMoveDownCommand);
    this.inputHandler.mapInput(KeyCode.LEFT, this.playerMoveLeftCommand);
    this.inputHandler.mapInput(KeyCode.RIGHT, this.playerMoveRightCommand);
    this.inputHandler.mapInput(KeyCode.UP, this.playerMoveUpCommand);
  }

  private void update(double elapsedMilliseconds) {
    this.player.update(elapsedMilliseconds);
  }
}
