package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import junit.framework.Assert;
public class IntegralTest {
    private static final double DELTA = 1E-2;
    @Test
    public void IntegralTest() {
        Integral inte = new Integral(0.0, 2.0, new MathFunction() {
            @Override
            public double apply(double x) {
                return Math.pow(x, 2);
            }
        });
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);
        MathFunction result = inte.andThen(function);
        double expected = 2.6666666699994988;
        assertEquals(expected, result.apply(2), DELTA);
    }
}