package com.sbt.codeit.core.control;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerListener extends Remote {

    /**
     * Сервер будет вызывать на клиенте этот метод, передавая текущее состояние игрового поля
     */
    void update(ArrayList<ArrayList<Character>> field) throws RemoteException;

}
