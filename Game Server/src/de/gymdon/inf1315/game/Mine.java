package de.gymdon.inf1315.game;

public class Mine extends Building {
    
    int income;
    
    public Mine(int x, int y){
	this.x=x;
	this.y=y;
	
    }

    @Override
    public void occupy(Player p) {
	this.owner=p;
	
    }
    
    
    

}
