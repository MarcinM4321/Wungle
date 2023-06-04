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
    private final String choosenWord;
    private char[] wordArray;
    private int lettersWritten = 0;
    private int position = 0;
    private int howManyGuesses = 0; //będę tym sprawdzał ile prób wykonał gracz
    JLabel[][] letterGrid;
    GameArea(JPanel mainPanel, MainGameProfile profile) {

        this.mainPanel = mainPanel;
        this.profile = profile;
        allowedWords = new WordList();
        allowedWords.loadWords();
        choosenWord = allowedWords.chooseWord();
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
//TODO napisać kod który będzie kończył grę po zgadnięciu słowa, lub gdy wartość howManyEnters = 6
    //TODO zmieniające się kolory kratek
    //TODO uniemożliwienie wpisywania innych znaków w grid niż liter
    @Override
    public void keyTyped(KeyEvent e) {
        if(lettersWritten == 5){
            wordArray = new char[5];
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                int k = 0;
                for(int i = position - WORD_LENGTH; i < position; i++){
                    char letter = letterGrid[i % NUMBER_OF_GUESSES][i / NUMBER_OF_GUESSES].getText().charAt(0);
                    wordArray[k] = letter;
                    k++;
                }
                String guessWord = String.valueOf(wordArray);
                System.out.println(guessWord);

                if(allowedWords.isWordAllowed(guessWord)){
                    if (guessWord.equals(choosenWord))
                        System.out.println("Wygrałeś");
                    else {
                        howManyGuesses = howManyGuesses + 1;
                        lettersWritten = 0;
                    }
                } else {
                        for(int i = position - WORD_LENGTH; i < position; i++){
                            letterGrid[i%NUMBER_OF_GUESSES][i/NUMBER_OF_GUESSES].setText("");
                        }
                        lettersWritten = 0;
                        position = position - 5;
                    }
            }
            else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                letterGrid[(position - 1)%NUMBER_OF_GUESSES][(position - 1)/NUMBER_OF_GUESSES].setText("");
                position = position - 1;
                lettersWritten = lettersWritten - 1;
            }
        } else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                if(lettersWritten != 0){
                    letterGrid[(position - 1)%NUMBER_OF_GUESSES][(position - 1)/NUMBER_OF_GUESSES].setText("");
                    position = position - 1;
                    lettersWritten = lettersWritten - 1;
                } else
                    letterGrid[position%NUMBER_OF_GUESSES][position/NUMBER_OF_GUESSES].setText("");
        } else {
            letterGrid[position%NUMBER_OF_GUESSES][position/NUMBER_OF_GUESSES].setText(""+e.getKeyChar());
            position = position + 1;
            lettersWritten = lettersWritten + 1;
        }

        System.out.println("pressed: "+ e.getKeyChar());
        System.out.println(position);
        System.out.println("lettersWritten = " + lettersWritten);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
