package rpg.text_box;

import javafx.scene.control.Label;

/** Represents a text box shown in the game screen. */
public class TextBox {
  public Label textBox = new Label();
  public String textPrefix = "";
  public String text = "";
  public double timeUntilExpiration = 1;
  public boolean stayOpen;
  public boolean onScreen;

  /**
   * Constructs a TextBox object with the specified parameters.
   *
   * @param textPrefix The prefix to be displayed before the text.
   * @param text The text to be displayed in the text box.
   * @param timeUntilExpiration The time in seconds until the text box disappears.
   * @param x The x-coordinate of the text box's position.
   * @param y The y-coordinate of the text box's position.
   */
  public TextBox(String textPrefix, String text, double timeUntilExpiration, double x, double y) {
    this.textPrefix = textPrefix;
    this.text = text;

    this.textBox = new Label();
    this.textBox.relocate(x, y);
    this.textBox.setStyle("-fx-background-color: white;");
    this.textBox.toFront();

    this.setText(this.text);
    this.setTimeUntilExpiration(timeUntilExpiration);
    this.stayOpen = false;
    this.onScreen = true;
  }

  /**
   * Constructs a TextBox object with the specified parameters.
   *
   * @param textPrefix The prefix to be displayed before the text.
   * @param text The text to be displayed in the text box.
   * @param x The x-coordinate of the text box's position.
   * @param y The y-coordinate of the text box's position.
   */
  public TextBox(String textPrefix, String text, double x, double y) {
    this.textPrefix = textPrefix;
    this.text = text;

    this.textBox = new Label();
    this.textBox.relocate(x, y);
    this.textBox.setStyle("-fx-background-color: white;");
    this.textBox.toFront();

    this.setText(this.text);
    this.textBox.setVisible(true);
    this.stayOpen = true;
    this.onScreen = true;
  }

  /**
   * Sets the text to be displayed in the text box.
   *
   * @param text The text to set.
   */
  public void setText(String text) {
    this.text = text;
    this.textBox.setText(this.textPrefix + text);
  }

  /**
   * Returns the text from a text box.
   *
   * @return Text to return .
   */
  public String getText() {
    return this.text;
  }

  /**
   * Sets the text to be displayed in the text box with a specified prefix.
   *
   * @param text The text to set.
   * @param textPrefix The prefix before the text.
   */
  public void setText(String textPrefix, String text) {
    this.text = text;
    this.textPrefix = textPrefix;
    this.textBox.setText(textPrefix + text);
  }

  /**
   * Sets the text prefix in a text box.
   *
   * @param textPrefix The text prefix to be set.
   */
  public void setTextPrefix(String textPrefix) {
    this.textBox.setText(textPrefix + this.text);
  }

  /**
   * Sets the style of the text box.
   *
   * @param style The style to be set.
   */
  public void setStyle(String style) {
    this.textBox.setStyle(style);
  }

  /**
   * Sets the time until the text box disappears.
   *
   * @param newTimeUntilExpiration The new time until expiration.
   */
  public void setTimeUntilExpiration(double newTimeUntilExpiration) {
    this.onScreen = true;
    this.timeUntilExpiration = newTimeUntilExpiration;
    this.textBox.setVisible(true);
  }

  /**
   * Updates the text box based on the deltaTime.
   *
   * @param deltaTime The time elapsed since the last update.
   */
  public void update(double deltaTime) {
    if (!stayOpen) {
      double newTimeUntilExpiration = this.timeUntilExpiration - deltaTime;
      if (newTimeUntilExpiration > 0) {
        this.timeUntilExpiration = newTimeUntilExpiration;
      } else {
        this.timeUntilExpiration = 0;
        this.textBox.setVisible(false);
        this.onScreen = false;
      }
    }
  }
}
