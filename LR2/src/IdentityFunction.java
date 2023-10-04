public class IdentityFunction implements MathFunction{
    double x;
    IdentityFunction(double x){
        this.x=x;
    }

    public void print(){
        System.out.print(x);
    }
}
