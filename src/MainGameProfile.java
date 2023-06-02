import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MainGameProfile {
    // klasa odpowiedzialna za profil obecnego gracza
    // Wszystkie inny klasy mają korzystać z tej klasy aby otrzymywać informacje o profilu gracza

    private ProfileData currentProfile;
    private ProfileDataIO database;
    private ArrayList<Integer> allUsersID;
    private ArrayList<String> allUsernames;

    private ErrorMessenger errorMessenger;

    private static final int MAX_NUMBER_OF_IDS = 10000;
    private static final String FILE_PATH_USER_LIST = "allUsers.txt";

    MainGameProfile(ErrorMessenger errorMessenger) {
        this.currentProfile = null;
        database = new ProfileDataIO(errorMessenger);
        allUsersID =  new ArrayList<>();
        allUsernames =  new ArrayList<>();
        this.errorMessenger = errorMessenger;
        readListOfUsers();
    }

    public Boolean isEmpty() {
        return currentProfile == null;
    }

    public String getUsername( ) {
        return currentProfile.getUsername();
    }

    public ProfileData getProfileData () {
        return currentProfile;
    }
    public ArrayList<Integer> getUsersID () {
        return allUsersID;
    }

    public ArrayList<String> getAllUsernames () {
        return allUsernames;
    }

    public void addNewGameHistory(SingleGameHistory hist) {

        currentProfile.addNewGameHistory(hist);
        saveProfile();
    }

    public Boolean createProfile(String username) {
        int newID;
        Random rand = new Random();
        if (allUsernames.contains(username)) {
            errorMessenger.showError("Użytkownik o takiej nazwie już istnieje");
            return false;
        }

        newID = rand.nextInt(MAX_NUMBER_OF_IDS); //generuje nowe ID
        while(allUsersID.contains(newID)) {
            newID = rand.nextInt(MAX_NUMBER_OF_IDS);
        }

        currentProfile = new ProfileData(username, newID);
        allUsersID.add(newID);
        allUsernames.add(username);
        saveProfile();

        saveListOfUsers();
        return true;
    }

    public Boolean loadProfile(int index) {
        try {
            currentProfile = database.loadProfile(allUsersID.get(index));
        }
        catch (IOException ex){
            return false;
        }
        return true;
    }

    public Boolean saveProfile() {
        try {
            database.saveProfile(currentProfile, currentProfile.getID());
        }
        catch (IOException ex) {
            return false;
        }
        return true;
    }
    private void saveListOfUsers() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH_USER_LIST));
            for (int i=0; i< allUsersID.size(); i++) {
                bw.write(allUsersID.get(i) + " " + allUsernames.get(i) + "\n");
            }
            bw.close();
        } catch (IOException ex) {
            errorMessenger.showError("błąd przy zapisywaniu pliku "+FILE_PATH_USER_LIST +" z listą probili");
        }
    }


    private void readListOfUsers() {
        File listUsers = new File(FILE_PATH_USER_LIST);
        if(!listUsers.exists()) {
            try{
                listUsers.createNewFile();
            }
            catch( IOException ex) {
                errorMessenger.showError("Nie można utworzyć pliku" + FILE_PATH_USER_LIST);
            }
        }
        try(FileReader listFile = new FileReader(FILE_PATH_USER_LIST);
                Scanner input = new Scanner(listFile)) {
            while (input.hasNextLine()) {
                allUsersID.add(input.nextInt());
                allUsernames.add(input.nextLine().substring(1)); //substring(1) usuwa znak rozdzielający
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println();
        }
        catch (IOException ex) {
            errorMessenger.showError("błąd przy czytaniu pliku " + FILE_PATH_USER_LIST);
        }
        catch (InputMismatchException ex) {
            errorMessenger.showError("plik "+FILE_PATH_USER_LIST+" z listą wszystkich profili jest niepoprawny");
        }
    }
}