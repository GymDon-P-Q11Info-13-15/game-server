package de.gymdon.inf1315.game.server;

import java.io.DataInputStream;
import java.io.IOException;
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
		if (din.available() >= 2) {
		    short id = din.readShort();
		    Packet p = Packet.newPacket(id, c);
		    if (p != null)
			p.handlePacket();
		    else
			c.leave("Invalid Packet");
		}
		if (c.getSocket().isClosed())
		    c.leave("Socket closed");
		if (ticksRunning % 40 == 0) {
		    PacketHeartbeat heartbeat = new PacketHeartbeat(c);
		    heartbeat.response = false;
		    byte[] bytes = new byte[43];
		    new Random().nextBytes(bytes);
		    heartbeat.payload = bytes;
		    heartbeat.send();
		}
	    } catch (IOException e) {
		c.leave(e.getMessage());
	    }
	    if (c.left())
		i.remove();
	}
    }
}
