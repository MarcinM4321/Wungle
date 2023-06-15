import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorModeButton extends JButton {
    private DayNightSwitchable component;
    private boolean isNightMode;
    public ColorModeButton(DayNightSwitchable component, boolean isNightMode) {
        this.component = component;
        this.isNightMode = isNightMode;
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flipColorMode();
                updateColorMode();
            }
        });
    }
    private void flipColorMode() {
        isNightMode = !isNightMode;
        updateColorMode();
    }
    public void updateColorMode() {
        if (isNightMode) {
            component.setToNightMode();
            setText("tryb dzienny");
        }
        else {
            component.setToDayMode();
            setText("tryb nocy");
        }
    }
}
