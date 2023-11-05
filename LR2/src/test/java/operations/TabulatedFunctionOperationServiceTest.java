package operations;

import static org.junit.jupiter.api.Assertions.*;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.Point;
import functions.TabulatedFunction;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import operations.TabulatedFunctionOperationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TabulatedFunctionOperationServiceTest {
    double[] xValue = {1, 1.5, 2, 2.5, 3};
    double[] yValue1 = {2, 3, 4, 5, 6};
    double[] yValue2 = {8, 9, 10, 11, 12};
    ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(xValue, yValue1);
    ArrayTabulatedFunction func2 = new ArrayTabulatedFunction(xValue, yValue2);
    LinkedListTabulatedFunction func3 = new LinkedListTabulatedFunction(xValue, yValue1);
    LinkedListTabulatedFunction func4 = new LinkedListTabulatedFunction(xValue, yValue2);
    TabulatedFunctionOperationService tabulatedFunctionOperationService = new TabulatedFunctionOperationService();

    @Test
    public void asPointsTest() {
        Point[] arrayOfPoints = TabulatedFunctionOperationService.asPoints(func1);
        int i = 0;
        for (Point point : arrayOfPoints) {
            assertEquals(point.x, xValue[i]);
            assertEquals(point.y, yValue1[i]);
            ++i;
        }
    }

    TabulatedFunctionFactory factory1 = new LinkedListTabulatedFunctionFactory();
    TabulatedFunctionOperationService operation1 = new TabulatedFunctionOperationService(factory1);
    TabulatedFunctionOperationService operation2 = new TabulatedFunctionOperationService();

    @Test
    public void addTest() {

        TabulatedFunction result1 = operation1.add(func1, func2);
        for (int i = 0; i < result1.getCount(); i++) {
            assertEquals(yValue1[i] + yValue2[i], result1.getY(i));
        }

        TabulatedFunction result2 = operation2.add(func3, func4);
        for (int i = 0; i < result2.getCount(); i++) {
            assertEquals(yValue1[i] + yValue2[i], result2.getY(i));
        }

        TabulatedFunction result3 = operation2.add(func1, func3);
        for (int i = 0; i < result3.getCount(); i++) {
            assertEquals(yValue1[i] + yValue2[i], result3.getY(i));
        }
    }

    @Test
    void subtractionTest() {
        TabulatedFunction result1 = operation1.subtraction(func1, func2);
        for (int i = 0; i < result1.getCount(); i++) {
            assertEquals(yValue1[i] - yValue2[i], result1.getY(i));
        }

        TabulatedFunction result2 = operation2.subtraction(func4, func3);
        for (int i = 0; i < result2.getCount(); i++) {
            assertEquals(yValue1[i] - yValue2[i], result2.getY(i));
        }

        TabulatedFunction result3 = operation2.subtraction(func1, func3);
        for (int i = 0; i < result3.getCount(); i++) {
            assertEquals(yValue1[i] - yValue2[i], result3.getY(i));
        }
    }
}