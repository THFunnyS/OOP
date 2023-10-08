package functions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import junit.framework.Assert;

public class LinkedListTabulatedFunctionTest {
    double[] arr1={1,2,3};
    double[] arr2={4,5,6};
    LinkedListTabulatedFunction testLink=new LinkedListTabulatedFunction(arr1,arr2);
    @Test
    public void testgetCount(){
        Assert.assertEquals(3,testLink.getCount());
    }
    @Test
    public void testrightBound(){
        Assert.assertEquals(3.0,testLink.rightBound());
    }
    @Test
    public void testindexOfX(){
        Assert.assertEquals(1,testLink.indexOfX(2));
    }
    @Test
    public void testindexOfY(){
        Assert.assertEquals(2,testLink.indexOfY(6));
    }
    @Test
    public void testGetX() {
        Assert.assertEquals(3.0, testLink.getX(2));
        Assert.assertEquals(1.0,testLink.getX(0));
    }

    @Test
    public void testGetY(){
        Assert.assertEquals(4.0,testLink.getY(0));
        Assert.assertEquals(6.0,testLink.getY(2));
    }
    @Test
    public void testAddNode() {
        testLink.addNode(7.5, 4);
        assertEquals(4, testLink.getCount());
        assertEquals(7.5, testLink.getX(3));
        assertEquals(4, testLink.getY(3));
    }

    @Test
    public void testRemove(){
        testLink.remove(1);
        assertEquals(3,testLink.getX(1));
        assertEquals(6,testLink.getY(1));
    }

    @Test
    public void testRemove2(){
        testLink.remove(0);
        assertEquals(2,testLink.getX(0));
        assertEquals(5,testLink.getY(0));
    }
}
