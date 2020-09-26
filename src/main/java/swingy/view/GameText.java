package swingy.view;

import swingy.controller.ActionEngine;
import swingy.controller.CombatReport;
import swingy.model.Artifact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameText {
    public GameText(ActionEngine gameEngine) throws IOException {
        new TextRenderer().renderMap(gameEngine.getWorld());
        String in = new TextRenderer().getInput(
                "Move (n,s,e,w), extra commands gui(g) exit(ex)",
                new String[]{"n","s","e","w","g","ex"},
                1
        );
        if (in.equals("ex")){
            new MainMenuText(gameEngine);
        } else if (in.equals("i")) {
//            inventory(gameEngine);
        } else if (in.equals("save")) {
//            gameEngine.getGameData().saveState();
        } else if (in.equals("g")) {
            new GameGUI(gameEngine);
            return;
        } else {
            CombatReport report = gameEngine.preMove(in);
            System.out.println(report.proceed);
            if (report.proceed) {
                gameEngine.getGameData().nextMap();
                gameEngine.genWorld(gameEngine.getGameData().tmpHero);
                new GameText(gameEngine);
            } else if (report.combat) {
                in = new TextRenderer().getInput(
                        " << You have encountered an enemy >>\n\t(c): Cower and Run\n\t(f): Stand and Fight",
                        new String[]{"c","f"},
                        1
                );
                if (in.equals("c") && report.escape){
                    new TextRenderer().outAwait("\n <<< You live another day >>> \n  > press Enter to continue");
                    new GameText(gameEngine);
                } else {
                    new TextRenderer().outAwait("\n <<< Do or die >>> \n  > press Enter to proceed");
                    if (fight(report, gameEngine)){
                        new GameText(gameEngine);
                    } else {
                        new MainMenuText(gameEngine);
                    }
                }
            } else {
                gameEngine.move(in, report);
                new GameText(gameEngine);
            }
        }
    }

    private boolean fight(CombatReport report, ActionEngine gameEngine) throws IOException {
        if (report.result == null){
            die();
            return false;
        } else {
            gameEngine.getGameData().setTmpHero(report.result);
            gameEngine.getWorld().defeatEnemy(report.enemy);
            new TextRenderer().out("You Survived the battle with " + gameEngine.getGameData().tmpHero.getStats().getHP() + " HP");
            if (report.validDrop){
                System.out.println("You have found: " + report.drop.getName());
                String in = new TextRenderer().getInput(
                        "Do you wish to add this item to your inventory",
                        new String[]{"y","n"},
                        1
                );
                if (in.equals("y")){
                    gameEngine.getGameData().getTmpHero().equipItem(report.drop);//updateBackPack(report.drop);
                }
            } else {
                new TextRenderer().outAwait("Press Enter to continue");
            }
            return true;
        }
    }

    private void inventory(ActionEngine gameEngine) throws IOException {
        String in = new TextRenderer().getInput(
                "\nWhich actions do you wish to perform\n(e): equip\n(u): unequip\n(d): delete \n(l) list inventory and equipped\n(r) return to game",
                new String[]{"e","u","d","r","l"},
                1
        );
        if (in.equals("r"))
            new GameText(gameEngine);
        else if (in.equals("l")) {
            printLoadout(gameEngine.getGameData().tmpHero.getBackPack(),gameEngine.getGameData().tmpHero.getEquipped());
            new TextRenderer().outAwait("Press enter to continue");
            inventory(gameEngine);
        } else {
            if (in.equals("e")) {
                if (gameEngine.getGameData().tmpHero.getBackPack().size() == 0){
                    new TextRenderer().outAwait("You have no items in your inventory , press enter to continue");
                    inventory(gameEngine);
                }
                printList(gameEngine.getGameData().tmpHero.getBackPack());
//                System.out.println(Integer.toString(gameEngine.getGameData().tmpHero.getBackPack().size()));
                String itemNumber = new TextRenderer().getInput(
                        "What item do you wish to equip, input r to abort selection",
                        new String[]{Integer.toString(gameEngine.getGameData().tmpHero.getBackPack().size())},
                        2
                );
                if (itemNumber.equals("-1")){
                    inventory(gameEngine);
                } else {
                    gameEngine.getGameData().getTmpHero().equipItem(gameEngine.getGameData().tmpHero.getBackPack().get(Integer.parseInt(itemNumber)));
                }
            }  else if (in.equals("u")) {
                if (gameEngine.getGameData().tmpHero.getEquipped().size() == 0){
                    new TextRenderer().outAwait("You have no items equipped , press enter to continue");
                    inventory(gameEngine);
                }
                printList(gameEngine.getGameData().tmpHero.getEquipped());
                String itemNumber = new TextRenderer().getInput(
                        "What item do you wish to unequip, input r to abort selection",
                        new String[]{Integer.toString(gameEngine.getGameData().tmpHero.getEquipped().size())},
                        2
                );
                if (itemNumber.equals("-1")){
                    inventory(gameEngine);
                } else {
                    gameEngine.getGameData().getTmpHero().unequipItem(gameEngine.getGameData().tmpHero.getEquipped().get(Integer.parseInt(itemNumber)));
                }
            } else if (in.equals("d")) {
                if (gameEngine.getGameData().tmpHero.getBackPack().size() == 0){
                    new TextRenderer().outAwait("You have no items in your inventory , press enter to continue");
                    inventory(gameEngine);
                }
                printList(gameEngine.getGameData().tmpHero.getBackPack());
                String itemNumber = new TextRenderer().getInput(
                        "What item do you wish to remove, input r to abort selection",
                        new String[]{Integer.toString(gameEngine.getGameData().tmpHero.getBackPack().size())},
                        2
                );
                if (itemNumber.equals("-1")){
                    inventory(gameEngine);
                } else {
                    gameEngine.getGameData().getTmpHero().removeItem(gameEngine.getGameData().tmpHero.getBackPack().get(Integer.parseInt(itemNumber)));
                }
            }
//            inventory(gameEngine);
        }
    }

    private String[] toStringArray(List<Artifact> backpack){
        List<String> nameArray = new ArrayList<>();
        for (Artifact item : backpack){
            nameArray.add(item.getName());
        }
        nameArray.add("r");
        return (String[]) nameArray.toArray();
    }

    private void printList(List<Artifact> backpack){
        int pos = 0;
        while (pos < backpack.size()){
            new TextRenderer().out(pos + ": " + backpack.get(pos).getName());
            pos++;
        }
    }

    private void printLoadout(List<Artifact> backpack, List<Artifact> equipment){
        new TextRenderer().out(" Backpack");
        if (backpack.size() == 0)
            new TextRenderer().out("< empty >");
        else
            printList(backpack);
        new TextRenderer().out(" Equipment");
        if (equipment.size() == 0)
            new TextRenderer().out("< empty >");
        else
            printList(equipment);
    }

    private void die(){
        new TextRenderer().out(" << YOU DIED >>");
        return;
    }
}
