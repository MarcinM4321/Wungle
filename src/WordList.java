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
        final File dictionary;
        dictionary = new File("src/slowa.txt");
        try {
            Scanner readDictionary = new Scanner(dictionary);
            for(int i = 0; i < 3186044; i++) {
                listOfPossibleWords.add((readDictionary.nextLine()));
            }
            readDictionary.close();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        //TODO: wywalenie ze słownika słów, które nie mają 5 liter
        //TODO: poprawienie 3186044 na długość pliku
        //TODO: ewentualnie przerobić, że pobieramy plik z internetu
    }

    public Boolean isWordAllowed(String guess) {
        //TODO: Zaimplementować funkcjonalność
        //TODO: dokonczyc projekt
        return true;
    }
}
