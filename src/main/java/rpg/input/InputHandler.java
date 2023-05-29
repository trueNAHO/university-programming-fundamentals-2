package rpg.input;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import rpg.command.Command;
import rpg.command.NullCommand;

/** Handles input and maps keycode to commands. */
public class InputHandler {
  private Map<KeyCode, Command> inputCommandMap = new HashMap<>();

  /**
   * Maps a key code to a command.
   *
   * @param keyCode The key code to map.
   * @param command The command associated with the key code.
   */
  public void mapInput(KeyCode keyCode, Command command) {
    inputCommandMap.put(keyCode, command);
  }

  /**
   * Handles the input by returning the command associated with the key code. A Nullcommand is
   * returned if no command is mapped to the key code.
   *
   * @param keyCode The key code representing the input.
   * @return The command associated with the key code.
   */
  public Command handleInput(KeyCode keyCode) {
    return inputCommandMap.getOrDefault(keyCode, new NullCommand());
  }
}
