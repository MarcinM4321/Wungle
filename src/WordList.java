import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class WordList {
    ArrayList<String> listOfPossibleWords;
    WordList() {
        listOfPossibleWords = new ArrayList<String>();
    }

    public void loadWords() {
        String word;
        final File dictionary;
        dictionary = new File("slowa.txt");
        try {
            Scanner readDictionary = new Scanner(dictionary);
            for(int i = 0; i < 3186044; i++) {
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
        //TODO: Zaimplementować funkcjonalność
        //TODO: dokonczyc projekt
        return true;
    }
}
