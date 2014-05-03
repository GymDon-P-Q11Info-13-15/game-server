package de.gymdon.inf1315.game.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.gymdon.inf1315.game.server.Client;

public class PacketHeartbeat extends Packet {
    
    public boolean response;
    public String payload;

    public PacketHeartbeat(Client c) {
	super(c);
    }

    @Override
    public void handlePacket() throws IOException {
	DataInputStream in = client.getInputStream();
	response = in.readBoolean();
	payload = in.readUTF();
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
	out.writeBoolean(response);
	out.writeUTF(payload);
    }

}
