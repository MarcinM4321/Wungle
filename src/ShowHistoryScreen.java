
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ShowHistoryScreen {
    MainGameProfile gameProfile;
    JPanel historyPanel;
    JLabel username;
    private JTree tree1;


    ShowHistoryScreen(MainGameProfile gameProfile) {
        this.gameProfile = gameProfile;
        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("wszystkie gry gracza " + gameProfile.getUsername());
        populateNode(topNode);
        tree1.setModel(new DefaultTreeModel(topNode) );
        JFrame mainGUIFrame = new JFrame("History");
        mainGUIFrame.setContentPane(historyPanel);
        username.setText(gameProfile.getUsername());
        mainGUIFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainGUIFrame.setBounds(150,20,200,500);
        mainGUIFrame.show();
    }

    private void populateNode(DefaultMutableTreeNode topNode) {
        ProfileData profile = gameProfile.getProfileData();
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
