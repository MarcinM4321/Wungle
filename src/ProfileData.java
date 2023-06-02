import java.util.ArrayList;

public class ProfileData {
    private String username;
    private int ID;
    private ArrayList<SingleGameHistory> allRecords;

    ProfileData(String username, int ID) {
        this.ID = ID;
        this.username = username;
        allRecords = new ArrayList<SingleGameHistory>();
    }

    int getID() {
        return ID;
    }
    String getUsername() {
        return username;
    }
    int getNumberOfGames() {
        return allRecords.size();
    }

    void addNewGameHistory(SingleGameHistory hist) {
        allRecords.add(hist);
    }

    SingleGameHistory getSingleHistory(int position) {
        return allRecords.get(position);
    }
}
