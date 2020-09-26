package swingy.controller;

import swingy.model.Artifact;
import swingy.model.Enemy;
import swingy.model.Hero;

public class CombatReport {
    public boolean combat = false;
    public Artifact drop = null;
    public boolean escape = true;
    public Hero result = null;
    public boolean proceed = false;
    public Enemy enemy = null;
    public boolean validDrop = false;

    public CombatReport(Enemy enemy, Hero hero){
        escape = new Interaction().runAway();
        validDrop = new Interaction().runAway();
        if (enemy == null)
            result = hero;
        else {
            this.enemy = enemy;
            drop = enemy.getDrop();
            combat = true;
            result = new Interaction().combat(hero, enemy);
        }
    }

    public CombatReport(){
        proceed = true;
    }
}
