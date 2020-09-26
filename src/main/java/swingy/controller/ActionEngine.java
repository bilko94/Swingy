package swingy.controller;

import swingy.model.*;

import java.util.List;

public class ActionEngine {
    private GameData gameData = new GameData();
    private WorldGeneration world = null;

    public void init(){
        gameData.duplicateHero();
        genWorld(gameData.tmpHero);
    }

    public void genWorld(Hero hero){
        world = new WorldGeneration();
        world.generateWorld(hero);
    }

    public List<MapData> getMapData(){
        return world.exportWorld();
    }

    public boolean loadSave() {
        if (gameData.checkLoad()){
            gameData.loadHero();
            return true;
        }
        return false;
    }

    public void saveGame(){
        gameData.saveHero();
    }

    public CombatReport preMove(String dir){
        if (world.ghostMove(dir))
            return new CombatReport();
        return new CombatReport(world.getEnemy(dir), gameData.getTmpHero().duplicateHero());
    }

    public void move(String dir, CombatReport report){
        world.move(dir);
        gameData.saveState();
        if (!report.proceed)
            gameData.setTmpHero(report.result);
        else {} // reset health
    }

    public GameData getGameData() {
        return gameData;
    }

    public WorldGeneration getWorld() {
        return world;
    }

//    public void test(){
//        // test create
//        gameData.createHero("CYKO","weeb");
//        gameData.saveHero();
//
//        // test load
//        if (loadSave())
//            System.out.println("Load success");
//        gameData.saveHero();
//
//        // test hero class
//        gameData.duplicateHero();
//        gameData.getHero();
//        gameData.getTmpHero().updateBackPack(new Artifact(Math.random()));
//
//        // Map test
//        genWorld(gameData.getHero());
//        List<MapData> mapData = getMapData();
//        for (MapData point :  mapData){
//            System.out.print(point.getCoords()[0] + " " + point.getCoords()[1]);
//            if (point.getOccupant() == 1){
//                System.out.print(" Enemy, ");
//            } else if (point.getOccupant() == 2){
//                System.out.println(" Hero");
//            }
//        }
//        System.out.println("");
//        int y = world.boundsY;
//        int x = 0;
//        while (y >= 0){
//            x = 0;
//            while (x <= world.boundsX){
//                switch (getOccupants(x, y)){
//                    case 0  : System.out.print("   "); break;
//                    case 1  : System.out.print("X  "); break;
//                    case 2  : System.out.print("H  "); break;
//                }
//                x++;
//            }
//            System.out.println("|");
//            y--;
//        }
//
//        // Combat test
//        CombatReport report = new CombatReport(mapData.get(0).getEnemy(), gameData.getTmpHero().duplicateHero());
//        System.out.println(" <<< Combat report >>>");
//        System.out.println("Hp " + gameData.getTmpHero().getStats().getHP() + " > " + report.result.getStats().getHP());
//        System.out.println("Run res: " + report.escape);
//        System.out.println("Drop : " + report.drop.getName());
//
//        // Move test
//        CombatReport reportTestN = preMove("n");
//        CombatReport reportTestS = preMove("s");
//        CombatReport reportTestE = preMove("e");
//        CombatReport reportTestW = preMove("w");
//        move("n",reportTestN);
//        System.out.println(world.heroX + " " + world.heroY);
//        move("s",reportTestS);
//        System.out.println(world.heroX + " " + world.heroY);
//        move("e",reportTestE);
//        System.out.println(world.heroX + " " + world.heroY);
//        move("w",reportTestW);
//        System.out.println(world.heroX + " " + world.heroY);
//
//        // equipItems Test
//        gameData.tmpHero.updateBackPack(new Artifact(Math.random()));
//        gameData.tmpHero.updateBackPack(new Artifact(Math.random()));
//        gameData.tmpHero.updateBackPack(new Artifact(Math.random()));
//        gameData.tmpHero.equipItem(gameData.tmpHero.getBackPack().get(0));
//        echoInv();
//        System.out.println("\nunequiping item");
//        gameData.tmpHero.unequipItem(gameData.tmpHero.getEquipped().get(0));
//        echoInv();
//        System.out.println("\nequiping item");
//        gameData.tmpHero.equipItem(gameData.tmpHero.getBackPack().get(0));
//        echoInv();
//        System.out.println("\nequiping same class item");
//        gameData.tmpHero.getBackPack().clear();
//        gameData.tmpHero.getEquipped().clear();
//        gameData.tmpHero.updateBackPack(new Artifact("Hellblade"));
//        gameData.tmpHero.updateBackPack(new Artifact("Carona Gun"));
//        gameData.tmpHero.equipItem(gameData.tmpHero.getBackPack().get(0));
//        echoInv();
//        System.out.println(" << reading eqipment >> ");
//        System.out.println(gameData.getTmpHero().getEquipped().get(0).getName());
//        gameData.tmpHero.equipItem(gameData.tmpHero.getBackPack().get(0));
//        System.out.println(gameData.getTmpHero().getEquipped().get(0).getName());
//        gameData.tmpHero.equipItem(gameData.tmpHero.getBackPack().get(0));
//        System.out.println(gameData.getTmpHero().getEquipped().get(0).getName());
//        gameData.tmpHero.equipItem(gameData.tmpHero.getBackPack().get(0));
//        System.out.println(gameData.getTmpHero().getEquipped().get(0).getName());
//    }

    int getOccupants(int x, int y){
        List<MapData> mapData = getMapData();
        for (MapData point : mapData){
            if (point.getCoords()[0] == x && point.getCoords()[1] == y){
                return point.getOccupant();
            }
        }
        return 0;
    }

    void echoInv(){
        System.out.println(" <<< backpack >>>");
        for (Artifact backpackItem : gameData.tmpHero.getBackPack())
            System.out.println(backpackItem.getName());
        System.out.println(" <<< equipment >>>");
        for (Artifact equippedItem : gameData.tmpHero.getEquipped())
            System.out.println(equippedItem.getName());
    }

}
