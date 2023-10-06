package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import junit.framework.Assert;

public class IdentityFunctionTest {
    MathFunction test=new IdentityFunction();
    @Test
    public void test(){
        Assert.assertEquals(15.0, test.apply(15));
    }
}