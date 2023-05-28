package rpg.blocks;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
  public final Label textBox;

  private BlockState state;
  private static final String TEXT_BOX_TEXT = "Interactable";

  public Block(double x, double y, double width, double height, Color color) {
    super(x, y, width, height);
    this.state = new BlockNullState();
    setFill(color);

    this.textBox = new Label();
    this.textBox.relocate(x, y);
    this.textBox.setStyle("-fx-background-color: white;");
    this.textBox.setText(TEXT_BOX_TEXT);
    this.textBox.toFront();
    this.textBox.setVisible(false);
  }

  public Block(double x, double y, double width, double height, Image image) {
    super(x, y, width, height);
    this.state = new BlockNullState();
    setFill(new ImagePattern(image));

    this.textBox = new Label();
    this.textBox.relocate(x, y);
    this.textBox.setStyle("-fx-background-color: white;");
    this.textBox.setText(TEXT_BOX_TEXT);
    this.textBox.toFront();
    this.textBox.setVisible(false);
  }

  public boolean isInteractable() {
    return this.state instanceof BlockInteractableState;
  }

  public void setState(BlockState newState) {
    this.state.exit(this.textBox);
    this.state = newState;
    this.state.enter(this.textBox);
  }

  public void setText(String text) {
    this.textBox.setText(text);
  }

  public void changeImage(Image image) {
    setFill(new ImagePattern(image));
  }

  public void update(double deltaTime) {
    this.state.update();
  }
}
