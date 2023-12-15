package ui;

import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;

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
        //super("Main Window");
        mainFrame = new JFrame();
        mainFrame.setTitle("Main Window");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(600, 500);
        mainFrame.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu settingMenu = new JMenu("Settings");
        JMenu funcMenu = new JMenu("Create function");
        JMenu operationMenu = new JMenu("Operations");

        menuBar.add(settingMenu);
        menuBar.add(funcMenu);
        menuBar.add(operationMenu);

        JMenuItem openSettingsMenu = new JMenuItem("Open settings");
        settingMenu.add(openSettingsMenu);
        JMenuItem tabulatedItem = new JMenuItem("Create Tabulated function");
        JMenuItem mathFuncItem = new JMenuItem("Create Math function");
        funcMenu.add(tabulatedItem);
        funcMenu.add(mathFuncItem);

        JMenuItem mathOperations = new JMenuItem("Math Operations");
        JMenuItem difOperations = new JMenuItem("Differentiation operation");
        operationMenu.add(mathOperations);
        operationMenu.add(difOperations);

        //setLocationRelativeTo(null);
        //defaultFuncTableModel.addColumn("Function");
        //defaultFuncTableModel.addColumn("Type");
        defaultFuncTableModel = new NonEditableTableModel(new String[]{"Function", "Type"}, 0);
        funcTable = new JTable(defaultFuncTableModel);

        JScrollPane scrollPane = new JScrollPane(funcTable);

        //функционал кнопок
        tabulatedItem.addActionListener(e -> openCreatedTabulatedFunction());

        openSettingsMenu.addActionListener(e -> openSettingsWindow());

        mathFuncItem.addActionListener(e -> openCreateMathFunction());

        mathOperations.addActionListener(e -> openFunctionOperationController());

        difOperations.addActionListener(e-> openDifferentialController());

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

        funcDescription.setPreferredSize(new Dimension(WIDTH, 100));

        mainFrame.getContentPane().add(BorderLayout.NORTH, menuBar);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        mainFrame.add(funcDescription, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    private void openSettingsWindow() {
        Settings settings = new Settings(mainFrame);
    }

    private void openCreatedTabulatedFunction() {
        TabulatedFunctionController creator = new TabulatedFunctionController(mainFrame, Settings.getFuncType());
        if (TabulatedFunctionController.getStatus()) {
            funcList.add(TabulatedFunctionController.getFunction());
            addFunctionToTable(TabulatedFunctionController.getFunction(), "Tabulated function");
        }
    }

    private void openCreateMathFunction() {
        MathFunctionController creator = new MathFunctionController(mainFrame, Settings.getFuncType());
        if (creator.getStatus()) {
            funcList.add(creator.getFunction());
            addFunctionToTable(creator.getFunction(), MathFunctionController.getFuncName());
        }
    }

    private void openFunctionOperationController(){
        FunctionOperationController creator=new FunctionOperationController(mainFrame,Settings.getFuncType(),MainWindow.GetFunctionList());
    }

    private void openDifferentialController(){
        DifferentialOperationController creator=new DifferentialOperationController(mainFrame,Settings.getFuncType(),MainWindow.GetFunctionList());
    }

    private void descriptionTable(JTextArea funcDescriptionText, int[] row) {
        int newRow = funcTable.getSelectedRow();
        if (newRow != -1 && newRow != row[0]) {
            row[0] = newRow;
            TabulatedFunction function = (TabulatedFunction) funcList.get(row[0]);
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
        defaultFuncTableModel.addRow(new String[]{name, function.getClass().getSimpleName()});
    }

    //private void addRow(TabulatedFunction function,)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
