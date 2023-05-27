package rpg.command;

import rpg.inventory.Inventory;
import rpg.inventory.states.SelectDownState;

public class InventorySelectDownCommand implements Command {
  private final Inventory inventory;

  public InventorySelectDownCommand(Inventory inventory) {
    this.inventory = inventory;
  }

  @Override
  public void execute(double deltaTime) {
    inventory.setState(new SelectDownState());
  }
}
