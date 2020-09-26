package swingy.view;

import swingy.controller.ActionEngine;
import swingy.controller.CombatReport;
import swingy.model.MapData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameGUI extends JFrame implements ActionListener {

//    private JPanel          Main;
//    private JPanel          Main2;
    private JPanel          MainBtns;
    private JLabel          Map;
    private MapGui          mapClass;
    private List<JLabel>    mapPoints = new ArrayList<>();
    private int             mapGuiBoundsX = 21;
    private int             mapGuiBoundsY = 11;
    private JButton         South, East, North, West, Inventory, TextMode, saveGame;
    private CombatReport    combatreport;
    private ActionEngine    game;
    BufferedImage bounds = ImageIO.read(new File("src\\main\\java\\swingy\\bounds.png"));
    BufferedImage empty = ImageIO.read(new File("src\\main\\java\\swingy\\empty.png"));
    BufferedImage enemy = ImageIO.read(new File("src\\main\\java\\swingy\\enemy.png"));
    BufferedImage hero = ImageIO.read(new File("src\\main\\java\\swingy\\hero.png"));

    public GameGUI(ActionEngine gameEngine) throws IOException {

        game = gameEngine;
//        Color myColor = new Color(90,151,255);
        Color myColor = new Color(50,50,50);
//        mapClass = new MapGui(mapGuiBoundsX,mapGuiBoundsY, gameEngine);
        this.setTitle("World of Anime");
//        this.getContentPane().setBackground(Color.BLUE);
        this.getContentPane().setBackground(new Color(70,70,70));
        this.setSize(550, 550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout(2,1));

//        Main = new JPanel(new GridLayout(1, 1));
//        Main2 = new JPanel(new GridLayout(1, 1));
        MainBtns = new JPanel();
        MainBtns.setBackground(myColor);
        South = new JButton("Move: South [s]");
        West = new JButton("Move: West [w]");
        North = new JButton("Move: North [n]");
        East = new JButton("Move: East [e]");
        Inventory = new JButton("Inventory");
        TextMode = new JButton("Text Mode");
        saveGame = new JButton("Save Game");

//        if (game.getWorld().boundsY > 15)
//            this.setSize(1500, 1500);

        /// Set Map
        Map = new JLabel("No map generated");
        if (gameEngine.getWorld().exportMapHtml() != null)
            Map.setText(gameEngine.getWorld().exportMapHtml());
        Map.setBackground(myColor);
        Map.setForeground(Color.WHITE);

        //Graph layout
        MainBtns.add(West);
        MainBtns.add(North);
        MainBtns.add(South);
        MainBtns.add(East);
        MainBtns.add(Inventory);
        MainBtns.add(TextMode);
        MainBtns.add(saveGame);
        Map.setHorizontalAlignment(SwingConstants.CENTER);
        Map.setVerticalAlignment(SwingConstants.CENTER);
//        Main2.add(new JLabel(""));
        this.add(Map);
//        this.add(buildMap());
//        this.add(mapClass.buildMap());
//        Main2.add(new JLabel(""));
        this.add(MainBtns);
//        this.add(Main2);
//        this.add(Main);

        North.addActionListener(this);
        East.addActionListener(this);
        South.addActionListener(this);
        West.addActionListener(this);
        Inventory.addActionListener(this);
        TextMode.addActionListener(this);
        saveGame.addActionListener(this);

        this.setResizable(false);
        this.setVisible(true);
    }

    public void combatSimulator(int i){
        if (i == 1){
            if (combatreport.result == null){
                JOptionPane.showMessageDialog(null,"YOU DIED...");
                try {
                    setVisible(false); //you can't see me!
                    dispose(); //Destroy the JFrame object
                    new MainMenuGUI(game);
                } catch (IOException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
            else {
                game.getGameData().setTmpHero(combatreport.result);
                game.getWorld().defeatEnemy(combatreport.enemy);
                JOptionPane.showMessageDialog(null,"You won the battle remaining HP:" + combatreport.result.getStats().getHP());
                if (combatreport.validDrop){
                    int n = JOptionPane.showConfirmDialog(null,"You have found "+combatreport.drop.getName()+" do you wish to keep it?","Loot",JOptionPane.YES_NO_OPTION );
                    if (n == 0) {
                        game.getGameData().getTmpHero().updateBackPack(combatreport.drop);
                    }
                }
//                System.out.println(game.getGameData().getTmpHero().getStats().getXp());
//                System.out.println(game.getGameData().getTmpHero().getStats().getLevel());
                this.Map.setText(game.getWorld().exportMapHtml());
//                renderMap();
//                mapClass.renderMap();
            }
        }
        else {
            if (combatreport.escape){
                JOptionPane.showMessageDialog(null,"You have successfully run like a little bitch... Subaru");
            }
            else {
                JOptionPane.showMessageDialog(null, "Fight or die there is no escape");
                combatSimulator(1);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ae = e.getSource();

        if (ae.equals(this.North)){
//            System.out.println("Move action North");
            this.combatreport = game.preMove("n");
            if (combatreport.combat){
                int n = JOptionPane.showConfirmDialog(null,"You have encountered and enemy! Do you wish to Fight?","Fight or die",JOptionPane.YES_NO_OPTION );
                if (n == 0){
                    combatSimulator(1);
                }
                else {
                    combatSimulator(0);
                }
            }
            else {
//                System.out.println("Moving North");
                game.move("n", combatreport);
                if (combatreport.proceed) {
                    game.genWorld(game.getGameData().getTmpHero());
                    JOptionPane.showMessageDialog(null,"You have proceeded to the next stage");
                }
                this.Map.setText(game.getWorld().exportMapHtml());
//                mapClass.renderMap();
            }
        }
        else if (ae.equals(this.East)){
//            System.out.println("Move action East");
            this.combatreport = game.preMove("e");
            if (combatreport.combat){
                int n = JOptionPane.showConfirmDialog(null,"You have encountered and enemy! Do you wish to Fight?","Fight or die",JOptionPane.YES_NO_OPTION );
                if (n == 0){
                    combatSimulator(1);
                }
                else {
                    combatSimulator(0);
                }
            }
            else {
//                System.out.println("Moving East");
                game.move("e", combatreport);
                if (combatreport.proceed) {
                    game.genWorld(game.getGameData().getTmpHero());
                    JOptionPane.showMessageDialog(null,"You have proceeded to the next stage");
                }
                this.Map.setText(game.getWorld().exportMapHtml());
//                mapClass.renderMap();
            }
        }
        else if (ae.equals(this.South)){
//            System.out.println("Move action South");
            this.combatreport = game.preMove("s");
            if (combatreport.combat){
                int n = JOptionPane.showConfirmDialog(null,"You have encountered and enemy! Do you wish to Fight?","Fight or die",JOptionPane.YES_NO_OPTION );
                if (n == 0){
                    combatSimulator(1);
                }
                else {
                    combatSimulator(0);
                }
            }
            else {
//                System.out.println("Moving South");
                game.move("s", combatreport);
                if (combatreport.proceed) {
                    game.genWorld(game.getGameData().getTmpHero());
                    JOptionPane.showMessageDialog(null,"You have proceeded to the next stage");
                }
                this.Map.setText(game.getWorld().exportMapHtml());
//                mapClass.renderMap();
            }
        }
        else if (ae.equals(this.West)){
//            System.out.println("Move action West");
            this.combatreport = game.preMove("w");
            if (combatreport.combat){
                int n = JOptionPane.showConfirmDialog(null,"You have encountered and enemy! Do you wish to Fight?","Fight or die",JOptionPane.YES_NO_OPTION );
                if (n == 0){
                    combatSimulator(1);
                }
                else {
                    combatSimulator(0);
                }
            }
            else {
//                System.out.println("Moving West");
                game.move("w", combatreport);
                if (combatreport.proceed) {
                    game.genWorld(game.getGameData().getTmpHero());
                    JOptionPane.showMessageDialog(null,"You have proceeded to the next stage");
                }
                this.Map.setText(game.getWorld().exportMapHtml());
//                mapClass.renderMap();
            }
        }
        else if (ae.equals(this.Inventory)){
            new InventoryGUI(this.game);
        }
        else if (ae.equals(this.TextMode)){
            try {
                setVisible(false); //you can't see me!
                dispose(); //Destroy the JFrame object
                new GameText(game);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        else if (ae.equals(this.saveGame)){
            game.getGameData().saveState();
            JOptionPane.showMessageDialog(null,"You game session has been saved");
        }
    }

//    private JPanel buildMap(){
//        // make map
//        JPanel map = new JPanel();
//        int x = mapGuiBoundsX;
//        int y = mapGuiBoundsY;
//        map.setLayout(new GridLayout(y,x));
//        map.setBackground(new Color(0,0,0));
//
//        // make new labels
//        int labelPos = 0;
//        JLabel tempLabel;
//        List<JLabel> tempLabelList = new ArrayList<>();
//        while (labelPos < x * y){
//            tempLabel = new JLabel("ok");
//            tempLabelList.add(tempLabel);
//            map.add(tempLabel);
//            labelPos++;
//        }
//
//        // reorder labels
//        int labelRowPos = y;
//        int startRowPos;
//        int endRowPos;
//        while (labelRowPos > 0){
//            startRowPos = (labelRowPos - 1) * x;
//            endRowPos = labelRowPos * x;
//            while (startRowPos < endRowPos){
//                mapPoints.add(tempLabelList.get(startRowPos));
//                startRowPos++;
//            }
//            labelRowPos--;
//        }
//
//        // test render
//        renderMap();
//
//        // return map to be set in constructor
//        return map;
//    }
//
//    public void renderMap(){
//        int x = 1;
//        int y = 1;
//        List<MapData> mapData = game.getWorld().exportWorld();
//        while (y <= mapGuiBoundsY){
//            x = 1;
//            while (x <= mapGuiBoundsX){
//                mapPoints.get(((y - 1) * mapGuiBoundsX) + (x - 1)).setIcon(new ImageIcon(empty));
////                mapPoints.get(((y - 1) * mapGuiBoundsX) + (x - 1)).setText(String.valueOf(y * x));
////                if ((y <= game.getWorld().boundsY && !(y < 0)) && (x <= game.getWorld().boundsX) && !(x < 0)){
////                    switch (getOccupants(x, y, mapData)){
////                        case 0  : mapPoints.get(((y - 1) * mapGuiBoundsX) + (x - 1)).setIcon(new ImageIcon(empty)); break;
////                        case 1  : mapPoints.get(((y - 1) * mapGuiBoundsX) + (x - 1)).setIcon(new ImageIcon(enemy)); break;
////                        case 2  : mapPoints.get(((y - 1) * mapGuiBoundsX) + (x - 1)).setIcon(new ImageIcon(hero)); break;
////                    }
////                } else {
////                    mapPoints.get(((y - 1) * mapGuiBoundsX) + (x - 1)).setIcon(new ImageIcon(bounds));
////                }
//                x++;
//            }
//            y++;
//        }
//    }
//
//    public void setPoint(int row, int col, String icon){
//        int rowPos = ((row - 1) * 10);
//        mapPoints.get(rowPos).setText(icon);
//    }
//
//    private int getOccupants(int x, int y, List<MapData> mapData){
//        for (MapData point : mapData){
//            if (point.getCoords()[0] == x && point.getCoords()[1] == y){
//                return point.getOccupant();
//            }
//        }
//        return 0;
//    }
}
