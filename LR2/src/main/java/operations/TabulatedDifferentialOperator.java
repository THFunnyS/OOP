package operations;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction> {
    protected TabulatedFunctionFactory factory;

    public TabulatedDifferentialOperator(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedDifferentialOperator() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionFactory getFactory() {
        return this.factory;
    }

    public TabulatedFunction devire(TabulatedFunction function) {
        Point[] pointsArr = TabulatedFunctionOperationService.asPoints(function);
        double[] xValue = new double[pointsArr.length];
        double[] yValue = new double[pointsArr.length];
        int i = 0;
        for (; i < (xValue.length - 1); ++i) {
            xValue[i] = pointsArr[i].x;
            yValue[i] = (pointsArr[i + 1].y - pointsArr[i].y) / (pointsArr[i + 1].x - pointsArr[i].x);
        }
        xValue[i] = pointsArr[i].x;
        yValue[i] = yValue[i - 1];
        return factory.create(xValue, yValue);
    }
}