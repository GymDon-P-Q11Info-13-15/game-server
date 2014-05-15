package de.gymdon.inf1315.game.packet;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class NBDataInputStream implements DataInput {

    private DataInputStream inputStream;
    
    public NBDataInputStream (InputStream in) {
	inputStream = new DataInputStream(in);
    }
    
    @Override
    public boolean readBoolean() throws IOException {
	if (inputStream.available() >= 1)
	    return inputStream.readBoolean();
	else
	    throw new EmptyStreamException();
    }

    @Override
    public byte readByte() throws IOException {
	if (inputStream.available() >= 1)
	    return inputStream.readByte();
	else
	    throw new EmptyStreamException();
    }

    @Override
    public char readChar() throws IOException {
	if (inputStream.available() >= 2)
	    return inputStream.readChar();
	else
	    throw new EmptyStreamException();
    }

    @Override
    public double readDouble() throws IOException {
	if (inputStream.available() >= 8)
	    return inputStream.readDouble();
	else
	    throw new EmptyStreamException();
    }

    @Override
    public float readFloat() throws IOException {
	if (inputStream.available() >= 4)
	    return inputStream.readFloat();
	else
	    throw new EmptyStreamException();
    }

    @Override
    public void readFully(byte[] b) throws IOException {
	if (inputStream.available() >= b.length)
	    inputStream.readFully(b);
	else
	    throw new EmptyStreamException();
	
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
	if (inputStream.available() >= len)
	    inputStream.readFully(b, off, len);
	else
	    throw new EmptyStreamException();
	
    }

    @Override
    public int readInt() throws IOException {
	if (inputStream.available() >= 4)
	    return inputStream.readInt();
	else
	    throw new EmptyStreamException();
    }

    @Override
    public String readLine() throws IOException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public long readLong() throws IOException {
	if (inputStream.available() >= 8)
	    return inputStream.readLong();
	else
	    throw new EmptyStreamException();
    }

    @Override
    public short readShort() throws IOException {
	if (inputStream.available() >= 2)
	    return inputStream.readShort();
	else
	    throw new EmptyStreamException();
    }

    @Override
    public String readUTF() throws IOException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int readUnsignedByte() throws IOException {
	if (inputStream.available() >= 1)
	    return inputStream.readUnsignedByte();
	else
	    throw new EmptyStreamException();
    }

    @Override
    public int readUnsignedShort() throws IOException {
	if (inputStream.available() >= 2)
	    return inputStream.readUnsignedShort();
	else
	    throw new EmptyStreamException();
    }

    @Override
    public int skipBytes(int n) throws IOException {
	return inputStream.skipBytes(n);
    }
}
