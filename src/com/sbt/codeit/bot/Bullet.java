package com.sbt.codeit.bot;

public class Bullet {
  Point p;
  Direction d;

  Bullet(Point p) {
    this.p = p;
  }

  Bullet(Point p, Direction d) {
    this.p = p;
    this.d = d;
  }
  
  public boolean hasDirection() {
    return d != null;
  }
}
