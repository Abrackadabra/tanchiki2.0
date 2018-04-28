package com.sbt.codeit.bot;

import com.sbt.codeit.core.control.GameController;
import com.sbt.codeit.core.control.ServerListener;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Bot implements ServerListener {

  void info(Object... objects) {
    System.out.print(myName + ": ");
    for (Object object : objects) {
      System.out.print(object.toString() + " ");
    }
    System.out.println();
  }

  private GameController controller;
  private Character ourTankSymbol;
  private Character ourBaseSymbol;
  private int cooldown = 0;

  public Bot(GameController controller) {
    this.controller = controller;
  }

  public void setOurTankSymbol(Character ourTankSymbol) {
    this.ourTankSymbol = ourTankSymbol;
  }

  public void setOurBaseSymbol(Character ourBaseSymbol) {
    this.ourBaseSymbol = ourBaseSymbol;
  }

  int tick = 0;
  Map map;
  Map prevMap;

  public void rotateSelf(Direction direction) throws RemoteException {
    switch (direction) {
      case UP:
        controller.up(this);
        break;
      case DOWN:
        controller.down(this);
        break;
      case LEFT:
        controller.left(this);
        break;
      case RIGHT:
        controller.right(this);
        break;
    }
  }


  public void update(ArrayList<ArrayList<Character>> arrayList) throws RemoteException {
    try {
      info("Start of tick", tick);

      ////////////////////////////////////////////// init

      prevMap = map;
      map = new Map(arrayList, ourBaseSymbol, ourTankSymbol);

      map.bfs();
      map.extrapolate();
      map.detectEverything(prevMap);


      ////////////////////////////////////////////// logic


//      controller.start(this); //начинаем ехать сразу и больше не останавливаемся
//      if (tick % 10 == 0) {
//        controller.right(this); //поворачиваем направо
//      }
//      if (tick % 10 == 5) {
//        controller.left(this); //поворачиваем направо
//      }
//      if (tick % 10 == 7) {
//        controller.putMine(this);
//        info("put mine");
//      }

      Point notMyBase = map.notMyBase;

      Point closestReachable = null;

      for (java.util.Map.Entry<Point, Integer> pointIntegerEntry : map.distances.entrySet()) {
        Point p = pointIntegerEntry.getKey();
        int dist = pointIntegerEntry.getValue();

        if (closestReachable == null || closestReachable.mDistance(notMyBase) > p.mDistance(notMyBase)) {
          closestReachable = p;
        }
      }

      Direction whereToLook = map.myBoat.p.whereToLook(map.notMyBase);

      if (whereToLook != null && cooldown == 0) {
        rotateSelf(whereToLook);
        controller.fire(this);
        cooldown = 10;
      }

      if (cooldown)


      ////////////////////////////////////////////// update

      tick++;
      cooldown = Math.max(0, cooldown - 1);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //  String myName = "Sberkek strikes back " + randomName();
  String myName = "Sksb" + randomName();

  public String randomName() {
    String s = "";
    for (int i = 0; i < 10; i++) {
      s += (char) ('A' + Math.random() * 26);
    }
    return s;
  }

  public String getName() {
    return myName;
  }

  public boolean canShoot() {
    return cooldown == 0;
  }

  void shoot() throws RemoteException {
    controller.fire(this);
    cooldown = 10;
  }
}
