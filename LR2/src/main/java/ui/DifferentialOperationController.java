package ui;

import exceptions.ArrayIsNotSortedException;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import io.FunctionsIO;
import operations.TabulatedDifferentialOperator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class DifferentialOperationController extends JDialog {
    private TabulatedFunction function;
    private TabulatedFunctionFactory functionFactory;
    private TabulatedFunction derivative;
    private LinkedList<TabulatedFunction> list_of_derivative;
    private boolean arrIsType;
    private TabulatedDifferentialOperator differentialOperator;
    private HashMap<String, TabulatedFunction> map_Of_Functions = new HashMap<>();
    private DefaultTableModel resultTableModel;
    private JTable resultTable;

    private class NonEditableTableModel extends DefaultTableModel {
        public NonEditableTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
    public DifferentialOperationController(JFrame parent,boolean arrType,LinkedList<TabulatedFunction> list){
        super(parent,"Найти производную",true);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        LinkedList<TabulatedFunction> listOfFunc=list;
        arrIsType=arrType;

        JPanel funcPanel=new JPanel();
        funcPanel.setLayout(new FlowLayout());
        JLabel functionsLabel = new JLabel("Выбрать функцию:");
        functionsLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        functionsLabel.setHorizontalAlignment(JLabel.CENTER); //

        JComboBox<String> functionsComboBox;
        String[] functionNames = new String[listOfFunc.size()];
        for (int i = 0; i < listOfFunc.size(); i++) {
            functionNames[i] = listOfFunc.get(i).toString();
        }
        for (int i = 0; i < listOfFunc.size(); i++) {
            map_Of_Functions.put(functionNames[i], listOfFunc.get(i));
        }

        functionsComboBox = new JComboBox<>(functionNames);
        functionsComboBox.setPreferredSize(new Dimension(100, 26));
        functionsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function = map_Of_Functions.get(functionsComboBox.getSelectedItem());
            }
        });
        JButton difOperationButton = new JButton("Найти производную");
        difOperationButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        difOperationButton.setHorizontalAlignment(JLabel.CENTER); //

        JButton saveDerivativeButton = new JButton("Сохранить");
        saveDerivativeButton.setPreferredSize(new Dimension(120, 30));
        saveDerivativeButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        saveDerivativeButton.setHorizontalAlignment(JLabel.CENTER); //


        funcPanel.add(functionsLabel);
        funcPanel.add(functionsComboBox);
        funcPanel.add(difOperationButton);
        funcPanel.add(saveDerivativeButton);

        JPanel resultPanel = new JPanel(new BorderLayout());

        resultTableModel = new DifferentialOperationController.NonEditableTableModel(new String[]{"Название функции", "Тип"}, 0);
        resultTable = new JTable(resultTableModel);

        resultPanel.add(resultTable);

        JScrollPane resultScrollPane = new JScrollPane(resultTable);

        resultPanel.add(resultScrollPane);

        JPanel derivativeDescriptionPanel = new JPanel(new BorderLayout());
        derivativeDescriptionPanel.setPreferredSize(new Dimension(WIDTH, 100));

        JTextArea derivativeDescriptionTextArea = new JTextArea();
        derivativeDescriptionTextArea.setEditable(false);
        derivativeDescriptionTextArea.setLineWrap(true);
        derivativeDescriptionTextArea.setWrapStyleWord(true);
        derivativeDescriptionPanel.add(derivativeDescriptionTextArea, BorderLayout.CENTER);

        JScrollPane derivativeDescriptionScrollPane = new JScrollPane(derivativeDescriptionTextArea);
        derivativeDescriptionPanel.add(derivativeDescriptionScrollPane, BorderLayout.CENTER);
        derivativeDescriptionTextArea.setRows(derivativeDescriptionTextArea.getLineCount());

        list_of_derivative = new LinkedList<TabulatedFunction>();

        derivativeDescriptionPanel.setPreferredSize(new Dimension(WIDTH, 100));

        difOperationButton.addActionListener(e->difOperationButtonFunctional());

        saveDerivativeButton.addActionListener(e -> saveButtonFunctional());

        int[] row={-1};

        resultTable.getSelectionModel().addListSelectionListener(e -> descriptionTable(derivativeDescriptionTextArea,row));
        derivativeDescriptionPanel.setPreferredSize(new Dimension(WIDTH, 100));
        add(funcPanel, BorderLayout.SOUTH);
        add(resultPanel, BorderLayout.NORTH);
        add(derivativeDescriptionPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void difOperationButtonFunctional(){
        functionFactory = arrIsType ? new ArrayTabulatedFunctionFactory() : new LinkedListTabulatedFunctionFactory();
        try {
            differentialOperator = new TabulatedDifferentialOperator(functionFactory);
            derivative = differentialOperator.derive2(function);
            list_of_derivative.add(derivative);
            addFunctionToTable(derivative, "Производная табулированной функции");
        }catch (NullPointerException e){
            ExceptionCatcher exception=new ExceptionCatcher(this,"Функция равна нулю");
        }catch (ArrayIsNotSortedException e){
            ExceptionCatcher exception=new ExceptionCatcher(this,"Массив не отсортирован");
        }
    }

    private void saveButtonFunctional(){
        int row=resultTable.getSelectedRow();
        if (row!=-1){
            JFileChooser fileChooser=new JFileChooser();
            int val=fileChooser.showSaveDialog(this);
            if (val==JFileChooser.APPROVE_OPTION){
                File file=fileChooser.getSelectedFile();
                try{
                    FunctionsIO.writeTabulatedFunction(new BufferedWriter(new FileWriter(file.getAbsolutePath())),derivative);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            ExceptionCatcher exception = new ExceptionCatcher(this, "Выберите функцию");
        }
    }

    private void descriptionTable(JTextArea funcDescriptionText, int[] row) {
        int newRow = resultTable.getSelectedRow();
        if (newRow != -1 && newRow != row[0]) {
            row[0] = newRow;
            TabulatedFunction function = (TabulatedFunction) list_of_derivative.get(row[0]);
            StringBuilder funcView = new StringBuilder();
            funcView.append(function.getClass().getName());
            funcView.append(" size = ");
            funcView.append(function.getCount());
            funcView.append("\n");
            for (int i = 0; i < function.getCount(); i++) {
                funcView.append("[");
                funcView.append(function.getX(i));
                funcView.append("; ");
                funcView.append(function.getY(i));
                funcView.append("]\n");
            }
            funcDescriptionText.setText(funcView.toString());
        }
    }

    private void addFunctionToTable(TabulatedFunction function, String name) {
        resultTableModel.addRow(new String[]{name, function.getClass().getSimpleName()});
    }
}
