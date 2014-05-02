package de.gymdon.inf1315.game.server;

import java.util.HashMap;
import java.util.Map;

public abstract class Packet {

    public static final Map<Integer, Class<? extends Packet>> packetTypes = new HashMap<Integer, Class<? extends Packet>>();

    protected Client client;
    
    public Packet (Client c) {
	this.client = c;
    }
    
    public abstract void handlePacket();

    public static Packet newPacket(int id, Client c) {
	if (packetTypes.containsKey(id))
	    try {
		return packetTypes.get(id).getConstructor(Client.class).newInstance(c);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	return null;
    }
}
