import pygame

from Modules.Board import Board
pygame.display.set_caption("A* Path Finding Algorithm")

width = 800
height = 800
rows = 25
cols = 25
board = Board(width, height, rows, cols)
print(board)

board.run()