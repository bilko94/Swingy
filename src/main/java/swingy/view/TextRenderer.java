package swingy.view;
import swingy.controller.WorldGeneration;
import swingy.model.MapData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TextRenderer {
    public String getInput(String out, String[] options, int inputCheckToggle) throws IOException {
        System.out.println(out);
        System.out.print(">>> ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String in = reader.readLine();
        int pos = 0;
        while (pos < options.length){
            if (inputCheckToggle == 0) {
                if (in.length() < 2){
                    System.out.println("\n INPUT TOO SHORT");
                    return getInput(out, options, inputCheckToggle);
                }
                return in;
            } else if (inputCheckToggle == 2) {
                if (in.equals("r"))
                    return "-1";
                if ((checkParse(in) && Integer.parseInt(in) <= Integer.parseInt(options[0])) && Integer.parseInt(in) >= 0){
                    return in;
                } else {
                    System.out.println("\n invalid number , range is 1 to " + options[0]);
                }
            } else if (in.toLowerCase().equals(options[pos].toLowerCase())) {
                final String s = in.toLowerCase();
                return s;
            }
            pos++;
        }
        System.out.println("\n INVALID INPUT");
        return getInput(out, options, inputCheckToggle);
    }

    private boolean checkParse(String number){
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public void out(String in){
        System.out.println(in);
    }

    public void outAwait(String in) throws IOException {
        System.out.println(in);
        System.in.read();
    }

    public void renderMap(WorldGeneration world){
        int minY = world.getHeroY() - 5;
        int maxY = world.getHeroY() + 5;
        int minX;
        int maxX = world.getHeroX() + 10;
        while (maxY >= minY){
            minX = world.getHeroX() - 10;
            while (minX < maxX){
                if ((maxY <= world.boundsY && !(maxY < 0)) && (minX <= world.boundsX) && !(minX < 0)){
                    switch (getOccupants(minX, maxY, world.exportWorld())){
                        case 0  : System.out.print("   "); break;
                        case 1  : System.out.print(" E "); break;
                        case 2  : System.out.print(" H "); break;
                    }
                } else {
                    System.out.print(" X ");
                }
                minX++;
            }
            System.out.println("|");
            maxY--;
        }
    }

    private int getOccupants(int x, int y, List<MapData> mapData){
        for (MapData point : mapData){
            if (point.getCoords()[0] == x && point.getCoords()[1] == y){
                return point.getOccupant();
            }
        }
        return 0;
    }
}