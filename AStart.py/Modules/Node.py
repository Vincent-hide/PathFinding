import pygame
from colors import COLORS

class Node:
  def __init__(self, row, col, width, height, total_rows, total_cols):
    self.row = row
    self.col = col
    self.x = row * width
    self.y = col * height
    self.color = COLORS["WHITE"]
    self.neighbor = []
    self.width = width
    self.height = height
    self.total_rows = total_rows
    self.total_cols = total_cols

  def get_pos(self):
    return self.row, self.col

  def is_closed(self):
    return self.color == COLORS["RED"]

  def is_open(self):
    return self.color == COLORS["GREEN"]

  def is_barrier(self):
    return self.color == COLORS["GREY"]

  def is_start(self):
    return self.color == COLORS["ORANGE"]

  def is_end(self):
    return self.color == COLORS["TURQUOISE"]

  def reset(self):
    self.color = COLORS["WHITE"]

  def make_close(self):
    self.color = COLORS["RED"]

  def make_open(self):
    self.color = COLORS["GREEN"]

  def make_barrier(self):
    self.color = COLORS["GREY"]

  def make_start(self):
    self.color = COLORS["ORANGE"]

  def make_end(self):
    self.color = COLORS["TURQUOISE"]

  def make_path(self):
    self.color = COLORS["PURPLE"]

  def draw(self, win):
    pygame.draw.rect(win, self.color, (self.x, self.y, self.width, self.height))

  def update_neighbors(self, grid):
    self.neighbors = []
    if self.row < self.total_rows - 1 and not grid[self.row + 1][self.col].is_barrier():  # DOWN
      self.neighbors.append(grid[self.row + 1][self.col])

    if self.row > 0 and not grid[self.row - 1][self.col].is_barrier():  # UP
      self.neighbors.append(grid[self.row - 1][self.col])

    if self.col < self.total_rows - 1 and not grid[self.row][self.col + 1].is_barrier():  # RIGHT
      self.neighbors.append(grid[self.row][self.col + 1])

    if self.col > 0 and not grid[self.row][self.col - 1].is_barrier():  # LEFT
      self.neighbors.append(grid[self.row][self.col - 1])

  def __lt__(self, other):
    return False

  def h(point1, point2):
    x1, y1 = point1
    x2, y2 = point2
    return abs(x1 - x2) + abs(y1 - y2)
