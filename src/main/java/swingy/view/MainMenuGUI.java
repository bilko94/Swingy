package swingy.view;

import swingy.controller.ActionEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainMenuGUI extends JFrame implements ActionListener{

    private JPanel          Mainb;
    private JPanel          Maintitle;
    private JLabel          title;
    private JPanel          Mainbtns;
    private JButton         loadGame, newGame, textMode;
    private ActionEngine    game;
    BufferedImage myPicture = ImageIO.read(new File("src\\main\\java\\swingy\\Loli.png"));
    JLabel picLabel = new JLabel(new ImageIcon(myPicture));
    JLabel picLabel2 = new JLabel(new ImageIcon(myPicture));


    public MainMenuGUI(ActionEngine gameEngine) throws IOException {

        this.game = gameEngine;
//        Color myColor = new Color(90,151,255);
        Color myColor = new Color(200,200,200);
        this.setTitle("World of Anime");
        this.getContentPane().setBackground(Color.BLUE);
        this.setSize(450, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout());


        Mainb = new JPanel();
        Maintitle = new JPanel();
        Mainbtns = new JPanel();
        title = new JLabel("World Of Anime");
        loadGame = new JButton("Load Game");
        newGame = new JButton("New Game");
        textMode = new JButton("Text Mode");

        setLayout(new GridLayout(3, 1));
        Mainb.setLayout(new GridLayout(2,3));
        Maintitle.setLayout(new GridLayout(1, 3));
        Mainbtns.setLayout(new GridLayout(2, 3));
        Mainb.setBackground(myColor);
        Maintitle.setBackground(myColor);
        Mainbtns.setBackground(myColor);
        title.setForeground(Color.WHITE);

        title.setHorizontalAlignment(SwingConstants.CENTER);

        Maintitle.add(picLabel);
        Maintitle.add(title);
        Maintitle.add(picLabel2);
        Mainbtns.add(new JLabel(" "));
        Mainbtns.add(newGame);
        Mainbtns.add(new JLabel(" "));
        Mainbtns.add(new JLabel(" "));
        Mainbtns.add(loadGame);
        Mainbtns.add(new JLabel(" "));
        this.add(Maintitle);
        this.add(Mainbtns);
        Mainb.add(new JLabel(" "));
        Mainb.add(new JLabel(" "));
        Mainb.add(new JLabel(" "));
        Mainb.add(new JLabel(" "));
        Mainb.add(textMode);
        Mainb.add(new JLabel(" "));
        this.add(Mainb);

        loadGame.addActionListener(this);
        newGame.addActionListener(this);
        textMode.addActionListener(this);


        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ae = e.getSource();

        if (ae.equals(this.newGame)){
//            System.out.println("NewGame selected");
            setVisible(false); //you can't see me!
            dispose(); //Destroy the JFrame object
            new CharCreationGUI(this.game);
        }
        else if (ae.equals(this.loadGame)){
//            System.out.println("loading Game");
            if (game.getGameData().checkLoad()) {
                game.getGameData().loadHero();
                game.init();
                setVisible(false); //you can't see me!
                dispose(); //Destroy the JFrame object
                try {
                    new GameGUI(game);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        else if (ae.equals(this.textMode)){
            try {
                setVisible(false); //you can't see me!
                dispose(); //Destroy the JFrame object
                new MainMenuText(game);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}
