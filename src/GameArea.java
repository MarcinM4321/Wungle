import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameArea implements KeyListener {
    JPanel mainPanel;
    MainGameProfile profile;
    WordList allowedWords;
    static final int WORD_LENGTH = 5;
    static final int NUMBER_OF_GUESSES = 6;

    private int position = 0;
    JLabel[][] letterGrid;
    GameArea(JPanel mainPanel, MainGameProfile profile) {

        this.mainPanel = mainPanel;
        this.profile = profile;
        allowedWords = new WordList();
        allowedWords.loadWords();
        String choosenWord = allowedWords.chooseWord();
        GridLayout wordleLayout = new GridLayout(NUMBER_OF_GUESSES, WORD_LENGTH);
        mainPanel.setFocusable(true);

        mainPanel.setLayout(wordleLayout);

        letterGrid = new JLabel[NUMBER_OF_GUESSES][WORD_LENGTH];
        for (int y=0; y < WORD_LENGTH; y++) {
            for (int x=0; x<NUMBER_OF_GUESSES; x++) {
                letterGrid[x][y] = new JLabel("("+x+","+y+")");
                letterGrid[x][y].setSize(10,10);
                mainPanel.add(letterGrid[x][y]);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
            if(position != 0){
                letterGrid[(position - 1)%NUMBER_OF_GUESSES][(position - 1)/NUMBER_OF_GUESSES].setText("");
                position = position == WORD_LENGTH * NUMBER_OF_GUESSES - 1 ? 0 : position - 1;
            } else
                letterGrid[position%NUMBER_OF_GUESSES][position/NUMBER_OF_GUESSES].setText("");
        } else {
            letterGrid[position%NUMBER_OF_GUESSES][position/NUMBER_OF_GUESSES].setText(""+e.getKeyChar());
            position = position == WORD_LENGTH * NUMBER_OF_GUESSES - 1 ? 0 : position + 1;
        }

        System.out.println("pressed: "+ e.getKeyChar());
        System.out.println(position);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
