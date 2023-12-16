package ui;

import javax.swing.*;

public class ExceptionCatcher extends JDialog {
    public ExceptionCatcher(JDialog parent, String exception) {
        JOptionPane.showMessageDialog(parent, exception, "ОШИБКА", JOptionPane.ERROR_MESSAGE);
    }

    public ExceptionCatcher(JFrame parent, String exception) {
        JOptionPane.showMessageDialog(parent, exception, "ОШИБКА", JOptionPane.ERROR_MESSAGE);
    }
}
