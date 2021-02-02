import pygame

from colors import COLORS
from Modules.Node import Node

class Board():

  def __init__(self):
    self.width = 0
    self.height = 0
    self.rows = 0
    self.cols = 0


  def __init__(self, width, height, rows, cols):
    self.width = width
    self.height = height
    self.rows = rows
    self.cols = cols
    self.screen = pygame.display.set_mode((width, height))

  def __str__(self):
    return """
      --- Board Info ---
      Width:  %.2f
      Height: %.2f
      rows:  %.2f
      cols: %.2f
      """ %(self.width, self.height, self.rows, self.cols)

  def make_grid(self):
    grid = []
    node_width = self.width // self.rows
    node_height = self.height // self.cols

    print(node_width, node_height)
    for i in range(node_width):
      grid.append([])
      for s in range(node_height):
        node = Node(i, s, node_width, node_height, self.rows, self.cols)
        grid[i].append(node)
    return grid

  def draw_grid(self):
    grid_width = self.width // self.rows
    grid_height = self.height // self.cols

    for i in range(self.rows):
      pygame.draw.line(self.screen, COLORS["BLACK"], (0, i * grid_width), (self.width, i * grid_width))
      for s in range(self.cols):
        pygame.draw.line(self.screen, COLORS["BLACK"], (s * grid_height, 0), (s * grid_height, self.height))

  def draw(self, grid):
    self.screen.fill(COLORS["ORANGE"])

    for row in grid:
      for spot in row:
        spot.draw(self.screen)

    self.draw_grid()
    pygame.display.update()


  def get_clicked_position(self, postion):
    gap = self.width // self.rows
    y, x = postion

    row = y // gap
    col = x // gap

    return row, col

  def run(self):
    grid = self.make_grid()

    start = None
    end = None

    run = True
    started = False

    while run:
      self.draw(grid)
      for event in pygame.event.get():
        if event.type == pygame.QUIT:
          run = False

        if started:
          continue

        # LEFT CLICK
        if pygame.mouse.get_pressed()[0]:
          position = pygame.mouse.get_pos()
          row, col = self.get_clicked_position(position)
          spot = grid[row][col]
          if not start and spot != end: # if start is not selected & the cell clicked is not end node
            start = spot
            start.make_start()
          elif not end and spot != start: # if end is not selected & the cell clicked is not start node
            end = spot
            end.make_end()

          elif spot != end and spot != start:
            spot.make_barrier()

        # RIGHT CLICK
        elif pygame.mouse.get_pressed()[2]:
          position = pygame.mouse.get_pos()
          row, col = self.get_clicked_position(position)
          spot = grid[row][col]
          spot.reset()
          if spot == start:
            start = None
          elif spot == end:
            end = None

    # pygame.quit()