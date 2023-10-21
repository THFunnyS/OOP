package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import junit.framework.Assert;
    public class ArrayTabulatedFunctionTest {
        double[] xValue = {2, 2.5, 3, 3.5, 4};
        double[] yValue = {3, 4, 5, 6, 7};
        double[] xValue2 = {6};
        double[] yValue2 = {3};
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(xValue, yValue);
        ArrayTabulatedFunction arrayTabulatedFunction2 = new ArrayTabulatedFunction(xValue2, yValue2);

        @Test
        public void getCount() {
            assertEquals(5, arrayTabulatedFunction.getCount());
            assertNotEquals(0, arrayTabulatedFunction.getCount());
        }

        @Test
        public void getX() {
            assertEquals(2.5, arrayTabulatedFunction.getX(1));
            assertNotEquals(0, arrayTabulatedFunction.getX(1));
        }

        @Test
        public void getY() {
            assertEquals(4, arrayTabulatedFunction.getY(1));
            assertNotEquals(0, arrayTabulatedFunction.getY(2));
        }

        @Test
        public void setY() {
            arrayTabulatedFunction.setY(0, 7);
            assertEquals(7, arrayTabulatedFunction.getY(0));
            assertNotEquals(0, arrayTabulatedFunction.getY(0));
        }

        @Test
        public void leftBound() {
            assertEquals(2, arrayTabulatedFunction.leftBound());
            assertNotEquals(0, arrayTabulatedFunction.leftBound());
        }

        @Test
        public void rightBound() {
            assertEquals(4, arrayTabulatedFunction.rightBound());
            assertNotEquals(0, arrayTabulatedFunction.rightBound());
        }

        @Test
        public void indexOfX() {
            assertEquals(1, arrayTabulatedFunction.indexOfX(2.5));
            assertNotEquals(0, arrayTabulatedFunction.indexOfX(2.5));

            assertEquals(-1, arrayTabulatedFunction.indexOfX(10));
            assertNotEquals(0, arrayTabulatedFunction.indexOfX(10));
        }

        @Test
        public void indexOfY() {
            assertEquals(1, arrayTabulatedFunction.indexOfY(4));
            assertNotEquals(5, arrayTabulatedFunction.indexOfY(3));

            assertEquals(-1, arrayTabulatedFunction.indexOfY(10));
            assertNotEquals(0, arrayTabulatedFunction.indexOfY(10));
        }

        @Test
        public void floorIndexOfX() {
            assertEquals(0, arrayTabulatedFunction.floorIndexOfX(-5));
            assertNotEquals(2, arrayTabulatedFunction.floorIndexOfX(-5));

            assertEquals(5, arrayTabulatedFunction.floorIndexOfX(10));
            assertNotEquals(0, arrayTabulatedFunction.floorIndexOfX(10));

            assertEquals(0, arrayTabulatedFunction.floorIndexOfX(2));
            assertNotEquals(3, arrayTabulatedFunction.floorIndexOfX(2));

            assertEquals(1, arrayTabulatedFunction.floorIndexOfX(2.7));
            assertNotEquals(0, arrayTabulatedFunction.floorIndexOfX(2.7));
        }

        @Test
        public void interpolate() {
            assertEquals(2.4, arrayTabulatedFunction.interpolate(1.7, 2));
            assertNotEquals(0, arrayTabulatedFunction.interpolate(1.7, 2));

            assertEquals(3, arrayTabulatedFunction2.interpolate(1.7, 1));
            assertNotEquals(0, arrayTabulatedFunction2.interpolate(1.7, 1));
        }

        @Test
        public void extrapolateLeft() {
            assertEquals(-11, arrayTabulatedFunction.extrapolateLeft(-5));
            assertNotEquals(0, arrayTabulatedFunction.extrapolateLeft(-5));

            assertEquals(3, arrayTabulatedFunction2.extrapolateLeft(1));
            assertNotEquals(0, arrayTabulatedFunction2.extrapolateLeft(1));
        }

        @Test
        public void extrapolateRight() {
            assertEquals(19, arrayTabulatedFunction.extrapolateRight(10));
            assertNotEquals(0, arrayTabulatedFunction.extrapolateRight(10));

            assertEquals(3, arrayTabulatedFunction2.extrapolateRight(1));
            assertNotEquals(0, arrayTabulatedFunction2.extrapolateRight(1));
        }

        @Test
        public void InsertTest() {
            arrayTabulatedFunction.insert(3, 5);
            assertEquals(5, arrayTabulatedFunction.getY(2));
            arrayTabulatedFunction2.insert(3.5, 1);
            assertEquals(3.5, arrayTabulatedFunction2.getX(1));
        }
        @Test
        public void toStringTest() {
            assertEquals("(2.0;3.0) (2.5;4.0) (3.0;5.0) (3.5;6.0) (4.0;7.0) ", arrayTabulatedFunction.toString());
            assertNotEquals("(0;0)", arrayTabulatedFunction.toString());

            assertEquals("(6.0;3.0) ", arrayTabulatedFunction2.toString());
            assertNotEquals("(0;0)", arrayTabulatedFunction2.toString());
        }

        @Test
        public void equalsTest() {
            ArrayTabulatedFunction arrayTabulatedFunctionTest = new ArrayTabulatedFunction(xValue, yValue);
            assertTrue(arrayTabulatedFunction.equals(arrayTabulatedFunctionTest));
        }

        @Test
        public void cloneTest() {
            Object arrayTabulatedFunctionTest = arrayTabulatedFunction.clone();
            assertTrue(arrayTabulatedFunction.equals(arrayTabulatedFunctionTest));
        }

        @Test
        public void hashCodeTest() {
            ArrayTabulatedFunction arrayTabulatedFunction3 = new ArrayTabulatedFunction(xValue, yValue);
            assertEquals(arrayTabulatedFunction.hashCode(), arrayTabulatedFunction3.hashCode());
            assertNotEquals(arrayTabulatedFunction.hashCode(), arrayTabulatedFunction2.hashCode());
        }
    }
