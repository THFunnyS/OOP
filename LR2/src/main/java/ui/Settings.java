package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JDialog {
    private static boolean arrIsType = true;

    public void setArrIsType(boolean arrIsType) {
        Settings.arrIsType = arrIsType;
    }

    public static boolean getFuncType() {
        return arrIsType;
    }

    public Settings(JFrame parent) {
        super(parent, "Настройки", true);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        JLabel Question = new JLabel("Выберите способ создания функции:");
        JButton arrayFactoryButton = new JButton("Массив");
        JButton linkedListFactoryButton = new JButton("Cвязанный список");
        ButtonGroup group = new ButtonGroup();
        group.add(arrayFactoryButton);
        group.add(linkedListFactoryButton);

        arrayFactoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arrIsType = true;
                Settings.this.dispose();  // Закрытие окна настроек
            }
        });

        linkedListFactoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arrIsType = false;
                Settings.this.dispose();  // Закрытие окна настроек
            }
        });


        JPanel contentPane = new JPanel(new GridLayout(3, 1));
        Question.setFont(new Font("Times New Roman", Font.BOLD, 16));
        Question.setHorizontalAlignment(JLabel.CENTER); //
        arrayFactoryButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        arrayFactoryButton.setHorizontalAlignment(JLabel.CENTER); //
        linkedListFactoryButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        linkedListFactoryButton.setHorizontalAlignment(JLabel.CENTER); //
        contentPane.add(Question);
        contentPane.add(arrayFactoryButton);
        contentPane.add(linkedListFactoryButton);


        setContentPane(contentPane);
        setVisible(true);
    }

}
