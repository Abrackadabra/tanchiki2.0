package com.sbt.codeit.core.control;

import javafx.util.Pair;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameController extends Remote {

    /**
     * Регистрация бота на сервере. Вызывается 1 раз в методе main
     * Возвращает пару значений:
     * символ, которым будет обозначен ваш корабль на игровом поле;
     * символ, которым будет обозначена ваша база  на игровом поле.
     */
    Pair<Character, Character> register(ServerListener serverListener, String name) throws RemoteException;

    /**
     * Начать движение
     */
    void start(ServerListener serverListener) throws RemoteException;

    /**
     * Остановиться
     */
    void stop(ServerListener serverListener) throws RemoteException;

    /**
     * Повернуть в соответствующую сторону
     */
    void up(ServerListener serverListener) throws RemoteException;
    void down(ServerListener serverListener) throws RemoteException;
    void left(ServerListener serverListener) throws RemoteException;
    void right(ServerListener serverListener) throws RemoteException;

    /**
     * Выстрелить
     */
    void fire(ServerListener serverListener) throws RemoteException;

    /**
     * Сбросить мину
     */
    void putMine(ServerListener serverListener) throws RemoteException;

}
