package functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    protected int count;
    abstract protected int floorIndexOfX(double x);
    abstract protected double extrapolateLeft(double x);
    abstract protected double extrapolateRight(double x);

    abstract protected double interpolate(double x, int floorIndex);
    abstract protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY);
    {
        return leftY + (rightY - leftY) / (rightX - leftX) * (x - leftX);
    }
    public double apply(double x){
        double interpolate;
        if(x<getX(0)) interpolate = this.extrapolateLeft(x);
        else if(x>getX(count-1))interpolate = this.extrapolateRight(x);
        else{
            int indexOfX = indexOfX(x);
            if(indexOfX>0) interpolate = this.getY(indexOfX);
            else interpolate = this.interpolate(x, floorIndexOfX(x));
        }
        return interpolate;
    }

}
