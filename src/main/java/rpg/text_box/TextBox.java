package rpg.text_box;

import javafx.scene.control.Label;

public class TextBox {
  public Label textBox = new Label();
  public String textPrefix = "";
  public String text = "";
  public double timeUntilExpiration = 1;
  public boolean stayOpen;
  public boolean onScreen;

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

  public void setText(String text) {
    this.text = text;
    this.textBox.setText(this.textPrefix + text);
  }

  public String getText() {
    return this.text;
  }

  public void setText(String textPrefix, String text) {
    this.text = text;
    this.textPrefix = textPrefix;
    this.textBox.setText(textPrefix + text);
  }

  public void setTextPrefix(String textPrefix) {
    this.textBox.setText(textPrefix + this.text);
  }

  public void setStyle(String style) {
    this.textBox.setStyle(style);
  }

  public void setTimeUntilExpiration(double newTimeUntilExpiration) {
    this.onScreen = true;
    this.timeUntilExpiration = newTimeUntilExpiration;
    this.textBox.setVisible(true);
  }

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
