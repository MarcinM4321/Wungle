import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameArea implements KeyListener {
    JPanel mainPanel;
    MainGameProfile profile;
    WordList allowedWords;
    static final int WORD_LENGTH = 5;
    static final int NUMBER_OF_GUESSES = 6;
    private final String choosenWord;
    private ArrayList<String> allGuesses = new ArrayList<>();
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
                letterGrid[y][x] = new JLabel("("+x+","+y+")");
                letterGrid[y][x].setSize(10,10);
                letterGrid[y][x].setOpaque(true);
                mainPanel.add(letterGrid[y][x]);
            }
        }
    }
//TODO napisać kod który będzie kończył grę po zgadnięciu słowa, lub gdy wartość howManyGuesses = 6
    @Override
    public void keyTyped(KeyEvent e) {
        String allowedLetters = "qwertyuiopasdfghjklzxcvbnmęóąśłżźćń";
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
                    allGuesses.add(guessWord);
                    if (guessWord.equals(choosenWord)){
                        for(int i = 0; i < WORD_LENGTH; i++)
                            letterGrid[i][howManyGuesses].setBackground(Color.GREEN);
                        System.out.println("Gratulacje, odgadłeś słowo");
                        System.out.println(allGuesses);
                        //profile.addNewGameHistory(choosenWord, howManyGuesses, allGuesses);
                    }
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
                if(howManyGuesses == 6){
                    System.out.println("Przegrałeś, szukane słowo brzmiało: " + choosenWord);
                    System.out.println(allGuesses);
                    //profile.addNewGameHistory(choosenWord, howManyGuesses, allGuesses);
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
        } else if (allowedLetters.contains("" + e.getKeyChar())) {
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
