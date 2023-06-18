
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;

public class ShowHistoryScreen extends JFrame implements DayNightSwitchable{
    ProfileData profile;
    JPanel historyPanel;
    JLabel username;
    private JTree tree1;
    private DefaultTreeCellRenderer treeRender;
    private JLabel statisticsLabel;

    private static final int MAX_NUMBER_OF_GUESSES = 6;

    private int numberOfGames;
    private int numberOfFinishedGames;
    private int numberOfWins;
    private int numberOfLosses;
    private int[] winsPerGameLength;

    ArrayList<JComponent> allColorableComponents;

    ShowHistoryScreen(MainGameProfile gameProfile) {
        this.profile = gameProfile.getProfileData();
        setTitle("Historia");
        setBounds(150,20,400,500);

        allColorableComponents = new ArrayList<JComponent>();


        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("wszystkie gry gracza " + gameProfile.getUsername());
        populateNode(topNode);

        tree1 = new JTree(new DefaultTreeModel(topNode));
        allColorableComponents.add(tree1);

        treeRender =  new DefaultTreeCellRenderer();
        treeRender.setClosedIcon(null);
        treeRender.setLeafIcon(null);
        treeRender.setOpenIcon(null);
        allColorableComponents.add(treeRender);
        tree1.setCellRenderer(treeRender);
        add(tree1, BorderLayout.CENTER);


        JPanel topPanel = new JPanel();
        allColorableComponents.add(topPanel);

        username = new JLabel(gameProfile.getUsername());
        allColorableComponents.add(username);

        topPanel.add(username);
        add(topPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        allColorableComponents.add(leftPanel);

        statisticsLabel = new JLabel();
        allColorableComponents.add(statisticsLabel);

        statisticsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        winsPerGameLength = new int[MAX_NUMBER_OF_GUESSES];
        calculateStatistics();
        writeStatistics();
        leftPanel.add(statisticsLabel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        setBackground(Color.black);
        setForeground(Color.white);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        show();
    }

    private void writeStatistics() {
        String text = "<html>";
        text += "<p>liczba wszystkich gier: <b>" + numberOfGames + "</b></p>";
        text += "<p>liczba skończonych gier: <b>" + numberOfFinishedGames + "</b></p>";
        text += "<p>liczba wygranych gier: <b>" + numberOfWins +  "</b></p>";
        text += "";
        text += "<p>&emsp w " + 1 + " odpowiedzi: <b>" + winsPerGameLength[0] + "</b></p>";
        for (int i = 1; i < MAX_NUMBER_OF_GUESSES; i++) {
            text += "<p>&emsp w " + (i+1) + " odpowiedziach: <b>" + winsPerGameLength[i] + "</b></p>";
        }
        text += "";
        text += "<p>liczba gier przegranych: <b>" + numberOfLosses + "</b></p>";
        text += "</html>";
        statisticsLabel.setText(text);
    }

    private void calculateStatistics() {

        for (int i = 0; i <profile.getNumberOfGames(); i++) {
            SingleGameHistory game = profile.getSingleHistory(i);
            String last_word = game.getAllGuesses().get(game.getNumberOfGuesses()-1);
            //System.out.println(game.getTarget()+" "+last_word);
            //System.out.println(game.getTarget().equals(last_word));
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
            ArrayList<String> gameGuesses = game.getAllGuesses();
            for (int j=0; j < game.getNumberOfGuesses(); j++) {
                nodeForGame.add(new DefaultMutableTreeNode(gameGuesses.get(j)));
            }
        }
    }

    @Override
    public void setToDayMode() {
        treeRender.setBackgroundNonSelectionColor(Color.white);
        treeRender.setBackgroundSelectionColor(new Color(162, 170, 210));
        treeRender.setTextNonSelectionColor(Color.black);
        treeRender.setTextSelectionColor(Color.black);

        for (int i = 0; i < allColorableComponents.size(); i++) {
            allColorableComponents.get(i).setBackground(Color.WHITE);
            allColorableComponents.get(i).setForeground(Color.black);
        }
    }

    @Override
    public void setToNightMode() {
        treeRender.setBackgroundNonSelectionColor(Color.black);
        treeRender.setBackgroundSelectionColor(new Color(0, 7, 79));
        treeRender.setTextNonSelectionColor(Color.white);
        treeRender.setTextSelectionColor(Color.white);
        for (int i = 0; i < allColorableComponents.size(); i++) {
            allColorableComponents.get(i).setBackground(Color.BLACK);
            allColorableComponents.get(i).setForeground(Color.white);
        }
    }

    public void setColorMode(boolean isNightMode) {
        if (isNightMode)
            setToNightMode();
        else
            setToDayMode();
    }
}
