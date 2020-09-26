package swingy.controller;

import swingy.model.*;
import swingy.view.*;

public class Interaction {

    public Hero combat(Hero tmphero, Enemy enemy){

        long Hatk = tmphero.getStats().getAtkDmg() + tmphero.getEquipmentStats()[0] * (tmphero.getStats().getLevel() / 3);
        long Eatk = enemy.getEnemyAtkDmg();
        long HHP = (tmphero.getStats().getHP() + tmphero.getEquipmentStats()[1] + tmphero.getEquipmentStats()[1]);
        long EHP = enemy.getEnemyHP();
        long dmg;
        int XP;
        Stats   stats;

        //combat
        while (HHP > 0 && EHP > 0){
            EHP = EHP - Hatk;
            HHP = HHP - Eatk;
        }

        //hero wins
        if (HHP > 0 && EHP <= 0){
            dmg = HHP - tmphero.getStats().getHP();
            XP = tmphero.getStats().getLevel() * 750;
            stats = new Stats(
                    tmphero.getStats().getHP() + dmg,
                    tmphero.getStats().getAtkDmg(),
                    tmphero.getStats().getArmor(),
                    tmphero.getStats().getXp() + XP,
                    tmphero.getStats().getLevel()
            );
            tmphero.setStats(stats);
//            System.out.println("You won the battle");
            return (tmphero);
        }
        //Enemy Wins
        else {
//            System.out.println("The Immense strength of the enemy tears you limb from limb in a bloody massacre!");
//            System.out.println("YOU DIED...");
            return (null);
        }
    }

    public boolean runAway(){
        double chance = Math.random();

        if (chance <= 0.5)
            return true;
        else
            return false;
    }
}