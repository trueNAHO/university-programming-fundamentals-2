package rpg.day_night_cycle;

import javafx.scene.paint.Color;
import rpg.blocks.Block;
import rpg.field.Field;

/** Represents the day and night cycle of the game */
public class DayNightCycle {
  private static final double DAY_DURATION_MS = 5 * 1000;
  private static final double NIGHT_DURATION_MS = 5 * 1000;

  public boolean isDay;
  public double dayDuration;
  public double nightDuration;
  private Field field;
  private Color nightColor = new Color(0, 0, 0, 0.5);
  private Color dayColor = new Color(0, 0, 0, 0);
  public Block nightBlock = new Block(0, 0, 1500, 900, dayColor);

  /**
   * Constructs a DayNightCycle object
   *
   * @param isDay Indicates if it's currently day
   * @param field The field block of the game
   */
  public DayNightCycle(boolean isDay, Field field) {
    this.dayDuration = 0;
    this.isDay = isDay;
    this.nightDuration = 0;
    this.field = field;
  }

  /**
   * Transitions from night to day
   *
   * @param deltatime The time elapsed since last update
   */
  public void transitionToDay(double deltaTime) {
    this.isDay = true;
    this.nightDuration = 0;
    this.dayDuration = deltaTime;
    this.field.growAllField();
  }

  /**
   * Transitions from day to night
   *
   * @param deltatime The time elapsed since last update
   */
  public void transitionToNight(double deltaTime) {
    this.isDay = false;
    this.dayDuration = 0;
    this.nightDuration = deltaTime;
  }

  /**
   * Updates the day and night cycle
   *
   * @param deltatime The time elapsed since last updates
   */
  public void update(double deltaTime) {
    nightBlock.update(deltaTime);
    if (this.isDay) {
      double newDayDuration = this.dayDuration + deltaTime;
      nightBlock.changeColor(dayColor);
      if (newDayDuration > DAY_DURATION_MS) {
        transitionToNight(newDayDuration - DAY_DURATION_MS);
      } else {
        this.dayDuration = newDayDuration;
      }
    } else {
      double newNightDuration = this.nightDuration + deltaTime;
      nightBlock.changeColor(nightColor);
      if (newNightDuration > NIGHT_DURATION_MS) {
        transitionToDay(newNightDuration - NIGHT_DURATION_MS);
      } else {
        this.nightDuration = newNightDuration;
      }
    }
  }
}
