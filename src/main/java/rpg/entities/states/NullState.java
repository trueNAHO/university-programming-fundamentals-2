package rpg.entities.states;

import rpg.entities.Player;

public class NullState implements PlayerState {
  @Override
  public void enter(Player player) {}

  @Override
  public void update(Player player, double deltaTime) {}

  @Override
  public void exit(Player player) {}
}
