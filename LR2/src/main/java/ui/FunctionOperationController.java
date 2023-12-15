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

    public FunctionOperationController(JFrame parent,boolean arrType,LinkedList<TabulatedFunction> list){
        super(parent,"Math operations",true);
        setSize(600,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        arrIsType=arrType;
        LinkedList<TabulatedFunction> functionsList=list;

        JPanel panel=new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel function1Panel = new JPanel();
        function1Panel.setLayout(new FlowLayout());

        JLabel selectedFunction1Label = new JLabel("Select 1st Function and press OK:");
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

        JButton ok1Button = new JButton("OK");

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

        JLabel selectedFunction2Label = new JLabel("Select 2nd Function and press OK:");
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

        JButton ok2Button = new JButton("OK");

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

        JLabel operationLabel = new JLabel("Select Operation and press Apply:");
        operationPanel.add(operationLabel);

        JComboBox<String> operationComboBox;

        String[] operationNames = {"Addition", "Subtraction", "Multiplication", "Division"};

        operationComboBox = new JComboBox<>(operationNames);
        operationComboBox.setPreferredSize(new Dimension(120, 26));
        operationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationName = operationComboBox.getSelectedItem().toString();
            }
        });

        operationPanel.add(operationComboBox);

        JButton applyButton = new JButton("Apply");
        operationPanel.add(applyButton);

        JButton saveButton = new JButton("Save");
        operationPanel.add(saveButton);

        panel.add(operationPanel, BorderLayout.SOUTH);

        JPanel operationResultPanel = new JPanel();
        operationResultPanel.setLayout(new BorderLayout());

        operationResultTableModel = new NonEditableTableModel(new String[]{"Function", "Type"}, 0);
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

        applyButton.addActionListener(e-> functionOperation(operationNames));

        saveButton.addActionListener(e -> saveOperation());

        int[] row={-1};

        operationResultTable.getSelectionModel().addListSelectionListener(e ->{
            descriptionTable(operationDescriptionTextArea,row);
                });
        add(panel, BorderLayout.NORTH);
        add(operationResultPanel, BorderLayout.CENTER);
        add(operationDescriptionPanel, BorderLayout.SOUTH);
        setVisible(true);


    }

    private void functionOperation(String[] operationNames){
        functionFactory = arrIsType ? new ArrayTabulatedFunctionFactory() : new LinkedListTabulatedFunctionFactory();
        try {
            operation=new TabulatedFunctionOperationService(functionFactory);
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
            addFunctionToTable(resultFunction,"Result of "+operationName);
        }catch (InconsistentFunctionsException e){
            ExceptionCatcher exception=new ExceptionCatcher(this,"Different lenght");
        }
    }

    private void saveOperation(){
        int row=operationResultTable.getSelectedRow();
        if (row!=-1){
            JFileChooser fileChooser=new JFileChooser();
            int val=fileChooser.showSaveDialog(this);
            if (val==JFileChooser.APPROVE_OPTION){
                File file=fileChooser.getSelectedFile();
                try{
                    FunctionsIO.writeTabulatedFunction(new BufferedWriter(new FileWriter(file.getAbsolutePath())),resultFunction);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            ExceptionCatcher exception = new ExceptionCatcher(this, "Choose function");
        }
    }
    private void descriptionTable(JTextArea funcDescriptionText, int[] row) {
        int newRow = operationResultTable.getSelectedRow();
        if (newRow != -1 && newRow != row[0]) {
            row[0] = newRow;
            TabulatedFunction function = (TabulatedFunction) list_of_results.get(row[0]);
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
        operationResultTableModel.addRow(new String[]{name, function.getClass().getSimpleName()});
    }
    /*private final TabulatedFunctionOperationService operationService;
    private final MainWindow mainWindow;

    private JComboBox factoryComboBox;
    private JButton createFirstFunctionButton;
    private JButton createSecondFunctionButton;
    private JButton performOperationButton;
    private JTable firstFunctionTable;
    private JTable secondFunctionTable;
    private JTable resultFunctionTable;

    private TabulatedFunction firstFunction;
    private TabulatedFunction secondFunction;
    private TabulatedFunction resultFunction;

    public FunctionOperationController(MainWindow mainWindow,TabulatedFunctionOperationService operationService ){
        super("Function Operation Controller");
        this.mainWindow=mainWindow;
        this.operationService=operationService;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,400);
        initializeUI();
    }

    private void initializeUI() {
        // Создание элементов интерфейса
        factoryComboBox = new JComboBox<>();
        createFirstFunctionButton = new JButton("Создать/Загрузить первую функцию");
        createSecondFunctionButton = new JButton("Создать/Загрузить вторую функцию");
        performOperationButton = new JButton("Выполнить операцию");
        firstFunctionTable = new JTable();
        secondFunctionTable = new JTable();
        resultFunctionTable = new JTable();

        // Установка стандартной фабрики
        factoryComboBox.setSelectedItem();

        // Добавление слушателей событий
        createFirstFunctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFunctionCreationDialog(true);
            }
        });

        createSecondFunctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFunctionCreationDialog(false);
            }
        });

        performOperationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperation();
            }
        });

        // Размещение элементов интерфейса на панели
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(createTopPanel(), BorderLayout.NORTH);
        contentPane.add(createFunctionsPanel(), BorderLayout.CENTER);
        contentPane.add(createResultPanel(), BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("Выберите фабрику:"));
        topPanel.add(factoryComboBox);
        topPanel.add(createFirstFunctionButton);
        topPanel.add(createSecondFunctionButton);
        topPanel.add(performOperationButton);

        return topPanel;
    }

    private JPanel createFunctionsPanel() {
        JPanel functionsPanel = new JPanel(new GridLayout(1, 2));

        // Добавление таблиц для первой и второй функций
        functionsPanel.add(createFunctionTablePanel("Первая функция", firstFunctionTable));
        functionsPanel.add(createFunctionTablePanel("Вторая функция", secondFunctionTable));

        return functionsPanel;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new GridLayout(1, 1));

        // Добавление таблицы для результата операции
        resultPanel.add(createFunctionTablePanel("Результат", resultFunctionTable));

        return resultPanel;
    }

    private JPanel createFunctionTablePanel(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(new JLabel(title), BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private void openFunctionCreationDialog(boolean isFirstFunction) {
        TabulatedFunctionFactory selectedFactory = (TabulatedFunctionFactory) factoryComboBox.getSelectedItem();
        TabulatedFunctionController creationDialog = new TabulatedFunctionController(this, selectedFactory);

        // Открываем диалог для создания/загрузки функции
        creationDialog.setVisible(true);

        // Получаем созданную/загруженную функцию из диалога
        TabulatedFunction createdFunction = creationDialog.getCreatedFunction();

        // Устанавливаем функцию в соответствующую переменную и отображаем в таблице
        if (isFirstFunction) {
            firstFunction = createdFunction;
            updateFunctionTable(firstFunction, firstFunctionTable);
        } else {
            secondFunction = createdFunction;
            updateFunctionTable(secondFunction, secondFunctionTable);
        }
    }

    private void performOperation() {
        if (firstFunction != null && secondFunction != null) {
            // Выполняем операцию
            resultFunction = performSelectedOperation(firstFunction, secondFunction);

            // Отображаем результат в таблице
            updateFunctionTable(resultFunction, resultFunctionTable);
        } else {
            JOptionPane.showMessageDialog(this, "Необходимо создать обе функции", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private TabulatedFunction performSelectedOperation(TabulatedFunction first, TabulatedFunction second) {
        TabulatedFunctionFactory selectedFactory = (TabulatedFunctionFactory) factoryComboBox.getSelectedItem();
        operationService.setFactory(selectedFactory);

        // Выбираем операцию в зависимости от выбора пользователя
        String operationType = JOptionPane.showInputDialog(this,
                "Выберите операцию:\n1. Сложение\n2. Вычитание\n3. Умножение\n4. Деление",
                "Выбор операции", JOptionPane.QUESTION_MESSAGE);

        if (operationType != null) {
            switch (operationType) {
                case "1":
                    return operationService.add(first, second);
                case "2":
                    return operationService.subtraction(first, second);
                case "3":
                    return operationService.multiplication(first, second);
                case "4":
                    return operationService.division(first, second);
                default:
                    JOptionPane.showMessageDialog(this, "Неверный выбор операции", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }

        return null;
    }

    private void updateFunctionTable(TabulatedFunction function, JTable table) {
        // Обновляем таблицу на основе данных функции
        if (function != null) {
            Object[][] data = new Object[function.getCount()][2];

            for (int i = 0; i < function.getCount(); i++) {
                data[i][0] = function.getX(i);
                data[i][1] = function.getY(i);
            }

            String[] columnNames = {"X", "Y"};
            table.setModel(new DefaultTableModel(data, columnNames));

            // Блокируем редактирование колонки X
            table.getColumnModel().getColumn(0).setCellEditor(null);
        }
    }

    private TabulatedFunctionOperationService operationService;

    private JTable function1Table;
    private JTable function2Table;
    private JTable resultTable;

    public FunctionOperationController() {

    }
    public FunctionOperationController(ActionListener parent) {
        operationService = new TabulatedFunctionOperationService();

        setTitle("Tabulated Function Operation");
        setSize(800, 400);

        JPanel panel = new JPanel(new GridLayout(5, 3));

        function1Table = new JTable();
        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("X");
        model1.addColumn("Y");
        function1Table.setModel(model1);

        function2Table = new JTable();
        DefaultTableModel model2 = new DefaultTableModel();
        model2.addColumn("X");
        model2.addColumn("Y");
        function2Table.setModel(model2);

        resultTable = new JTable();
        DefaultTableModel model3 = new DefaultTableModel();
        model3.addColumn("X");
        model3.addColumn("Y");
        resultTable.setModel(model3);

        JButton createButton1 = new JButton("Create First Function");
        JButton loadButton1 = new JButton("Load First Function");
        JButton saveButton1 = new JButton("Save First Function");

        JButton createButton2 = new JButton("Create Second Function");
        JButton loadButton2 = new JButton("Load Second Function");
        JButton saveButton2 = new JButton("Save Second Function");

        /*createButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TabulatedFunctionController dialog = new TabulatedFunctionController();
                dialog.setVisible(true);
            }
        });

        loadButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TabulatedFunctionController dialog = new TabulatedFunctionController();
                dialog.setVisible(true);
            }
        });

        saveButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                        FunctionsIO.serialize(outputStream, (TabulatedFunction) function1Table);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

       createButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TabulatedFunctionController dialog = new TabulatedFunctionController();
                dialog.setVisible(true);
            }
        });

        loadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TabulatedFunctionController dialog = new TabulatedFunctionController();
                dialog.setVisible(true);
            }
        });

        saveButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                        FunctionsIO.serialize(outputStream, (TabulatedFunction) function2Table);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JButton addButton = new JButton("Add");
        JButton subtractButton = new JButton("Subtract");
        JButton multiplyButton = new JButton("Multiply");
        JButton divideButton = new JButton("Divide");
        JButton saveResultButton = new JButton("Save Result");

        GridLayout layout = new GridLayout(5,3, 1, 1);
        panel.add(new JScrollPane(function1Table));
        panel.add(new JScrollPane(function2Table));
        panel.add(new JScrollPane(resultTable));

        panel.add(createButton1);
        panel.add(createButton2);
        panel.add(addButton);

        panel.add(loadButton1);
        panel.add(loadButton2);
        panel.add(subtractButton);

        panel.add(saveButton1);
        panel.add(saveButton2);
        panel.add(multiplyButton);

        createButton1.setLayout(layout);
        loadButton1.setLayout(layout);
        saveButton1.setLayout(layout);

        createButton2.setLayout(layout);
        loadButton2.setLayout(layout);
        saveButton2.setLayout(layout);

        panel.add(divideButton);
        panel.add(saveResultButton);

        add(panel);
        setLocationRelativeTo((Component) parent); // îòîáðàæàåì îêíî ïî öåíòðó ýêðàíà
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FunctionOperationController::new);
    } */

}
