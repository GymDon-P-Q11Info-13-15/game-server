package de.gymdon.inf1315.game;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Translation {
    
    private Map<String, String> translations = new HashMap<String,String>();
    
    public Translation(String lang) {
	load(lang);
    }
    
    public Translation(Reader reader) {
	load(reader);
    }
    
    public void load(String lang) {
	try {
	    load(new InputStreamReader(Translation.class.getResourceAsStream("/lang/" + lang + ".json")));
	}catch(Exception e) {
	    System.err.println("Couldn't load language \"" + lang + "\"");
	}
    }
    
    @SuppressWarnings("unchecked")
    public void load(Reader reader) {
	translations.putAll((Map<String, String>) new Gson().fromJson(reader, new TypeToken<Map<String,String>>(){}.getType()));
    }
    
    public void reload(String lang) {
	translations.clear();
	load(lang);
    }
    
    public void reload(Reader reader) {
	translations.clear();
	load(reader);
    }
    
    public String translate(String code, Object... args) {
	if(translations.containsKey(code))
	    return String.format(translations.get(code), args);
	if(translations.containsKey("translation.missing"))
	    System.err.println(translate("translation.missing", code));
	else
	    System.err.println("translation.missing[" + code + "]");
	return code + (args.length > 0 ? Arrays.toString(args) : "");
    }
}
