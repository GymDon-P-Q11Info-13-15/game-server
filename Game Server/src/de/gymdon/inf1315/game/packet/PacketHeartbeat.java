package de.gymdon.inf1315.game.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketHeartbeat extends Packet {

    public static final short ID = 1;
    public boolean response;
    public byte[] payload;

    public PacketHeartbeat(Remote r) {
	super(r);
    }

    @Override
    public void handlePacket() throws IOException {
	super.handlePacket();
	DataInputStream in = remote.getInputStream();
	response = in.readBoolean();
	payload = new byte[in.readShort()];
	in.read(payload);
	if (!response) {
	    PacketHeartbeat resp = new PacketHeartbeat(remote);
	    resp.response = true;
	    resp.payload = payload;
	    resp.send();
	}
    }

    @Override
    public void send() throws IOException {
	super.send();
	DataOutputStream out = remote.getOutputStream();
	out.writeBoolean(response);
	out.writeShort(payload.length);
	out.write(payload);
    }

    @Override
    public short getId() {
	return ID;
    }
}
