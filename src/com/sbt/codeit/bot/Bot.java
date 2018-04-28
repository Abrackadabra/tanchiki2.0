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


  public void update(ArrayList<ArrayList<Character>> arrayList) throws RemoteException {
    info("Start of tick", tick);

    ////////////////////////////////////////////// init

    prevMap = map;
    map = new Map(arrayList, ourBaseSymbol, ourTankSymbol);

    map.bfs();
    map.detectEverything(prevMap);

    ////////////////////////////////////////////// logic


    controller.start(this); //начинаем ехать сразу и больше не останавливаемся
    if (tick % 10 == 0) {
      controller.right(this); //поворачиваем направо
    }
    if (tick % 10 == 5) {
      controller.left(this); //поворачиваем направо
    }
    if (tick % 10 == 7) {
      controller.putMine(this);
      info("put mine");
    }


    ////////////////////////////////////////////// update

    tick++;
    cooldown = Math.max(0, cooldown - 1);
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
