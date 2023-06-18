import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainScreen extends JFrame implements DayNightSwitchable{

    private final MainGameProfile gameProfile;
    private final GameArea game;

    private final JButton showHistoryButton;
    private final JButton returnToMenu;
    private final JButton resetGameButton;
    private final JPanel wordlePanel;
    private final ColorModeButton switchColorMode;
    private final JPanel buttonPanel;
    private final JPanel bottomPanel;

    private boolean isNightMode;
    public MainScreen(MainGameProfile profile, SelectScreenFrame parentScreen, boolean isNightMode) {
        setTitle("Wordle");

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
                requestFocus(); //potrzebne, aby okno rejestrowało klawiaturę
            }
        });

        resetGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.resetGame();
                requestFocus(); //potrzebne, aby okno rejestrowało klawiaturę
            }
        });
        //setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(180,180,400,600);
        show();
        setFocusable(true);
        addKeyListener(game);
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
}
