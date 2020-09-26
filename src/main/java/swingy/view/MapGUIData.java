package swingy.view;

import javax.swing.*;

public class MapGUIData {
    JLabel label;
    int x = 0;
    int y = 0;


    public MapGUIData(JLabel point, int x, int y){
        this.label = point;
        this.x = x;
        this.y = y;
    }
}
