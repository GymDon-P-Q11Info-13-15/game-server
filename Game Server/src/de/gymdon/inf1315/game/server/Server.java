package de.gymdon.inf1315.game.server;

import java.net.*;
import java.util.*;
import java.io.*;
import java.util.ArrayList;

public class Server implements Runnable {

    public static final int PORT = 22422;
    List<Client> clientList = new ArrayList<Client>();

    public static void main(String[] args) {
	new Server();
    }

    public Server() {
	this.run();
    }

    public void run() {
	ServerSocket ss;
	try {
	    ss = new ServerSocket(PORT);
	} catch (IOException e) {
	    e.printStackTrace();
	    return;
	}
	while (true) {
	    try {
		Socket s = ss.accept();
		clientList.add(new Client(s));
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }
}
