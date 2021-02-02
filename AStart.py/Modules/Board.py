import pygame

from colors import COLORS
from Modules.Node import Node
from queue import PriorityQueue

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

  def algorithm(self, draw, grid, start, end):
    count = 0
    visited = PriorityQueue()
    # A* Start, count, node
    visited.put((0, count, start))
    # to trace back where a node came from 
    trace = {}
    # cost: set the code of all the nodes to 0
    cost = {spot: float("inf") for row in grid for spot in row}
    # cost of start node is 0
    cost[start] = 0
    # A* Score: set the A* Score of all the nodes to 0
    A_Score = {spot: float("inf") for row in grid for spot in row}
    # A* Score = Heuristic + Cost
    A_Score[start] = self.heuristic(start.get_pos(), end.get_pos())

    # since PriorityQueue does not have function to tell us if a node is visited or not
    visited_hash = {start}

    while not visited.empty():
      for event in pygame.event.get():
        if event.type == pygame.QUIT:
          pygame.quit()

      curr_node = visited.get()[2] # node is stored at the index of 2
      visited_hash.remove(curr_node)

      if curr_node == end:
        return True

      for neighbor in curr_node.neighbors:
        temp_cost = cost[curr_node] + 1

        # if the temp cost is lesser than the cost score previously
        if temp_cost < cost[neighbor]:
          # update
          trace[neighbor] = curr_node
          cost[neighbor] = temp_cost
          A_Score[neighbor] = temp_cost + self.heuristic(neighbor.get_pos(), end.get_pos())

          if neighbor not in visited_hash:
            count += 1
            visited.put((A_Score[neighbor], count, neighbor))
            visited_hash.add(neighbor)
            neighbor.make_open()
      draw()

      if curr_node != start:
        curr_node.make_open()

    return False

  # estimate how much cost to get to the goal from a given coordinate
  def heuristic(self, p1, p2):
    x1, y1 = p1
    x2, y2 = p2
    return abs(x1 - x2) + abs(y1 - y2)

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

        # if keyboard down is pressed
        if event.type == pygame.KEYDOWN:
          if event.key == pygame.K_SPACE and not started:
            for row in grid:
              for spot in row:
                spot.update_neighbors(grid)
            self.algorithm(lambda: self.draw(grid), grid, start, end)
    # pygame.quit()