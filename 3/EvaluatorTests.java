package edu.stevens.cs570.assignments;

public class EvaluatorTests {
	public static void main(String [] args) {
		try {
			ExpressionEvaluator ex =new ExpressionEvaluator("-2+  ((-3*1)**5-5/6)");
			System.out.println(ex.evaluate());
		} catch (IllegalArugumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
