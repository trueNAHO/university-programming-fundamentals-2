package rpg.entities.player.states;

import rpg.entities.player.Player;

public interface PlayerState {
  void enter(Player player);

  void update(Player player, double deltaTime);

  void exit(Player player);
}
