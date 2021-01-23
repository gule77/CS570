package edu.stevens.cs570.assignments;

import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class ExpressionEvaluator {
	private Stack<Double> numbers;
	private Stack<Operator> operatorStack;
	private StringTokenizer tokenizer;
	private static final String DELIMITERS = "+-*^/() ";
	//Determine whether are valid
	private static boolean isValid( String token ) throws IllegalArugumentException{
	      try {
	          Float.parseFloat(token);
	          return true; 
	      } catch(NumberFormatException e) {
	         return false;
	      }
	  } 
	public ExpressionEvaluator(String s) throws IllegalArugumentException {
		//Replace the cube root in the string. Using regular to parenthesize negative numbers in expressions
		s=s.replace("**", "^");
		s=s.replaceAll("([^\\d])(-\\d+)", "($10$2)");
		s=s.replaceAll("^(-\\d+)", "(0$1)");
		System.out.println(s);
		numbers = new Stack<>();
		operatorStack = new Stack<>();
		//Split according to the specified format
		this.tokenizer = new StringTokenizer(s, DELIMITERS, true);
		String token;
		while (this.tokenizer.hasMoreTokens()) {
			if (!(token = this.tokenizer.nextToken()).equals("")) {
				//Ignore spaces in expressions
				if(token.equals(" ")) {
					continue;
				}
				if (isValid(token)) {
					//If it is a valid character added to the stack
					 try {
						 numbers.push(Double.parseDouble(token));
				      } catch(NumberFormatException e) {
				    	  throw new IllegalArugumentException();
				      }
					
				} else {
					//Check whether the operator is valid
					if (!Operator.check(token)) {
						throw new IllegalArugumentException();
					} else if (")".equals(token)) { //If it is equal to the closing bracket, the current expression can be evaluated.
						//Take values from the stack according to the priority of operators and calculate them. Here, encapsulate the operators. For details, see operator.
						while (operatorStack.peek().priority() != 0) {
							Operator oldOpr = operatorStack.pop();
							double op2 = numbers.pop();
							double op1 = numbers.pop();
							//Put the calculated results into the stack
							numbers.push(oldOpr.execute(op1, op2));
						}
						operatorStack.pop();
						//If it's left parenthesis, stack
					} else if ("(".equals(token)) {
						operatorStack.push(new LeftParenthesis());

					} else {
						//If it is neither a left bracket nor a right bracket, evaluate the value of the current expression
						Operator newOperator = Operator.getOperator(token);
						//Continue calculation if stack is not empty
						if (!operatorStack.empty()) {
							//Determine whether the operator stack is empty, and determine the priority. Take out for calculation and stack the results
							while ((operatorStack.peek().priority() >= newOperator.priority())
									&& (!operatorStack.isEmpty()) && (numbers.size() >= 2)) {
								Operator oldOpr = operatorStack.pop();
								double op2 = numbers.pop();
								double op1 = numbers.pop();
								numbers.push(oldOpr.execute(op1, op2));
							}
						}
						operatorStack.push(newOperator);
					}
				}
			}
		}
	}
	//The process of calculation is the operation of stack and merge calculation.
	public double evaluate() {
		while (!operatorStack.empty()) {
            Operator oldOpr = operatorStack.pop();
            double op2 = numbers.pop();
            double op1 = numbers.pop();
            numbers.push(oldOpr.execute(op1, op2));
        } 

        return (numbers.pop());
	}
}
