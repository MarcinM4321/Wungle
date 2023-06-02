import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProfileDataIO {
    private ErrorMessenger errorMessenger;
    private static final String USER_DATA_FILE_SUFFIX = "userdata.txt";
    ProfileData loadProfile(int userId) throws IOException {
        ProfileData result = null;
        try {
            FileReader fr = new FileReader(userId + USER_DATA_FILE_SUFFIX);
            Scanner input = new Scanner(fr);

            String username = input.nextLine();

            result =  new ProfileData(username, userId);

            while(input.hasNextLine()) { // czyta dane dla każdej gry

                int cur_num_of_guesses = input.nextInt();
                String guess = input.nextLine().substring(1); // nie uwzględniam spacji pomiędzy.

                String[] allGuesses = new String[cur_num_of_guesses];
                for (int i = 0; i < cur_num_of_guesses; i++) {
                    allGuesses[i] = input.nextLine(); // usuwam rodzielający
                }

                result.addNewGameHistory(new SingleGameHistory(guess, cur_num_of_guesses, allGuesses));
            }
            input.close();
            fr.close();
        }
        catch (InputMismatchException ex) {
            errorMessenger.showError("plik "+ userId + USER_DATA_FILE_SUFFIX + " z profilem gracza jest niepoprawny");
            throw ex;
        }
        catch (IOException ex) {
            errorMessenger.showError("Napotkano bład podczas czytania danych z pliku " +userId + USER_DATA_FILE_SUFFIX);
            throw ex;
        }
        return result;
    }

    void saveProfile(ProfileData profile, int profileID) throws IOException {
        try {
            FileWriter fw = new FileWriter(profileID + USER_DATA_FILE_SUFFIX);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(profile.getUsername()+ "\n");

            for (int cur_game_idx = 0; cur_game_idx < profile.getNumberOfGames(); cur_game_idx++) {
                SingleGameHistory game = profile.getSingleHistory(cur_game_idx);

                bw.write(game.getNumberOfGuesses()+" "+game.getTarget()+"\n");

                String[] guess = game.getAllGuesses();
                for (int i = 0; i < game.getNumberOfGuesses(); i++) {
                    bw.write(guess[i] + "\n");
                }
            }
            bw.close();
            fw.close();

        } catch ( IOException ex) {
            errorMessenger.showError("bład podczas zapisywania danych z profilu");
            throw ex;
        }

    }
    ProfileDataIO (ErrorMessenger errorMessenger) {
        this.errorMessenger = errorMessenger;
    }
}
