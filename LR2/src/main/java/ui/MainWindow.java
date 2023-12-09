package ui;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private TabulatedFunctionFactory factory;
    private ArrayTabulatedFunctionFactory arrFunc;
    private LinkedListTabulatedFunctionFactory listFunc;
    private Settings settingsDialog;

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

        // Создаем кнопку для открытия окна настроек
        JButton settingsButton = new JButton("Открыть окно настроек");
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSettingsWindow();
            }
        });

        // Создаем кнопку для создания табулированной функции
        JButton createTabulatedFunctionButton = new JButton("Создать табулированную функцию");
        createTabulatedFunctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TabulatedFunctionController();
            }
        });

        JButton createMathFunctionButton=new JButton("Create Math Function");
        createMathFunctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MathFunctionController();
            }
        });

        JButton functionOperationControllerButton=new JButton("Function operations");
        functionOperationControllerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FunctionOperationController(this);
                FunctionOperationController funcOperController=new FunctionOperationController(this);
                funcOperController.setVisible(true);
            }
        });

        JPanel contentPane = new JPanel(new FlowLayout());
        contentPane.add(settingsButton);
        contentPane.add(createTabulatedFunctionButton);
        contentPane.add(createMathFunctionButton);
        contentPane.add(functionOperationControllerButton);

        setContentPane(contentPane);
        setVisible(true);

        // Инициализация фабрики по умолчанию
        factory = new ArrayTabulatedFunctionFactory();
    }

    private void openSettingsWindow() {
        Settings settingsWindow = new Settings(this, factory);

        setVisible(false);
        settingsWindow.setVisible(true);
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
