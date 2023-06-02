
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ShowHistoryScreen {
    ProfileData profile;
    JPanel historyPanel;
    JLabel username;
    private JTree tree1;
    private JLabel statisticsLabel;

    private static final int MAX_NUMBER_OF_GUESSES = 6;

    private int numberOfGames;
    private int numberOfFinishedGames;
    private int numberOfWins;
    private int numberOfLosses;
    private int[] winsPerGameLength;


    ShowHistoryScreen(MainGameProfile gameProfile) {
        this.profile = gameProfile.getProfileData();
        winsPerGameLength = new int[MAX_NUMBER_OF_GUESSES];
        for (int i = 0; i < MAX_NUMBER_OF_GUESSES; i++) {
            winsPerGameLength[i] = 0;
        }
        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("wszystkie gry gracza " + gameProfile.getUsername());
        populateNode(topNode);
        tree1.setModel(new DefaultTreeModel(topNode) );
        JFrame mainGUIFrame = new JFrame("History");
        mainGUIFrame.setContentPane(historyPanel);
        username.setText(gameProfile.getUsername());
        calculateStatistics();
        writeStatistics();
        mainGUIFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainGUIFrame.setBounds(150,20,400,500);
        mainGUIFrame.show();
    }

    private void writeStatistics() {
        String text = "<html>";
        text += "<p>liczba wszystkich gier: " + numberOfGames + "</p>";
        text += "<p>liczba skończonych gier: " + numberOfFinishedGames + "</p>";
        text += "<p>liczba wygranych gier: " + numberOfWins +  "</p>";
        text += "";
        for (int i = 0; i < MAX_NUMBER_OF_GUESSES; i++) {
            text += "<p>&emsp w " + (i+1) + " odpowiedziach: " + winsPerGameLength[i] + "</p>";
        }
        text += "";
        text += "<p>liczba gier przegranych: " + numberOfLosses + "</p>";
        text += "</html>";
        statisticsLabel.setText(text);
    }

    private void calculateStatistics() {

        for (int i = 0; i <profile.getNumberOfGames(); i++) {
            SingleGameHistory game = profile.getSingleHistory(i);
            System.out.println(game.getTarget()+" "+game.getAllGuesses()[game.getNumberOfGuesses()-1]);
            System.out.println(game.getTarget().equals(game.getAllGuesses()[game.getNumberOfGuesses()-1]));
            if (game.isFinnished()) {
                numberOfFinishedGames++;
                if (game.isWinning()) {
                    numberOfWins++;
                    winsPerGameLength[game.getNumberOfGuesses() - 1]++;
                }
            }
        }
        numberOfGames = profile.getNumberOfGames();
        numberOfLosses = numberOfFinishedGames - numberOfWins;
    }


    private void populateNode(DefaultMutableTreeNode topNode) {
        for (int i=0; i < profile.getNumberOfGames(); i++) {
            SingleGameHistory game = profile.getSingleHistory(i);
            DefaultMutableTreeNode nodeForGame = new DefaultMutableTreeNode(game.getTarget() + " - cel");
            topNode.add(nodeForGame);
            String[] gameGuesses = game.getAllGuesses();
            for (int j=0; j < game.getNumberOfGuesses(); j++) {
                nodeForGame.add(new DefaultMutableTreeNode(gameGuesses[j]));
            }
        }
    }
}
