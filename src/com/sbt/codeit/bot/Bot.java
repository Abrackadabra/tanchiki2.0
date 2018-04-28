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
  private Character ourSymbol;
  private Character ourBaseSymbol;

  public Bot(GameController controller) {
    this.controller = controller;
  }

  public void setOurSymbol(Character ourSymbol) {
    this.ourSymbol = ourSymbol;
  }

  public void setOurBaseSymbol(Character ourBaseSymbol) {
    this.ourBaseSymbol = ourBaseSymbol;
  }

  int tick = 0;
  

  public void update(ArrayList<ArrayList<Character>> arrayList) throws RemoteException {
    info("Start of tick", tick);

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
    

//    controller.fire(this); //стреляем всегда, когда это возможно

    tick++;
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
}
