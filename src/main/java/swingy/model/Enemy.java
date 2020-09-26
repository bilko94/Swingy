package swingy.model;

public class Enemy {

    private int     enemyHP;
    private int     enemyAtkDmg;
    private int     enemyArmor;
    private int     enemyLevel;
    private int     x;
    private int     y;
    private Artifact drop;

    public Enemy(int Herolvl, int x, int y){
        double chance = Math.random();
        this.drop = new Artifact(chance);
        this.enemyHP = (int) (10 * Herolvl * chance);
        this.enemyAtkDmg = (int) (5 * Herolvl * chance);
        this.enemyArmor = (int) (2 * Herolvl * chance);
        this.enemyLevel = (int) (Herolvl * chance);
        this.x = x;
        this.y = y;
    }

    public long getEnemyHP() {
        return enemyHP;
    }

    public long getEnemyAtkDmg() {
        return enemyAtkDmg;
    }

    public Artifact getDrop(){ return this.drop; }

    public int getX(){ return x; };

    public int getY(){ return y; }
}