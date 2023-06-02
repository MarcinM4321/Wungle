public class SingleGameHistory {
    private String target;
    private int numberOfGuesses;
    private String[] allGuesses;

    SingleGameHistory(String target, int numberOfGuesses, String[] allGuesses) {
        this.target = target;
        this.numberOfGuesses = numberOfGuesses;
        this.allGuesses = new String[numberOfGuesses];
        for (int i=0; i<numberOfGuesses; i++) {
            this.allGuesses[i] = allGuesses[i];
        }
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
