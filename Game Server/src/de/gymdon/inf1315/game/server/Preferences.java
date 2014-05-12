package de.gymdon.inf1315.game.server;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Preferences {
    public static final int CURRENT_VERSION = 1;
    public int version =  CURRENT_VERSION;
    public String server_name = "Game Server";
    public String hostname = "0.0.0.0";
    public int port = 22422;
    public SSL ssl = new SSL();
    
    public static class SSL {
	public final String __comment = "Not implemented";
	public boolean enabled = false;
    }
    
    public void write(Writer writer) throws IOException {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	gson.toJson(this, writer);
	writer.flush();
    }
    
    public void read(Reader reader) {
	Preferences np = Preferences.readNew(reader);
	this.server_name = np.server_name;
	this.ssl = np.ssl;
    }
    
    public static Preferences readNew(Reader reader) {
	Gson gson = new Gson();
	return gson.fromJson(reader, Preferences.class);
    }
}
