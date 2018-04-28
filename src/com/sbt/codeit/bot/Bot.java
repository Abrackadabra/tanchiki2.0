package com.sbt.codeit.bot;

import com.sbt.codeit.core.control.GameController;
import com.sbt.codeit.core.control.ServerListener;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.pmw.tinylog.Logger;

public class Bot implements ServerListener {

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
    //TODO Разместите свой код здесь. Пример вызова методов:
    controller.start(this); //начинаем ехать сразу и больше не останавливаемся
    if (tick % 5 == 0) {
      controller.right(this); //поворачиваем направо
    }
//    controller.fire(this); //стреляем всегда, когда это возможно


    tick++;
    Logger.info("Tick " + tick);
  }

  public String randomName() {
    String s = "";
    for (int i = 0; i < 10; i++) {
      s += (char) ('A' + Math.random() * 26);
    }
    return s;
  }

  public String getName() {
//        return "Сберкек наносит ответный удар";
    return "Sberkek strikes back " + randomName();
  }
}
