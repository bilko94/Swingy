package swingy.model;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class Stats {

    @Positive
    private long    HP;
    @Positive
    private long    AtkDmg;
    @Positive
    private int     Armor;
    @PositiveOrZero
    private int     xp;
    @PositiveOrZero
    private int     xpBar;

    public Stats(long HP, long AtkDmg, int Armor, int xp, int Level) {
        this.HP = HP;
        this.AtkDmg = AtkDmg;
        this.Armor = Armor;
        this.xpBar = Level * 1000 + (int)Math.pow(Level - 1, 2) * 450;
        this.xp = xp;
    }

    public int getXpBar() {
        return this.xpBar;
    }

    public long getHP() {
        return HP;
    }

    public long getAtkDmg() {
        return AtkDmg;
    }

    public int getArmor() {
        return Armor;
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        int lvl = 1;
        while (xp > lvl * 1000 + (int)Math.pow(lvl - 1, 2) * 450){
            lvl++;
//            System.out.println(lvl);
        }
        return lvl;
    }
}
