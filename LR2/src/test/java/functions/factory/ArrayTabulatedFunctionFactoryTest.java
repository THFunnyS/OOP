package functions.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import junit.framework.Assert;
import static org.junit.jupiter.api.Assertions.assertThrows;
import functions.ArrayTabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;

public class ArrayTabulatedFunctionFactoryTest {
    double[] xValues = {1, 2, 3, 7};
    double[] yValues = {4, 5, 6, 8};
    ArrayTabulatedFunction list = new ArrayTabulatedFunction(xValues, yValues);

    @Test
    public void ListFactoryTest() {
        ArrayTabulatedFunctionFactory listFactory = new ArrayTabulatedFunctionFactory();
        ArrayTabulatedFunctionFactory listFactory2 = new ArrayTabulatedFunctionFactory();
        double[] xValues2 = {3.4, 12, 9};
        double[] yValues2 = {1.76, 4, 5.1};
        boolean test = list.getClass() == (listFactory.create(xValues, yValues)).getClass();
        boolean test2 = list.getClass() == (listFactory2.create(xValues2, yValues2)).getClass();
        assertTrue(test);
        assertTrue(test2);
    }
}