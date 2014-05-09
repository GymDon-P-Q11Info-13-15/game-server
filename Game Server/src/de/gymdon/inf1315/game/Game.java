package de.gymdon.inf1315.game;

import de.gymdon.inf1315.game.server.Client;

public class Game {
    private Client clientA;
    private Client clientB;

    public Game(Client clientA) {
	this.clientA = clientA;
    }

    public Game(Client clientA, Client clientB) {
	this.clientA = clientA;
	this.clientB = clientB;
    }

    public boolean hasBothClients() {
	return clientA != null && clientB != null;
    }

    public Client getClientA() {
	return clientA;
    }

    public Client getClientB() {
	return clientA;
    }

    public int getNumClients() {
	return clientA != null ? clientB != null ? 2 : 1 : 0;
    }

    public void end(Client leaver) {
	if (leaver == clientA)
	    clientA = null;
	if (leaver == clientB)
	    clientB = null;
	if (hasBothClients())
	    return;
	System.out.println("Game ended: " + this);
    }
}
