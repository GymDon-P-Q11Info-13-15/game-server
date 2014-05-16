package de.gymdon.inf1315.game.packet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

import de.gymdon.inf1315.game.Translation;

public abstract class Remote {

    public static boolean isServer;
    private Socket socket;
    private NBDataInputStream in;
    private DataOutputStream out;
    private long lastPacket;
    private boolean left = false;
    public Map<String,Object> properties = new HashMap<String,Object>();

    public Remote(Socket s) throws IOException {
	this.socket = s;
	out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	in = new NBDataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public Socket getSocket() {
	return socket;
    }

    public DataOutputStream getOutputStream() {
	if (left())
	    throw new RuntimeException("Client left");
	return out;
    }

    public NBDataInputStream getInputStream() {
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
	if(properties.containsKey("translation")) {
	    Translation t = (Translation)properties.get("translation");
	    System.out.println(t.translate("client.left", message));
	}
	if(message == null)
	    throw new NullPointerException();
	try {
	    socket.close();
	} catch (IOException e) {
	}
    }
    
    public void kick(String message, Object... args) {
	if (left)
	    return;
	PacketKick kick = new PacketKick(this);
	kick.message = message;
	kick.args = args;
	if(properties.containsKey("translation")) {
	    Translation t = (Translation)properties.get("translation");
	    System.out.println(t.translate("client.kicked", socket.getInetAddress().getCanonicalHostName(), t.translate(message, args)));
	}
	try {
	    kick.send();
	} catch (IOException e) {
	}
	try {
	    socket.close();
	} catch (IOException e) {
	}
	left = true;
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
