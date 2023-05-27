package rpg.command;

import rpg.inventory.Inventory;
import rpg.inventory.states.SelectLeftState;

public class InventorySelectLeftCommand implements Command {
  private final Inventory inventory;

  public InventorySelectLeftCommand(Inventory inventory) {
    this.inventory = inventory;
  }

  @Override
  public void execute(double deltaTime) {
    inventory.setState(new SelectLeftState());
  }
}
