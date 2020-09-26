package swingy.model;

public class MapData {
    int pointX = 0;
    int pointY = 0;
    int occupant = 0;// can be empty(0), enemy(1) or hero(2)
    Enemy enemy = null;
    Hero hero = null;

    public MapData(int x, int y, int occ, Enemy enemy, Hero hero){
        pointX = x;
        pointY = y;
        occupant = occ;
        this.hero = hero;
        this.enemy = enemy;
    }

    public int[] getCoords(){
        return new int[]{pointX,pointY};
    }

    public int getOccupant(){
        return occupant;
    }

    public void setOccupant(int occupant) {
        this.occupant = occupant;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        if (enemy == null)
            setOccupant(0);
        this.enemy = enemy;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        if (enemy == null)
            setOccupant(0);
        this.hero = hero;
        this.occupant = 2;
    }
}
