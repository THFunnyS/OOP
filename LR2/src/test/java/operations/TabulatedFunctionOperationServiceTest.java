package operations;

import static org.junit.jupiter.api.Assertions.*;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.Point;
import operations.TabulatedFunctionOperationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TabulatedFunctionOperationServiceTest {
    double[] xValue = {1, 1.5, 2, 2.5, 3};
    double[] yValue1 = {2, 3, 4, 5, 6};
    double[] yValue2 = {8, 9, 10, 11, 12};
    ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(xValue, yValue1);
    ArrayTabulatedFunction func2 = new ArrayTabulatedFunction(xValue,yValue2);
    LinkedListTabulatedFunction func3 = new LinkedListTabulatedFunction(xValue, yValue1);
    LinkedListTabulatedFunction func4 = new LinkedListTabulatedFunction(xValue,yValue2);
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
}