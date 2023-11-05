package operations;

import functions.MathFunction;

public interface DifferentialOperator <T extends MathFunction> {
    T devire(T function);
}
