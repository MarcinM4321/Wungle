

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MainScreen {

    final private MainGameProfile gameProfile;
    private GameArea game;

    private JPanel mainPanel;
    private JButton showHistoryButton;
    private JPanel wordlePanel;



    public MainScreen() {
        JFrame mainGUIFrame = new JFrame("Wordle");


        gameProfile = new MainGameProfile();
        game = new GameArea(wordlePanel, gameProfile);


        showHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //MainGameProfile user
                ShowHistoryScreen screen = new ShowHistoryScreen(gameProfile);
            }
        });
        mainGUIFrame.setContentPane(mainPanel);
        mainGUIFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainGUIFrame.setBounds(10,10,400,600);
        mainGUIFrame.show();
        mainGUIFrame.setFocusable(true);
        mainGUIFrame.addFocusListener(new FocusListener() {
            //Potrzebne aby wordlePanel miał zawsze focus, aby mógł rejestrować klawiaturę.
            @Override
            public void focusGained(FocusEvent e) {
                wordlePanel.requestFocus();
            }
            @Override
            public void focusLost(FocusEvent e) {}
        });
    }

    public static void main(String[] args){
        new MainScreen();
    }
}
