package de.gymdon.inf1315.game.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.gymdon.inf1315.game.server.Client;

public class PacketHeartbeat extends Packet {
    
    public static final short ID = 1;
    public boolean response;
    public byte[] payload;

    public PacketHeartbeat(Client c) {
	super(c);
    }

    @Override
    public void handlePacket() throws IOException {
	DataInputStream in = client.getInputStream();
	response = in.readBoolean();
	payload = new byte[in.readShort()];
	in.read(payload);
	if(!response) {
	    PacketHeartbeat resp = new PacketHeartbeat(client);
	    resp.response = true;
	    resp.payload = payload;
	    resp.send();
	}
	    
    }

    @Override
    public void send() throws IOException {
	DataOutputStream out = client.getOutputStream();
	out.writeInt(ID);
	out.writeBoolean(response);
	out.writeShort(payload.length);
	out.write(payload);
    }

    @Override
    public short getId() {
	return ID;
    }
}
