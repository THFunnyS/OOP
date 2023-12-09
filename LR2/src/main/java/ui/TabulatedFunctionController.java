package ui;


import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabulatedFunctionController extends JFrame {
    private JTable table;
    private final JTextField numOfPoints;
    private final DefaultTableModel tableModel;

    public TabulatedFunctionController() {
        super("Tabulated Function Creator");
        setSize(470, 300);

        numOfPoints = new JTextField(10);
        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createTabulatedFunction();
            }
        });
        tableModel = new DefaultTableModel();
        // Создайте и настройте JTable
        tableModel.addColumn("X");
        tableModel.addColumn("Y");
        table = new JTable(tableModel);


        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setLayout(new FlowLayout());
        contentPane.add(new JLabel("Number of Points:"), BorderLayout.NORTH);
        contentPane.add(numOfPoints, BorderLayout.NORTH);
        contentPane.add(createButton, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
        setContentPane(contentPane);
        setVisible(true);
    }

    private void createTabulatedFunction() {
        try {
            int count = Integer.parseInt(numOfPoints.getText());
            if (count < 2) JOptionPane.showMessageDialog(this, "Size must be >=2", "Error", JOptionPane.ERROR_MESSAGE);
            else {
                tableModel.setRowCount(0);
                for (int i = 0; i < count; ++i) {
                    Object[] rowData = new Object[2];
                    rowData[0] = JOptionPane.showInputDialog("Enter value of X" + (i + 1));
                    rowData[1] = JOptionPane.showInputDialog("Enter value of Y" + (i + 1));
                    tableModel.addRow(rowData);
                }
                TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
                double[] xValues = new double[count];
                double[] yValues = new double[count];

                for (int i = 0; i < count; ++i) {
                    xValues[i] = Double.parseDouble(tableModel.getValueAt(i, 0).toString());
                    yValues[i] = Double.parseDouble(tableModel.getValueAt(i, 1).toString());
                }

                TabulatedFunction function = factory.create(xValues, yValues);
                System.out.println("Tabulated function: " + function);

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Wrong input, try again", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TabulatedFunctionController::new);
    }
}
