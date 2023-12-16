package ui;

import exceptions.ArrayIsNotSortedException;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import ui3.SettingsWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;

import static io.FunctionsIO.readTabulatedFunction;
import static io.FunctionsIO.writeTabulatedFunction;

public class MainWindow extends JFrame {
    private JFrame mainFrame;
    private JTable funcTable;
    private DefaultTableModel defaultFuncTableModel;
    private TabulatedFunctionFactory factory;
    private static LinkedList<TabulatedFunction> funcList = new LinkedList<TabulatedFunction>();

    public static LinkedList<TabulatedFunction> GetFunctionList() {
        return funcList;
    }

    private static class NonEditableTableModel extends DefaultTableModel {
        public NonEditableTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    public MainWindow() {
        mainFrame = new JFrame();
        mainFrame.setTitle("Основное окно");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(800, 700);
        mainFrame.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu settingMenu = new JMenu("Настройки");
        settingMenu.setFont(new Font("Times New Roman", Font.BOLD, 16));
        settingMenu.setHorizontalAlignment(JLabel.CENTER); //
        JMenu funcMenu = new JMenu("Создать функцию");
        funcMenu.setFont(new Font("Times New Roman", Font.BOLD, 16));
        funcMenu.setHorizontalAlignment(JLabel.CENTER); //
        JButton loadFunctionButton = new JButton("Загрузить функцию");
        loadFunctionButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        loadFunctionButton.setHorizontalAlignment(JLabel.CENTER); //
        JButton saveFunctionButton = new JButton("Сохранить функцию");
        saveFunctionButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        saveFunctionButton.setHorizontalAlignment(JLabel.CENTER); //
        JMenu operationMenu = new JMenu("Операции");
        operationMenu.setFont(new Font("Times New Roman", Font.BOLD, 16));
        operationMenu.setHorizontalAlignment(JLabel.CENTER); //
        JPanel sidePanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel(new GridLayout(1, 2));
        southPanel.add(loadFunctionButton);
        southPanel.add(saveFunctionButton);

        menuBar.add(settingMenu);
        menuBar.add(funcMenu);
        menuBar.add(operationMenu);

        JMenuItem openSettingsMenu = new JMenuItem("Открыть настройки");
        settingMenu.add(openSettingsMenu);
        JMenuItem tabulatedItem = new JMenuItem("Создать табличную функцию");
        JMenuItem mathFuncItem = new JMenuItem("Создать математическую функцию");
        funcMenu.add(tabulatedItem);
        funcMenu.add(mathFuncItem);

        JMenuItem mathOperations = new JMenuItem("Арифметические операции");
        JMenuItem difOperations = new JMenuItem("Производные операции");
        operationMenu.add(mathOperations);
        operationMenu.add(difOperations);

        defaultFuncTableModel = new NonEditableTableModel(new String[]{"Функция", "Тип"}, 0);
        funcTable = new JTable(defaultFuncTableModel);
        JScrollPane scrollPane = new JScrollPane(funcTable);

        //функционал кнопок
        tabulatedItem.addActionListener(e -> openCreatedTabulatedFunction());

        openSettingsMenu.addActionListener(e -> openSettingsWindow());

        mathFuncItem.addActionListener(e -> openCreateMathFunction());

        mathOperations.addActionListener(e -> openFunctionOperationController());

        difOperations.addActionListener(e -> openDifferentialController());

        mainFrame.getContentPane().add(BorderLayout.NORTH, menuBar);

        JPanel funcDescription = new JPanel(new BorderLayout());
        JTextArea funcDescriptionText = new JTextArea();
        funcDescriptionText.setEditable(false);
        funcDescription.add(funcDescriptionText, BorderLayout.CENTER);

        JScrollPane functionDescriptionScrollPane = new JScrollPane(funcDescriptionText);
        funcDescription.add(functionDescriptionScrollPane, BorderLayout.CENTER);
        funcDescriptionText.setRows(funcDescriptionText.getLineCount());

        int[] row = {-1};
        funcTable.getSelectionModel().addListSelectionListener(e -> {
            descriptionTable(funcDescriptionText, row);
        });
        funcTable.getSelectionModel().addListSelectionListener(e -> {
            int newRow = funcTable.getSelectedRow();
            if (newRow != -1 && newRow != row[0]) {
                row[0] = newRow;
                TabulatedFunction selectedFunction = (TabulatedFunction) funcList.get(row[0]);
                StringBuilder functionString = new StringBuilder();
                functionString.append(selectedFunction.getClass().getSimpleName());
                functionString.append(" size = ");
                functionString.append(selectedFunction.getCount());
                functionString.append("\n");
                for (int i = 0; i < selectedFunction.getCount(); i++) {
                    functionString.append("[");
                    functionString.append(selectedFunction.getX(i));
                    functionString.append("; ");
                    functionString.append(selectedFunction.getY(i));
                    functionString.append("]\n");
                }
                funcDescriptionText.setText(functionString.toString());
            }
        });

        funcDescription.setPreferredSize(new Dimension(WIDTH, 100));
        mainFrame.add(funcDescription, BorderLayout.SOUTH);
        mainFrame.getContentPane().add(BorderLayout.NORTH, menuBar);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        loadFunctionButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(mainFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!SettingsWindow.getTypeOfFabric()) {
                    factory = new ArrayTabulatedFunctionFactory();
                } else {
                    factory = new LinkedListTabulatedFunctionFactory();
                }
                try {
                    funcList.add(readTabulatedFunction(new BufferedReader(new FileReader(file.getAbsolutePath())), factory));
                    addFunctionToTable(readTabulatedFunction(new BufferedReader(new FileReader(file.getAbsolutePath())), factory), "Tabulated Function");

                } catch (IOException ex) {
                    ExceptionCatcher exception = new ExceptionCatcher(mainFrame, "Ошибка ввода/ввывода");
                } catch (ArrayIsNotSortedException ex) {
                    ExceptionCatcher exception = new ExceptionCatcher(mainFrame, "Таблица не отсортирована");
                }
            }
        });

        saveFunctionButton.addActionListener(e -> {
            int selectedRow = funcTable.getSelectedRow();
            if (selectedRow != -1) {
                TabulatedFunction selectedFunction = funcList.get(selectedRow);
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showSaveDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        writeTabulatedFunction(new BufferedWriter(new FileWriter(file.getAbsolutePath())), selectedFunction);
                    } catch (IOException ex) {
                        ExceptionCatcher exception = new ExceptionCatcher(mainFrame, "Ошибка ввода/ввывода");
                    }
                }
            } else {
                ExceptionCatcher exception = new ExceptionCatcher(mainFrame, "Функция не найдена");
            }
        });
        sidePanel.add(funcDescription, BorderLayout.NORTH);
        sidePanel.add(southPanel, BorderLayout.SOUTH);
        mainFrame.add(sidePanel, BorderLayout.SOUTH);
    }

    private void openSettingsWindow() {
        Settings settings = new Settings(mainFrame);
    }

    private void openCreatedTabulatedFunction() {
        TabulatedFunctionController creator = new TabulatedFunctionController(mainFrame, Settings.getFuncType());
        if (TabulatedFunctionController.getStatus()) {
            funcList.add(TabulatedFunctionController.getFunction());
            addFunctionToTable(TabulatedFunctionController.getFunction(), "Табличная функция");
        }
    }

    private void openCreateMathFunction() {
        MathFunctionController creator = new MathFunctionController(mainFrame, Settings.getFuncType());
        if (creator.getStatus()) {
            funcList.add(creator.getFunction());
            addFunctionToTable(creator.getFunction(), MathFunctionController.getFuncName());
        }
    }

    private void openFunctionOperationController() {
        FunctionOperationController creator = new FunctionOperationController(mainFrame, Settings.getFuncType(), MainWindow.GetFunctionList());
    }

    private void openDifferentialController() {
        DifferentialOperationController creator = new DifferentialOperationController(mainFrame, Settings.getFuncType(), MainWindow.GetFunctionList());
    }

    private void descriptionTable(JTextArea funcDescriptionText, int[] row) {
        int newRow = funcTable.getSelectedRow();
        if (newRow != -1 && newRow != row[0]) {
            row[0] = newRow;
            TabulatedFunction function = (TabulatedFunction) funcList.get(row[0]);
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
        defaultFuncTableModel.addRow(new String[]{name, function.getClass().getSimpleName()});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
