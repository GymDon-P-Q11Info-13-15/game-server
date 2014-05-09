package de.gymdon.inf1315.game.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import de.gymdon.inf1315.game.packet.Packet;
import de.gymdon.inf1315.game.packet.PacketHeartbeat;
import de.gymdon.inf1315.game.packet.Remote;

public class ConnectionHandler {

    private Server server;

    public ConnectionHandler(Server s) {
	this.server = s;
    }

    public void update() {
	if (server.clientList.isEmpty())
	    return;
	for (Iterator<Remote> i = server.clientList.iterator(); i.hasNext();) {
	    Remote r = i.next();
	    try {
		DataInputStream din = r.getInputStream();
		if (din.available() >= 2) {
		    short id = din.readShort();
		    Packet p = Packet.newPacket(id, r);
		    if (p != null)
			p.handlePacket();
		    else
			r.leave("Invalid Packet");
		}
		if (r.getSocket().isClosed())
		    r.leave("Socket closed");
		long now = System.currentTimeMillis();
		if (r.getLastPacketTime() - now >= 2000) {
		    PacketHeartbeat heartbeat = new PacketHeartbeat(r);
		    heartbeat.response = false;
		    byte[] bytes = new byte[43];
		    new Random().nextBytes(bytes);
		    heartbeat.payload = bytes;
		    heartbeat.send();
		}
	    } catch (IOException e) {
		r.leave(e.getMessage());
	    }
	    if (r.left())
		i.remove();
	}
    }
}
