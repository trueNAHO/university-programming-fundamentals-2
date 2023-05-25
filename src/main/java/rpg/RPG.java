package rpg;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
    private static final double HOUSE_X = 200;
    private static final double HOUSE_Y = 200;
    private static final int SCENE_HEIGHT = 900;
    private static final int SCENE_WIDTH = 1500;
    private static final Color BACKGROUND_COLOR = Color.BLUE;
    private static final Image BACKGROUND_IMAGE = new Image("sprites/background.png", SCENE_WIDTH, SCENE_HEIGHT, false,
            false);
    private static final Color PLAYER_COLOR = Color.RED;
    private static final Image PLAYER_IMAGE = new Image("sprites/GraveRobber.png");
    private static final Image HOUSE_IMAGE = new Image("sprites/house-002.png");

    private static final double MS_PER_UPDATE = 1000 / FPS;

    private ArrayList<Block> blocks = new ArrayList<>();
    private Block house = new Block(HOUSE_X, HOUSE_Y, 200, 200, HOUSE_IMAGE);
    // private DayNightCycle dayNightCycle = new DayNightCycle(true);
    private Field field = new Field(300, 500, 500, 250, Color.BROWN, Color.GREEN, 5, 8, 20);
    private Group root = new Group();
    private InputHandler inputHandler = new InputHandler();
    private Player player = new Player(PLAYER_X, PLAYER_Y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_IMAGE);
    private Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
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

        root.getChildren().add(new ImageView(BACKGROUND_IMAGE));
        setupDefaultKeyBindings();
        setupBlocks();
        setupPlayer();

        primaryStage.setTitle("RPG");
        primaryStage.setScene(this.scene);

        handleInput();

        primaryStage.show();

        AnimationTimer gameLoop = new AnimationTimer() {
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

    private void render(double elapsedMilliseconds) {
    }

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
        }
    }

    private void setupDefaultKeyBindings() {
        this.inputHandler.mapInput(KeyCode.DOWN, this.playerMoveDownCommand);
        this.inputHandler.mapInput(KeyCode.LEFT, this.playerMoveLeftCommand);
        this.inputHandler.mapInput(KeyCode.RIGHT, this.playerMoveRightCommand);
        this.inputHandler.mapInput(KeyCode.UP, this.playerMoveUpCommand);
    }

    private void setupPlayer() {
        this.root.getChildren().add(this.player);
    }

    // private void borderControl(Block block) {
    //   double playerPosX = player.getX();
    //   double playerPosY = player.getY();
    //   double houseMinX = block.getBoundsInLocal().getMinX();
    //   double houseMinY = block.getBoundsInLocal().getMinY();
    //   double houseMaxX = block.getBoundsInLocal().getMaxX();
    //   double houseMaxY = block.getBoundsInLocal().getMaxY();

    //   while (player.intersects(
    //                         block.getBoundsInLocal())) { // Works because of added 10 to playerPosX somehow the
    //                     // player comes from the left and gets stuck behind the
    //                     // max of the house even though he should already be reset
    //                     // when he hits 400.
    //                     if (playerPosX + 20 >= houseMaxX) {
    //                         System.out.println("Player X " + playerPosX);
    //                         System.out.println("Player Y " + playerPosY);
    //                         System.out.println("You just hit House Max X: " + block.getBoundsInLocal().getMaxX());
    //                         player.setX(houseMaxX + 1 * MS_PER_UPDATE);
    //                         player.setState(new IdleState());
    //                         break;
    //                     } else if (playerPosY + 50 >= houseMaxY) { // Same as above
    //                         System.out.println("Player X " + playerPosX);
    //                         System.out.println("Player Y " + playerPosY);
    //                         System.out.println("You just hit House MaxY: " + block.getBoundsInLocal().getMaxY());
    //                         player.setY(houseMaxY + 1 * MS_PER_UPDATE);
    //                         player.setState(new IdleState());
    //                         break;
    //                     } else if (playerPosX <= houseMinX) { // Works, Player comes from above gets blocked by house
    //                         System.out.println("Player X " + playerPosX);
    //                         System.out.println("Player Y " + playerPosY);
    //                         System.out.println("You just hit House Min X: " + block.getBoundsInLocal().getMinX());
    //                         player.setX(houseMinX - 1 * MS_PER_UPDATE);
    //                         player.setState(new IdleState());
    //                         break;
    //                     } else if (playerPosY <= houseMinY) { // Works, Player comes from left gets blocked by house
    //                         System.out.println("Player X " + playerPosX);
    //                         System.out.println("Player Y " + playerPosY);
    //                         System.out.println("You just hit House MinY: " + block.getBoundsInLocal().getMinY());
    //                         System.out.println("Bounds " + block.getBoundsInLocal());
    //                         player.setY(houseMinY - 1 * MS_PER_UPDATE);
    //                         player.setState(new IdleState());
    //                         break;
    //                     } else {
    //                         System.out.println("Player X " + playerPosX);
    //                         System.out.println("Player Y " + playerPosY);
    //                         System.out.println("You just hit House Max X: " + block.getBoundsInLocal().getMaxX());
    //                         System.out.println("You just hit House MaxY: " + block.getBoundsInLocal().getMaxY());
    //                         System.out.println("Bounds " + block.getBoundsInLocal());
    //                         System.out.println("This is the fucking error!");
    //                     }
    //                 }
    // }

    private void playerCollideBlocks() {
        double playerPosX = player.getX();
        double playerPosY = player.getY();
        double houseMinX = house.getBoundsInLocal().getMinX();
        double houseMinY = house.getBoundsInLocal().getMinY();
        double houseMaxX = house.getBoundsInLocal().getMaxX();
        double houseMaxY = house.getBoundsInLocal().getMaxY();

        for (Block block : this.blocks) {
            if (player.intersects(block.getBoundsInLocal())) {
                if (block.equals(house)) {
                    while (player.intersects(
                            house.getBoundsInLocal())) { // Works because of added 10 to playerPosX somehow the
                        // player comes from the left and gets stuck behind the
                        // max of the house even though he should already be reset
                        // when he hits 400.
                        if (playerPosX + 20 >= houseMaxX) {
                            System.out.println("Player X " + playerPosX);
                            System.out.println("Player Y " + playerPosY);
                            System.out.println("You just hit House Max X: " + house.getBoundsInLocal().getMaxX());
                            player.setX(houseMaxX + 1 * MS_PER_UPDATE);
                            player.setState(new IdleState());
                            break;
                        } else if (playerPosY + 50 >= houseMaxY) { // Same as above
                            System.out.println("Player X " + playerPosX);
                            System.out.println("Player Y " + playerPosY);
                            System.out.println("You just hit House MaxY: " + house.getBoundsInLocal().getMaxY());
                            player.setY(houseMaxY + 1 * MS_PER_UPDATE);
                            player.setState(new IdleState());
                            break;
                        } else if (playerPosX <= houseMinX) { // Works, Player comes from above gets blocked by house
                            System.out.println("Player X " + playerPosX);
                            System.out.println("Player Y " + playerPosY);
                            System.out.println("You just hit House Min X: " + house.getBoundsInLocal().getMinX());
                            player.setX(houseMinX - 1 * MS_PER_UPDATE);
                            player.setState(new IdleState());
                            break;
                        } else if (playerPosY <= houseMinY) { // Works, Player comes from left gets blocked by house
                            System.out.println("Player X " + playerPosX);
                            System.out.println("Player Y " + playerPosY);
                            System.out.println("You just hit House MinY: " + house.getBoundsInLocal().getMinY());
                            System.out.println("Bounds " + house.getBoundsInLocal());
                            player.setY(houseMinY - 1 * MS_PER_UPDATE);
                            player.setState(new IdleState());
                            break;
                        } else {
                            System.out.println("Player X " + playerPosX);
                            System.out.println("Player Y " + playerPosY);
                            System.out.println("You just hit House Max X: " + house.getBoundsInLocal().getMaxX());
                            System.out.println("You just hit House MaxY: " + house.getBoundsInLocal().getMaxY());
                            System.out.println("Bounds " + house.getBoundsInLocal());
                            System.out.println("This is the fucking error!");
                        }
                    }
                }
                block.setState(new BlockInteractableState());
            } else if (block.isInteractable()) {
                block.setState(new BlockIdleState());
            }
        }
    }

    private void update(double elapsedMilliseconds) {
        // this.dayNightCycle.update(elapsedMilliseconds);
        this.player.update(elapsedMilliseconds);
        this.playerCollideBlocks();
        this.field.update(elapsedMilliseconds);
    }
}
