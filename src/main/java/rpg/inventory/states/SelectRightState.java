package rpg.inventory.states;

import rpg.inventory.Inventory;

public class SelectRightState implements InventoryState {
  @Override
  public void enter(Inventory inventory) {
    inventory.selectRight();
  }

  @Override
  public void update(Inventory inventory) {}

  @Override
  public void exit(Inventory inventory) {}
}
