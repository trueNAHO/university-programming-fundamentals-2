package rpg;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RPG extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  private static final int size = 10;
  private int x = 5;
  private int y = 5;
  private Stage stage;

  private void createScene() {
    Image grassImage = new Image("sprites/grass-tile.png");
    Image bulbasaur = new Image("sprites/bulbasaur.png");
    StackPane[][] sprites = new StackPane[size][size];
    TilePane tiles = new TilePane();
    tiles.setPrefColumns(size);
    tiles.setPrefRows(size);
    tiles.setTileAlignment(Pos.CENTER);

    for (int i = 0; i < size; ++i) {
      for (int j = 0; j < size; ++j) {
        sprites[i][j] = new StackPane();
        sprites[i][j].getChildren().add(makeView(grassImage));
        tiles.getChildren().add(sprites[i][j]);
      }
    }

    System.out.println("Set bulbasaur to " + x + "," + y);
    sprites[x][y].getChildren().add(makeView(bulbasaur));

    Scene scene = new Scene(tiles);
    scene.setOnKeyReleased(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            sprites[x][y].getChildren().remove(1);
            switch (event.getCode()) {
              case UP:
                if (x > 0) --x;
                break;
              case DOWN:
                if (x < size - 1) ++x;
                break;
              case LEFT:
                if (y > 0) --y;
                break;
              case RIGHT:
                if (y < size - 1) ++y;
                break;
              default:
                break;
            }
            System.out.println("Set bulbasaur to " + x + "," + y);
            sprites[x][y].getChildren().add(makeView(bulbasaur));
          }
        });
    stage.setScene(scene);
  }

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    createScene();
    stage.setWidth(640);
    stage.setHeight(640);
    stage.sizeToScene();
    stage.show();
  }

  private ImageView makeView(Image image) {
    ImageView view = new ImageView();
    view.setImage(image);
    return view;
  }
}
