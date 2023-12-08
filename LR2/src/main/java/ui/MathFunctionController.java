package ui;

import functions.*;

import javax.swing.*;
import java.util.HashMap;

public class MathFunctionController extends JFrame {
    String[] funcList=new String[]{"Unit Function","Zero Function","Identity Function","Sqr Function","Integral Function","ArcSum Function"};
    private final JComboBox mathFuncList=new JComboBox(funcList);
    private final JTextField numOfPoints;
    private final JTextField startInterval;
    private final JTextField endInterval;
    HashMap<String, MathFunction> mathFunctionHashMap;
    public MathFunctionController() {
        super("Math Function Creator");
        setSize(400,300);
        mathFunctionHashMap=new HashMap<String,MathFunction>();
        mathFunctionHashMap.put("ArcSum Function",new ArcSumFunction());
        mathFunctionHashMap.put("Identity Function",new IdentityFunction());
        mathFunctionHashMap.put("Integral Function",new Integral());
        mathFunctionHashMap.put("Sqr Function",new SqrFunction());
        mathFunctionHashMap.put("Unit Function",new UnitFunction());
        mathFunctionHashMap.put("Zero Function",new ZeroFunction());


    }
}
