package functions.factory;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;

public class LinkedListTabulatedFunctionFactory {
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }
}
