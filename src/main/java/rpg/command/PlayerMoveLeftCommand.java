package rpg.command;

import rpg.entities.player.Player;

public class PlayerMoveLeftCommand implements Command {
  private final Player player;

  public PlayerMoveLeftCommand(Player player) {
    this.player = player;
  }

  @Override
  public void execute(double deltaTime) {
    player.moveLeft(deltaTime);
  }
}
