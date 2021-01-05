package sample.mapdata.method;

import sample.mapdata.Map;

public class Recursion extends MazeSolver {

  public Recursion(Map map, int[][] values) {
    super(map, values);
  }

  public boolean solve(int currentRow, int currentCol, int endRow, int endCol) {
    // === base cases
    // out of bounds
    if(currentRow < 0 || currentCol < 0 || currentRow >= map.getRows() || currentCol >= map.getCols()) {
      return false;
    }

    // reached the destination
    if(currentRow == endRow && currentCol == endCol) {
      return true;
    }

    // reached to the wall
    if(map.getMapData()[currentRow][currentCol].getLayerValue() == 1) {
      return false;
    }

    // have been here before
    if(this.values[currentRow][currentCol] == 1) {
      return false;
    }
    // base cases ===

    // make as visited
    this.values[currentRow][currentCol] = 1;

    // north
    if(recursive(map, this.values, currentRow-1, currentCol, endRow, endCol)) {
      this.values[currentRow][currentCol] = 2; // make as correct path
      return true;
    }

    // east
    if(recursive(map, this.values, currentRow, currentCol+1, endRow, endCol)) {
      this.values[currentRow][currentCol] = 2; // make as correct path
      return true;
    }

    // south
    if(recursive(map, this.values, currentRow+1, currentCol, endRow, endCol)) {
      this.values[currentRow][currentCol] = 2; // make as correct path
      return true;
    }

    // east
    if(recursive(map, this.values, currentRow, currentCol-1, endRow, endCol)) {
      this.values[currentRow][currentCol] = 2; // make as correct path
      return true;
    }

    return false;
  }

  public static boolean recursive(Map map, int[][] temp, int currentRow, int currentCol, int endRow, int endCol) {
    // === base cases
    // out of bounds
    if(currentRow < 0 || currentCol < 0 || currentRow >= map.getRows() || currentCol >= map.getCols()) {
      return false;
    }

    // reached the destination
    if(currentRow == endRow && currentCol == endCol) {
      return true;
    }

    // reached to the wall
    if(map.getMapData()[currentRow][currentCol].getLayerValue() == 1) {
      return false;
    }

    // have been here before
    if(temp[currentRow][currentCol] == 1) {
      return false;
    }
    // base cases ===

    // make as visited
    temp[currentRow][currentCol] = 1;

    // north
    if(recursive(map, temp, currentRow-1, currentCol, endRow, endCol)) {
      temp[currentRow][currentCol] = 2; // make as correct path
      return true;
    }

    // east
    if(recursive(map, temp, currentRow, currentCol+1, endRow, endCol)) {
      temp[currentRow][currentCol] = 2; // make as correct path
      return true;
    }

    // south
    if(recursive(map, temp, currentRow+1, currentCol, endRow, endCol)) {
      temp[currentRow][currentCol] = 2; // make as correct path
      return true;
    }

    // east
    if(recursive(map, temp, currentRow, currentCol-1, endRow, endCol)) {
      temp[currentRow][currentCol] = 2; // make as correct path
      return true;
    }

    return false;
  }
}
