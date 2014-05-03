package de.gymdon.inf1315.game.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public interface Remote {
    public Socket getSocket();
    public DataOutputStream getOutputStream();
    public DataInputStream getInputStream();
    public boolean left();
    public void leave(String message);
    public boolean isServer();
    public boolean isClient();
}
