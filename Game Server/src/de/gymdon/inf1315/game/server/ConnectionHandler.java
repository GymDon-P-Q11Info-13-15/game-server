package de.gymdon.inf1315.game.server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Random;

import de.gymdon.inf1315.game.packet.Packet;
import de.gymdon.inf1315.game.packet.PacketHeartbeat;

public class ConnectionHandler {

    private Server server;
    private int ticksRunning = 0;

    public ConnectionHandler(Server s) {
	this.server = s;
    }

    public void update() {
	ticksRunning++;
	if (server.clientList.isEmpty())
	    return;
	for (Iterator<Client> i = server.clientList.iterator(); i.hasNext();) {
	    Client c = i.next();
	    try {
		DataInputStream din = c.getInputStream();
		if (din.available() >= 4) {
		    int id = din.readInt();
		    Packet p = Packet.newPacket(id, c);
		    if(p != null)
			p.handlePacket();
		    else
			c.leave("Invalid Packet");
		}
		if(c.getSocket().isClosed())
		    c.leave("Socket closed");
		if(ticksRunning%40 == 0) {
		    PacketHeartbeat heartbeat = new PacketHeartbeat(c);
		    heartbeat.response = false;
		    byte[] bytes = new byte[40];
		    new Random().nextBytes(bytes);
		    heartbeat.payload = new String(bytes, Charset.forName("ASCII"));
		    heartbeat.send();
		}
	    } catch (IOException e) {
		if (e instanceof EOFException)
		    c.leave(e.getMessage());
		else
		    e.printStackTrace();
	    }
	    if(c.left())
		i.remove();
	}
    }
}
