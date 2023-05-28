package rpg.command;

import rpg.RPG;

public class fieldHarvestCommand implements Command {
  private final RPG rpg;

  public fieldHarvestCommand(RPG rpg) {
    this.rpg = rpg;
  }

  @Override
  public void execute(double deltaTime) {
    rpg.pressedSpace(true);
  }
}
