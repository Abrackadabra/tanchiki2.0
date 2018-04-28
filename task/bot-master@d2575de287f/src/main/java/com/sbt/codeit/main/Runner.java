package com.sbt.codeit.main;

import com.sbt.codeit.bot.Bot;
import com.sbt.codeit.core.control.GameController;
import com.sbt.codeit.core.control.ServerListener;
import javafx.util.Pair;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;

/**
 * Подключение бота к серверу.
 * ИЗМЕНЯЯ КЛАСС, ВЫ СОГЛАШАЕТЕСЬ РЕШАТЬ ВСЕ ВОЗНИКАЮЩИЕ ПРОБЛЕМЫ САМОСТОЯТЕЛЬНО
 */
public class Runner {

    private static final int PORT = 2018;
    private static final String STUB_NAME = "GameController";

    private static Registry registry;
    private static GameController server;
    private static ServerListener client;

    public static void main(String... args) throws Exception {
        registry = LocateRegistry.getRegistry(PORT);
        server = (GameController) registry.lookup(STUB_NAME);
        Bot bot = new Bot(server);
        client = (ServerListener) UnicastRemoteObject.exportObject(bot, 0);
        Pair<Character, Character> pair = server.register(client, bot.getName());
        bot.setId(pair.getKey());
        bot.setBaseId(pair.getValue());
        while(true) {
            checkConnection();
        }
    }

    private static void checkConnection() {
        try {
            registry.lookup(STUB_NAME);
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ex) {
            System.err.println("Connection closed");
            System.exit(0);
        }
    }

}