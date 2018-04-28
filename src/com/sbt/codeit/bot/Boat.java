package com.sbt.codeit.bot;

public class Boat {
  Point p;
  Direction d = Direction.NONE;
  Character id;

  Boat(Point p, Direction d, Character id) {
    this.p = p;
    this.d = d;
    this.id = id;
  }

  Boat(Point p, Character id) {
    this.p = p;
    this.id = id;
  }

  public boolean isMine(Character id) {
    return this.id == id;
  }


}
