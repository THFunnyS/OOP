package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import junit.framework.Assert;
public class IntegralTest {
    MathFunction test2=new Integral();
    @Test
    public void test2(){
        Assert.assertEquals(9.0,test2.apply(3));
    }
}