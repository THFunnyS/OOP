package functions;

public class  CompositeFunction implements MathFunction{
    private double firstFunction(double x){
        return x*x;
    }

    private double secondFunction(double x){
        return Math.sqrt(x);
    }

    public double apply(double x){
        return secondFunction(firstFunction(x));
    }
}
