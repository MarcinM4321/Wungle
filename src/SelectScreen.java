import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SelectScreen {

    private JTextField newUsername;
    private JPanel panel1;
    private JComboBox profileChooser;
    private JButton confirmButton;
    private JLabel label1;

    private MainGameProfile profile;
    SelectScreen(MainGameProfile profile) {
        this.profile = profile;

        JFrame mainFrame = new JFrame("wybór profilu gracza");
        mainFrame.setContentPane(panel1);
        mainFrame.setSize(400,200);

        ArrayList<String> allUsersNames = profile.getAllUsernames();
        for (int i=0; i < allUsersNames.size(); i++) {
            profileChooser.addItem(allUsersNames.get(i));
        }
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean wasSuccessful = true;
                if(!newUsername.getText().equals("")) {
                    System.out.println("text: \""+ newUsername.getText()+"\"");
                    wasSuccessful =  profile.createProfile(newUsername.getText());
                }
                else {
                    int index = profileChooser.getSelectedIndex();
                    wasSuccessful = profile.loadProfile(index);
                }
                mainFrame.setVisible(!wasSuccessful);
            }
        });

        mainFrame.setVisible(true);
    }
}
