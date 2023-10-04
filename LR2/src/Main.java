import functions.*;
public class Main {
    public static void main(String args[]){
        IdentityFunction func=new IdentityFunction();
        double y=func.apply(12.5);
        System.out.print(y);
        System.out.println();

        CompositeFunction func2=new CompositeFunction();
        double z= func2.apply(51);
        System.out.print(z);
    }
}
