package ui;

import javax.swing.*;

public class ExceptionCatcher {
    public void ExceptionCatch(Exception exception){
        String error=ShowErrorMessage(exception);
        JOptionPane.showMessageDialog(null,error,"Error",JOptionPane.ERROR_MESSAGE);
    }

    private String ShowErrorMessage(Exception exception) {
        return switch (exception) {
            case NumberFormatException numberFormatException -> "Wrong input, try numbers";
            case ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException -> "Out of given bounds";
            case IllegalArgumentException illegalArgumentException -> "Wrong input, try again";
            case null, default -> {
                assert exception != null;
                yield exception.getMessage();
            }
        };
    }
}
