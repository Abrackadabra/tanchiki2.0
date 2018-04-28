package com.sbt.codeit.bot;

import java.util.ArrayList;
import java.util.List;

public class Map {
  int n;
  int m;

  boolean[][] walls;
  List<Point> bullets = new ArrayList<>();
  List<Point> heals = new ArrayList<>();
  List<Point> mines = new ArrayList<>();
  Character myBaseId;
  Character myId;
  Character notMyBaseId;
  Character notMyId;
  Point myBase;
  Point notMyBase;
  Point myBoat;
  Point notMyBoat;

  public Map(ArrayList<ArrayList<Character>> map, Character baseId, Character id) {

    this.n = map.size();
    this.m = map.get(0).size();

    myId = id;
    myBaseId = baseId;

    walls = new boolean[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        char c = map.get(i).get(j);

        if (c == '#') {
          walls[i][j] = true;
        }

        if (c == '*') {
          bullets.add(new Point(i, j));
        }

        if (c == id) {
          myBoat = new Point(i, j);
        } else if ('0' <= c && c <= '9') {
          notMyBoat = new Point(i, j);
          notMyId = c;
        }

        if (c == 'H') {
          heals.add(new Point(i, j));
        } else if (c == 'X') {
          mines.add(new Point(i, j));
        } else if (c == baseId) {
          myBase = new Point(i, j);
        } else if ('A' <= c && c <= 'Z') {
          notMyBase = new Point(i, j);
          notMyBaseId = c;
        }
      }
    }
  }

  public String toString() {
    Character[][] map = new Character[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (walls[i][j]) {
          map[i][j] = '#';
        }
      }
    }
    for (Point p : bullets) {
      map[p.x][p.y] = '*';
    }
    for (Point p : heals) {
      map[p.x][p.y] = 'H';
    }
    for (Point p : mines) {
      map[p.x][p.y] = 'X';
    }
    map[myBase.x][myBase.y] = myBaseId;
    map[notMyBase.x][notMyBase.y] = notMyBaseId;
    map[myBoat.x][myBoat.y] = myId;
    map[notMyBoat.x][notMyBoat.y] = notMyId;

    String result = "";
    for (int i = 0; i < n; i++) {
      result += map[i].toString() + '\n';
    }
    return result;
  }

  void detectBullets(Map prevMap) {
    // TODO
  }

}
