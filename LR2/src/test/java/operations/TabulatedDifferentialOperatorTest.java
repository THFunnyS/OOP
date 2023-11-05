package operations;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.LinkedListTabulatedFunctionFactory;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TabulatedDifferentialOperatorTest {

    @Test
    public void setFactoryTest() {
        LinkedListTabulatedFunctionFactory fact = new LinkedListTabulatedFunctionFactory();
        TabulatedDifferentialOperator operation = new TabulatedDifferentialOperator(fact);
        operation.setFactory(fact);
        assertEquals(operation.getFactory().getClass(), fact.getClass());
    }

    @Test
    public void devireTest() {
        double[] xValues = {2, 4, 6, 8};
        double[] yValues = {1, 3, 5, 7};
        LinkedListTabulatedFunctionFactory fact = new LinkedListTabulatedFunctionFactory();
        TabulatedDifferentialOperator operation = new TabulatedDifferentialOperator(fact);

        ArrayTabulatedFunction array = new ArrayTabulatedFunction(xValues, yValues);
        TabulatedFunction func = operation.devire(array);
        assertEquals(1, func.getY(0));
        assertEquals(1, func.getY(1));
        assertEquals(1, func.getY(2));
        assertEquals(1, func.getY(3));
    }

    @Test
    public void devireTest2() {
        double[] xValues = {1, 2, 3, 4};
        double[] yValues = {5, 6, 7, 8};
        LinkedListTabulatedFunctionFactory fact = new LinkedListTabulatedFunctionFactory();
        TabulatedDifferentialOperator operation = new TabulatedDifferentialOperator(fact);
        LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(xValues, yValues);
        TabulatedFunction func = operation.devire(list);
        assertEquals(1, func.getY(0));
        assertEquals(1, func.getY(1));
        assertEquals(1, func.getY(2));
        assertEquals(1, func.getY(3));
    }
}