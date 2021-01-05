package sample.mapdata.minheap;

public class Node {
  private Position location;
  private double cost;

  public Node(int row, int col, double cost) {
    this.location = new Position(row, col);
    this.cost = cost;
  }

  public String toString() {
    return String.format("(Position %s - Cost %.2f)", this.location, this.cost);
  }

  public Position getLocation() {
    return location;
  }

  public void setLocation(Position location) {
    this.location = location;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }
}
