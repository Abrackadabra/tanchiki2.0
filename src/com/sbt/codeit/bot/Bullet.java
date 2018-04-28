package com.sbt.codeit.bot;

public class Bullet {
  Point p;
  Direction d = Direction.NONE;

  Bullet(Point p) {
    this.p = p;
  }

  Bullet(Point p, Direction d) {
    this.p = p;
    this.d = d;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return  (p.equals(((Bullet)o).p) && d == ((Bullet)o).d);
  }
}
