package rpg.blocks;

import javafx.scene.control.Label;

public class BlockInteractableState implements BlockState {
  @Override
  public void enter(Label textBox) {
    textBox.setVisible(true);
  }

  @Override
  public void update() {}

  @Override
  public void exit(Label textBox) {
    textBox.setVisible(false);
  }
}
