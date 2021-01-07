package sample.mapdata.minheap;

public class MinHeap {
  private Node[] heapData;
  private int max;
  private int lastIndex;

  public MinHeap(int size) {
    this.lastIndex = -1;
    this.max = size;
    this.heapData = new Node[size];
  }

  public boolean isEmpty() {
    return this.lastIndex == -1;
  }

  public boolean isFull() {
    return this.lastIndex == (this.max - 1);
  }

  public boolean enqueue(int row, int col, double cost) {
    Node temp = new Node(row, col, cost);
    if (this.lastIndex < this.max - 1) {
      this.lastIndex++;
      this.heapData[this.lastIndex] = temp;
      int currentIndex = this.lastIndex;
      int parent = (currentIndex - 1) / 2;
      while (currentIndex != 0 && this.heapData[parent].getCost() > temp.getCost()) {
        this.heapData[currentIndex] = this.heapData[parent];
        currentIndex = parent;
        parent = (currentIndex - 1) / 2;
      }
      this.heapData[currentIndex] = temp;
      return true;
    } else {
      // not more space in the heap
      return false;
    }
  }

  public Position dequeue() {
    if (!this.isEmpty()) {
      Position dequeued = this.heapData[0].getLocation();
      this.heapData[0] = this.heapData[this.lastIndex];
      this.lastIndex--;
      if (this.lastIndex > 0) {
        this.shiftDown(0);
      }
      return dequeued;
    }
    return null;
  }

  public void shiftDown(int currentIndex) {
    int leftIndex, rightIndex, smallerIndex;
    Node temp;
    leftIndex = currentIndex * 2 + 1;
    rightIndex = currentIndex * 2 + 2;

    if (rightIndex > lastIndex) {
      if (leftIndex > lastIndex) {
        return;
      } else {
        smallerIndex = leftIndex;
      }
    } else {
      if (this.heapData[leftIndex].getCost() < this.heapData[rightIndex].getCost()) {
        smallerIndex = leftIndex;
      } else {
        smallerIndex = rightIndex;
      }
    }

    if (this.heapData[currentIndex].getCost() > this.heapData[smallerIndex].getCost()) {
      temp = this.heapData[currentIndex];
      this.heapData[currentIndex] = this.heapData[smallerIndex];
      this.heapData[smallerIndex] = temp;
      this.shiftDown(smallerIndex);
    }
  }

  public String toString() {
    String s = "";
    for (Node val : this.heapData) {
      s = String.format("%s ", val);
    }
    return s + "\n";
  }
}

// created by Suho Kang in conjunction with Andrew Rudder
// created by Suho Kang in coraboration with Andrew Rudder