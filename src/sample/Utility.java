package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Utility {
  public static Button createBtn(String text) {
    Button btn = new Button(text);
    btn.getStyleClass().add("btnStyle");
    btn.setMaxWidth(Double.MAX_VALUE);
    btn.setOnAction(e -> System.out.println(text + " clicked !!"));
    return btn;
  }

  public static Label createLabel(String text, String styleClass) {
    Label lbl = new Label(text);
    lbl.getStyleClass().add(styleClass);
    return lbl;
  }
}
