package com.sbt.codeit.bot;

public enum Direction {
  NONE(0, 0),
  UP(-1, 0),
  DOWN(1, 0),
  LEFT(0, -1),
  RIGHT(0, 1);
  int dx, dy;

  Direction(int dx, int dy) {
    this.dx = dx;
    this.dy = dy;
  }

  Direction reverse() {
    switch (this) {
      case UP:
        return DOWN;
      case DOWN:
        return UP;
      case LEFT:
        return RIGHT;
      case RIGHT:
        return LEFT;
      case NONE:
        return NONE;
    }

    return null;
  }
}
