package com.sbt.codeit.bot;

import com.sbt.codeit.core.control.GameController;
import com.sbt.codeit.core.control.ServerListener;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Bot implements ServerListener {

    private GameController controller;
    private Character id;
    private Character baseId;

    public Bot(GameController controller) {
        this.controller = controller;
    }

    public void setId(Character id) {
        this.id = id;
    }

    public void setBaseId(Character baseId) {
        this.baseId = baseId;
    }

    public void update(ArrayList<ArrayList<Character>> arrayList) throws RemoteException {
        //TODO Разместите свой код здесь. Пример вызова методов:
        controller.start(this); //начинаем ехать сразу и больше не останавливаемся
        controller.right(this); //поворачиваем направо
        controller.fire(this); //стреляем всегда, когда это возможно
    }

    public String getName() {
        //TODO return "Имя вашей команды"
        throw new UnsupportedOperationException("Метод должен вернуть имя вашей команды");
    }
}