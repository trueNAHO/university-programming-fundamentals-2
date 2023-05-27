package rpg.command;

import rpg.inventory.Inventory;
import rpg.inventory.states.SelectUpState;

public class InventorySelectUpCommand implements Command {
  private final Inventory inventory;

  public InventorySelectUpCommand(Inventory inventory) {
    this.inventory = inventory;
  }

  @Override
  public void execute(double deltaTime) {
    inventory.setState(new SelectUpState());
  }
}
