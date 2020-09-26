package swingy.view;

import swingy.controller.ActionEngine;

import java.io.IOException;

public class CharCreationText {
    public CharCreationText(ActionEngine gameEngine) throws IOException {
        String name = new TextRenderer().getInput(
                "Enter name of hero",
                new String[]{""},
                0
        );
        String heroClass = new TextRenderer().getInput(
                "Choose your hero class : normie,weeb,otaku or MethHead",
                new String[]{"normie","weeb","otaku","MethHead"},
                1
        );
        if (gameEngine.getGameData().createHero(name, heroClass))
            gameEngine.init();
        else {
            new TextRenderer().outAwait("Invalid name or class input");
            new CharCreationText(gameEngine);
        }
    }
}
