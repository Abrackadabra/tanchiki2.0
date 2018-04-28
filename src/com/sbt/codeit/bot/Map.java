package sberkek;

import java.util.ArrayList;
import java.util.List;

public class Map {
  int n;
  int m;

  boolean[][] walls;
  List<Point> bullets = new ArrayList<>();
  List<Point> heals = new ArrayList<>();
  List<Point> mines = new ArrayList<>();
  List<Point> bases = new ArrayList<>();

  public Map(String[] lines, int n, int m) {
    this.n = n;
    this.m = m;

    walls = new boolean[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        char c = lines[i].charAt(j);

        if (c == '#') {
          walls[i][j] = true;
        }

        if ('0' <= c && c <= '9') {

        }

        if ('A' <= c && c <= 'Z') {

        }
      }
    }
  }

}
