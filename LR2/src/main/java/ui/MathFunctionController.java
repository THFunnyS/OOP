package ui;

import functions.*;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MathFunctionController extends JDialog {
    String[] funcList = new String[]{"Unit Function", "Zero Function", "Identity Function", "Sqr Function", "Integral Function", "ArcSum Function"};
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
        super(parent, "Math Function Creator", true);
        arrIsType = arrType;

        setSize(470, 300);
        mathFunctionHashMap = new HashMap<String, MathFunction>();
        mathFunctionHashMap.put("ArcSum Function", new ArcSumFunction());
        mathFunctionHashMap.put("Identity Function", new IdentityFunction());
        mathFunctionHashMap.put("Integral Function", new Integral(0, 0, new ZeroFunction()));
        mathFunctionHashMap.put("Sqr Function", new SqrFunction());
        mathFunctionHashMap.put("Unit Function", new UnitFunction());
        mathFunctionHashMap.put("Zero Function", new ZeroFunction());

        this.setLayout(null);
        JLabel points = new JLabel("Number of points:");
        points.setBounds(5, 10, 250, 30);
        JLabel sInterval = new JLabel("Interval start:");
        sInterval.setBounds(5, 40, 250, 30);
        JLabel eInterval = new JLabel("Interval end:");
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

        JLabel pickFunc = new JLabel("Pick a function");
        pickFunc.setBounds(5, 100, 200, 30);
        mathFuncList.setBounds(250, 100, 200, 30);
        this.add(pickFunc);
        this.add(mathFuncList);

        JButton createFunction = new JButton("Create");
        createFunction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTabulatedFunction();
            }
        });
        createFunction.setBounds(5, 150, 440, 100);
        this.add(createFunction);
        setVisible(true);
    }

    private void createTabulatedFunction() {
        setStatus(true);
        funcName = mathFuncList.getSelectedItem().toString();
        MathFunction mathFunction = mathFunctionHashMap.get(mathFuncList.getSelectedItem());
        try {
            //String mathFunction = (String) mathFuncList.getSelectedItem();
            int count = Integer.parseInt(numOfPoints.getText());
            if (count < 2) {
                ExceptionCatcher exception = new ExceptionCatcher(this, "Wrong Input");
                //JOptionPane.showMessageDialog(this,"Wrong Input","Error",JOptionPane.ERROR_MESSAGE);
            } else {

                double intervalB = Double.parseDouble(startInterval.getText());
                double intervalE = Double.parseDouble(endInterval.getText());
                functionFactory = arrIsType ? new ArrayTabulatedFunctionFactory() : new LinkedListTabulatedFunctionFactory();
                function = functionFactory.create(mathFunction, intervalB, intervalE, count);
                System.out.println(function);
                dispose();
            }
        } catch (NumberFormatException e) {
            ExceptionCatcher exception = new ExceptionCatcher(this, "Wrong Input");
            //JOptionPane.showMessageDialog(this,"Wrong Input","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
