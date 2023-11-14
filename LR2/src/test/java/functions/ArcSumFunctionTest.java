package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ArcSumFunctionTest {
    @Test
    void apply() {
        ArcSumFunction a = new ArcSumFunction();
        assertEquals(a.apply(0.5), Math.PI/2, 0.00001);
    }
}