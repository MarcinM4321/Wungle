import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorModeButton extends JButton {
    private DayNightSwitchable component;
    private boolean isNightMode;
    public ColorModeButton(DayNightSwitchable component, boolean isNightMode) {
        this.component = component;
        this.isNightMode = isNightMode;
        setFocusable(false);//domyślnie nie zabiera focusu przy tworzeniu
        setVisible(true);//domyślnie guzik pojawia sie
        setEnabled(true);//domyślnie można go wciskać
        setText("Tryb nocy");
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
            setText("tryb dzień");
        }
        else {
            component.setToDayMode();
            setText("tryb noc");
        }
    }
}
