import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SelectScreen {
    public SelectScreen() {//klasa wywołująca okno
        SelectScreenFrame selectScreenFrame = new SelectScreenFrame();
        selectScreenFrame.render();
    }

    public static void main(String[] args) {
        new SelectScreen();
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
    private JLabel infoAboutChoise,infoAboutProfile;//pole tekstowe, które będziemy wyświetlać w momencie, gdy wybieramy profile oraz w glownym menu - powie nam, jaki profil wybraliśmy
    private SelectScreenPanel selectPanel;//deklaracja panelu jako atrybut
    private Buttons buttonGame, buttonStats, buttonProfiles, buttonConfirm;//deklaracja guzików jako atrybuty
    private GridBagConstraints buttonGameGBC, buttonStatsGBC, buttonProfilesGBC, buttonConfirmGBC, profileChooserGBC, newUserNameGBC, infoAboutChoiseGBC, infoAboutProfileGBC;//deklaracja pozycjonowania guzików jako atrybuty
    private MainGameProfile profile;
    private ArrayList<String> allUsersNames;

    private MainScreen FrameWithGame;
    public SelectScreenFrame() {
        //ściągnięcie danych o profilach
        this.profile = new MainGameProfile(new ErrorMessenger(this));
        this.allUsersNames = profile.getAllUsernames();

        //parametry początkowe okna
        setTitle("Wordle");//ustawienie tytułu okna
        setPreferredSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));//ustawienie domyślnej szerokości i wysokości nowo otwieranego okna
        setLocation(DEFAULT_X_POSITION,DEFAULT_Y_POSITION);//ustawienie domyślnej pozycji nowo otwieranego okna na ekranie
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ustawienie domyślnej operacji zamykania okna na zamknięcie programu - potem to ustawienie zostanie zmienione
        setResizable(false);//zablokowanie możliwości zmiany rozmiaru okna
        pack();//bez tego okno jest zminimalizowane i nic nie widać
        setVisible(true);//okno domyślnie jest widoczne

        //wpisanie do ComboBoxa nazw wszystkich profili - zrobione w bloku kodu, żeby nie zaśmiecać pamięci
        {
            JComboBox profileChooserHelp = new JComboBox();
            for (int i = 0; i < allUsersNames.size(); i++) {
                profileChooserHelp.addItem(allUsersNames.get(i));
            }
            this.profileChooser = profileChooserHelp;
        }

        //zadeklarowanie pól związanych z tekstem i ukrycie ich
        this.newUsername = new JTextField();
        this.infoAboutChoise = new JLabel("Wybierz profil z listy użytkowników już istniejących lub wpisz nazwę nowego użytkownika.");
        this.infoAboutProfile = new JLabel("Wybrałeś profil: ");
        infoAboutProfile.setVisible(false);
        infoAboutChoise.setVisible(false);

        //deklaracja guzików
        this.buttonGame = new Buttons("Rozpocznij nową grę!",true,false,false);//guzik "Rozpocznij nową grę"
        this.buttonStats = new Buttons("Pokaż statystyki profilu",true,false,false);//guzik "Pokaż statystyki"
        this.buttonProfiles = new Buttons("Wybierz profil",true,true,false);//guzik "Wybierz profil"
        this.buttonConfirm = new Buttons("Zatwierdź wybór",false,false,false);

        //przypisanie guzikom rozmiarów
        buttonGame.setPreferredSize(new Dimension(200,30));
        buttonStats.setPreferredSize(new Dimension(200,30));
        buttonProfiles.setPreferredSize(new Dimension(200,30));
        buttonConfirm.setPreferredSize(new Dimension(200,30));
        profileChooser.setPreferredSize(new Dimension(100,30));
        newUsername.setPreferredSize(new Dimension(400,30));
        infoAboutChoise.setPreferredSize(new Dimension(600,30));
        infoAboutProfile.setPreferredSize(new Dimension(600,30));

        //kasowanie tła pól tekstowych - żeby były przezroczyste
        infoAboutChoise.setBackground(Color.lightGray);
        infoAboutProfile.setBackground(Color.lightGray);

        //przypisanie ActionListenerów do guzików
        buttonGame.addActionListener(new ButtonGameListener(profile));
        buttonStats.addActionListener(new ButtonStatsListener(profile));
        buttonProfiles.addActionListener(new ButtonProfilesListener());
        buttonConfirm.addActionListener(new ButtonConfirmListener());

        //deklaracja GBC dla komponentów (GridBagConstrains) - umożliwia to pozycjonowanie guzika w gridzie
        this.buttonGameGBC = new GridBagConstraints();
        this.buttonStatsGBC = new GridBagConstraints();
        this.buttonProfilesGBC = new GridBagConstraints();
        this.buttonConfirmGBC = new GridBagConstraints();
        this.profileChooserGBC = new GridBagConstraints();
        this.newUserNameGBC = new GridBagConstraints();
        this.infoAboutChoiseGBC = new GridBagConstraints();
        this.infoAboutProfileGBC = new GridBagConstraints();

        //ustawienie GBC dla komponentów
        buttonGameGBC.gridx = 0;
        buttonStatsGBC.gridx = 0;
        buttonProfilesGBC.gridx = 0;
        buttonConfirmGBC.gridx = 0;
        profileChooserGBC.gridx = 0;
        newUserNameGBC.gridx = 0;
        infoAboutChoiseGBC.gridx = 0;
        infoAboutProfileGBC.gridx = 0;

        buttonGameGBC.gridy = 0;
        buttonStatsGBC.gridy = 0;
        buttonProfilesGBC.gridy = 0;
        buttonConfirmGBC.gridy = 0;
        profileChooserGBC.gridy = 0;
        newUserNameGBC.gridy = 0;
        infoAboutChoiseGBC.gridy = 0;
        infoAboutProfileGBC.gridy = 0;

        buttonGameGBC.insets = new Insets(150,0,0,0);
        buttonStatsGBC.insets = new Insets(220,0,0,0);
        buttonProfilesGBC.insets = new Insets(290,0,0,0);
        buttonConfirmGBC.insets = new Insets(550,0,0,0);
        profileChooserGBC.insets = new Insets(150,0,0,0);
        newUserNameGBC.insets = new Insets(350,0,0,0);
        infoAboutProfileGBC.insets = new Insets(0,0,0,0);
        infoAboutChoiseGBC.insets = new Insets(0,0,0,0);

        this.selectPanel = new SelectScreenPanel(); //deklaracja panelu głównego
        selectPanel.repaint();//wyrysowanie napisu "WORDLE"
        selectPanel.setLayout(new GridBagLayout());//ustawienie nowego Layoutu - GridBagLayout
        setContentPane(selectPanel);

        //dodanie komponentów do panelu
        selectPanel.add(buttonGame,buttonGameGBC);
        selectPanel.add(buttonStats,buttonStatsGBC);
        selectPanel.add(buttonProfiles,buttonProfilesGBC);
        selectPanel.add(buttonConfirm,buttonConfirmGBC);
        selectPanel.add(profileChooser,profileChooserGBC);
        selectPanel.add(newUsername,newUserNameGBC);
        selectPanel.add(infoAboutChoise,infoAboutChoiseGBC);
        selectPanel.add(infoAboutProfile,infoAboutProfileGBC);

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
            new MainScreen(this.WhatProfile);
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
            infoAboutChoise.setVisible(true);
        }
    }
    private class ButtonConfirmListener implements ActionListener {
        public ButtonConfirmListener() {

        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Boolean wasSuccessful;
            if(!newUsername.getText().equals("")) {
                wasSuccessful =  profile.createProfile(newUsername.getText());
                infoAboutProfile = new JLabel("Wybrałeś profil: " + newUsername.getText());
            }
            else {
                int index = profileChooser.getSelectedIndex();
                wasSuccessful = profile.loadProfile(index);
                infoAboutProfile = new JLabel("Wybrałeś profil: " + allUsersNames.get(index));
            }
            //ustawiam komponenty z 2 strony
            setVisible2page(false);
            setEnabled2page(false);
            infoAboutChoise.setVisible(false);
            //ustawiam komponenty z 1 strony
            setVisible1page(true);//widzialność
            setEnabled1page(true);//użyteczność
            infoAboutProfile.setVisible(true);
        }
    }

}

class Buttons extends JButton {//klasa pomocnicza, tworząca guzik
    public Buttons(String buttonName, boolean IfVisible, boolean IfEnabled, boolean IfFocusable) {//konstruktor klasy JButton
        setFont(new Font("Arial", Font.PLAIN, 17));//ustawienie czcionki
        setBackground(Color.lightGray);//domyślnie jest koloru jasno-szarego
        setSize(new Dimension(200,30));//domyślnie ma rozmiary 200 x 30
        setFocusable(IfFocusable);//domyślnie nie zabiera focusu przy tworzeniu
        setVisible(IfVisible);//domyślnie guzik pojawia sie
        setEnabled(IfEnabled);//domyślnie można go wciskać
        setText(buttonName);//ustawienie napisu na guziku
    }
}
