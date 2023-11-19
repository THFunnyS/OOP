package concurrent;

import functions.Point;
import functions.TabulatedFunction;
import java.util.Iterator;

public class SynchronizedTabulatedFunction implements TabulatedFunction {
    private final TabulatedFunction delegator;

    public SynchronizedTabulatedFunction(TabulatedFunction delegator) {
        this.delegator = delegator;
    }

    public int getCount() {
        synchronized (delegator) {
            return delegator.getCount();
        }
    }

    public double getX(int index) {
        synchronized (delegator) {
            return delegator.getX(index);
        }
    }

    public double getY(int index) {
        synchronized (delegator) {
            return delegator.getY(index);
        }
    }

    public void setY(int index, double value) {
        synchronized (delegator) {
            delegator.setY(index, value);
        }
    }

    public int indexOfX(double value) {
        synchronized (delegator) {
            return delegator.indexOfX(value);
        }
    }

    public int indexOfY(double value) {
        synchronized (delegator) {
            return delegator.indexOfY(value);
        }
    }

    public double rightBound() {
        synchronized (delegator) {
            return delegator.rightBound();
        }
    }

    public double leftBound() {
        synchronized (delegator) {
            return delegator.leftBound();
        }
    }

    public Iterator<Point> iterator() {
        return null;
    }

    public double apply(double x) {
        return 0;
    }
}
