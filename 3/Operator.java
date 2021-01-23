package edu.stevens.cs570.assignments;

import java.util.HashMap;


public abstract class Operator {

	private static HashMap<String, Operator> operation;
	//Static code block, which will be loaded into memory when the class is loaded
	static {
		operation = new HashMap<>();
		operation.put("+", new AddOperator());
		operation.put("-", new SubtractOperator());
		operation.put("*", new MultiplyOperator());
		operation.put("/", new DivideOperator());
		operation.put("^", new PowerOperator());
		operation.put("(", new LeftParenthesis());
		operation.put(")", new RightParenthesis());
	}
	//Define abstract interface to get the priority of operators
	public abstract int priority();
	//Perform calculation operations on operators, and implement different operations according to specific operators
	public abstract double execute(double op1, double op2);

	public static boolean check(String token) {
		return operation.containsKey(token);
	}

	public static Operator getOperator(String token) {

		return operation.get(token);
	}
}
class RightParenthesis extends Operator {

	public int priority() {
		return 4;

	}

	public double execute(double op1, double op2) {
		return 0;
	}
}

class MultiplyOperator extends Operator {

	public int priority() {
		return 2;

	}

	public double execute(double op1, double op2) {
		return op1*op2;
	}
}

class LeftParenthesis extends Operator {

	public int priority() {
		return 0;
	}

	public double execute(double op1, double op2) {
		return 0;
	}
}

class DivideOperator extends Operator {

	public int priority() {
		return 2;

	}

	public double execute(double op1, double op2) {
		return op1/op2;
	}
}

class PowerOperator extends Operator {

	public int priority() {
		return 3;

	}

	public double execute(double op1, double op2) {

		double result = Math.pow(op1, op2);
		return result;
	}
}

class SubtractOperator extends Operator {

	public int priority() {
		return 1;

	}

	public double execute(double op1, double op2) {
		return op1-op2;
	}
}

class AddOperator extends Operator {

	public int priority() {
		return 1;

	}

	public double execute(double op1, double op2) {
		return op1+ op2;
	}

}