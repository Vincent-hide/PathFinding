package sample.mapdata;

import java.io.*;

// Static class which holds utility helper functions
public class Utility {
  public static Map loadMap(String file) {

    try {
      String name;
      int row, col;
      double unit;
      BufferedReader br = new BufferedReader(new FileReader(file));


      name = br.readLine();
      String[] data = br.readLine().split(" ");
      row = Integer.parseInt(data[0]);
      col = Integer.parseInt(data[1]);
      unit = Integer.parseInt(data[2]);

      Map newMap = new Map(name, row, col, unit);

      for(int i=0; i<row; i++) {
        data = br.readLine().split(" ");
        for(int v=0; v<col; v++) {
          newMap.setMapData(i, v, new Cell(Integer.parseInt(data[v])));
        }
      }
      br.close();
      return newMap;
    } catch(IOException e) {
      System.out.println("File not found. ERROR: " + e);
    }
    return null;
  }

  public static void saveMap(String filename, Map mapData) {
    try {
      int layerIndex = 0;
      String name;
      int row, col;
      double unit;
      BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
      bw.write(mapData.getName());
      bw.newLine();
      bw.write(mapData.getRows() + " " + mapData.getCols() + " " + mapData.getUnit());
      bw.newLine();

      for(int i=0; i<mapData.getRows(); i++) {
        for(int v=0; v<mapData.getCols(); v++) {
          bw.write(mapData.getValue(i, v, layerIndex));
        }
        bw.newLine();
      }
    } catch(IOException e) {
      System.out.println("File not found. ERROR: " + e);
    }
  }

  public static void visualizeMap(Map map, String unicode) {
    for(int i=0; i < map.getRows(); i++) {
      for(int v=0; v<map.getCols(); v++) {
        if(map.getValue(i, v, 0) == 1) {
          // 1: make a wall
          System.out.println(unicode);
        } else {
          System.out.println(" ");
        }
      }
      System.out.println("");
    }
  }
}
