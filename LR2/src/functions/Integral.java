package functions;

public class Integral implements MathFunction{

    public double apply(double x){
        return (((Math.pow(x,3))/3)-((Math.pow(0,3))/3));
    }
}
