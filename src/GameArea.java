import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameArea implements KeyListener, DayNightSwitchable {
    JPanel mainPanel;
    MainGameProfile profile;
    WordList allowedWords;
    static final int WORD_LENGTH = 5;
    static final int NUMBER_OF_GUESSES = 6;
    private final String choosenWord;
    private int lettersWritten = 0;
    private int position = 0;
    private int howManyGuesses = 0; //będę tym sprawdzał ile prób wykonał gracz
    WordleLetter[][] letterGrid;
    GameArea(JPanel mainPanel, MainGameProfile profile, boolean isNightMode) {

        this.mainPanel = mainPanel;
        this.profile = profile;
        allowedWords = new WordList();
        allowedWords.loadWords();
        choosenWord = allowedWords.chooseWord();
        GridLayout wordleLayout = new GridLayout(NUMBER_OF_GUESSES, WORD_LENGTH);
        mainPanel.setFocusable(true);

        mainPanel.setLayout(wordleLayout);

        letterGrid = new WordleLetter[WORD_LENGTH][NUMBER_OF_GUESSES];
        for (int x=0; x<NUMBER_OF_GUESSES; x++) {
            for (int y=0; y < WORD_LENGTH; y++) {
                letterGrid[y][x] = new WordleLetter(isNightMode);
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
                                letterGrid[i][howManyGuesses].setState(WordleLetter.CORRECT);
                            else if (choosenWord.contains("" + wordArray[i]))
                                letterGrid[i][howManyGuesses].setState(WordleLetter.PARTIAL);
                            else letterGrid[i][howManyGuesses].setState(WordleLetter.INCORRECT);
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

    @Override
    public void setToDayMode() {
        for (int x=0; x<NUMBER_OF_GUESSES; x++) {
            for (int y = 0; y < WORD_LENGTH; y++) {
                letterGrid[y][x].setToDayMode();
            }
        }
    }

    @Override
    public void setToNightMode() {
        for (int x=0; x<NUMBER_OF_GUESSES; x++) {
            for (int y = 0; y < WORD_LENGTH; y++) {
                letterGrid[y][x].setToNightMode();
            }
        }
    }


    private class WordleLetter extends JLabel implements DayNightSwitchable {
        private int state;
        private boolean isNightMode;
        static final int UNDECIDED = 0;
        static final int CORRECT = 1;
        static final int PARTIAL = 2;
        static final int INCORRECT = 3;

        WordleLetter(boolean isNightMode) {
            state = UNDECIDED;
            this.isNightMode = isNightMode;
            setHorizontalAlignment(SwingConstants.CENTER);
            setSize(50,50);
            setFont(new Font("arial", Font.BOLD, 50));
            setOpaque(true);
            setBorder(BorderFactory.createLineBorder(Color.black,3));
        }

        public void setState(int state) {
            this.state = state;
            if (isNightMode)
                setToNightMode();
            else
                setToDayMode();
        }

        @Override
        public void setToDayMode() {
            isNightMode = false;
            setForeground(Color.black);
            setBorder(BorderFactory.createLineBorder(Color.black,3));
            switch (state) {
                case UNDECIDED:
                    setBackground(Color.WHITE);
                    break;
                case CORRECT:
                    setBackground(Color.GREEN);
                    break;
                case PARTIAL:
                    setBackground(Color.ORANGE);
                    break;
                case INCORRECT:
                    setBackground(Color.RED);
                    break;
            }
        }

        @Override
        public void setToNightMode() {
            isNightMode = true;
            setForeground(Color.white);
            setBorder(BorderFactory.createLineBorder(Color.white,3));
            switch (state) {
                case UNDECIDED:
                    setBackground(Color.BLACK);
                    break;
                case CORRECT:
                    setBackground(new Color(22, 98, 0, 255));
                    break;
                case PARTIAL:
                    setBackground(new Color(122, 64, 0));
                    break;
                case INCORRECT:
                    setBackground(new Color(101, 0, 0));
                    break;
            }
        }
    }
}
