package de.gymdon.inf1315.game;

public abstract class Unit extends GameObject {
int hp, //0-100 
speed,
attack, defense,  // 0-100
range;

public abstract void move();
public abstract void attack();
public abstract void setHP(int health);
public abstract int getSpeed();


}
