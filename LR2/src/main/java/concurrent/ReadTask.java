package concurrent;

import functions.TabulatedFunction;

public class ReadTask implements Runnable {
    private final TabulatedFunction function;

    public ReadTask(TabulatedFunction function) {
        this.function = function;
    }

    @Override
    public void run() {
        for (int i = 0; i < function.getCount(); i++) {
            synchronized (function) {
                String str = String.format("After read: i = %d, x = %f, y = %f", i, function.getX(i), function.getY(i));
                System.out.println(str);
            }
        }
    }
}
