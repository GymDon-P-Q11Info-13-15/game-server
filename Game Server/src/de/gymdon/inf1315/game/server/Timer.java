package de.gymdon.inf1315.game.server;

public class Timer extends Thread {

    private boolean running = false;
    private ConnectionHandler handler;

    public Timer(ConnectionHandler handler) {
	this.handler = handler;
    }

    @Override
    public void run() {
	running = true;
	double unprocessed = 0;
	double nsPerTick = 1e9 / 20;
	long lastTimeNanos = System.nanoTime();
	while (running) {
	    long now = System.nanoTime();
	    unprocessed += (now - lastTimeNanos) / nsPerTick;
	    lastTimeNanos = now;
	    while (unprocessed >= 1) {
		handler.update();
		--unprocessed;
	    }
	    try {
		Thread.sleep(2);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    public void stopTimer() {
	running = false;
    }
}
