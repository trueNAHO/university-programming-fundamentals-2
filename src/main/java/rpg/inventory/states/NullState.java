package rpg.inventory.states;

import rpg.inventory.Inventory;

public class NullState implements InventoryState {
  @Override
  public void enter(Inventory inventory) {}

  @Override
  public void update(Inventory inventory) {}

  @Override
  public void exit(Inventory inventory) {}
}
