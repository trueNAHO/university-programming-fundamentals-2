package rpg.inventory.states;

import rpg.inventory.Inventory;

public class SelectLeftState implements InventoryState {
  @Override
  public void enter(Inventory inventory) {
    inventory.selectLeft();
  }

  @Override
  public void update(Inventory inventory) {}

  @Override
  public void exit(Inventory inventory) {}
}
