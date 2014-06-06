package de.gymdon.inf1315.game.packet;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.SocketChannel;

import com.google.gson.Gson;

public class PacketKick extends Packet {

    public static final short ID = 2;
    public String message;
    public Object[] args;

    public PacketKick(Remote r) {
	super(r);
    }

    @Override
    public void handlePacket() throws IOException {
	super.handlePacket();
	ByteBuffer buffer = remote.getInBuffer();
	message = in.readUTF();
	args = new Gson().fromJson(in.readUTF(), Object[].class);
    }

    @Override
    public void send() throws IOException {
	super.send();
	SocketChannel socketChannel = remote.getSocketChannel();
	ByteBuffer buffer = remote.getInBuffer();
	buffer.flip();
	
	ShortBuffer shortBuffer = buffer.asShortBuffer();
	shortBuffer.put(ID);
	
	out.writeUTF(message);
	out.writeUTF(new Gson().toJson(args));
	super.send();
    }
    
    
}
