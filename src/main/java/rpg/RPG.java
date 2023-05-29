package rpg;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import rpg.blocks.Block;
import rpg.blocks.BlockIdleState;
import rpg.blocks.BlockInteractableState;
import rpg.command.Command;
import rpg.command.InventorySelectDownCommand;
import rpg.command.InventorySelectLeftCommand;
import rpg.command.InventorySelectRightCommand;
import rpg.command.InventorySelectUpCommand;
import rpg.command.PlayerMoveDownCommand;
import rpg.command.PlayerMoveLeftCommand;
import rpg.command.PlayerMoveRightCommand;
import rpg.command.PlayerMoveUpCommand;
import rpg.command.fieldAddCommand;
import rpg.command.fieldGrowAllFieldCommand;
import rpg.command.fieldHarvestCommand;
import rpg.day_night_cycle.DayNightCycle;
import rpg.entities.player.Player;
import rpg.entities.player.states.IdleState;
import rpg.field.Field;
import rpg.input.InputHandler;
import rpg.inventory.Inventory;
import rpg.inventory.states.InventoryIdleState;
import rpg.plants.Plant;
import rpg.text_box.TextBox;

/**
 * The RPG class represents th main class of our rpg game. It extends the JavaFX Application class
 * and is responsible for setting up and running the game.
 */
public class RPG extends Application {
  // Constants
  private static final double FPS = 24;
  private static final double PLAYER_HEIGHT = 56;
  private static final double PLAYER_WIDTH = 28;
  private static final double PLAYER_X = 100;
  private static final double PLAYER_Y = 100;
  private static final int SCENE_HEIGHT = 900;
  private static final int SCENE_WIDTH = 1500;
  private static final Image BACKGROUND_IMAGE =
      new Image("sprites/background.png", SCENE_WIDTH, SCENE_HEIGHT, false, false);
  private static final Image PLAYER_IMAGE = new Image("sprites/GraveRobber.png");
  private static final Image HOUSE_IMAGE =
      new Image("sprites/house-002.png", 200, 200, false, false);
  private static final Image PLANT_IMAGE = new Image("sprites/empty/empty_stage_1.png");
  private static final Image FIEL_IMAGE = new Image("sprites/field.png");
  private static final double TEXT_BOX_X_PADDING = 50;
  private static final double TEXT_BOX_Y_PADDING = 50;

  private static final double MS_PER_UPDATE = 1000 / FPS;

  // Instance variables
  private ArrayList<Block> blocks = new ArrayList<>();
  private Inventory inventory = new Inventory();
  private Block house = new Block(200, 200, 200, 200, HOUSE_IMAGE);
  private Field field = new Field(300, 500, 650, 350, FIEL_IMAGE, PLANT_IMAGE, 5, 8, 30);
  private Group root = new Group();
  private HashMap<String, TextBox> textBoxes = new HashMap<>();
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
  private Command fieldHarvestCommand = new fieldHarvestCommand(this);
  private Command InventorySelectDownCommand = new InventorySelectDownCommand(inventory);
  private Command InventorySelectLeftCommand = new InventorySelectLeftCommand(inventory);
  private Command InventorySelectRightCommand = new InventorySelectRightCommand(inventory);
  private Command InventorySelectUpCommand = new InventorySelectUpCommand(inventory);

  public boolean spacePress = false;

  /** The main method of the RPG game. It launches the JavaFX application. */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * The start method of the RPG game. It is called when the JavaFX application, the rpg game,
   * starts. This method sets up the initial state of the game, inlcuding the stage, the scene, the
   * blocks, the key bindings, the player, the text boxes.
   *
   * @param primaryStage This is the primary stage of the JavaFX application.
   */
  @Override
  public void start(Stage primaryStage) {
    root.getChildren().add(new ImageView(BACKGROUND_IMAGE));
    setupDefaultKeyBindings();
    setupBlocks();
    setupPlayer();
    setupInventory();
    setupTextBoxes();

    primaryStage.setTitle("RPG");
    primaryStage.setScene(this.scene);

    handleInput();

    primaryStage.show();
    this.inventory.description();

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

    this.inventory.set(0, 0, 64, "cpu_seed");
    this.inventory.set(1, 0, 64, "gpu_seed");
    this.inventory.set(2, 0, 64, "ram_seed");
    this.inventory.set(3, 0, 64, "hdd_seed");
    this.inventory.set(4, 0, 64, "board_seed");
    this.inventory.set(5, 0, 64, "cooler_seed");
  }

  /**
   * Handles user input in the game, it registers if keys pressed or released and determines the
   * corresponding command to be executed. It updates the player and inventory states accordingly.
   */
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
              || command == this.playerMoveUpCommand
              || command == this.InventorySelectDownCommand
              || command == this.InventorySelectLeftCommand
              || command == this.InventorySelectRightCommand
              || command == this.InventorySelectUpCommand
              || command == this.fieldHarvestCommand) {
            this.player.setState(new IdleState());
            this.inventory.setState(new InventoryIdleState());
            pressedSpace(false);
          }
        });
  }

  /**
   * Renders the game based on elapsed time, in milliseconds, since the last update. It is called in
   * the gameloop to update the visual representation of the game state.
   *
   * @param elapsedMilliseconds The elapsed time in milliseconds since the last update.
   */
  private void render(double elapsedMilliseconds) {}

  /**
   * Sets up all the different blocks making up the game world. These blocks are of various types
   * such as: House, Field, Plants, Inventory and all of the text boxes belonging to each type.
   */
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
  }

  /** Maps the different commands to specific key bidnings */
  private void setupDefaultKeyBindings() {
    this.inputHandler.mapInput(KeyCode.DOWN, this.playerMoveDownCommand);
    this.inputHandler.mapInput(KeyCode.LEFT, this.playerMoveLeftCommand);
    this.inputHandler.mapInput(KeyCode.RIGHT, this.playerMoveRightCommand);
    this.inputHandler.mapInput(KeyCode.UP, this.playerMoveUpCommand);

    this.inputHandler.mapInput(KeyCode.S, this.InventorySelectDownCommand);
    this.inputHandler.mapInput(KeyCode.Q, this.InventorySelectLeftCommand);
    this.inputHandler.mapInput(KeyCode.D, this.InventorySelectRightCommand);
    this.inputHandler.mapInput(KeyCode.Z, this.InventorySelectUpCommand);

    this.inputHandler.mapInput(KeyCode.G, this.fieldGrowAllFieldCommand);
    this.inputHandler.mapInput(KeyCode.A, this.fieldAddCommand);
    this.inputHandler.mapInput(KeyCode.SPACE, this.fieldHarvestCommand);
  }

  /** Sets the player up in the game world */
  private void setupPlayer() {
    this.root.getChildren().add(this.player);
    this.root.getChildren().add(this.dayNightCycle.nightBlock);
  }

  private void setupInventory() {
    this.root.getChildren().add(this.inventory.inventory);
    this.root.getChildren().add(this.inventory.inventoryTextBox);

    for (int i = 0; i < this.inventory.items.size(); i++) {
      for (int j = 0; j < this.inventory.items.get(0).size(); j++) {
        this.root.getChildren().add(this.inventory.slots.get(i).get(j));
        this.root.getChildren().add(this.inventory.items.get(i).get(j));
      }
    }
  }

  /** Sets up text boxes for each plant type and inventory to the root pane */
  private void setupTextBoxes() {
    String[] plants = {
      "CPU", "Cooler", "GPU", "HDD", "Mother Board", "RAM",
    };

    for (int i = 0; i < plants.length; i++) {
      String plant = plants[i];
      TextBox textBox =
          new TextBox(
              String.format("%s: ", plant),
              "",
              0,
              TEXT_BOX_X_PADDING,
              SCENE_HEIGHT - (i + 1) * TEXT_BOX_Y_PADDING);

      this.textBoxes.put(plant, textBox);
      this.root.getChildren().add(textBox.textBox);
    }
    this.textBoxes.put("textBox", this.inventory.textBoxInventory);
    this.root.getChildren().add(this.inventory.textBoxInventory.textBox);
    this.textBoxes.put("add", this.inventory.textAdd);
    this.root.getChildren().add(this.inventory.textAdd.textBox);
    this.textBoxes.put("remove", this.inventory.textRemove);
    this.root.getChildren().add(this.inventory.textRemove.textBox);
  }

  /**
   * Controls and limits the player movement in the game so the player can't run out of the scene.
   * If the player touches the borders the player position is adjusted and the player state is set
   * to idle
   */
  private void borderControl() {
    double playerPosX = player.getX();
    double playerPosY = player.getY();

    if (playerPosY <= 0) {
      player.setY(player.getY() + 1 * MS_PER_UPDATE);
      player.setState(new IdleState());
    }
    if (playerPosY >= SCENE_HEIGHT - PLAYER_HEIGHT) {
      player.setY(player.getY() - 1 * MS_PER_UPDATE);
      player.setState(new IdleState());
    }
    if (playerPosX <= 0) {
      player.setX(player.getX() + 1 * MS_PER_UPDATE);
      player.setState(new IdleState());
    }
    if (playerPosX >= SCENE_WIDTH - PLAYER_WIDTH) {
      player.setX(player.getX() - 1 * MS_PER_UPDATE);
      player.setState(new IdleState());
    }
  }

  /**
   * This handles collision between the player and the house block, so that the player cannot walk
   * over or through the house.
   *
   * @param block The house block involved in the collision.
   */
  private void houseCollision(Block block) {
    double playerPosX = player.getX();
    double playerPosY = player.getY();
    double blockMinX = block.getBoundsInLocal().getMinX();
    double blockMinY = block.getBoundsInLocal().getMinY();
    double maxX = block.getBoundsInLocal().getMaxX();
    double maxY = block.getBoundsInLocal().getMaxY();
    double blockMaxX = maxX - 66;
    double blockMaxY = maxY - 38;

    while (player.intersects(block.getBoundsInLocal())) {
      if (playerPosX >= blockMaxX) {
        player.setX(blockMaxX + 1 * MS_PER_UPDATE);
        player.setState(new IdleState());
        break;
      } else if (playerPosY >= blockMaxY) {
        player.setY(blockMaxY + 1 * MS_PER_UPDATE);
        player.setState(new IdleState());
        break;
      } else if (playerPosX - 10 <= blockMinX) {
        player.setX(blockMinX - 1 * MS_PER_UPDATE);
        player.setState(new IdleState());
        break;
      } else if (playerPosY <= blockMinY) {
        player.setY(blockMinY - 1 * MS_PER_UPDATE);
        player.setState(new IdleState());
        break;
      } else {
        System.out.println("Error!");
      }
    }
  }

  /**
   * Handles the collision between the player and all the different blocks in the game scene. It
   * checks for the intersection between the player and different blocks and executes different
   * actions, based on the block types. It also updates the state of the blocks to interactable or
   * Non-interactable, based on player action.
   */
  private void playerCollideBlocks() {
    this.borderControl();
    for (Block block : this.blocks) {
      if (player.intersects(block.getBoundsInLocal())) {
        if (block.equals(house)) {
          this.houseCollision(house);
        }
        if (block instanceof Plant) {
          Plant plant = (Plant) block;
          if (plant.type.equals("empty")) {
            plant.setText("Plant");
            block.setState(new BlockInteractableState());
          } else if (!plant.type.equals("empty") && plant.fullyGrown()) {
            plant.setText("Harvest");
            block.setState(new BlockInteractableState());
          }
          if (spacePress && !plant.type.equals("empty")) {
            this.field.harvest(plant, this.inventory);
          } else if (spacePress && plant.type.equals("empty")) {
            this.field.plant(plant, this.inventory);
          }
        }
      } else if (block.isInteractable()) {
        block.setState(new BlockIdleState());
        if (block instanceof Plant) {
          Plant plant = (Plant) block;
        }
      }
    }
  }

  /**
   * @param press True if the space key is pressed, false otherwise.
   */
  public void pressedSpace(boolean press) {
    spacePress = press;
  }

  /**
   * Updates the game state based on the elapsed time in milliseconds.
   *
   * @param elapsedMilliseconds The elapsed time in milliseconds.
   */
  private void update(double elapsedMilliseconds) {
    this.dayNightCycle.update(elapsedMilliseconds);
    this.player.update(elapsedMilliseconds);
    this.playerCollideBlocks();
    this.field.update(elapsedMilliseconds);
    this.inventory.update(elapsedMilliseconds);

    for (TextBox textBox : textBoxes.values()) {
      textBox.update(elapsedMilliseconds);
    }
  }
}
