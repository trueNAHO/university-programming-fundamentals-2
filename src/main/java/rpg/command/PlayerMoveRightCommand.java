package rpg.command;

import rpg.entities.player.Player;
import rpg.entities.player.states.MoveRightState;

public class PlayerMoveRightCommand implements Command {
  private final Player player;

  public PlayerMoveRightCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute(double deltaTime) {
    player.setState(new MoveRightState());
  }
}
