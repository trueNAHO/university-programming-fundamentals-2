package rpg.inventory.states;

import rpg.inventory.Inventory;

public interface InventoryState {
  void enter(Inventory inventory);

  void update(Inventory inventory);

  void exit(Inventory inventory);
}
