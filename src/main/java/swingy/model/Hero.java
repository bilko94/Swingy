package swingy.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;


public class Hero {

    @Valid
    private Stats               stats;
    @NotNull(message = "Name cannot be null")
    private String              name;
    @NotNull(message = "Name cannot be null")
    private String              heroClass;
    private int                 currX;
    private int                 currY;
    private List<Artifact>      backPack = new ArrayList<>();


    private List<Artifact>      equipped = new ArrayList<>();

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public Hero(String name, String heroClass) {
        this.name = name;
        this.heroClass = heroClass;
        InitializeStats(heroClass);
    }

    public void InitializeStats(String heroClass){

        String str = heroClass.toLowerCase();
        if (str.equals("normie")){
            this.stats = new Stats(125,20,5,1,1);
        }
        else if (str.equals("weeb")){
            this.stats = new Stats(145,15,10,1,1);
        }
        else if (str.equals("otaku")){
            this.stats = new Stats(80,35,7,1,1);
        }
        else if (str.equals("methhead")){
            this.stats = new Stats(100,25,0,500,1);
        }
        else {
//            System.out.println("Invalid Class");
        }
    }

    public void initializeSave(int armor, long AtkDmg, long HP, int lvl, int xp, String name, String Class, List<Artifact> backpack, List<Artifact> equipment){
//        System.out.println("Loading last save...");
        this.stats = new Stats(HP, AtkDmg, armor, xp, lvl);
        this.name = name;
        this.heroClass = Class;
        this.backPack = backpack;
        this.equipped = equipment;
//        for (int i = 0; i < backpack.size(); i++) {
//            updateBackPack(backpack.get(i));
//            i++;
//        }
//        for (int i = 0; i < equipment.size(); i++){
//            equipItem(equipment.get(i));
//            i++;
//        }
    }
    public void resetHealth(){
        if (getHeroClass().equals("normie")){
            this.stats = new Stats(125 * getStats().getLevel(), stats.getAtkDmg(), stats.getArmor(), stats.getXp(), stats.getLevel());
        }
        else if (getHeroClass().equals("weeb")){
            this.stats = new Stats(145 * getStats().getLevel(),stats.getAtkDmg(), stats.getArmor(), stats.getXp(), stats.getLevel());
        }
        else if (getHeroClass().equals("otaku")){
            this.stats = new Stats(80 * getStats().getLevel(),stats.getAtkDmg(), stats.getArmor(), stats.getXp(), stats.getLevel());
        }
        else if (getHeroClass().equals("methhead")){
            this.stats = new Stats(100 * getStats().getLevel(),stats.getAtkDmg(), stats.getArmor(), stats.getXp(), stats.getLevel());
        }
        else {
//            System.out.println("Invalid Class");
        }
    }

    public int[] getEquipmentStats(){
        int hp = 0;
        int atk = 0;
        int arm  = 0;
        for (Artifact item : equipped){
            hp += item.getHp();
            atk += item.getDamage();
            arm += item.getArmour();
        }
        return new int[]{atk,hp,arm};
    }

    public Hero duplicateHero(){
        Hero heroObj = new Hero(name , heroClass);
        heroObj.initializeSave(
                stats.getArmor(),
                stats.getAtkDmg(),
                stats.getHP(),
                stats.getLevel(),
                stats.getXp(),
                name,
                heroClass,
                this.backPack,
                this.equipped
        );
        return heroObj;
    }

    public void updateBackPack(Artifact item){
        this.backPack.add(item);
    }

    public void removeItem(Artifact item){
        this.backPack.remove(item);
    }

    public void equipItem(Artifact item){
        removeItem(item);
        if (classMatch(item.getType()) != null)
            unequipItem(classMatch(item.getType()));
        this.equipped.add(item);
    }
    private Artifact classMatch(String itemClass){
        for (Artifact item: equipped){
            if (item.getType().equals(itemClass))
                return item;
        }
        return null;
    }

    public void unequipItem(Artifact item){
        updateBackPack(item);
        this.equipped.remove(item);
    }

    public List<Artifact> getBackPack() {
        return backPack;
    }

    public List<Artifact> getEquipped() {
        return equipped;
    }


    public void updateConditions(int xp) {

        this.stats = new Stats(
                stats.getHP(),
                stats.getAtkDmg(),
                stats.getArmor(),
                stats.getXp() + xp,
                stats.getLevel()
        );

        if (stats.getXp() >= stats.getXpBar()) {
            this.stats = new Stats(
                    stats.getHP(),
                    stats.getAtkDmg(),
                    stats.getArmor(),
                    stats.getXp() - stats.getXpBar(),
                    stats.getLevel() + 1);
        }
    }

}