import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainScreen extends JFrame {

    final private MainGameProfile gameProfile;
    private GameArea game;

    private JPanel mainPanel;
    private JButton showHistoryButton;
    private JPanel wordlePanel;

    public MainScreen(MainGameProfile profile) {
        setTitle("Wordle");

        JPanel buttonPanel = new JPanel();

        showHistoryButton =  new JButton("pokaż historie");
        buttonPanel.add(showHistoryButton);
        add(buttonPanel, BorderLayout.PAGE_START);

        JPanel bottomPanel = new JPanel();
        wordlePanel = new JPanel();
        wordlePanel.setPreferredSize(new Dimension(350, 400));
        bottomPanel.add(wordlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);



        gameProfile = profile;
        game = new GameArea(wordlePanel, gameProfile);

        //wordlePanel.setSize(300, 400);
        showHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //MainGameProfile user
                ShowHistoryScreen screen = new ShowHistoryScreen(gameProfile);
                requestFocus(); //potrzebne, aby okno rejestrowało klawiaturę
            }
        });
        //setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(180,180,400,600);
        show();
        setFocusable(true);
        addKeyListener(game);
        addFocusListener(new FocusListener() {
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

    public static void main(String[] args){new MainScreen(new MainGameProfile(new ErrorMessenger(new JFrame("test"))));}
}
