import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainScreen extends JFrame implements DayNightSwitchable{

    final private MainGameProfile gameProfile;
    private GameArea game;

    private JButton showHistoryButton;
    private JButton returnToMenu;
    private JButton resetGameButton;
    private SelectScreenFrame parentScreen;
    private JPanel wordlePanel;
    private ColorModeButton switchColorMode;
    private JPanel buttonPanel;
    private JPanel bottomPanel;

    private boolean isNightMode;
    public MainScreen(MainGameProfile profile, SelectScreenFrame parentScreen, boolean isNightMode) {
        setTitle("Wordle");

        this.parentScreen = parentScreen;
        buttonPanel = new JPanel();

        resetGameButton = new JButton("nowa gra");
        showHistoryButton =  new JButton("pokaż historie");
        returnToMenu = new JButton("powrót");
        this.isNightMode = isNightMode;
        switchColorMode = new ColorModeButton(this, isNightMode);

        buttonPanel.add(resetGameButton);
        buttonPanel.add(showHistoryButton);
        buttonPanel.add(returnToMenu);
        buttonPanel.add(switchColorMode, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.PAGE_START);

        bottomPanel = new JPanel();
        wordlePanel = new JPanel();
        wordlePanel.setPreferredSize(new Dimension(350, 400));
        bottomPanel.add(wordlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        gameProfile = profile;
        game = new GameArea(wordlePanel, gameProfile, isNightMode);

        switchColorMode.updateColorMode();
        returnToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentScreen.returnFromGameScreen(getIsNightMode());
                dispose();
            }
        });
        showHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //MainGameProfile user
                ShowHistoryScreen screen = new ShowHistoryScreen(gameProfile);
                screen.setColorMode(getIsNightMode());
                requestFocus(); //potrzebne, aby okno rejestrowało klawiaturę
            }
        });
        switchColorMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestFocus();
            }
        });

        resetGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.resetGame();
                requestFocus();
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
                //System.out.println("in focus");
                //wordlePanel.requestFocus();
            }
            @Override
            public void focusLost(FocusEvent e) {
                //System.out.println("out of focus");
            }
        });
    }

    public void setToNightMode() {
        isNightMode = true;
        setPanelsToColor(Color.black);
        game.setToNightMode();
    }

    public void setToDayMode() {
        isNightMode = false;
        setPanelsToColor(Color.white);
        game.setToDayMode();
    }

    private void setPanelsToColor(Color color) {
        setBackground(color);
        buttonPanel.setBackground(color);
        bottomPanel.setBackground(color);
        getContentPane().setBackground(color);
    }

    private boolean getIsNightMode() {
        return isNightMode;
    }
    //public static void main(String[] args){new MainScreen(new MainGameProfile(new ErrorMessenger(new JFrame("test"))));}
}
