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

        letterGrid = new JLabel[WORD_LENGTH][NUMBER_OF_GUESSES];
        for (int x=0; x<NUMBER_OF_GUESSES; x++) {
            for (int y=0; y < WORD_LENGTH; y++) {
                letterGrid[y][x] = new JLabel("", SwingConstants.CENTER);
                letterGrid[y][x].setSize(50,50);
                letterGrid[y][x].setFont(new Font("arial", Font.BOLD, 50));
                letterGrid[y][x].setOpaque(true);
                letterGrid[y][x].setBorder(BorderFactory.createLineBorder(Color.black,3));
                mainPanel.add(letterGrid[y][x], BorderLayout.CENTER);
            }
        }
    }
//TODO napisać kod który będzie kończył grę po zgadnięciu słowa, lub gdy wartość howManyGuesses = 6
    //TODO uniemożliwienie wpisywania innych znaków w grid niż liter
    @Override
    public void keyTyped(KeyEvent e) {
        if(lettersWritten == 5){
            char[] wordArray = new char[5];
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                for(int i = 0; i < WORD_LENGTH; i++){
                    char letter = letterGrid[i][howManyGuesses].getText().charAt(0);
                    wordArray[i] = letter;
                }

                String guessWord = String.valueOf(wordArray);
                System.out.println(guessWord);

                if(allowedWords.isWordAllowed(guessWord)){
                    if (guessWord.equals(choosenWord))
                        for(int i = 0; i < WORD_LENGTH; i++)
                            letterGrid[i][howManyGuesses].setBackground(Color.GREEN);
                    else {
                        for(int i = 0; i < WORD_LENGTH; i++){
                            if(choosenWord.charAt(i) == wordArray[i])
                                letterGrid[i][howManyGuesses].setBackground(Color.GREEN);
                            else if (choosenWord.contains("" + wordArray[i]))
                                letterGrid[i][howManyGuesses].setBackground(Color.ORANGE);
                            else letterGrid[i][howManyGuesses].setBackground(Color.RED);
                        }
                        howManyGuesses = howManyGuesses + 1;
                        lettersWritten = 0;
                    }
                } else {
                        for(int i = 0; i < WORD_LENGTH; i++){
                            letterGrid[i][howManyGuesses].setText("");
                        }
                        lettersWritten = 0;
                        position = position - 5;
                    }
            }
            else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                letterGrid[(position - 1)%WORD_LENGTH][(position - 1)/WORD_LENGTH].setText("");
                position = position - 1;
                lettersWritten = lettersWritten - 1;
            }
        } else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                if(lettersWritten != 0){
                    letterGrid[(position - 1)%WORD_LENGTH][(position - 1)/WORD_LENGTH].setText("");
                    position = position - 1;
                    lettersWritten = lettersWritten - 1;
                } else
                    letterGrid[position%WORD_LENGTH][position/WORD_LENGTH].setText("");
        } else {
            letterGrid[position%WORD_LENGTH][position/WORD_LENGTH].setText(""+e.getKeyChar());
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
