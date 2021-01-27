package sample.mapdata;

public class Cell {
  private int numLayers;
  private Layer[] layers;

  public Cell(int value) {
    this.numLayers = 1;
    this.layers = new Layer[3];
    this.layers[0] = new Layer("Collision", value);
  }

  public void setLayer(int value) {
    this.layers[0].setValue(value);
  }

  public boolean setLayer(int index, int value) {
    if(index >= this.numLayers || index < 0) {
      return false;
    }
    this.layers[index].setValue(value);
    return true;
  }

  public boolean setLayer(String name, int value) {
    int loc = this.getLayerValue(name);
    if(loc != -1) {
      this.layers[loc].setValue(value);
      return true;
    }
    return false;
  }

  public int getNumLayers() {
    return numLayers;
  }

  public void setNumLayers(int numLayers) {
    this.numLayers = numLayers;
  }

  public int getLayerValue() {
    return this.layers[0].getValue();
  }

  public int getLayerValue(int index) {
    return this.layers[index].getValue();
  }

  private int getLayerIndex(String name) {
    for (int i = 0; i < this.numLayers; i++) {
      if (this.layers[i].getName().equals(name)) {
        return i;
      }
    }
    return -1;
  }

  public int getLayerValue(String name) {
    int loc = this.getLayerIndex(name);
    if (loc == -1) return -1;
    return this.layers[loc].getValue();
  }
}
