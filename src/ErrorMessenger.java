import javax.swing.*;

public class ErrorMessenger {
    private JFrame parentFrame;
    ErrorMessenger(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    void showError(String message) {
        JOptionPane.showMessageDialog(parentFrame,message, "Komunikat o błędzie", JOptionPane.ERROR_MESSAGE);
    }

    void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }
}
