package rpg.command;

import rpg.inventory.Inventory;
import rpg.inventory.states.SelectRightState;

public class InventorySelectRightCommand implements Command {
  private final Inventory inventory;

  public InventorySelectRightCommand(Inventory inventory) {
    this.inventory = inventory;
  }

  @Override
  public void execute(double deltaTime) {
    inventory.setState(new SelectRightState());
  }
}
