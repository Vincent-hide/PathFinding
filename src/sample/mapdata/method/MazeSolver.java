package sample.mapdata.method;

import sample.mapdata.Map;

abstract public class MazeSolver {
  Map map;
  int[][] values;

  MazeSolver(Map map, int[][] values) {
    this.map = map;
    this.values = values;
  }

  abstract boolean solve(int row, int col, int endRow, int endCol);
}
