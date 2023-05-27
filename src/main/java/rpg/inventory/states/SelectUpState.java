package rpg.inventory.states;

import rpg.inventory.Inventory;

public class SelectUpState implements InventoryState {
  @Override
  public void enter(Inventory inventory) {
    inventory.selectUp();
  }

  @Override
  public void update(Inventory inventory) {}

  @Override
  public void exit(Inventory inventory) {}
}
