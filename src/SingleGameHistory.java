public class SingleGameHistory {
    private String target;
    private int numberOfGuesses;
    private String[] allGuesses;

    private static final int MAX_NUMBER_OF_GUESSES = 6;

    SingleGameHistory(String target, int numberOfGuesses, String[] allGuesses) {
        this.target = target;
        this.numberOfGuesses = numberOfGuesses;
        this.allGuesses = new String[numberOfGuesses];
        for (int i=0; i<numberOfGuesses; i++) {
            this.allGuesses[i] = allGuesses[i];
        }
    }

    Boolean isWinning() {
        return allGuesses[numberOfGuesses - 1].equals(target);
    }

    Boolean isFinnished() {
        return (numberOfGuesses == 6) || isWinning();
    }
    String getTarget() {
        return target;
    }

    int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    String[] getAllGuesses() {
        return allGuesses;
    }
}
