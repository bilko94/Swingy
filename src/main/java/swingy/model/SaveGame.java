package swingy.model;

import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.lang.reflect.Array;

public class  SaveGame {

    public void saveData(Hero ObjHero){

        try {
            int i = 7;
            FileWriter myWriter = new FileWriter("SavedGame.txt");
            String[] data = new String[57];
//            System.out.println("Saving game...");
            data[0] = String.valueOf("Armour "+ObjHero.getStats().getArmor());
            data[1] = String.valueOf("AtkDmg "+ObjHero.getStats().getAtkDmg());
            data[2] = String.valueOf("HP "+ObjHero.getStats().getHP());
            data[3] = String.valueOf("Lvl "+ObjHero.getStats().getLevel());
            data[4] = String.valueOf("XP "+ObjHero.getStats().getXp());
            data[5] = "Name "+ObjHero.getName();
            data[6] = "heroClass "+ObjHero.getHeroClass();

            data[i++] = "Backpack";
            if (!ObjHero.getBackPack().isEmpty()){
                for (Artifact element : ObjHero.getBackPack()) {
                    data[i++] = element.getName();
                }
            }
            data[i++] = "equipment";
            if (!ObjHero.getEquipped().isEmpty()){
                for (Artifact element : ObjHero.getEquipped()) {
                    data[i++] = element.getName();
                }
            }

            /// Writer
            i = 0;
            while (data[i] != null){
                myWriter.write(data[i]+"\n");
                i++;
            }
            myWriter.close();

        } catch (IOException e) {
//            System.out.println("Well shit son");
            e.printStackTrace();
        }
    }

}
