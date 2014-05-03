package de.gymdon.inf1315.game.packet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet {

    public static final Map<Short, Class<? extends Packet>> packetTypes = new HashMap<Short, Class<? extends Packet>>();

    protected Remote remote;

    public Packet(Remote r) {
	this.remote = r;
    }

    public abstract void handlePacket() throws IOException;

    public abstract void send() throws IOException;

    public abstract short getId();

    public static Packet newPacket(short id, Remote r) {
	if (packetTypes.containsKey(id))
	    try {
		return packetTypes.get(id).getConstructor(Remote.class)
			.newInstance(r);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	return null;
    }

    static {
	packetTypes.put(new PacketHello(null).getId(), PacketHello.class);
	packetTypes.put(new PacketHeartbeat(null).getId(),
		PacketHeartbeat.class);
    }
}
