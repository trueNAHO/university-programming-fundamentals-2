package rpg.command;

import rpg.entities.player.Player;
import rpg.entities.player.states.MoveDownState;

public class PlayerMoveDownCommand implements Command {
  private final Player player;

  public PlayerMoveDownCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute(double deltaTime) {
    player.setState(new MoveDownState());
  }
}
