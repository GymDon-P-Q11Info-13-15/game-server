package de.gymdon.inf1315.game.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Game game;
    private boolean left = false;

    public Client(Socket s) throws IOException {
	this.socket = s;
	out = new DataOutputStream(socket.getOutputStream());
	in = new DataInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
	if(left && !socket.isClosed())
	    try {
		socket.close();
	    } catch (IOException e) {
	    }
	return socket;
    }

    public DataOutputStream getOutputStream() {
	if(left)
	    throw new RuntimeException("Client left");
	return out;
    }

    public DataInputStream getInputStream() {
	if(left)
	    throw new RuntimeException("Client left");
	return in;
    }

    public Client getOtherClient() {
	if (game == null)
	    return null;
	if (game.hasBothClients())
	    return game.getClientA() == this ? game.getClientB() : game
		    .getClientA();
	return null;
    }

    public Game getGame() {
	return game;
    }
    
    public boolean left() {
	return left;
    }

    public void leave(String message) {
	if(left)
	    return;
	if (game != null)
	    game.end(this);
	left = true;
	System.out.println(socket.getInetAddress().getCanonicalHostName()
		+ " left" + (message != null ? ": " + message : ""));
	try {
	    socket.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
