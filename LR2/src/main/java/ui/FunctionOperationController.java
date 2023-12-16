package ui;

import exceptions.InconsistentFunctionsException;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import io.FunctionsIO;
import operations.TabulatedFunctionOperationService;

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
import java.util.Objects;

public class FunctionOperationController extends JDialog {

    private TabulatedFunction function1;
    private TabulatedFunction function2;
    private TabulatedFunction tempFunction;
    private TabulatedFunction resultFunction;
    private LinkedList<TabulatedFunction> list_of_results;
    private boolean arrIsType;
    private TabulatedFunctionFactory functionFactory;
    private String operationName;
    private TabulatedFunctionOperationService operation;
    private HashMap<String, TabulatedFunction> map_Of_Functions1 = new HashMap<>();
    private HashMap<String, TabulatedFunction> map_Of_Functions2 = new HashMap<>();
    private DefaultTableModel operationResultTableModel;
    private JTable operationResultTable;

    private class NonEditableTableModel extends DefaultTableModel {
        public NonEditableTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    public FunctionOperationController(JFrame parent, boolean arrType, LinkedList<TabulatedFunction> list) {
        super(parent, "Арифметические операции", true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        arrIsType = arrType;
        LinkedList<TabulatedFunction> functionsList = list;

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel function1Panel = new JPanel();
        function1Panel.setLayout(new FlowLayout());

        JLabel selectedFunction1Label = new JLabel("Выберите 1-ю функцию и нажмите OK:");
        selectedFunction1Label.setFont(new Font("Times New Roman", Font.BOLD, 16));
        selectedFunction1Label.setHorizontalAlignment(JLabel.CENTER); //
        function1Panel.add(selectedFunction1Label);

        JComboBox<String> function1ComboBox;

        String[] selectedFunction1Names = new String[functionsList.size()];

        for (int i = 0; i < functionsList.size(); i++) {
            selectedFunction1Names[i] = functionsList.get(i).toString();
        }
        for (int i = 0; i < functionsList.size(); i++) {
            map_Of_Functions1.put(selectedFunction1Names[i], functionsList.get(i));
        }

        function1ComboBox = new JComboBox<>(selectedFunction1Names);
        function1ComboBox.setPreferredSize(new Dimension(300, 26));

        function1ComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempFunction = map_Of_Functions1.get(function1ComboBox.getSelectedItem());
            }
        });

        function1Panel.add(function1ComboBox);

        JButton ok1Button = new JButton("ОК");
        ok1Button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        ok1Button.setHorizontalAlignment(JLabel.CENTER); //
        function1Panel.add(ok1Button);

        ok1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function1 = tempFunction;
            }
        });

        panel.add(function1Panel, BorderLayout.NORTH);

        JPanel function2Panel = new JPanel();
        function2Panel.setLayout(new FlowLayout());

        JLabel selectedFunction2Label = new JLabel("Выберите 2-ю функцию и нажмите OK:");
        selectedFunction2Label.setFont(new Font("Times New Roman", Font.BOLD, 16));
        selectedFunction2Label.setHorizontalAlignment(JLabel.CENTER); //

        function2Panel.add(selectedFunction2Label);

        JComboBox<String> selectedFunction2ComboBox;

        String[] selectedFunction2Names = new String[functionsList.size()];

        for (int i = 0; i < functionsList.size(); i++) {
            selectedFunction2Names[i] = functionsList.get(i).toString();
        }
        for (int i = 0; i < functionsList.size(); i++) {
            map_Of_Functions2.put(selectedFunction2Names[i], functionsList.get(i));
        }

        selectedFunction2ComboBox = new JComboBox<>(selectedFunction2Names);
        selectedFunction2ComboBox.setPreferredSize(new Dimension(300, 26));

        selectedFunction2ComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempFunction = map_Of_Functions2.get(selectedFunction2ComboBox.getSelectedItem());
            }
        });

        function2Panel.add(selectedFunction2ComboBox);

        JButton ok2Button = new JButton("ОК");
        ok2Button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        ok2Button.setHorizontalAlignment(JLabel.CENTER); //
        function2Panel.add(ok2Button);

        ok2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function2 = tempFunction;
            }
        });

        panel.add(function2Panel, BorderLayout.CENTER);

        JPanel operationPanel = new JPanel();
        operationPanel.setLayout(new FlowLayout());

        JLabel operationLabel = new JLabel("Выберите Операцию и нажмите Применить:");
        operationLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        operationLabel.setHorizontalAlignment(JLabel.CENTER); //
        operationPanel.add(operationLabel);

        JComboBox<String> operationComboBox;

        String[] operationNames = {"Сложение", "Вычитание", "Умножение", "Деление"};

        operationComboBox = new JComboBox<>(operationNames);
        operationComboBox.setPreferredSize(new Dimension(120, 26));
        operationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationName = operationComboBox.getSelectedItem().toString();
            }
        });

        operationPanel.add(operationComboBox);

        JButton applyButton = new JButton("Принять");
        applyButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        applyButton.setHorizontalAlignment(JLabel.CENTER); //
        operationPanel.add(applyButton);

        JButton saveButton = new JButton("Сохранить");
        saveButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        saveButton.setHorizontalAlignment(JLabel.CENTER); //
        operationPanel.add(saveButton);

        panel.add(operationPanel, BorderLayout.SOUTH);

        JPanel operationResultPanel = new JPanel();
        operationResultPanel.setLayout(new BorderLayout());

        operationResultTableModel = new NonEditableTableModel(new String[]{"Функция", "Тип"}, 0);
        operationResultTable = new JTable(operationResultTableModel);
        operationResultPanel.add(operationResultTable);

        JScrollPane operationScrollPane = new JScrollPane(operationResultTable);
        operationResultPanel.add(operationScrollPane);

        JPanel operationDescriptionPanel = new JPanel();
        operationDescriptionPanel.setLayout(new BorderLayout());

        JTextArea operationDescriptionTextArea = new JTextArea();
        operationDescriptionTextArea.setEditable(false);
        operationDescriptionTextArea.setLineWrap(true);
        operationDescriptionTextArea.setWrapStyleWord(true);
        operationDescriptionPanel.add(operationDescriptionTextArea, BorderLayout.CENTER);

        JScrollPane operationDescriptionScrollPane = new JScrollPane(operationDescriptionTextArea);
        operationDescriptionPanel.add(operationDescriptionScrollPane, BorderLayout.CENTER);
        operationDescriptionTextArea.setRows(operationDescriptionTextArea.getLineCount());

        list_of_results = new LinkedList<TabulatedFunction>();
        operationDescriptionPanel.setPreferredSize(new Dimension(WIDTH, 100));

        applyButton.addActionListener(e -> functionOperation(operationNames));

        saveButton.addActionListener(e -> saveOperation());

        int[] row = {-1};

        operationResultTable.getSelectionModel().addListSelectionListener(e -> {
            descriptionTable(operationDescriptionTextArea, row);
        });
        add(panel, BorderLayout.NORTH);
        add(operationResultPanel, BorderLayout.CENTER);
        add(operationDescriptionPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void functionOperation(String[] operationNames) {
        functionFactory = arrIsType ? new ArrayTabulatedFunctionFactory() : new LinkedListTabulatedFunctionFactory();
        try {
            operation = new TabulatedFunctionOperationService(functionFactory);
            if (Objects.equals(operationName, operationNames[0])) {
                resultFunction = operation.add(function1, function2);
            }
            if (Objects.equals(operationName, operationNames[1])) {
                resultFunction = operation.subtraction(function1, function2);
            }
            if (Objects.equals(operationName, operationNames[2])) {
                resultFunction = operation.multiplication(function1, function2);
            }
            if (Objects.equals(operationName, operationNames[3])) {
                resultFunction = operation.division(function1, function2);
            }
            list_of_results.add(resultFunction);
            addFunctionToTable(resultFunction, "Результат " + operationName);
        } catch (InconsistentFunctionsException e) {
            ExceptionCatcher exception = new ExceptionCatcher(this, "Разная длина функций");
        } catch (NullPointerException e) {
            ExceptionCatcher exception = new ExceptionCatcher(this, "Функция не найдена");
        } catch (ArithmeticException e) {
            ExceptionCatcher exception = new ExceptionCatcher(this, "Деление на 0");
        }
    }

    private void saveOperation() {
        int row = operationResultTable.getSelectedRow();
        if (row != -1) {
            JFileChooser fileChooser = new JFileChooser();
            int val = fileChooser.showSaveDialog(this);
            if (val == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    FunctionsIO.writeTabulatedFunction(new BufferedWriter(new FileWriter(file.getAbsolutePath())), resultFunction);
                } catch (IOException e) {
                    ExceptionCatcher exception = new ExceptionCatcher(this, "Ошибка ввода/вывода");
                }
            }
        } else {
            ExceptionCatcher exception = new ExceptionCatcher(this, "Функия не найдена");
        }
    }

    private void descriptionTable(JTextArea funcDescriptionText, int[] row) {
        int newRow = operationResultTable.getSelectedRow();
        if (newRow != -1 && newRow != row[0]) {
            row[0] = newRow;
            TabulatedFunction function = (TabulatedFunction) list_of_results.get(row[0]);
            StringBuilder funcView = new StringBuilder();
            funcView.append(function.getClass().getName());
            funcView.append(" размер = ");
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
        operationResultTableModel.addRow(new String[]{name, function.getClass().getSimpleName()});
    }
}