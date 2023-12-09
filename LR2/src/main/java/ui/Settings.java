package ui;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JFrame {
    private final MainWindow mainWindow;
    private final TabulatedFunctionFactory factory;
    private boolean arrActive;
    public Settings(MainWindow mainWindow,TabulatedFunctionFactory factory){
        super("Settings");
        setSize(400,300);
        this.arrActive=true;
        this.mainWindow = mainWindow;
        this.factory = factory;
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JRadioButton arrayFactoryButton = new JRadioButton("Array");
        JRadioButton linkedListFactoryButton = new JRadioButton("Linked list");
        ButtonGroup group = new ButtonGroup();
        group.add(arrayFactoryButton);
        group.add(linkedListFactoryButton);

        // Устанавливаем текущую фабрику в соответствии с текущим выбором пользователя
        if (factory instanceof ArrayTabulatedFunctionFactory) {
            arrayFactoryButton.setSelected(true);
        } else if (factory instanceof LinkedListTabulatedFunctionFactory) {
            linkedListFactoryButton.setSelected(true);
        }

        // Создаем кнопку "Применить"
        JButton applyButton = new JButton("Применить");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applySettings();
            }
        });

        JPanel contentPane = new JPanel(new GridLayout(3, 1));
        contentPane.add(arrayFactoryButton);
        contentPane.add(linkedListFactoryButton);
        contentPane.add(applyButton);

        setContentPane(contentPane);
    }
    private void applySettings() {
        // Изменяем текущую фабрику в соответствии с выбором пользователя
        if (factory != null) {
            mainWindow.setFactory(factory);
        }
        // Закрываем окно настроек
        dispose();
        // Показываем главное окно
        mainWindow.setVisible(true);
    }
}
