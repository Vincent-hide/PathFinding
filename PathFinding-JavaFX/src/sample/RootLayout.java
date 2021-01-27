package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.mapdata.Map;

import java.io.File;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;
import sample.mapdata.Utility;
import sample.mapdata.method.Dijkstra;
import sample.mapdata.method.Recursion;

public class RootLayout {
  private Stage mainStage;
  private Map primaryMap;
  private Canvas canvas;
  private GraphicsContext gc;

  public RootLayout() {
  }

  public BorderPane create() {
    BorderPane borderPane = new BorderPane();
    borderPane.getStyleClass().add("root");
    borderPane.setPadding(new Insets(5));

    Label top = sample.Utility.createLabel("Path Finding - Dijkstra", "bg-2");
    top.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    borderPane.setTop(top);

    VBox left = leftSizeToolBar();
    borderPane.setLeft(left);

    StackPane center = centerCanvas();
    borderPane.setCenter(center);

    VBox right = rightSizePathBar();
    borderPane.setRight(right);

    Label bottom = sample.Utility.createLabel("Built in conjunction with Dr.Andrew Rudder", "bg-6");
    bottom.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    borderPane.setBottom(bottom);

    return borderPane;
  }

  private StackPane centerCanvas() {
    StackPane center = new StackPane();
//    Button centerBtn = new Button("CANVAS");
    int width = 600, height = 500;
    this.canvas = new Canvas(width, height);
    gc = this.canvas.getGraphicsContext2D();
    gc.setStroke(Color.BLACK);
    updateCanvas();

    center.getStyleClass().add("bg-4");
    center.getChildren().add(this.canvas);

    return center;
  }

  private VBox leftSizeToolBar() {
    VBox toolbar = new VBox();

    Button loadBtn = sample.Utility.createBtn("Load");
    Button saveBtn = sample.Utility.createBtn("Save");

    loadBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File choice = fileChooser.showOpenDialog(mainStage);
        if (choice != null) {
          System.out.println(choice.getName());
          System.out.println(choice.getAbsolutePath());
          primaryMap = Utility.loadMap(choice.getAbsolutePath());
          primaryMap.displayRawInfo();
          updateCanvasMap();
        }
      }
    });

    saveBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Map");

        File file = fileChooser.showSaveDialog(mainStage);
        if (file != null) {
          Utility.saveMap(file.getAbsolutePath(), primaryMap);
        }
      }
    });

    toolbar.getChildren().addAll(loadBtn, saveBtn);
    toolbar.getStyleClass().add("bg-3");
    toolbar.setPrefWidth(100);
    return toolbar;
  }

  private VBox rightSizePathBar() {
    VBox pathbar = new VBox();
    Button recursive = sample.Utility.createBtn("Recursive");
    Button dijkstra = sample.Utility.createBtn("Dijkstra");

    recursive.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (primaryMap != null) {
          int startX = 0, startY = 0;
          int endX = primaryMap.getRows() - 1;
          int endY = primaryMap.getCols() - 1;
          int[][] path = Utility.intScratchPad(primaryMap, -1);

          Recursion recursion = new Recursion(primaryMap, path);
          if (recursion.solve(startX, startY, endX, endY)) {
            System.out.println("Path Detected");
            updateCanvas(path, 2, Color.BLUE);
          } else {
            System.out.println("No Path Detected");
          }
        }
      }
    });

    dijkstra.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (primaryMap != null) {
          int startX = 0;
          int startY = 0;
          int endX = primaryMap.getRows() - 1;
          int endY = primaryMap.getCols() - 1;

          int[][] path = Utility.intScratchPad(primaryMap, 0);
          Dijkstra dijkstra = new Dijkstra(primaryMap, path);
          if(dijkstra.solve(startX, startY, endX, endY)) {
            System.out.println("path found");
            updateCanvas(path, startX, startY, endX, endY, Color.BLUEVIOLET);
          } else {
            System.out.println("No Path Detected");
          }
        }
      }
    });

    pathbar.getChildren().addAll(recursive, dijkstra);
    pathbar.getStyleClass().add("bg-5");
    pathbar.setPrefWidth(100);
    return pathbar;
  }

  // prints the grid
  private void updateCanvas() {
    int incY = 10;
    int incX = 10;

    this.gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
    if (this.primaryMap != null) {
      incX = (int) this.canvas.getWidth() / this.primaryMap.getCols();
      incY = (int) this.canvas.getHeight() / this.primaryMap.getRows();
    }

    for (int i = 0; i < this.canvas.getWidth(); i += incX) {
      this.gc.strokeLine(i, 0, i, this.canvas.getHeight());
    }

    for (int i = 0; i < this.canvas.getHeight(); i += incY) {
      this.gc.strokeLine(0, i, this.canvas.getWidth(), i);
    }
  }

  // prints the wall of the maze
  private void updateCanvasMap() {
    int scaleX = (int) this.canvas.getWidth() / this.primaryMap.getCols();
    int scaleY = (int) this.canvas.getHeight() / this.primaryMap.getRows();

    gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
    if (this.primaryMap != null) {
      for (int row = 0; row < this.primaryMap.getRows(); row++) {
        for (int col = 0; col < this.primaryMap.getCols(); col++) {
          if (this.primaryMap.getValue(row, col, 0) == 1) {
            gc.strokeLine(col * scaleX, row * scaleY, col * scaleX + scaleX, row * scaleY);
            gc.strokeLine(col * scaleX, row * scaleY + scaleY, col * scaleX + scaleX, row * scaleY + scaleY);
            gc.strokeLine(col * scaleX, row * scaleY, col * scaleX, row * scaleY + scaleY);
            gc.strokeLine(col * scaleX + scaleX, row * scaleY, col * scaleX + scaleX, row * scaleY + scaleY);
          }
        }
      }
    }
  }

  // display a path
  private void updateCanvas(int[][] path, int value, Color color) {
    double scaleX = this.canvas.getWidth() / primaryMap.getCols();
    double scaleY = this.canvas.getHeight() / primaryMap.getRows();
    gc.setFill(color);
    for (int row = 0; row < primaryMap.getRows(); row++) {
      for (int col = 0; col < primaryMap.getCols(); col++) {
        if (path[row][col] == 2) {
          gc.fillOval(col * scaleX, row * scaleY, scaleX - 1, scaleY - 1);
        }
      }
    }
    gc.setFill(Color.BLACK);
  }

  // Dijkstra
  private void updateCanvas(int[][] path, int startRow, int startCol, int endRow, int endCol, Color color) {
    double scaleX = this.canvas.getWidth() / primaryMap.getCols();
    double scaleY = this.canvas.getHeight() / primaryMap.getRows();

    int currentRow = endRow;
    int currentCol = endCol;
    this.gc.setFill(color);

    while(currentRow != startRow || currentCol != startCol) {
      this.gc.fillOval(currentCol * scaleX, currentRow*scaleY, scaleX-1, scaleY-1);
      switch(path[currentRow][currentCol]) {
        case 1:
          currentRow--;
          break;
        case 2:
          currentCol++;
          break;
        case 3:
          currentRow++;
          break;
        case 4:
          currentCol--;
          break;
        default:
          System.out.println("Default Reach. Check what you are passing");

      }
    }
    this.gc.setFill(Color.BLACK);
  }
}
