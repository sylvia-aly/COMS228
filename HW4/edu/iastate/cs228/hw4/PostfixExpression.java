package edu.iastate.cs228.hw4;

/**
 *  
 * @author Sylvia Nguyen
 *
 */

/**
 * 
 * This class evaluates a postfix expression using one stack.    
 *
 */

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PostfixExpression extends Expression {
	private int leftOperand = 0; // left operand for the current evaluation step
	private int rightOperand = 0; // right operand (or the only operand in the case of
	// a unary minus) for the current evaluation step

	private PureStack<Integer> operandStack = new ArrayBasedStack<Integer>(); // stack of operands

	/**
	 * Constructor stores the input postfix string and initializes the operand
	 * stack.
	 * 
	 * @param st     input postfix string.
	 * @param varTbl hash map that stores variables from the postfix string and
	 *               their values.
	 */
	public PostfixExpression(String st, HashMap<Character, Integer> varTbl) {
		super(st, varTbl);
		// TODO
	}

	/**
	 * Constructor supplies a default hash map.
	 * 
	 * @param s
	 */
	public PostfixExpression(String s) {
		super(s);

		// TODO
	}

	/**
	 * Outputs the postfix expression according to the format in the project
	 * description.
	 */
	@Override
	public String toString() {
		// TODO
		String a = removeExtraSpaces(postfixExpression);
		return a.trim();
	}

	/**
	 * Resets the postfix expression.
	 * 
	 * @param st
	 */
	public void resetPostfix(String st) {
		postfixExpression = st;
	}

	/**
	 * Scan the postfixExpression and carry out the following:
	 * 
	 * 1. Whenever an integer is encountered, push it onto operandStack. 2. Whenever
	 * a binary (unary) operator is encountered, invoke it on the two (one) elements
	 * popped from operandStack, and push the result back onto the stack. 3. On
	 * encountering a character that is not a digit, an operator, or a blank space,
	 * stop the evaluation.
	 * 
	 * @return value of the postfix expression
	 * @throws UnassignedVariableException
	 * @throws ExpressionFormatException   with one of the messages below:
	 * 
	 *                                     -- "Invalid character" if encountering a
	 *                                     character that is not a digit, an
	 *                                     operator or a whitespace (blank, tab); --
	 *                                     "Too many operands" if operandStack is
	 *                                     non-empty at the end of evaluation; --
	 *                                     "Too many operators" if getOperands()
	 *                                     throws NoSuchElementException; -- "Divide
	 *                                     by zero" if division or modulo is the
	 *                                     current operation and rightOperand == 0;
	 *                                     -- "0^0" if the current operation is "^"
	 *                                     and leftOperand == 0 and rightOperand ==
	 *                                     0; -- self-defined message if the error
	 *                                     is not one of the above.
	 * 
	 *                                     UnassignedVariableException if the
	 *                                     operand as a variable does not have a
	 *                                     value stored in the hash map. In this
	 *                                     case, the exception is thrown with the
	 *                                     message
	 * 
	 *                                     -- "Variable <name> was not assigned a
	 *                                     value", where <name> is the name of the
	 *                                     variable.
	 * 
	 */
	public int evaluate() throws UnassignedVariableException, ExpressionFormatException {
		// scanning
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(postfixExpression);

		while (scan.hasNext()) {

			String value = scan.next();

			if (isInt(value)) {
				operandStack.push(Integer.parseInt(value));
			} else if (isVariable(value.charAt(0))) {

				if (varTable.containsKey(value.charAt(0))) {
					operandStack.push(varTable.get(value.charAt(0)));
				} else {

					throw new UnassignedVariableException("Variable " + value + " was not assigned a value");
				}

			} else if (isOperator(value.charAt(0))) {
				try {
					getOperands(value.charAt(0));

					if (value.charAt(0) == '^' && leftOperand == 0 && rightOperand == 0) {

						throw new ExpressionFormatException("0^0");
					}
					if (value.charAt(0) == '/' && rightOperand == 0) {

						throw new ExpressionFormatException("Divide by zero");
					}
					if (value.charAt(0) == '%' && rightOperand == 0) {

						throw new ExpressionFormatException("Divide by zero");
					}

					operandStack.push(compute(value.charAt(0)));

				} catch (NoSuchElementException e) {

					throw new ExpressionFormatException("Too many operators");

				}

			} else {

				throw new ExpressionFormatException("Invalid Character");
			}

		}
		if (operandStack.size() > 1) {

			throw new ExpressionFormatException("Too many operands");

		}

		// TODO

		return operandStack.pop().intValue();
	}

	/**
	 * For unary operator, pops the right operand from operandStack, and assign it
	 * to rightOperand. The stack must have at least one entry. Otherwise, throws
	 * NoSuchElementException. For binary operator, pops the right and left operands
	 * from operandStack, and assign them to rightOperand and leftOperand,
	 * respectively. The stack must have at least two entries. Otherwise, throws
	 * NoSuchElementException.
	 * 
	 * @param op char operator for checking if it is binary or unary operator.
	 */
	private void getOperands(char op) throws NoSuchElementException {
		if (op == '~') {
			if (operandStack.size() > 0) {
				rightOperand = operandStack.pop();
			} else {
				throw new NoSuchElementException();
			}
		} else if (operandStack.size() > 1) {
			rightOperand = operandStack.pop();
			leftOperand = operandStack.pop();

		} else {
			throw new NoSuchElementException();
		}

		// TODO
	}

	/**
	 * Computes "leftOperand op rightOprand" or "op rightOprand" if a unary
	 * operator.
	 * 
	 * @param op operator that acts on leftOperand and rightOperand.
	 * @return
	 */
	private int compute(char op) {

		if (op == '+') {
			return leftOperand + rightOperand;
		}
		if (op == '-') {
			return leftOperand - rightOperand;
		}
		if (op == '/') {
			return leftOperand / rightOperand;
		}
		if (op == '^') {
			return (int) Math.pow(leftOperand, rightOperand);
		}
		if (op == '%') {
			return leftOperand % rightOperand;
		}
		if (op == '*') {
			return leftOperand * rightOperand;
		}
		// TODO
		// else if it is unary
		return 0 - rightOperand; // TO MODIFY
	}
}
