package rpg.command;

import rpg.entities.Player;

public class PlayerMoveRightCommand implements Command {
  private final Player player;

  public PlayerMoveRightCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute(double deltaTime) {
    player.moveRight(deltaTime);
  }
}
