package rpg.command;

import rpg.entities.player.Player;

public class PlayerMoveUpCommand implements Command {
  private final Player player;

  public PlayerMoveUpCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute(double deltaTime) {
    player.moveUp(deltaTime);
  }
}