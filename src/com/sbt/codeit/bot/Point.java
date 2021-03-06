package com.sbt.codeit.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point {
  public final int x;
  public final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Point point = (Point) o;
    return x == point.x &&
        y == point.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  public int dx(Point o) {
    return o.x - x;
  }

  public int dy(Point o) {
    return o.y - y;
  }

  public int mDistance(Point o) {
    return Math.abs(x - o.x) + Math.abs(y - o.y);
  }

  public Point add(Direction dir) {
    return new Point(x + dir.dx, y + dir.dy);
  }

  public List<Point> neighbours() {
    List<Point> r = new ArrayList<>();
    for (Direction direction : Direction.values()) {
      r.add(this.add(direction));
    }
    return r;
  }

  public Direction whereToLook(Point o) {
    Direction result = null;
    if (this.x == o.x) {
      if (this.y > o.y) {
        result = Direction.LEFT;
      } else {
        result = Direction.RIGHT;
      }
    }
    if (this.y == o.y) {
      if (this.x > o.x) {
        result = Direction.UP;
      } else {
        result = Direction.DOWN;
      }
    }
    if (this.equals(o)) {
      result = null;
    }
    return result;
  }
}
