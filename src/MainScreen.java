import javax.swing.*;
import java.awt.event.*;

public class MainScreen {

    final private MainGameProfile gameProfile;
    private GameArea game;

    private JPanel mainPanel;
    private JButton showHistoryButton;
    private JPanel wordlePanel;

    public MainScreen(MainGameProfile profile) {
        JFrame mainGUIFrame = new JFrame("Wordle");

        ErrorMessenger errorMessenger = new ErrorMessenger(mainGUIFrame);
        gameProfile = profile;
        game = new GameArea(wordlePanel, gameProfile);
        showHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //MainGameProfile user
                ShowHistoryScreen screen = new ShowHistoryScreen(gameProfile);
                mainGUIFrame.requestFocus(); //potrzebne, aby okno rejestrowało klawiaturę
            }
        });
        mainGUIFrame.setContentPane(mainPanel);
        mainGUIFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainGUIFrame.setBounds(180,180,400,600);
        mainGUIFrame.show();
        mainGUIFrame.setFocusable(true);
        mainGUIFrame.addKeyListener(game);
        mainGUIFrame.addFocusListener(new FocusListener() {
            //Potrzebne aby wordlePanel miał zawsze focus, aby mógł rejestrować klawiaturę.
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("in focus");
                //wordlePanel.requestFocus();
            }
            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("out of focus");
            }
        });
    }

    //public static void main(String[] args){new MainScreen();}
}
