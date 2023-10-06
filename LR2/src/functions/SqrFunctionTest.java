package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import junit.framework.Assert;
public class SqrFunctionTest
{
    MathFunction powtest = new SqrFunction();
    @Test
        public void Powtest()
    {
        Assert.assertEquals(144.0, powtest.apply(12));
    }
}