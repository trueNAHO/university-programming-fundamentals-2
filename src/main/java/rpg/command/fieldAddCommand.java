package rpg.command;

import rpg.field.Field;

public class fieldAddCommand implements Command {
  private final Field field;

  public fieldAddCommand(Field field) {
    this.field = field;
  }

  @Override
  public void execute(double deltaTime) {
    field.set(3, 6, "ram");
  }
}
