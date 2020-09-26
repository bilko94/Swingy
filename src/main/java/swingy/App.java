package swingy;

import swingy.controller.ActionEngine;
import swingy.view.*;

import java.io.IOException;
/**
 * Hello world!
 *
*/
public class App {
    public static void main( String[] args ) {
        ActionEngine gameEngine =  new ActionEngine();
        try{
            if (args.length == 1){
                if (args[0].equals("console"))
                    new MainMenuText(gameEngine);
                else if (args[0].equals("gui"))
                    new MainMenuGUI(gameEngine);
                else if (args[0].equals("test")){
                    if (gameEngine.getGameData().checkLoad()){
                        gameEngine.getGameData().loadHero();
                        gameEngine.init();
                        new GameText(gameEngine);
//                        new GameGUI(gameEngine);
                    }
                    else {
                        new TextRenderer().out("No save game available");
                        new MainMenuText(gameEngine);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
