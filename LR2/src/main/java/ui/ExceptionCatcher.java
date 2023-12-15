package ui;

import javax.swing.*;

public class ExceptionCatcher extends JDialog{
    public ExceptionCatcher(JDialog parent,String exception){
        JOptionPane.showMessageDialog(parent,exception,"Error",JOptionPane.ERROR_MESSAGE);
    }
}
