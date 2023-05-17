package rpg.input;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import rpg.command.Command;
import rpg.command.NullCommand;

public class InputHandler {
  private Map<KeyCode, Command> inputCommandMap = new HashMap<>();

  public void mapInput(KeyCode keyCode, Command command) {
    inputCommandMap.put(keyCode, command);
  }

  public Command handleInput(KeyCode keyCode) {
    return inputCommandMap.getOrDefault(keyCode, new NullCommand());
  }
}
