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
  Character notMyBaseId;
  Character myBaseId;
  Point myBase;
  Point notMyBase;
  Boat myBoat;
  Boat notMyBoat;

  private HashMap<Point, Integer> distances;
  private HashMap<Point, Direction> dirCameFrom;

  public boolean isInside(Point p) {
    return p.x >= 0 && p.x < n
        && p.y >= 0 && p.y < m;
  }

  public Map() {
  }

  public Map(ArrayList<ArrayList<Character>> map, Character baseId, Character id) {

    this.n = map.size();
    this.m = map.get(0).size();

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
          myBoat = new Boat(new Point(i, j), c);
        } else if ('0' <= c && c <= '9') {
          notMyBoat = new Boat(new Point(i, j), c);
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
    map[myBoat.p.x][myBoat.p.y] = myBoat.id;
    map[notMyBoat.p.x][notMyBoat.p.y] = notMyBoat.id;

    String result = "";
    for (int i = 0; i < n; i++) {
      result += Arrays.toString(map[i]) + '\n';
    }
    return result;
  }

  void detectEverything(Map prevMap) {
    notMyBoat.d = Direction.NONE;
    for (Direction direction : Direction.values()) {
      if (prevMap.notMyBoat.p.add(direction).equals(notMyBoat.p)) {
        notMyBoat.d = direction;
      }
    }

    Map extr = prevMap.getExtrapolated(1);
    for (Bullet eb : extr.bullets) {
      for (Bullet b : bullets) {
        if (b.p.equals(eb.p)) {
          b.d = eb.d;
        }

        if (eb.d == Direction.NONE && b.d == Direction.NONE) {
          for (Direction direction : Direction.values()) {
            if (eb.p.add(direction).add(direction).equals(b.p)) {
              b.d = direction;
            }
          }
        }
      }
    }
  }

  void bfs() {
    Point start = myBoat.p;

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

  void extrapolate() {
    next.clear();
    next.add(this);
    for (int i = 1; i < MAX_STEP; i++) {
      Map prev = next.get(i - 1);
      Map next = new Map();
      next.n = this.n;
      next.m = this.m;
      next.mines = new ArrayList<>(prev.mines);
      this.bullets = new ArrayList<>();
      for (Bullet b : prev.bullets) {
        Bullet nextB = extrapolateBullet(b);
        if (isInside(nextB.p)) {
          next.bullets.add(extrapolateBullet(b));
        }
      }
      extrapolateEnemy(prev, next);
      next.walls = prev.walls;
      next.myBase = prev.myBase;
      next.notMyBase = prev.notMyBase;
      this.next.add(next);
    }
  }

  boolean isShoot(Bullet bullet, Boat enemy) {
    // TODO: 4/28/2018
    return true;
  }


  void extrapolateEnemy(Map prev, Map next) {
    Boat notMyBoat = prev.notMyBoat;
    Point nextEnemyPoint = (notMyBoat.d == null ? this.notMyBoat.p : notMyBoat.p.add(notMyBoat.d));
    if (isInside(nextEnemyPoint)) {
      Boat nextEnemyBoat = null;
      if (notMyBoat.d == null) {
        nextEnemyBoat = new Boat(nextEnemyPoint, notMyBoat.id);
      } else {
        nextEnemyBoat = new Boat(nextEnemyPoint, notMyBoat.d, notMyBoat.id);
      }
      next.notMyBoat = nextEnemyBoat;
    } else {
      Point nextEnemy = new Point(notMyBoat.p.x, notMyBoat.p.y);
      next.notMyBoat = new Boat(nextEnemy, notMyBoat.id);
    }
  }

  Bullet extrapolateBullet(Bullet prev) {
    return new Bullet(prev.p.add(prev.d), prev.d);
  }

  Map getExtrapolated(int index) {
    if (index < 0 || index >= MAX_STEP) {
      throw new IndexOutOfBoundsException("Extrapolated map out of bound");
    }
    return next.get(index);
  }
}
