package ui;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;

import javax.swing.*;

public class MainWindow extends JFrame {
    private ArrayTabulatedFunctionFactory arrFunc;
    private LinkedListTabulatedFunctionFactory listFunc;

    public  MainWindow(){
        super("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        JMenuBar menuBar=new JMenuBar();
        JMenu settingMenu=new JMenu("Settings");
        JMenuItem openSettingsMenu=new JMenuItem("Open settings");
        settingMenu.add(openSettingsMenu);
        menuBar.add(settingMenu);
        setJMenuBar(menuBar);

        arrFunc=new ArrayTabulatedFunctionFactory();
        listFunc=new LinkedListTabulatedFunctionFactory();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
