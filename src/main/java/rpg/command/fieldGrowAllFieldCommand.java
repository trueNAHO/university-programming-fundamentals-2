package rpg.command;

import rpg.field.Field;

public class fieldGrowAllFieldCommand implements Command {
  private final Field field;

  public fieldGrowAllFieldCommand(Field field) {
    this.field = field;
  }

  @Override
  public void execute(double deltaTime) {
    this.field.resetAllField();
  }
}
