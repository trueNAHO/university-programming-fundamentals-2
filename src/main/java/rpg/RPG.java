package rpg;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import rpg.blocks.Block;
import rpg.blocks.BlockIdleState;
import rpg.blocks.BlockInteractableState;
import rpg.command.Command;
import rpg.command.PlayerMoveDownCommand;
import rpg.command.PlayerMoveLeftCommand;
import rpg.command.PlayerMoveRightCommand;
import rpg.command.PlayerMoveUpCommand;
import rpg.command.fieldAddCommand;
import rpg.command.fieldGrowAllFieldCommand;
import rpg.day_night_cycle.DayNightCycle;
import rpg.entities.player.Player;
import rpg.entities.player.states.IdleState;
import rpg.field.Field;
import rpg.input.InputHandler;

public class RPG extends Application {
  private static final double FPS = 24;
  private static final double PLAYER_HEIGHT = 66;
  private static final double PLAYER_WIDTH = 38;
  private static final double PLAYER_X = 100;
  private static final double PLAYER_Y = 100;
  private static final int SCENE_HEIGHT = 900;
  private static final int SCENE_WIDTH = 1500;
  private static final Color BACKGROUND_COLOR = Color.BLUE;
  private static final Image BACKGROUND_IMAGE =
      new Image("sprites/background.png", SCENE_WIDTH, SCENE_HEIGHT, false, false);
  private static final Color PLAYER_COLOR = Color.RED;
  private static final Image PLAYER_IMAGE = new Image("sprites/GraveRobber.png");
  private static final Image HOUSE_IMAGE = new Image("sprites/house-002.png");
  private static final Image PLANT_IMAGE = new Image("sprites/TEST.png");
  private static final Image FIEL_IMAGE = new Image("sprites/field.png");

  private static final double MS_PER_UPDATE = 1000 / FPS;

  private ArrayList<Block> blocks = new ArrayList<>();
  private Block house = new Block(200, 200, 200, 200, HOUSE_IMAGE);
  private Field field = new Field(300, 500, 500, 250, FIEL_IMAGE, PLANT_IMAGE, 5, 8, 20);
  private Group root = new Group();
  private InputHandler inputHandler = new InputHandler();
  private Player player = new Player(PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_IMAGE);
  private Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
  private double elapsedMilliseconds = 0;
  private double lag = 0;
  private long previousTime = System.nanoTime();

  private DayNightCycle dayNightCycle = new DayNightCycle(true, this.field);

  private Command playerMoveDownCommand = new PlayerMoveDownCommand(player);
  private Command playerMoveLeftCommand = new PlayerMoveLeftCommand(player);
  private Command playerMoveRightCommand = new PlayerMoveRightCommand(player);
  private Command playerMoveUpCommand = new PlayerMoveUpCommand(player);
  private Command fieldGrowAllFieldCommand = new fieldGrowAllFieldCommand(field);
  private Command fieldAddCommand = new fieldAddCommand(field);

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    root.getChildren().add(new ImageView(BACKGROUND_IMAGE));
    setupDefaultKeyBindings();
    setupBlocks();
    setupPlayer();

    primaryStage.setTitle("RPG");
    primaryStage.setScene(this.scene);

    handleInput();

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

  private void handleInput() {
    this.scene.setOnKeyPressed(
        event -> {
          Command command = this.inputHandler.handleInput(event.getCode());
          command.execute(this.elapsedMilliseconds);
        });

    this.scene.setOnKeyReleased(
        event -> {
          Command command = this.inputHandler.handleInput(event.getCode());

          if (command == this.playerMoveDownCommand
              || command == this.playerMoveLeftCommand
              || command == this.playerMoveRightCommand
              || command == this.playerMoveUpCommand) {
            this.player.setState(new IdleState());
          }
        });
  }

  private void render(double elapsedMilliseconds) {}

  private void setupBlocks() {
    this.blocks.add(this.house);
    this.blocks.add(this.field.field);

    for (int i = 0; i < this.field.plants.size(); i++) {
      for (int j = 0; j < this.field.plants.get(0).size(); j++) {
        this.blocks.add(this.field.plants.get(i).get(j));
      }
    }

    for (Block block : this.blocks) {
      this.root.getChildren().add(block);
      this.root.getChildren().add(block.textBox);
      block.textBox.toFront();
    }

    Label newTextBox = new Label();
    newTextBox.relocate(100, 100);
    newTextBox.setText("This is a random text box.");
    newTextBox.toFront();
    this.root.getChildren().add(newTextBox);
  }

  private void setupDefaultKeyBindings() {
    this.inputHandler.mapInput(KeyCode.DOWN, this.playerMoveDownCommand);
    this.inputHandler.mapInput(KeyCode.LEFT, this.playerMoveLeftCommand);
    this.inputHandler.mapInput(KeyCode.RIGHT, this.playerMoveRightCommand);
    this.inputHandler.mapInput(KeyCode.UP, this.playerMoveUpCommand);
    this.inputHandler.mapInput(KeyCode.G, this.fieldGrowAllFieldCommand);
    this.inputHandler.mapInput(KeyCode.A, this.fieldAddCommand);
  }

  private void setupPlayer() {
    this.root.getChildren().add(this.player);
  }

  private void playerCollideBlocks() {
    for (Block block : this.blocks) {
      if (player.intersects(block.getBoundsInLocal())) {
        block.setState(new BlockInteractableState());
      } else if (block.isInteractable()) {
        block.setState(new BlockIdleState());
      }
    }
  }

  private void update(double elapsedMilliseconds) {
    this.dayNightCycle.update(elapsedMilliseconds);
    this.player.update(elapsedMilliseconds);
    this.playerCollideBlocks();
    this.field.update(elapsedMilliseconds);
  }
}
