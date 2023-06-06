import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SelectScreen {
    private final MainGameProfile profile;
    public SelectScreen(MainGameProfile profile) {//klasa wywołująca okno
        this.profile = profile;//przypisanie argumentu konstruktora do atrybutu profilu
        ArrayList<String> allUsersNames = profile.getAllUsernames();//zaczytanie nazwy wszystkich profili
        SelectScreenFrame selectScreenFrame = new SelectScreenFrame(profile);
        selectScreenFrame.render();
    }
}
class SelectScreenPanel extends JPanel implements ActionListener, KeyListener {//klasa pomocnicza, tworząca panel
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Napisanie tekstu na ekranie
        g.setFont(new Font("Arial", Font.BOLD, 110));//ustawienie czcionki
        g.drawString("Wordle",250,170);//napisanie tekstu "WORDLE"
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public SelectScreenPanel() {
    }
}

class SelectScreenFrame extends JFrame {//klasa pomocnicza, tworząca okno
    void render() {//funkcja, żeby jej wywoływanie profesjonalnie wyglądało :)
        pack();
        selectPanel.repaint();
        setVisible(true);
    }
    //widzialność
    void setVisible1page(boolean WhatSet) {
        buttonGame.setVisible(WhatSet);
        buttonStats.setVisible(WhatSet);
        buttonProfiles.setVisible(WhatSet);
    }
    void setVisible2page(boolean WhatSet) {
        buttonConfirm.setVisible(WhatSet);
        profileChooser.setVisible(WhatSet);
        newUsername.setVisible(WhatSet);
    }
    //użyteczność
    void setEnabled1page(boolean WhatSet) {
        buttonGame.setEnabled(WhatSet);
        buttonStats.setEnabled(WhatSet);
        buttonProfiles.setEnabled(WhatSet);
    }
    void setEnabled2page(boolean WhatSet) {
        buttonConfirm.setEnabled(WhatSet);
        profileChooser.setEnabled(WhatSet);
        newUsername.setEnabled(WhatSet);
    }
    private static final int DEFAULT_WIDTH = 900;//domyślna szerokość okna
    private static final int DEFAULT_HEIGHT = 750;//domyślna wysokość okna
    private static final int DEFAULT_X_POSITION = 150;//domyślne położenie okna - x-owa współrzędna (na szerokość)
    private static final int DEFAULT_Y_POSITION = 150;//domyślne położenie okna - y-owa współrzędna (na wysokość)
    private JTextField newUsername;//użytkownik może zechcieć wpisać nazwę nowego użytkownika
    private JComboBox profileChooser;//użytkownik zamiast wpisywać nazwę nowego użytkownika może zechcieć wybrać juz istniejącego
    private SelectScreenPanel selectPanel;//deklaracja panelu jako atrybut
    private Buttons buttonGame, buttonStats, buttonProfiles, buttonConfirm;//deklaracja guzików jako atrybuty
    private ButtonsGrid buttonGameGBC, buttonStatsGBC, buttonProfilesGBC, buttonConfirmGBC, profileChooserGBC, newUserNameGBC;//deklaracja pozycjonowania guzików jako atrybuty
    private MainGameProfile profile;
    private ArrayList<String> allUsersNames;
    public SelectScreenFrame(MainGameProfile profile) {
        //ściągnięcie danych o profilach
        this.profile = profile;
        this.allUsersNames = profile.getAllUsernames();
        {
            JComboBox profileChooserHelp = new JComboBox();
            for (int i = 0; i < allUsersNames.size(); i++) {
                profileChooserHelp.addItem(allUsersNames.get(i));
            }
            this.profileChooser = profileChooserHelp;
        }
        this.newUsername = new JTextField();

        for(int i = 0; i < allUsersNames.size(); i++) {
            this.profileChooser.addItem(allUsersNames.get(i));
        }
        this.newUsername = new JTextField();

        //parametry początkowe okna
        setTitle("Wordle");//ustawienie tytułu okna
        setPreferredSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));//ustawienie domyślnej szerokości i wysokości nowo otwieranego okna
        setLocation(DEFAULT_X_POSITION,DEFAULT_Y_POSITION);//ustawienie domyślnej pozycji nowo otwieranego okna na ekranie
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ustawienie domyślnej operacji zamykania okna na zamknięcie programu - potem to ustawienie zostanie zmienione
        setResizable(false);//zablokowanie możliwości zmiany rozmiaru okna
        pack();//bez tego okno jest zminimalizowane i nic nie widać
        setVisible(true);//okno domyślnie jest widoczne

        //deklaracja guzików
        this.buttonGame = new Buttons("Rozpocznij nową grę!",true,false,false);//guzik "Rozpocznij nową grę"
        this.buttonStats = new Buttons("Pokaż statystyki profilu",true,false,false);//guzik "Pokaż statystyki"
        this.buttonProfiles = new Buttons("Wybierz profil",true,true,false);//guzik "Wybierz profil"
        this.buttonConfirm = new Buttons("Zatwierdź wybór",false,false,false);

        //przypisanie ActionListenerów do guzików
        buttonGame.addActionListener(new ButtonGameListener(profile));
        buttonStats.addActionListener(new ButtonStatsListener(profile));
        buttonProfiles.addActionListener(new ButtonProfilesListener());
        buttonConfirm.addActionListener(new ButtonConfirmListener());

        this.selectPanel = new SelectScreenPanel(); //deklaracja panelu głównego
        selectPanel.repaint();//wyrysowanie napisu "WORDLE"
        selectPanel.setLayout(new GridBagLayout());//ustawienie nowego Layoutu - GridBagLayout
        setContentPane(selectPanel);


        //deklaracja GBC dla guzików (GridBagConstrains) - umożliwia to pozycjonowanie guzika w gridzie
        this.buttonGameGBC = new ButtonsGrid(0,0);
        this.buttonStatsGBC = new ButtonsGrid(0,1);
        this.buttonProfilesGBC = new ButtonsGrid(0,2);
        this.buttonConfirmGBC = new ButtonsGrid(0,0);
        this.profileChooserGBC = new ButtonsGrid(0,3);
        this.newUserNameGBC = new ButtonsGrid(0,5);

        //dodanie komponentów do panelu
        selectPanel.add(buttonGame,buttonGameGBC);
        selectPanel.add(buttonStats,buttonStatsGBC);
        selectPanel.add(buttonProfiles,buttonProfilesGBC);
        selectPanel.add(buttonConfirm,buttonConfirmGBC);
        selectPanel.add(profileChooser,profileChooserGBC);
        selectPanel.add(newUsername,newUserNameGBC);

        //deaktywowanie komponentów z 2 strony
        setVisible2page(false);
        setEnabled2page(false);
        //aktywowanie komponentów z 1 strony
    }




    //sekcja listenerów
    private class ButtonGameListener implements ActionListener {
        private MainGameProfile WhatProfile;
        public ButtonGameListener(MainGameProfile profile) {
            this.WhatProfile = profile;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }
    private class ButtonStatsListener implements ActionListener {
        private MainGameProfile WhatProfile;
        public ButtonStatsListener(MainGameProfile profile) {
            this.WhatProfile = profile;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            new ShowHistoryScreen(this.WhatProfile);
        }
    }
    private class ButtonProfilesListener implements ActionListener {
        public ButtonProfilesListener() {

        }
        @Override
        public void actionPerformed(ActionEvent e) {
            //ustawiam komponenty z 1 strony
            setVisible1page(false);
            setEnabled1page(false);
            //ustawiam komponenty z 2 strony
            setVisible2page(true);
            setEnabled2page(true);
        }
    }
    private class ButtonConfirmListener implements ActionListener {
        public ButtonConfirmListener() {

        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Boolean wasSuccessful;
            if(!newUsername.getText().equals("")) {
                System.out.println("text: \""+ newUsername.getText()+"\"");
                wasSuccessful =  profile.createProfile(newUsername.getText());
            }
            else {
                int index = profileChooser.getSelectedIndex();
                wasSuccessful = profile.loadProfile(index);
            }
            //ustawiam komponenty z 2 strony
            setVisible2page(false);
            setEnabled2page(false);
            //ustawiam komponenty z 1 strony
            setVisible1page(true);//widzialność
            setEnabled1page(true);//użyteczność
        }
    }

}

class Buttons extends JButton {//klasa pomocnicza, tworząca guzik
    public Buttons(String buttonName, boolean IfVisible, boolean IfEnabled, boolean IfFocusable) {//konstruktor klasy JButton
        setFont(new Font("Arial", Font.PLAIN, 17));//ustawienie czcionki
        setBackground(Color.lightGray);//domyślnie jest koloru jasno-szarego
        setPreferredSize(new Dimension(200,30));//domyślnie ma rozmiary 200 x 30
        setFocusable(IfFocusable);//domyślnie nie zabiera focusu przy tworzeniu
        setVisible(IfVisible);//domyślnie guzik pojawia sie
        setEnabled(IfEnabled);//domyślnie można go wciskać
        setText(buttonName);//ustawienie napisu na guziku
    }
}

class ButtonsGrid extends GridBagConstraints {//klasa pomocnicza, ustawiająca pozycje guzików
    public ButtonsGrid(int gridx, int gridy) {
        gridx = gridx;
        gridy = gridy;
        new Insets(150,150,150,150);
        gridheight = 50;
    }
}
