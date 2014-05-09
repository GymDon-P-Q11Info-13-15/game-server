package de.gymdon.inf1315.game.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.gymdon.inf1315.game.server.Server;

public class PacketHello extends Packet {

    public static final short ID = 0;
    public boolean serverHello;
    public String serverName;

    public PacketHello(Remote r) {
	super(r);
    }

    @Override
    public void handlePacket() throws IOException {
	super.handlePacket();
	DataInputStream in = remote.getInputStream();
	serverHello = in.readBoolean();
	if (serverHello)
	    serverName = in.readUTF();
	else {
	    PacketHello resp = new PacketHello(remote);
	    resp.serverHello = true;
	    resp.serverName = Server.instance.getName();
	    resp.send();
	}
    }

    @Override
    public void send() throws IOException {
	super.send();
	DataOutputStream out = remote.getOutputStream();
	out.writeShort(ID);
	out.writeBoolean(serverHello);
	if (serverHello)
	    out.writeUTF(serverName);
	out.flush();
    }

    @Override
    public short getId() {
	return ID;
    }

}
