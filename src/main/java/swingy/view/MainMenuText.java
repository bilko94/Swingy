package swingy.view;

import swingy.controller.ActionEngine;

import java.io.IOException;

public class MainMenuText {
    public MainMenuText(ActionEngine gameEngine) throws IOException {
        String in = new TextRenderer().getInput(
                " < Menu > \n(l): load game\n(n): new game\n(e): exit\n(g): gui",
                new String[]{"l","n","e","g","test"},
                1
        );
        if (in.equals("l")){
            if (gameEngine.getGameData().checkLoad()){
                gameEngine.getGameData().loadHero();
                gameEngine.init();
                new GameText(gameEngine);
            }
            else {
                new TextRenderer().out("No save game available");
                new MainMenuText(gameEngine);
            }
        } else if (in.equals("n")) {
            new CharCreationText(gameEngine);
            new GameText(gameEngine);
        } else if (in.equals("e")) {
            return;
        } else if (in.equals("g")) {
            new MainMenuGUI(gameEngine);
        }
    }
}
