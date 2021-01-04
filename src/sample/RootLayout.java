package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class RootLayout {
  public static BorderPane create() {
    BorderPane borderPane = new BorderPane();
    borderPane.getStyleClass().add("root");
    borderPane.setPadding(new Insets(5));

    Label top = createLabel("TOP", "bg-2");
    top.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    borderPane.setTop(top);

    VBox left = leftSizeToolBar();
    borderPane.setLeft(left);

    StackPane center = centerCanvas();
    borderPane.setCenter(center);

    VBox right = rightSizePathBar();
    borderPane.setRight(right);

    Label bottom = createLabel("BOTTOM", "bg-6");
    bottom.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    borderPane.setBottom(bottom);

    return borderPane;
  }

  private static StackPane centerCanvas() {
    StackPane center = new StackPane();
    Button centerBtn = new Button("CANVAS");
    center.getStyleClass().add("bg-4");
    center.getChildren().add(centerBtn);

    return center;
  }

  private static VBox leftSizeToolBar() {
    VBox toolbar = new VBox();

    Button loadBtn = createBtn("Load");
    Button saveBtn = createBtn("Save");

    toolbar.getChildren().addAll(loadBtn, saveBtn);
    toolbar.getStyleClass().add("bg-3");
    toolbar.setPrefWidth(100);
    return toolbar;
  }

  private static VBox rightSizePathBar() {
    VBox pathbar = new VBox();
    Button recursive = createBtn("Recursive");
    Button dijkstra = createBtn("Dijkstra");

    pathbar.getChildren().addAll(recursive, dijkstra);
    pathbar.getStyleClass().add("bg-5");
    pathbar.setPrefWidth(100);
    return pathbar;
  }

  private static Button createBtn(String text) {
    Button btn = new Button(text);
    btn.getStyleClass().add("btnStyle");
    btn.setMaxWidth(Double.MAX_VALUE);
    btn.setOnAction(e -> System.out.println(text + " clicked !!"));
    return btn;
  }

  private static Label createLabel(String text, String styleClass) {
    Label lbl = new Label(text);
    lbl.getStyleClass().add(styleClass);
    return lbl;
  }
}
