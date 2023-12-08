package ui;

import javax.swing.*;

public class ExceptionCatcher {
    public void ExceptionCatch(Exception exception){
        String error=ShowErrorMessage(exception);
        JOptionPane.showMessageDialog(null,error,"Error",JOptionPane.ERROR_MESSAGE);
    }

    private String ShowErrorMessage(Exception exception) {
        if (exception instanceof NumberFormatException) return "Wrong input, try numbers";
        else if (exception instanceof ArrayIndexOutOfBoundsException) return "Out of given bounds";
        else if (exception instanceof IllegalArgumentException) return "Wrong input, try again";
        else return exception.getMessage();
    }
}
