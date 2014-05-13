package de.gymdon.inf1315.game.server;

import java.net.*;
import java.util.*;
import java.io.*;
import java.util.ArrayList;

import de.gymdon.inf1315.game.Translation;
import de.gymdon.inf1315.game.packet.Remote;

public class Server implements Runnable {

    public static Server instance;

    List<Remote> clientList = new ArrayList<Remote>();
    private boolean running = false;
    private Timer timer;
    private ConnectionHandler connectionHandler;
    public Preferences preferences;
    public Translation translation;

    public static void main(String[] args) {
	new Server();
    }

    public Server() {
	if (instance != null)
	    throw new RuntimeException("Already running");
	instance = this;
	translation = new Translation("en");
	this.connectionHandler = new ConnectionHandler(this);
	this.timer = new Timer(connectionHandler);
	this.readPreferences();
	this.run();
    }
    
    private void readPreferences() {
	File f = new File("preferences.json");
	try {
	    if (f.exists())
		preferences = Preferences.readNew(new FileReader(f));
	    else {
		preferences = new Preferences();
		f.createNewFile();
		preferences.write(new FileWriter(f));
		System.out.println(translation.translate("file.created", "preferences.json"));
	    }
	    if(preferences.version != Preferences.CURRENT_VERSION) {
		preferences.version = Preferences.CURRENT_VERSION;
		preferences = new Preferences();
		f.createNewFile();
		preferences.write(new FileWriter(f));
		System.out.println(translation.translate("updated.version","preferences.json", preferences.version));
	    }
	} catch (IOException e) {
	    throw new RuntimeException("Preferences couldn't be loaded/saved", e);
	}
    }

    public void run() {
	running = true;
	ServerSocket ss;
	try {
	    ss = new ServerSocket(preferences.port);
	    if(!(preferences.hostname.equals("0.0.0.0") || preferences.hostname.equals("*") || preferences.hostname.equals("")))
		ss.bind(new InetSocketAddress(preferences.hostname, preferences.port));
	    System.out.println(translation.translate("server.started", preferences.hostname, preferences.port));
	} catch (IOException e) {
	    e.printStackTrace();
	    return;
	}
	timer.start();
	while (running) {
	    try {
		Socket s = ss.accept();
		Client c = new Client(s);
		c.properties.put("translation", translation);
		clientList.add(c);
		System.out.println(translation.translate("client.new", s.getInetAddress().getCanonicalHostName()));
		System.out.flush();
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
}
