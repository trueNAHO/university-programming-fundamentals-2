package rpg.entities.states;

import rpg.entities.Player;

public interface PlayerState {
  void enter(Player player);

  void update(Player player, double deltaTime);

  void exit(Player player);
}
