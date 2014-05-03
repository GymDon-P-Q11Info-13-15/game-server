package de.gymdon.inf1315.game.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.gymdon.inf1315.game.server.Client;
import de.gymdon.inf1315.game.server.Server;

public class PacketHello extends Packet {

    public static final short ID = 0;
    public boolean serverHello;
    public String serverName;

    public PacketHello(Client c) {
	super(c);
    }

    @Override
    public void handlePacket() throws IOException {
	DataInputStream in = client.getInputStream();
	serverHello = in.readBoolean();
	if (serverHello)
	    serverName = in.readUTF();
	else {
	    PacketHello resp = new PacketHello(client);
	    resp.serverHello = true;
	    resp.serverName = Server.instance.getName();
	    resp.send();
	}
    }

    @Override
    public void send() throws IOException {
	DataOutputStream out = client.getOutputStream();
	out.writeShort(ID);
	out.writeBoolean(serverHello);
	if (serverHello)
	    out.writeUTF(serverName);
    }

    @Override
    public short getId() {
	return ID;
    }

}
