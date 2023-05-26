package rpg.blocks;

import javafx.scene.control.Label;

public interface BlockState {
  void enter(Label textBox);

  void update();

  void exit(Label textBox);
}
