package ui;

import functions.*;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MathFunctionController extends JDialog {
    String[] funcList = new String[]{"Единичная функция", "Нулевая функция", "Тождественная функция", "Квадратичная функция", "Интегральная функция", "Функция arcsum"};
    private final JComboBox mathFuncList = new JComboBox(funcList);
    private final JTextField numOfPoints;
    private final JTextField startInterval;
    private final JTextField endInterval;
    private static TabulatedFunction function;
    private static TabulatedFunctionFactory functionFactory;
    private boolean arrIsType;
    private static boolean status;

    private static String funcName;

    public static TabulatedFunction getFunction() {
        return function;
    }

    public static boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static String getFuncName() {
        return funcName;
    }

    HashMap<String, MathFunction> mathFunctionHashMap;

    public MathFunctionController(JFrame parent, boolean arrType) {
        super(parent, "Математическая функция", true);
        arrIsType = arrType;

        setSize(470, 250);
        mathFunctionHashMap = new HashMap<String, MathFunction>();
        mathFunctionHashMap.put("Функция arcsum", new ArcSumFunction());
        mathFunctionHashMap.put("Тождественная функция", new IdentityFunction());
        mathFunctionHashMap.put("Интегральная функция", new Integral(0, 0, new ZeroFunction()));
        mathFunctionHashMap.put("Квадратичная функция", new SqrFunction());
        mathFunctionHashMap.put("Единичная функция", new UnitFunction());
        mathFunctionHashMap.put("Нулевая функция", new ZeroFunction());

        this.setLayout(null);
        JLabel points = new JLabel("Количество точек:");
        points.setFont(new Font("Times New Roman", Font.BOLD, 16));
        points.setBounds(5, 10, 250, 30);
        JLabel sInterval = new JLabel("Начало интервала:");
        sInterval.setFont(new Font("Times New Roman", Font.BOLD, 16));
        sInterval.setBounds(5, 40, 250, 30);
        JLabel eInterval = new JLabel("Конец интервала:");
        eInterval.setFont(new Font("Times New Roman", Font.BOLD, 16));
        eInterval.setBounds(5, 70, 150, 30);
        numOfPoints = new JTextField(10);
        numOfPoints.setBounds(250, 10, 200, 20);
        startInterval = new JTextField(10);
        startInterval.setBounds(250, 40, 200, 20);
        endInterval = new JTextField(10);
        endInterval.setBounds(250, 70, 200, 20);
        this.add(points);
        this.add(sInterval);
        this.add(eInterval);
        this.add(numOfPoints);
        this.add(startInterval);
        this.add(endInterval);

        JLabel pickFunc = new JLabel("Выберите функцию");
        pickFunc.setFont(new Font("Times New Roman", Font.BOLD, 16));
        pickFunc.setBounds(5, 100, 200, 30);
        mathFuncList.setBounds(250, 100, 200, 30);
        this.add(pickFunc);
        this.add(mathFuncList);

        JButton createFunction = new JButton("Создать");
        createFunction.setFont(new Font("Times New Roman", Font.BOLD, 16));
        createFunction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTabulatedFunction();
            }
        });
        createFunction.setBounds(100, 150, 220, 50);
        this.add(createFunction);
        setVisible(true);
    }

    private void createTabulatedFunction() {
        setStatus(true);
        funcName = mathFuncList.getSelectedItem().toString();
        MathFunction mathFunction = mathFunctionHashMap.get(mathFuncList.getSelectedItem());
        try {
            int count = Integer.parseInt(numOfPoints.getText());
            if (count < 2) {
                ExceptionCatcher exception = new ExceptionCatcher(this, "Неправильный ввод");
            } else {
                double intervalB = Double.parseDouble(startInterval.getText());
                double intervalE = Double.parseDouble(endInterval.getText());
                functionFactory = arrIsType ? new ArrayTabulatedFunctionFactory() : new LinkedListTabulatedFunctionFactory();
                function = functionFactory.create(mathFunction, intervalB, intervalE, count);
                System.out.println(function);
                dispose();
            }
        } catch (NumberFormatException e) {
            ExceptionCatcher exception = new ExceptionCatcher(this, "Неправильный ввод");
        }
    }
}
