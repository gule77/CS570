//author: Xianduo Zeng


package cs570;

public class Complexity {

//	 a method that has time complexity O(n^2).
	public static void method1(int n) {
		int counter = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.println("Operation " + counter);
				counter++;
			}
		}
	}
	
//	 a method that has time complexity O(n^3).
	public static void method2(int n) {
		int counter = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				for(int k = 0; k < n; k++) {
					System.out.println("Operation " + counter);
					counter++;
				}
			}
		}
	}
	
//	 a method that has time complexity O(log n).
	public static void method3(int n) {
		int counter = 0;
		for(int i = 1; i < n; i = i * 2) {
			System.out.println("Operation " + counter);
			counter++;
		}
	}
	
//	 a method that has time complexity O(n log n).
	public static void method4(int n) {
		int counter = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 1; j < n; j = j * 2) {
				System.out.println("Operation " + counter);
				counter++;
				}
			}
		}
	
//	a method that has time complexity O(log log n).
	public static void method5(int n) {
		int counter1 =0;
		int counter2=0;
		for(int i = 1; i < n; i = i * 2) {
			counter1++;
		}
		for(int j = 1; j < counter1; j = j * 2) {
			System.out.println("Operation " + counter2);
			counter2++;
		}
	}
	
//	a method that has time complexity O(2^n).
	static int counterFor6 = 0;
	public static void method6(int n) {
		
		if(n < 1) {
			return;
		}else {
			method6(n - 1); 
			method6(n - 1);
		}
		System.out.println("Operation " + counterFor6);
		counterFor6++;
	}//O(2^n-1)

	
	public static void main(String[] args) {
		System.out.println("test for method 1 complexity O(n^2)");
		method1(2);//0--3=4
		System.out.println("test for method 2 complexity O(n^3)");
		method2(2);//0--7=8
		System.out.println("test for method 3 complexity O(logn)");
		method3(64);//0--5=6
		System.out.println("test for method 4 complexity O(n*logn)");
		method4(8);//0--23=24
		System.out.println("test for method 5 complexity O(log logn)");
		method5(256);//0--2=3
		System.out.println("test for method 6 complexity O(2^n)");
		method6(10);//0--1022=1023

	}
}
