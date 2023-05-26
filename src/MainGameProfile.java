public class MainGameProfile {
    // klasa odpowiedzialna za profil obecnego gracza
    // Wszystkie inny klasy mają korzystać z tej klasy aby otrzymywać informacje o profilu gracza

    private ProfileData currentProfile;
    private ProfileDataIO database;
    MainGameProfile() {
        this.currentProfile = null;
        database = new ProfileDataIO();
    }
    Boolean isEmpty() {
        return currentProfile == null;
    }

    String getUsername( ) {
        return currentProfile.username;
    }

    void loadProfile() {
        // TODO: Funkcjonalność
        currentProfile =  new ProfileData("John");
    }
    void saveProfile() {
        database.saveProfile(currentProfile);
    }
}
