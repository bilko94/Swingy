package swingy.view;

import swingy.controller.ActionEngine;
import swingy.model.MapData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapGui {
    ActionEngine gameEngine = null;
    List<MapGUIData> mapGUIData = new ArrayList<>();
    JPanel map = new JPanel();
    BufferedImage bounds = ImageIO.read(new File("src\\main\\java\\swingy\\bounds.png"));
    BufferedImage enemy = ImageIO.read(new File("src\\main\\java\\swingy\\enemy.png"));
    BufferedImage empty = ImageIO.read(new File("src\\main\\java\\swingy\\empty.png"));
    BufferedImage hero = ImageIO.read(new File("src\\main\\java\\swingy\\hero.png"));

    private int mapGuiBoundsX;
    private int mapGuiBoundsY;

    public MapGui(int mapGuiBoundsX, int mapGuiBoundsY, ActionEngine gameEngine) throws IOException {
        this.mapGuiBoundsX = mapGuiBoundsX;
        this.mapGuiBoundsY = mapGuiBoundsY;
        this.gameEngine = gameEngine;
    }

    public JPanel buildMap(){
        // make map
        map.setLayout(new GridLayout(mapGuiBoundsY,mapGuiBoundsX));
        map.setBackground(new Color(0,0,0));

        // make new labels
        int x ;
        int y = 1;
        while (y <= mapGuiBoundsY){
            x = 1;
            while (x <= mapGuiBoundsX){
                mapGUIData.add(new MapGUIData(new JLabel(), x, y) );
                x++;
            }
            y++;
        }

        // setting pannels to propper positions
        placePanels();

        // testRender
        renderMap();
        return map;
    }

    public void placePanels(){
        int x = 1;
        int y = mapGuiBoundsY;
        int pos = 0;
        while (y > 0){
            x = 1;
            while (x < mapGuiBoundsX){
                map.add(getPoint(x, y));
                getPoint(x, y).setText(String.valueOf(pos));
                pos++;
                x++;
            }
            System.out.println();
            y--;
        }
    }

    JLabel getPoint(int x, int y){
        for (MapGUIData point : mapGUIData){
            if (point.x == x && point.y == y)
                return point.label;
        }
        return null;
    }

    public void renderMap(){
        List<MapData> mapData = gameEngine.getMapData();
        int y = 1;
        int x;
        int offsetX = 5 - gameEngine.getWorld().getHeroX();
        int offsetY = 10 - gameEngine.getWorld().getHeroY();
        while (y <= mapGuiBoundsY){
            x = 1;
            while (x <= mapGuiBoundsX){
                if ((((x + offsetX) > 0) && (x + offsetX <= mapGuiBoundsX)) && (((y + offsetY) > 0) && (y + offsetY <= mapGuiBoundsX))){
                    System.out.print("X");
                    switch (getOccupants(x ,y ,mapData)){
                        case 0 : getPoint(x, y).setIcon(new ImageIcon(empty));break;
                        case 1 : getPoint(x, y).setIcon(new ImageIcon(enemy));break;
                        case 2 : getPoint(x, y).setIcon(new ImageIcon(hero));break;
                    }
                } else {
                    getPoint(x, y).setIcon(new ImageIcon(bounds));
                    System.out.print(getOccupants(x,y,mapData));
                }
                x++;
            }
            System.out.println();
            y++;
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
