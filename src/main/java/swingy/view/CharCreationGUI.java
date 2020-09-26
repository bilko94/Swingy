package swingy.view;

import swingy.controller.ActionEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CharCreationGUI extends JFrame implements ActionListener {

    private JTextField      textField;
    private JButton         Normie, Weeb, Otaku, Methhead, Start;
    private JPanel          p1;
    private JPanel          name;
    private JPanel          start;
    private JLabel          heroHint;
    private JLabel          blank;
    private JLabel          nothing;
    private ActionEngine    game;

    public CharCreationGUI(ActionEngine gameEngine){

        this.game = gameEngine;
//        Color myColor = new Color(90,151,255);
        Color myColor = new Color(200,200,200);
        this.setTitle("World of Anime");
        this.getContentPane().setBackground(myColor);
        this.setSize(450, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout());

        this.textField = new JTextField(25);

        heroHint = new JLabel("Hero Name:");
        heroHint.setForeground(Color.WHITE);
        nothing = new JLabel("Class: ");
        nothing.setForeground(Color.WHITE);
        blank = new JLabel(" ");
        blank.setForeground(Color.WHITE);
        name = new JPanel();
        name.setBackground(myColor);
        p1 = new JPanel();
        p1.setBackground(myColor);
        start = new JPanel();
        start.setBackground(myColor);
        name.setLayout(new GridLayout(2, 2));
        p1.setLayout(new GridLayout(2,2));

        Normie = new JButton("Class: Normie");
        Weeb = new JButton("Class: Weeb");
        Otaku = new JButton("Class: Otaku");
        Methhead = new JButton("Class: Methhead");
        Start = new JButton("Start Game");

//        this.Submit.setVerticalAlignment(SwingConstants.TOP);

        this.p1.add(this.Normie);
        this.p1.add(this.Weeb);
        this.p1.add(this.Otaku);
        this.p1.add(this.Methhead);
        this.name.add(heroHint);
        this.name.add(this.textField);
        this.name.add(nothing);
        this.name.add(blank);
//        this.name.add(this.Submit);
        this.start.add(this.Start);

        this.Start.setHorizontalAlignment(2);

//        this.add(this.textField, new GridLayout(1, 2));
        this.add(this.name);
        this.add(this.p1);
        this.add(start);
//        this.add(this.start, BorderLayout.EAST);

        setLayout(new GridLayout(4, 1));

        Normie.addActionListener(this);
        Weeb.addActionListener(this);
        Otaku.addActionListener(this);
        Methhead.addActionListener(this);
        Start.addActionListener(this);

        this.setResizable(false);
        this.setVisible(true);

//        JPanel pan = new JPanel();
//        pan.setBackground(Color.BLUE);
//        this.setContentPane(new Panel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ae = e.getSource();

        if (ae.equals(this.Normie)){
            System.out.println("You selected Normie");
            blank.setText("normie");
        }
        else if (ae.equals(this.Weeb)){
            System.out.println("You selected Weeb");
            blank.setText("weeb");
        }
        else if (ae.equals(this.Otaku)){
            System.out.println("You selected Otaku");
            blank.setText("otaku");
        }
        else if (ae.equals(this.Methhead)){
            System.out.println("You selected Methhead");
            blank.setText("Methhead");
        }
        else if (ae.equals(this.Start)){
            System.out.println(textField.getText());
            System.out.println(blank.getText());
            if (!textField.getText().equals("") && !blank.getText().equals(" ")){
                System.out.println("Starting Game");
                if (this.game.getGameData().createHero(textField.getText(), blank.getText())){
                    game.init();
                    setVisible(false); //you can't see me!
                    dispose(); //Destroy the JFrame object
                    try {
                        new GameGUI(game);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"Invalid class and or name");
                }
            }
            else if(textField.getText().equals("") && blank.getText().equals(" ")){
                JOptionPane.showMessageDialog(null,"You must enter a Name and select a Class");
            }
            else if(textField.getText().equals("")){
                JOptionPane.showMessageDialog(null,"You must enter a Name");
            }
            else if (blank.getText().equals(" ")){
                JOptionPane.showMessageDialog(null,"You must select a Class");
            }

        }
    }
}
