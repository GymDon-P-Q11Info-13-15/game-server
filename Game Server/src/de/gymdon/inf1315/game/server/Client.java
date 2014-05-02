package de.gymdon.inf1315.game.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    
    public Client(Socket s) throws IOException {
	this.socket = s;
	out = new DataOutputStream(socket.getOutputStream());
	in = new DataInputStream(socket.getInputStream());
    }
    
    public Socket getSocket() {
	return socket;
    }
    
    public DataOutputStream getOutputStream() {
	return out;
    }
    
    public DataInputStream getInputStream() {
	return in;
    }
}
