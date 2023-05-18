package rpg.command;

import rpg.entities.Player;

public class PlayerMoveDownCommand implements Command {
  private final Player player;

  public PlayerMoveDownCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute() {
    player.moveDown();
  }
}
