package rpg.day_night_cycle;

import rpg.field.Field;

public class DayNightCycle {
  private static final double DAY_DURATION_MS = 2 * 1000;
  private static final double NIGHT_DURATION_MS = 2 * 1000;

  public boolean isDay;
  public double dayDuration;
  public double nightDuration;
  private Field field;

  public DayNightCycle(boolean isDay, Field field) {
    this.dayDuration = 0;
    this.isDay = isDay;
    this.nightDuration = 0;
    this.field = field;
  }

  public void transitionToDay(double deltaTime) {
    this.isDay = true;
    this.nightDuration = 0;
    this.dayDuration = deltaTime;
    this.field.growAllField();
  }

  public void transitionToNight(double deltaTime) {
    this.isDay = false;
    this.dayDuration = 0;
    this.nightDuration = deltaTime;
  }

  public void update(double deltaTime) {
    if (this.isDay) {
      double newDayDuration = this.dayDuration + deltaTime;

      if (newDayDuration > DAY_DURATION_MS) {
        transitionToNight(newDayDuration - DAY_DURATION_MS);
      } else {
        this.dayDuration = newDayDuration;
      }
    } else {
      double newNightDuration = this.nightDuration + deltaTime;

      if (newNightDuration > NIGHT_DURATION_MS) {
        transitionToDay(newNightDuration - NIGHT_DURATION_MS);
      } else {
        this.nightDuration = newNightDuration;
      }
    }
  }
}
