package de.gymdon.inf1315.game.packet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.gymdon.inf1315.game.server.Client;

public abstract class Packet {

    public static final Map<Integer, Class<? extends Packet>> packetTypes = new HashMap<Integer, Class<? extends Packet>>();

    protected Client client;
    
    public Packet (Client c) {
	this.client = c;
    }
    
    public abstract void handlePacket() throws IOException;
    public abstract void send() throws IOException;

    public static Packet newPacket(int id, Client c) {
	if (packetTypes.containsKey(id))
	    try {
		return packetTypes.get(id).getConstructor(Client.class).newInstance(c);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	return null;
    }
    
    static {
	packetTypes.put(PacketHello.ID, PacketHello.class);
    }
}
