package functions.factory;

import functions.ArrayTabulatedFunction;
import functions.MathFunction;
import functions.TabulatedFunction;
public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory {
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new ArrayTabulatedFunction(xValues, yValues);
    }

    public ArrayTabulatedFunction create(MathFunction function, double xFrom, double xTo, int count)
    {
        return new ArrayTabulatedFunction(function, xFrom, xTo, count);
    }
}