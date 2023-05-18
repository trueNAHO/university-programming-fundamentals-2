package rpg.command;

import rpg.entities.player.Player;
import rpg.entities.player.states.MoveUpState;

public class PlayerMoveUpCommand implements Command {
  private final Player player;

  public PlayerMoveUpCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute(double deltaTime) {
    player.setState(new MoveUpState());
  }
}
