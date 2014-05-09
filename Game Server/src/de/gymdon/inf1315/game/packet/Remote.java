package de.gymdon.inf1315.game.packet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class Remote {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private long lastPacket;
    private boolean left = false;

    public Remote(Socket s) throws IOException {
	this.socket = s;
	out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public Socket getSocket() {
	if (left() && !socket.isClosed())
	    try {
		socket.close();
	    } catch (IOException e) {
	    }
	return socket;
    }

    public DataOutputStream getOutputStream() {
	if (left())
	    throw new RuntimeException("Client left");
	return out;
    }

    public DataInputStream getInputStream() {
	if (left())
	    throw new RuntimeException("Client left");
	return in;
    }

    public boolean left() {
	return left;
    }

    public void leave(String message) {
	if (left)
	    return;
	left = true;
	System.out.println(socket.getInetAddress().getCanonicalHostName()
		+ " left" + (message != null ? ": " + message : ""));
	try {
	    socket.close();
	} catch (IOException e) {
	}
    }
    
    public void notifyPacket() {
	lastPacket = System.currentTimeMillis();
    }
    
    public long getLastPacketTime() {
	return lastPacket;
    }

    public abstract boolean isServer();

    public abstract boolean isClient();
}
