package functions;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import junit.framework.Assert;
public class ConstantFunctionTest {
    @Test
    public void apply() {
        UnitFunction unitFunction = new UnitFunction();
        Assert.assertEquals(1.0,unitFunction.apply(5.0));
        assertNotEquals(2.0,unitFunction.apply(5.0));
    }
    @Test
    public void apply2() {
        ZeroFunction zeroFunction = new ZeroFunction();
        Assert.assertEquals(0.0,zeroFunction.apply(5.0));
        assertNotEquals(2.0,zeroFunction.apply(5.0));
    }
    @Test
    public void apply3() {
        ConstantFunction constFunction = new ConstantFunction(3.0);
        Assert.assertEquals(3.0,constFunction.apply(5.0));
        assertNotEquals(2.0,constFunction.apply(5.0));
    }
}