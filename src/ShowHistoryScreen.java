
import javax.swing.*;

public class ShowHistoryScreen {
    MainGameProfile gameProfile;
    JPanel historyPanel;
    JLabel username;
    ShowHistoryScreen(MainGameProfile gameProfile) {
        this.gameProfile = gameProfile;
        JFrame mainGUIFrame = new JFrame("History");
        mainGUIFrame.setContentPane(historyPanel);
        gameProfile.loadProfile();
        username.setText(gameProfile.getUsername());
        mainGUIFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainGUIFrame.setBounds(150,20,100,500);
        mainGUIFrame.show();
    }
}
