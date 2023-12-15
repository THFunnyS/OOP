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
        super(parent, "Settings", true);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JRadioButton arrayFactoryButton = new JRadioButton("Array");
        JRadioButton linkedListFactoryButton = new JRadioButton("Linked list");
        ButtonGroup group = new ButtonGroup();
        group.add(arrayFactoryButton);
        group.add(linkedListFactoryButton);

        arrayFactoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arrayFactoryButton.isSelected()) {
                    arrIsType = true;
                }
            }
        });

        linkedListFactoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (linkedListFactoryButton.isSelected()) {
                    arrIsType = false;
                }
            }
        });
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel contentPane = new JPanel(new GridLayout(3, 1));
        contentPane.add(arrayFactoryButton);
        contentPane.add(linkedListFactoryButton);
        contentPane.add(applyButton);

        arrayFactoryButton.setSelected(arrIsType);
        linkedListFactoryButton.setSelected(!arrIsType);

        setContentPane(contentPane);
        setVisible(true);
    }
}
