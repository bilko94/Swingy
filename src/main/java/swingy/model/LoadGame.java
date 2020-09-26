package swingy.model;


import javax.validation.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Set;

public class LoadGame {

    private List<Artifact>        backPack = new ArrayList<>();
    private List<Artifact>        equipped = new ArrayList<>();

    public Hero load() throws FileNotFoundException {
        ///Stats
        Hero heroObj;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Hero>> violations;
        int Armour = 0;
        int AtkDmg = 0;
        int HP = 1;
        int Lvl = 1;
        int Xp = 0;
        String Name = null;
        String Class = null;

        try {
            File myObj = new File("SavedGame.txt");
            Scanner myReader = new Scanner(myObj);
            String[] array;
//            System.out.println("Loading Game");
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                array = data.split(" ");
//            System.out.println(array[0]);
                if (array[0].equals("Armour")) {
                    Armour = Integer.parseInt(array[1]);
                } else if (array[0].equals("AtkDmg")) {
                    AtkDmg = Integer.parseInt(array[1]);
                } else if (array[0].equals("HP")) {
                    HP = Integer.parseInt(array[1]);
                } else if (array[0].equals("Lvl")) {
                    Lvl = Integer.parseInt(array[1]);
                } else if (array[0].equals("XP")) {
                    Xp = Integer.parseInt(array[1]);
                } else if (array[0].equals("Name")) {
                    Name = array[1];
                } else if (array[0].equals("heroClass")) {
                    Class = array[1];
                } else if (array[0].equals("Backpack")) {
                    while (myReader.hasNextLine()) {
                        data = myReader.nextLine();
                        if (data.equals("equipment")) {
                            break;
                        } else {
                            this.backPack.add(new Artifact(data));
                        }
                    }
                    while (myReader.hasNextLine()) {
                        data = myReader.nextLine();
                        this.equipped.add(new Artifact(data));
                    }
                }
            }
            myReader.close();
            heroObj = new Hero(Name, Class);
            heroObj.initializeSave(Armour, AtkDmg, HP, Lvl, Xp, Name, Class, this.backPack, this.equipped);
            violations = validator.validate(heroObj);
//            System.out.println(violations.size() > 0);
            if (violations.size() > 0){
                return null;
            }
            return heroObj;
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            return (null);
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            return (null);
        }
    }

    public LoadGame() throws FileNotFoundException {
    }
}