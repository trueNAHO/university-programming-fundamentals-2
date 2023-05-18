package rpg.entities.player.states;

import rpg.entities.player.Player;

public class MoveDownState implements PlayerState {
  @Override
  public void enter(Player player) {}

  @Override
  public void update(Player player, double deltaTime) {
    player.moveDown(deltaTime);
  }

  @Override
  public void exit(Player player) {}
}
