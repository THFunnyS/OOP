package functions;

import java.util.Objects;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Removable {

     static class Node {
         public Node next;
         public Node prev;
         public double x, y;

         Node(double x, double y) {
             this.x = x;
             this.y = y;
         }

         public String toString() {
             String strX = String.valueOf(x);
             String strY = String.valueOf(y);
             return ("(" + strX + ";" + strY + "), где " + strX + " и " + strY + " - абсцисса и ордината точек соответственно ");
         }

         public boolean equals(Object o) {
             if (this == o) return true;
             if (getClass() != o.getClass()) return false;
             Node node = (Node) o;
             return (Double.compare(x, node.x) == Double.compare(y, node.y) == Objects.equals(next, node.next) == Objects.equals(prev, node.prev));
         }

         public int hashCode() {
             int result = 31 * Double.hashCode(x);
             result = 31 * result + Double.hashCode(y);
             return result;
         }

         public Object clone() {
             Node cloneNode = new Node(x, y);
             cloneNode.prev = this.prev;
             cloneNode.next = this.next;
             return cloneNode;
         }
     }
    private Node head;
    protected int count;

    protected void addNode(double x, double y) {
        Node newNode = new Node(x, y);
        if (head == null) {
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            Node last = head.prev;
            last.next = newNode;
            head.prev = newNode;
            newNode.prev = last;
            newNode.next = head;
        }
        ++count;
    }

     LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        for (int i = 0; i < xValues.length; ++i)
            addNode(xValues[i], yValues[i]);
    }

     LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (xFrom > xTo) {
            double step = (xFrom - xTo) / (count - 1);
            for (int i = 0; i < count; ++i) {
                addNode((xTo + (i * step)), source.apply(xTo + (i * step)));
            }
        } else if (xTo > xFrom) {
            double step = (xTo - xFrom) / count;
            for (int i = 0; i < count; ++i) {
                addNode((xFrom + (i * step)), source.apply(xFrom + (i * step)));
            }

        } else {
            for (int i = 0; i < count; ++i) {
                addNode(xFrom, source.apply(xFrom));
            }
        }
    }

    public int getCount() {
        return count;
    }

    public double leftBound() {
        return head.x;
    }

    public double rightBound() {
        return head.prev.x;
    }

    Node getNode(int index) {
        if (index == 0) {
            return head;
        }
        Node temp = head;
        for (int i = 0; i <= index; i++) {
            temp = temp.next;
        }
        return temp.prev;
    }

    public double getX(int index) {
        Node temp = getNode(index);
        return temp.x;
    }

    public double getY(int index) {
        Node temp = getNode(index);
        return temp.y;
    }

    public void setY(int index, double value) {
        Node temp = getNode(index);
        temp.y = value;
    }

    public int indexOfX(double x) {
        int index = 0;
        int i = 0;
        Node temp = head;
        while ((temp.x != x) && (temp != head.prev)) {
            temp = temp.next;
            ++index;
        }
        if (temp == head.prev) {
            if (temp.x == x) {
                return index;
            } else return -1;
        } else return index;
    }

    public int indexOfY(double y) {
        int index = 0;
        int i = 0;
        Node temp = head;
        while ((temp.y != y) && (temp != head.prev)) {
            temp = temp.next;
            ++index;
        }
        if (temp == head.prev) {
            if (temp.y == y) {
                return index;
            } else return -1;
        } else return index;
    }

    protected int floorIndexOfX(double x) {
        int index = 0;
        if (head.x > x) {
            return 0;
        } else if (head.prev.x < x) {
            return count;
        } else {
            for (Node temp = head; ; temp = temp.next) {
                if (temp.x == x) {
                    return index;
                } else if (temp.x > x) {
                    return index - 1;
                }
                index++;
            }
        }
    }

    protected double interpolate(double x, int floorIndex) {
        if (head.next == head) {
            return head.y;
        } else {
            double leftX = getX(floorIndex - 1);
            double rightX = getX(floorIndex);
            double leftY = getY(floorIndex - 1);
            double rightY = getY(floorIndex);
            return interpolate(x, leftX, rightX, leftY, rightY);
        }

    }

    protected double extrapolateLeft(double x) {
        if (head.next == head) {
            return head.y;
        } else
            return (head.y + (((head.prev.y - head.y) / (head.prev.x - head.x)) * (x - head.x)));
    }

    protected double extrapolateRight(double x) {
        if (head.next == head) {
            return head.y;
        } else
            return (head.prev.prev.y + (((head.prev.y - head.prev.prev.y) / (head.prev.x - head.prev.prev.x)) * (x - head.prev.prev.x)));
    }

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        if (head.next == head) {
            return head.y;
        } else
            return (leftY + (((rightY - leftY) / (rightX - leftX)) * (x - leftX)));
    }

    public double apply(double x) {
        double result;
        if (x < head.x) {
            result = extrapolateLeft(x);
        } else if (x > head.prev.x) {
            result = extrapolateRight(x);
        } else {
            if (indexOfX(x) != -1) {
                result = getY(indexOfX(x));
            } else {
                result = interpolate(x, floorIndexOfX(x));
            }
        }
        return result;
    }

    public void remove(int index) {
        Node temp = head;
        Node prev = null;
        if (temp != null && temp.x == getX(index) && temp.y == getY(index)) {
            head = temp.next;
            return;
        }
        while (temp != null && temp.x != getX(index) && temp.y != getY(index)) {
            prev = temp;
            temp = temp.next;
        }
        if (temp == head) return;
        prev.next = temp.next;
        --count;
    }

    public String toString() {
        String result = "";
        Node temp = head;
        while (temp != head.prev) {
            String strX = String.valueOf(temp.x);
            String strY = String.valueOf(temp.y);
            result += "(" + strX + ";" + strY + ")" + " ";
            temp = temp.next;
        }
        result += "(" + String.valueOf(head.prev.x) + ";" + String.valueOf(head.prev.y) + ")";
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        LinkedListTabulatedFunction otherList = (LinkedListTabulatedFunction) o;
        if (otherList.count != count) return false;
        Node temp1 = head;
        Node temp2 = otherList.head;
        for (int i = 0; i < count; ++i) {
            if (temp1.x != temp2.x || temp1.y != temp2.y) return false;
        }
        return true;
    }

    public int hashCode() {
        int result = 31;
        Node temp = head;
        while (temp != head.prev) {
            result *= 31 + temp.hashCode();
            temp = temp.next;
        }
        return result;
    }

    public Object clone() {
        double[] arrX = new double[count];
        double[] arrY = new double[count];
        Node temp = head;
        for (int i = 0; i < count; ++i, temp = temp.next) {
            arrX[i] = temp.x;
            arrY[i] = temp.y;
        }
        LinkedListTabulatedFunction cloneList = new LinkedListTabulatedFunction(arrX, arrY);
        return cloneList;
    }
}
