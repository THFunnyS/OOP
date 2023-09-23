import myfirstpackage.*;
public class MyFirstClass {
	public static void main(String[] args) {
		MySecondClass summ=new MySecondClass();
		System.out.print("Сложение");
		System.out.println();
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				summ.setA(i);
				summ.setB(j);
				System.out.print(summ.sum(i,j));
				System.out.print(" ");
			}
			System.out.println();	
		}

	}
}
