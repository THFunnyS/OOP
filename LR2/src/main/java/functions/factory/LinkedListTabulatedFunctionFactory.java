package functions.factory;

import functions.LinkedListTabulatedFunction;
import functions.MathFunction;

public class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory{
    public LinkedListTabulatedFunction create(double[] xValues, double[] yValues) {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }

    public LinkedListTabulatedFunction create(MathFunction function, double xFrom, double xTo, int count)
    {
        return new LinkedListTabulatedFunction(function, xFrom, xTo, count);
    }
}
