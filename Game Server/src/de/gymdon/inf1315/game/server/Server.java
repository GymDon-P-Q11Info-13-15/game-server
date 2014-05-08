package de.gymdon.inf1315.game.server;

import java.net.*;
import java.util.*;
import java.io.*;
import java.util.ArrayList;

import de.gymdon.inf1315.game.packet.Remote;

public class Server implements Runnable {

    public static final int PORT = 22422;
    public static final String NAME = "Main Server";
    public static Server instance;

    List<Remote> clientList = new ArrayList<Remote>();
    private boolean running = false;
    private Timer timer;
    private ConnectionHandler connectionHandler;

    public static void main(String[] args) {
	new Server();
    }

    public Server() {
	if (instance != null)
	    throw new RuntimeException("Already running");
	instance = this;
	this.connectionHandler = new ConnectionHandler(this);
	this.timer = new Timer(connectionHandler);
	this.run();
    }

    public void run() {
	running = true;
	ServerSocket ss;
	try {
	    ss = new ServerSocket(PORT);
	    System.out.println("Started server on port " + PORT);
	} catch (IOException e) {
	    e.printStackTrace();
	    return;
	}
	timer.start();
	while (running) {
	    try {
		Socket s = ss.accept();
		clientList.add(new Client(s));
		System.out.println("New client: "
			+ s.getInetAddress().getCanonicalHostName());
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	timer.stopTimer();
	try {
	    timer.join();
	} catch (InterruptedException e1) {
	}
	try {
	    ss.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void stop() {
	running = false;
    }

    public String getName() {
	return NAME;
    }
}
