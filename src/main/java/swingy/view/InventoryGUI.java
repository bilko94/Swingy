package swingy.view;

import swingy.controller.ActionEngine;
import swingy.model.Artifact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class InventoryGUI extends JFrame implements ActionListener {

    private JPanel          mainInv;
    private JPanel          btns;
    private JLabel          title;
    private JPanel          item;
    private JButton         equip, remove, next, prev, back;
    private JLabel          itemName;
    private JPanel          itemStats;
    private ActionEngine    game;
    private JLabel          atkL;
    private JLabel          armorL;
    private JLabel          hpL;
    private long            atk;
    private int             armor;
    private long            hp;
    private int             index = 0;
    private Artifact        backPack;

    public InventoryGUI(ActionEngine gameEngine){

        this.game = gameEngine;
        Color myColor = new Color(90,151,255);
//        Color myColor = new Color(200,200,200);
        this.setTitle("World of Anime");
        this.getContentPane().setBackground(Color.BLUE);
        this.setSize(450, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLayout(new GridLayout(3,1));

        mainInv = new JPanel(new GridLayout(1, 2));
        btns = new JPanel(new GridLayout(2, 2));
        item = new JPanel(new GridLayout(1,2));
        equip = new JButton("Equip");
        title = new JLabel("Inventory");
        remove = new JButton("Remove");
        back = new JButton("Back");
        next = new JButton("Next");
        prev = new JButton("Prev");
        if (game.getGameData().getTmpHero().getBackPack().size() != 0){
            backPack = game.getGameData().getTmpHero().getBackPack().get(this.index);
            itemName = new JLabel(backPack.getName());
            atk = backPack.getDamage();
            armor = backPack.getArmour();
            hp = backPack.getHp();
        }
        else {
            itemName = new JLabel("No items available");
            atk = 0;
            armor = 0;
            hp = 0;
        }
        itemStats = new JPanel(new GridLayout(3,1));
        armorL = new JLabel("Armor: "+ armor);
        atkL = new JLabel("Atk: "+atk);
        hpL = new JLabel("HP: "+ hp);

        mainInv.setBackground(myColor);
        btns.setBackground(myColor);
        title.setForeground(Color.WHITE);

        title.setHorizontalAlignment(SwingConstants.CENTER);

        itemStats.add(atkL);
        itemStats.add(armorL);
        itemStats.add(hpL);
        item.add(itemName);
        item.add(itemStats);
        mainInv.add(item);
        btns.add(prev);
        btns.add(next);
        btns.add(remove);
        btns.add(equip);
        this.add(title);
        this.add(mainInv);
        this.add(btns);

        prev.addActionListener(this);
        next.addActionListener(this);
        remove.addActionListener(this);
        equip.addActionListener(this);
        back.addActionListener(this);

        this.setResizable(false);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ae = e.getSource();
        List<Artifact> bag = game.getGameData().getTmpHero().getBackPack();
        int listSize = bag.size();

        if (ae.equals(this.next)){
            listSize = bag.size();
            if (listSize > 0){
                if ((this.index+1) < listSize){
//                    System.out.println(this.index);
                    this.index += 1;
                }
                else {
//                    System.out.println(this.index);
                    this.index = 0;
                }
                this.backPack = bag.get(this.index);
                this.itemName.setText(backPack.getName());
                atk = backPack.getDamage();
                armor = backPack.getArmour();
                hp = backPack.getHp();
                atkL.setText("Atk: "+atk);
                armorL.setText("Armor: "+armor);
                hpL.setText("HP: "+hp);
            }
            else {
                JOptionPane.showMessageDialog(null,"No items in inventory");
            }
        }
        else if (ae.equals(this.prev)){
            listSize = bag.size();
            if (listSize > 0){
                if (index > 0)
                    index -= 1;
                else
                    index = listSize-1;
//                System.out.println(this.index);
                this.backPack = bag.get(this.index);
                this.itemName.setText(backPack.getName());
                atk = backPack.getDamage();
                armor = backPack.getArmour();
                hp = backPack.getHp();
                atkL.setText("Atk: "+atk);
                armorL.setText("Armor: "+armor);
                hpL.setText("HP: "+hp);
            }
            else {
                JOptionPane.showMessageDialog(null,"No items in inventory");
            }
        }
        else if (ae.equals(this.remove)){
            listSize = bag.size();
            if (listSize > 0){
//                System.out.println(this.index);
                bag.remove(this.index);
            }else {
                JOptionPane.showMessageDialog(null,"No items in inventory");
            }
            listSize = bag.size();
            if (listSize > 0){
                this.backPack = bag.get(this.index);
                this.itemName.setText(backPack.getName());
                atk = backPack.getDamage();
                armor = backPack.getArmour();
                hp = backPack.getHp();
                atkL.setText("Atk: "+atk);
                armorL.setText("Armor: "+armor);
                hpL.setText("HP: "+hp);
            }
            else {
                this.itemName.setText("Empty");
                atkL.setText("Atk: 0");
                armorL.setText("Armor: 0");
                hpL.setText("HP: 0");
            }
        }
        else if (ae.equals(this.equip)){
            listSize = bag.size();
//            System.out.println(this.index);
            if (listSize > 0){
                JOptionPane.showMessageDialog(null,"You have equipped "+bag.get(this.index).getName());
                game.getGameData().getTmpHero().equipItem(bag.get(this.index));
                listSize = bag.size();
                if (this.index > (listSize-1) && listSize != 0)
                    this.index = listSize-1;
                if (listSize != 0) {
                    this.backPack = bag.get(this.index);
                    this.itemName.setText(backPack.getName());
                    atk = backPack.getDamage();
                    armor = backPack.getArmour();
                    hp = backPack.getHp();
                    atkL.setText("Atk: " + atk);
                    armorL.setText("Armor: " + armor);
                    hpL.setText("HP: " + hp);
                } else {
                    this.itemName.setText("Empty");
                    atkL.setText("Atk: 0");
                    armorL.setText("Armor: 0");
                    hpL.setText("HP: 0");
                }
            } else {
                JOptionPane.showMessageDialog(null,"No items in inventory");
            }
        }
    }
}
