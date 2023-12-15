package ui;

import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabulatedFunctionController extends JDialog {
    private static TabulatedFunction function;
    private static TabulatedFunctionFactory functionFactory;
    private JTable pointTable;
    private final JTextField numOfPoints;
    private final DefaultTableModel tableModel;
    private boolean arrIsType;
    private static boolean status;

    public static TabulatedFunction getFunction(){
        return function;
    }

    public static boolean getStatus(){
        return status;
    }

    public void setStatus(boolean status){
        this.status=status;
    }

    public TabulatedFunctionController(JFrame parent,boolean arrType) {
        super(parent,"Tabulated Function Creator",true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(470, 300);
        arrIsType=arrType;
        numOfPoints = new JTextField(5);
        JButton createButton = new JButton("Create");
        setStatus(false);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createTableFunction();
            }
        });
        JButton createFunctionButton=new JButton("Create function");


        tableModel = new DefaultTableModel();
        tableModel.addColumn("X");
        tableModel.addColumn("Y");
        pointTable = new JTable(tableModel);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setLayout(new FlowLayout());
        contentPane.add(new JLabel("Number of Points:"), BorderLayout.NORTH);
        contentPane.add(numOfPoints, BorderLayout.NORTH);
        contentPane.add(createButton, BorderLayout.NORTH);
        contentPane.add(createFunctionButton,BorderLayout.NORTH);
        contentPane.add(new JScrollPane(pointTable), BorderLayout.CENTER);
        setContentPane(contentPane);


        createFunctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTabulatedFunction();
            }
        });

        setVisible(true);
    }

    private void createTableFunction() {
        try {
            int count = Integer.parseInt(numOfPoints.getText());
            if (count<2) {
                ExceptionCatcher exception = new ExceptionCatcher(this, "Size must be >=2");
            }
            else {
                tableModel.setRowCount(0);
                for (int i = 0; i < count; ++i) {
                    Object[] rowData = new Object[2];
                    rowData[0] = JOptionPane.showInputDialog("Enter value of X" + (i + 1));
                    rowData[1] = JOptionPane.showInputDialog("Enter value of Y" + (i + 1));
                    tableModel.addRow(rowData);
                }
            }
        } catch (NumberFormatException e) {
            ExceptionCatcher exception=new ExceptionCatcher(this,"Wrong input,try again!");
        }
    }

    private void createTabulatedFunction(){
        setStatus(true);
        int count=Integer.parseInt(numOfPoints.getText());
        double[] xValues = new double[count];
        double[] yValues = new double[count];

        for (int i = 0; i < count; ++i) {
            xValues[i] = Double.parseDouble(tableModel.getValueAt(i, 0).toString());
            yValues[i] = Double.parseDouble(tableModel.getValueAt(i, 1).toString());
        }
        functionFactory = arrIsType ? new ArrayTabulatedFunctionFactory():new LinkedListTabulatedFunctionFactory();
        function =functionFactory.create(xValues, yValues);
        System.out.println("Tabulated function: " + function);
        dispose();
    }
}
