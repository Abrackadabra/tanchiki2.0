package com.sbt.codeit.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Map {
  int n;
  int m;

  public static int MAX_STEP = 100;

  boolean[][] walls;
  List<Bullet> bullets = new ArrayList<>();
  List<Point> heals = new ArrayList<>();
  List<Point> mines = new ArrayList<>();
  List<Map> next = new ArrayList<>();
  Character myBaseId;
  Character myId;
  Character notMyBaseId;
  Character notMyId;
  Point myBase;
  Point notMyBase;
  Point myBoat;
  Point notMyBoat;
  private HashMap<Point, Integer> distances;
  private HashMap<Point, Direction> dirCameFrom;

  public boolean isInside(Point p) {
    return p.x >= 0 && p.x < n
        && p.y >= 0 && p.y < m;
  }

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
          bullets.add(new Bullet(new Point(i, j)));
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

    extrapolate();
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
    for (Bullet b : bullets) {
      map[b.p.x][b.p.y] = '*';
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
      result += Arrays.toString(map[i]) + '\n';
    }
    return result;
  }

  void detectEverything(Map prevMap) {
    // TODO

  }

  void bfs() {
    Point start = myBoat;

    distances = new HashMap<>();
    dirCameFrom = new HashMap<>();
    Queue<Point> queue = new LinkedList<>();

    queue.add(start);
    distances.put(start, 0);
    dirCameFrom.put(start, null);

    while (!queue.isEmpty()) {
      Point p = queue.poll();
      int d = distances.get(p);

      for (Direction direction : Direction.values()) {
        Point q = p.add(direction);

        if (!isInside(q)) {
          continue;
        }

        if (!distances.containsKey(q)) {
          distances.put(q, d + 1);
          dirCameFrom.put(q, direction.reverse());
          queue.add(q);
        }
      }
    }
  }

  Direction whichWayToGoTo(Point target) {
    if (!distances.containsKey(target)) {
      throw new IllegalArgumentException("unreachable " + target);
    }

    Point t = target;
    Direction dir = null;

    while (distances.get(t) > 0) {
      dir = dirCameFrom.get(t);
      t = t.add(dir);
    }

    return dir.reverse();
  }

  private void extrapolate() {
    for (int i = 0; i < MAX_STEP; i++) {

    }
  }

  Map getExtrapolated(int index) {
    if (index < 0 || index >= MAX_STEP) {
      return null;
    }
    return next.get(index);
  }


}
