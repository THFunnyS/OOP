package operations;

import concurrent.SynchronizedTabulatedFunction;
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

    public TabulatedFunction derive(TabulatedFunction function) {
        Point[] arrayOfPoints = TabulatedFunctionOperationService.asPoints(function);
        double[] xValue = new double[function.getCount()];
        double[] yValue = new double[function.getCount()];
        int i = 0;
        while (i < (xValue.length - 1)) {
            xValue[i] = arrayOfPoints[i].x;
            yValue[i] = (arrayOfPoints[i + 1].y - arrayOfPoints[i].y) / (arrayOfPoints[i + 1].x - arrayOfPoints[i].x);
            i++;
        }
        xValue[i] = arrayOfPoints[i].x;
        yValue[i] = yValue[i - 1];
        return factory.create(xValue, yValue);

    }

    public TabulatedFunction derive2(TabulatedFunction function) {
        double[] xValues = new double[function.getCount()];
        double[] yValues = new double[function.getCount()];
        double[] xValuesRes = new double[function.getCount()];
        double[] yValuesRes = new double[function.getCount()];

        for (int i = 0; i < function.getCount(); i++) {
            xValues[i] = function.getX(i);
            yValues[i] = function.getY(i);
        }

        for (int i = 0; i < function.getCount() - 1; i++) {
            xValuesRes[i] = xValues[i];
            yValuesRes[i] = (yValues[i + 1] - yValues[i]) / (xValues[i + 1] - xValues[i]);
        }
        xValuesRes[function.getCount() - 1] = xValues[function.getCount() - 1];
        yValuesRes[function.getCount() - 1] = yValues[function.getCount() - 2];
        return factory.create(xValuesRes, yValuesRes);
    }
    public SynchronizedTabulatedFunction deriveSynchronously (TabulatedFunction function){
        SynchronizedTabulatedFunction synchronizedFunction = (function instanceof SynchronizedTabulatedFunction) ?
                (SynchronizedTabulatedFunction) function :
                new SynchronizedTabulatedFunction(function);

        return synchronizedFunction.doSynchronously(func -> new SynchronizedTabulatedFunction(derive(func)));
    }
}