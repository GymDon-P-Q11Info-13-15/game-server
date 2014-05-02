package de.gymdon.inf1315.game.server;

import java.io.IOException;

public class ConnectionHandler {

    private Server server;

    public ConnectionHandler(Server s) {
	this.server = s;
    }

    public void update() {
	if (server.clientList.isEmpty())
	    return;
	for (Client c : server.clientList) {
	    try {
		int id;
		id = c.getInputStream().readInt();
		Packet.newPacket(id, c).handlePacket();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return;
    }
}
