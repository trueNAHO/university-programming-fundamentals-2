package rpg.inventory.states;

import rpg.inventory.Inventory;

public class SelectDownState implements InventoryState {
  @Override
  public void enter(Inventory inventory) {
    inventory.selectDown();
  }

  @Override
  public void update(Inventory inventory) {}

  @Override
  public void exit(Inventory inventory) {}
}
