package swingy.model;

import swingy.controller.WorldGeneration;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class GameData {
    private Hero hero = null;
    public Hero tmpHero = null;
    public Hero loadBuffer = null;

    public Hero getHero() {
        return hero;
    }

    public Hero getTmpHero() { return tmpHero; }

    public void setTmpHero(Hero hero){
        this.tmpHero = hero;
    }

    public void duplicateHero(){ this.tmpHero = hero.duplicateHero(); }

    public boolean createHero(String name, String heroClass){
        hero = new Hero(name, heroClass);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Hero>> violations;
        violations = validator.validate(hero);
        if (violations.size() > 0){
            return false;
        }
        duplicateHero();
        saveState();
        return true;
    }

    public boolean checkLoad() {
        try {
            this.loadBuffer = new LoadGame().load();
            if (loadBuffer == null)
                return false;
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void nextMap(){
        tmpHero.resetHealth();
        hero = tmpHero.duplicateHero();
        saveHero();
    }

    public void loadHero() { hero = loadBuffer.duplicateHero(); }

    public void saveHero(){ new SaveGame().saveData(hero); }

    public void saveState() {
        Hero state = tmpHero.duplicateHero();
        state.resetHealth();
        hero = state.duplicateHero();
        new SaveGame().saveData(state);
    }

}