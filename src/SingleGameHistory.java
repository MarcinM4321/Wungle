import java.util.ArrayList;

public class SingleGameHistory {
    private String target;
    private ArrayList<String> allGuesses;

    private static final int MAX_NUMBER_OF_GUESSES = 6;

    SingleGameHistory(String target, ArrayList<String> allGuesses) {
        this.target = target;
        this.allGuesses = new ArrayList<String>();
        for (int i=0; i < allGuesses.size(); i++) {
            this.allGuesses.add(allGuesses.get(i));
        }
    }

    Boolean isWinning() {
        return allGuesses.get(allGuesses.size()-1).equals(target);
    }

    Boolean isFinnished() {
        return (allGuesses.size() == 6) || isWinning();
    }
    String getTarget() {
        return target;
    }

    int getNumberOfGuesses() {
        return allGuesses.size();
    }

    ArrayList<String> getAllGuesses() {
        return allGuesses;
    }
}
