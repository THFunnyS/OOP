package functions;

import java.util.Arrays;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction {
    private final double[] xValues;
    private final double[] yValues;

    ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        count = xValues.length;
    }

    ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        xValues = new double[count];
        yValues = new double[count];
        this.count = count;
        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }
        double step = (xFrom + xTo) / (count - 1);
        double xCordinate = xFrom;

        for (int i = 0; i < count; i++) {
            xValues[i] = xCordinate;
            yValues[i] = source.apply(xCordinate);
            xCordinate += step;
        }
    }

    protected int floorIndexOfX(double x) {
        if (xValues[0] > x) return 0;
        else if (xValues[count - 1] < x) return count;
        else {
            for (int index = 0; ; index++) {
                if (xValues[index] == x) return index;
                else if (xValues[index] > x) return index - 1;
            }
        }
    }

    protected double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return yValues[0];
        } else {
            double leftX = getX(floorIndex - 1);
            double rightX = getX(floorIndex);
            double leftY = getY(floorIndex - 1);
            double rightY = getY(floorIndex);
            return interpolate(x, leftX, rightX, leftY, rightY);
        }

    }

    protected  double extrapolateLeft(double x) {
        if (count == 1)
        {
            return yValues[0];
        }
        else
        {
            return (yValues[0] + (((yValues[1] - yValues[0]) / (xValues[1] - xValues[0])) * (x - xValues[0])));
        }
    }

    protected double extrapolateRight(double x)
    {
        if (count == 1)
        {
            return yValues[0];
        }
        else
        {
            return (yValues[count - 2] + (((yValues[count - 1] - yValues[count - 2]) / (xValues[count - 1] - xValues[count - 2])) * (x - xValues[count - 2])));
        }
    }
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY)
    {
        if (count == 1)
        {
            return yValues[0];
        }
        else
        {
            return (leftY + (((rightY - leftY) / (rightX - leftX)) * (x - leftX)));
        }
    }
    public double apply(double x)
    {
        double result;
        if (x < xValues[0])
        {
            result = extrapolateLeft(x);
        } else if (x > xValues[count - 1])
        {
            result = extrapolateRight(x);
        } else {
            if (indexOfX(x) != -1)
            {
                result = getY(indexOfX(x));
            } else
            {
                result = interpolate(x, floorIndexOfX(x));
            }
        }
        return result;
    }
    public int getCount()
    {
        return count;
    }
    public double getX(int index)
    {
        return xValues[index];
    }
    public double getY(int index)
    {
        return yValues[index];
    }
    public void setY(int index, double value)
    {
        yValues[index] = value;
    }
    public int indexOfX(double x)
    {
        int index = 0;
        while (index <= count - 1)
        {
            if (xValues[index] == x) return index;
            else index++;
        }
        return -1;
    }
    public  int indexOfY(double y)
    {
        int index = 0;
        while (index <= count - 1)
        {
            if (yValues[index] == y) return index;
            else index++;
        }
        return -1;
    }
    public double leftBound()
    {
        return xValues[0];
    }
    public double rightBound()
    {
        return xValues[count - 1];
    }
}