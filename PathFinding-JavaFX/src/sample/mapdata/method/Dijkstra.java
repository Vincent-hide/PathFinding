package sample.mapdata.method;

import sample.mapdata.Map;
import sample.mapdata.Utility;
import sample.mapdata.minheap.MinHeap;
import sample.mapdata.minheap.Position;

public class Dijkstra extends MazeSolver {

  enum Direction {
    NOWHERE(0), NORTH(1), EAST(2), SOUTH(3), WEST(4);

    int value;

    Direction(int value) {
      this.value = value;
    }
  }

  public Dijkstra(Map map, int[][] values) {
    super(map, values);
  }

  private String printValueBoard() {
    String s = "";
    for (int i = 0; i < this.map.getRows(); i++) {
      for (int v = 0; v < this.map.getCols(); v++) {
        s += String.format(" %d ", this.values[i][v]);
      }
      s += "\n";
    }
    return s;
  }

  private boolean isValid(int row, int col) {
    if (row < 0 || col < 0 || row >= this.map.getRows() || col >= this.map.getCols() || this.map.getMapData()[row][col].getLayerValue() == 1) {
      return false;
    }
    return true;
  }

  private void findBestRoute(MinHeap pq, int direction, int[][] cost, int currentRow, int currentCol, int newCost) {
    if (this.isValid(currentRow, currentCol) && newCost < cost[currentRow][currentCol]) {
      // found better route to west
      cost[currentRow][currentCol] = newCost;
      this.values[currentRow][currentCol] = direction; // east: update where it came from
      pq.enqueue(currentRow, currentCol, newCost);
    }
  }

  public boolean solve(int startRow, int startCol, int endRow, int endCol) {
    int newCost, cheapestCost, cr, cc;

    // reached to a wall
    if (this.map.getMapData()[startRow][startCol].getLayerValue() == 1 || this.map.getMapData()[endRow][endCol].getLayerValue() == 1) {
      return false;
    }

    // will never reach to (col * row) + 1
    int infinity = this.map.getCols() * this.map.getRows() + 1;

    // create a new 2d array with an initial value as an infinity
    int[][] cost = Utility.intScratchPad(this.map, infinity);

    cost[startRow][startCol] = 0;
    int currentRow = startRow;
    int currentCol = startCol;

    MinHeap pq = new MinHeap(infinity);

    pq.enqueue(currentRow, currentCol, 0);

    while (!pq.isEmpty()) {
      // dequeue
      Position dequeuedPosition = pq.dequeue();
      currentRow = dequeuedPosition.getRow();
      currentCol = dequeuedPosition.getCol();

      // undirected graph with a weight of 1
      newCost = cost[currentRow][currentCol] + 1;

      // to find the best route

      /*
       * 0 => from No where
       * 1 => from North
       * 2 => from East
       * 3 => from South
       * 4 => from West
       * */


      this.findBestRoute(pq, Direction.SOUTH.value, cost, currentRow, currentCol, newCost);

//      if (this.isValid(currentRow - 1, currentCol) && newCost < cost[currentRow - 1][currentCol]) {
//        // found better route to north
//        cost[currentRow - 1][currentCol] = newCost;
//        this.values[currentRow - 1][currentCol] = Direction.SOUTH.value; // south: update where it came from
//        pq.enqueue(currentRow - 1, currentCol, newCost);
//      }

      if (this.isValid(currentRow, currentCol + 1) && newCost < cost[currentRow][currentCol + 1]) {
        // found better route to east
        cost[currentRow][currentCol + 1] = newCost;
        this.values[currentRow][currentCol + 1] = Direction.WEST.value; // west: update where it came from
        pq.enqueue(currentRow, currentCol + 1, newCost);
      }

      if (this.isValid(currentRow + 1, currentCol) && newCost < cost[currentRow + 1][currentCol]) {
        // found better route to south
        cost[currentRow + 1][currentCol] = newCost;
        this.values[currentRow + 1][currentCol] = Direction.NORTH.value; // north: update where it came from
        pq.enqueue(currentRow + 1, currentCol, newCost);
      }

      if (this.isValid(currentRow, currentCol - 1) && newCost < cost[currentRow][currentCol - 1]) {
        // found better route to west
        cost[currentRow][currentCol - 1] = newCost;
        this.values[currentRow][currentCol - 1] = Direction.EAST.value; // east: update where it came from
        pq.enqueue(currentRow, currentCol - 1, newCost);
      }
    }

    return cost[endRow][endCol] != infinity;
  }
}
