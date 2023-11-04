package functions.factory;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;

public class ArrayTabulatedFunctionFactory {
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new ArrayTabulatedFunction(xValues, yValues);
    }
}
