package operations;

import exceptions.InconsistentFunctionsException;
import functions.Point;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

public class TabulatedFunctionOperationService {
    protected TabulatedFunctionFactory factory;

    public TabulatedFunctionOperationService(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionOperationService() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionFactory getFactory() {
        return this.factory;
    }

    TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BiOperation operation) {

        if (a.getCount() != b.getCount()) throw new InconsistentFunctionsException();
        else {
            Point[] arraysA = asPoints(a);
            Point[] arraysB = asPoints(b);

            double[] xValue = new double[a.getCount()];
            double[] yValue = new double[a.getCount()];

            for (int i = 0; i < a.getCount(); i++) {
                if (arraysA[i].x == arraysB[i].x) xValue[i] = arraysA[i].x;
                else throw new InconsistentFunctionsException();
                yValue[i] = operation.apply(arraysA[i].y, arraysB[i].y);
            }
            return factory.create(xValue, yValue);
        }
    }

    public static Point[] asPoints(TabulatedFunction tabulatedFunction) {

        Point[] asPointsArray = new Point[tabulatedFunction.getCount()];
        int i = 0;
        for (Point point : tabulatedFunction) {
            asPointsArray[i] = point;
            ++i;
        }
        return asPointsArray;
    }

    public TabulatedFunction add(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, Double::sum);
    }

    public TabulatedFunction subtraction(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (u, v) -> u - v);
    }

    private interface BiOperation {
        double apply(double u, double v);
    }
}