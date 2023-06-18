import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class WordList {
    private ArrayList<String> listOfPossibleWords;
    WordList() {
        listOfPossibleWords = new ArrayList<String>();
    }

    public void loadWords() {
        String word;
        final File dictionary;
        dictionary = new File("slowa.txt");
        try {
            Scanner readDictionary = new Scanner(dictionary);
            while (readDictionary.hasNextLine()) {
                word = readDictionary.nextLine();
                if(word.length() == 5)
                    listOfPossibleWords.add(word);
            }
            readDictionary.close();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        //TODO: poprawienie 3186044 na długość pliku
    }

    public Boolean isWordAllowed(String guess) {
        return listOfPossibleWords.contains(guess);
    }
    public String chooseWord(){
        Random randomise = new Random();
        int randomNumber = randomise.nextInt(listOfPossibleWords.size());
        return listOfPossibleWords.get(randomNumber);
    }
}
