package rpg.text_box;

import javafx.scene.control.Label;

public class TextBox {
  public Label textBox = new Label();
  public String textPrefix = "";
  public double timeUntilExpiration = 1;

  public TextBox(String textPrefix, String text, double timeUntilExpiration, double x, double y) {
    this.textPrefix = textPrefix;

    this.textBox = new Label();
    this.textBox.relocate(x, y);
    this.textBox.setStyle("-fx-background-color: white;");
    this.textBox.toFront();

    this.setText(text);
    this.setTimeUntilExpiration(timeUntilExpiration);
  }

  public void setText(String text) {
    this.textBox.setText(this.textPrefix + text);
  }

  public void setTimeUntilExpiration(double newTimeUntilExpiration) {
    this.timeUntilExpiration = newTimeUntilExpiration;
    this.textBox.setVisible(true);
  }

  public void update(double deltaTime) {
    double newTimeUntilExpiration = this.timeUntilExpiration - deltaTime;

    if (newTimeUntilExpiration > 0) {
      this.timeUntilExpiration = newTimeUntilExpiration;
    } else {
      this.timeUntilExpiration = 0;
      this.textBox.setVisible(false);
    }
  }
}
