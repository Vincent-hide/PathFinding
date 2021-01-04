package sample.mapdata;

public class Map {
  private String name;
  private Cell[][] mapData;
  private int rows, cols;
  private double unit;

  public Map(String name, int rows, int cols, double unit) {
    this.name = name;
    this.mapData = new Cell[rows][cols];
    this.rows = rows;
    this.cols = cols;
    this.unit = unit;
  }

  // for debugging
  public void displayRawInfo() {
    System.out.printf("Map Name: %s\n", this.name);
    System.out.printf("Rows: %s\nColumns: %s\n", this.rows, this.cols);
    for(int i=0; i<this.rows; i++) {
      for(int v=0; v<this.cols; v++) {
        System.out.printf(" %d", this.mapData[i][v].getLayerValue());
      }
      System.out.println("");
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Cell[][] getMapData() {
    return mapData;
  }

  public void setMapData(int row, int col, Cell value) {
    this.mapData[row][col] = value;
  }

  public void setMapData(Cell[][] mapData) {
    this.mapData = mapData;
  }

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getCols() {
    return cols;
  }

  public void setCols(int cols) {
    this.cols = cols;
  }

  public double getUnit() {
    return unit;
  }

  public void setUnit(double unit) {
    this.unit = unit;
  }

  public int getValue(int row, int col, int index) {
    return this.mapData[row][col].getLayerValue(index);
  }
}
