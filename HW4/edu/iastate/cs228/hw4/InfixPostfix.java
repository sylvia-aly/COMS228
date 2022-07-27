package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 
 * This class evaluates input infix and postfix expressions. 
 *
 */

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * @author Sylvia Nguyen
 *
 */
public class InfixPostfix {

	/**
	 * Repeatedly evaluates input infix and postfix expressions. See the project
	 * description for the input description. It constructs a HashMap object for
	 * each expression and passes it to the created InfixExpression or
	 * PostfixExpression object.
	 * 
	 * @param args
	 * @throws ExpressionFormatException
	 * @throws UnassignedVariableException
	 * @throws FileNotFoundException
	 **/
	public static void main(String[] args)
			throws ExpressionFormatException, UnassignedVariableException, FileNotFoundException {
		System.out.println("Evaluation of Infix and Postfix Expressions");
		System.out.println("keys: 1 (standard input) 2 (file input) 3 (exit)");
		System.out.println("(Enter \"I\" before an infix expression, \"P\" before a postfix expression)");
		@SuppressWarnings("resource")
		Scanner userInput = new Scanner(System.in);
		System.out.println();
		System.out.print("Trial 1: ");
		int key = userInput.nextInt();
		int trial = 1;

		while (key > 0 && key < 3) {
			trial++;

			if (key == 1) {
				System.out.print("Expression: ");
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);
				String expression = scan.nextLine();

//If the first character is I
				if (expression.charAt(0) == 'I') {
					HashMap<Character, Integer> variable = new HashMap<Character, Integer>();
					InfixExpression i = new InfixExpression(expression.substring(2));
					int varExist = 0;
					System.out.println("Infix form: " + i.toString());
					i.postfix();
					System.out.println("Postfix form: " + i.postfixString());

					@SuppressWarnings("resource")
					Scanner var = new Scanner(i.postfixExpression);
					while (var.hasNext()) {
						String b = var.next();
						if (Expression.isVariable(b.charAt(0)) && !variable.containsKey(b.charAt(0))) {
							varExist++;
							if (varExist == 1) {
								System.out.println("where");
							}

							System.out.print(b.charAt(0) + " = ");

							int x = scan.nextInt();
							variable.put(b.charAt(0), x);

						}

					}

					PostfixExpression last = new PostfixExpression(i.postfixExpression, variable);
					System.out.println("Expression value: " + last.evaluate());
					varExist = 0;

				}

				// -----------------------------

				// When character is P - focus on postfixExpression
				if (expression.charAt(0) == 'P') {

					PostfixExpression p = new PostfixExpression(expression.substring(2));
					int varExist = 0;
					System.out.println("Postfix form: " + p.toString());

					HashMap<Character, Integer> variable = new HashMap<Character, Integer>();
					@SuppressWarnings("resource")
					Scanner var = new Scanner(expression);
					while (var.hasNext()) {
						String b = var.next();
						if (Expression.isVariable(b.charAt(0)) && !variable.containsKey(b.charAt(0))) {
							varExist++;
							if (varExist == 1) {
								System.out.println("where");
							}

							System.out.print(b.charAt(0) + " = ");

							int x = scan.nextInt();
							variable.put(b.charAt(0), x);

						}

					}

					p.setVarTable(variable);
					System.out.println("Expression value: " + p.evaluate());
					varExist = 0;

				}

				// -----------------------------
				System.out.println();
				System.out.print("Trial " + trial + ": ");
				key = userInput.nextInt();

			}
			// ----------------------------- end of key is = to 1, start of key = 2

			if (key == 2) {
				System.out.println("Input from a file");
				System.out.print("Enter file name: ");
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);

				String theFile = sc.nextLine();

				System.out.println();
				File file = new File(theFile);
				Scanner fileSc = new Scanner(file);
				HashMap<Character, Integer> b = new HashMap<Character, Integer>();
				InfixExpression i = null;
				PostfixExpression p = null;

				while (fileSc.hasNextLine()) {
					String hol = fileSc.nextLine();

					if (!hol.isEmpty() && hol.charAt(0) != 'P' && hol.charAt(0) != 'I') {

						String v = "";

						Scanner scan3 = new Scanner(hol);
						while (scan3.hasNext()) {
							String a = scan3.next();
							v += a + " ";
							if (b.containsKey(a.charAt(0))) {
								v += scan3.next() + " ";
								int x = scan3.nextInt();
								v += x + " ";
								b.remove(a.charAt(0));
								b.put(a.charAt(0), x);
								System.out.println(v.trim());
							}
						}
						scan3.close();
					}

					else if (!hol.isEmpty() && hol.charAt(0) == 'I') {

						if (i == null) {
							if (b.size() != 0) {
								p.setVarTable(b);
								for (Entry<Character, Integer> entry : b.entrySet()) {
									if (entry.getValue() == null) {
										Character a = entry.getKey();
										fileSc.close();
										sc.close();

										throw new UnassignedVariableException(
												"Variable " + a + " was not assigned a value");

									}

								}
								System.out.println("Expression value: " + p.evaluate());
								System.out.println();
								b.clear();
							}
						}

						else if (b.size() != 0) {
							i.setVarTable(b);
							for (Entry<Character, Integer> entry : b.entrySet()) {
								if (entry.getValue() == null) {
									Character a = entry.getKey();
									fileSc.close();
									sc.close();
									throw new UnassignedVariableException(
											"Variable " + a + " was not assigned a value");

								}

							}
							System.out.println("Expression value: " + i.evaluate());
							System.out.println();
							b.clear();
						}
						i = new InfixExpression(hol.substring(2), b);
						p = null;

						System.out.println("Infix form: " + i.toString());
						System.out.println("Postfix form: " + i.postfixString());
						Scanner scan2 = new Scanner(i.postfixExpression);
						while (scan2.hasNext()) {
							String a = scan2.next();
							if (Expression.isVariable(a.charAt(0))) {
								b.put(a.charAt(0), null);

							}

						}
						scan2.close();
						if (b.size() == 0) {

							System.out.println("Expression value: " + i.evaluate());
							System.out.println();
						} else {
							System.out.println("where");

						}

					} else if (!hol.isEmpty() && hol.charAt(0) == 'P') {
						if (p == null) {
							if (b.size() != 0) {
								i.setVarTable(b);
								for (Entry<Character, Integer> entry : b.entrySet()) {
									if (entry.getValue() == null) {
										Character a = entry.getKey();

										fileSc.close();
										sc.close();
										throw new UnassignedVariableException(
												"Variable " + a + " was not assigned a value");

									}

								}

								System.out.println("Expression value: " + i.evaluate());
								System.out.println();
								b.clear();
							}
						}

						else if (b.size() != 0) {
							p.setVarTable(b);
							for (Entry<Character, Integer> entry : b.entrySet()) {
								if (entry.getValue() == null) {
									Character a = entry.getKey();
									sc.close();
									fileSc.close();
									throw new UnassignedVariableException(
											"Variable " + a + " was not assigned a value");

								}

							}
							System.out.println("Expression value: " + p.evaluate());
							System.out.println();
							b.clear();
						}
						p = new PostfixExpression(hol.substring(2), b);
						i = null;
						System.out.println("Postfix form: " + p.toString());
						Scanner scan2 = new Scanner(p.postfixExpression);
						while (scan2.hasNext()) {
							String a = scan2.next();
							if (Expression.isVariable(a.charAt(0))) {
								b.put(a.charAt(0), null);

							}

						}
						scan2.close();
						if (b.size() == 0) {

							System.out.println("Expression value: " + p.evaluate());
							System.out.println();
						} else {
							System.out.println("where");

						}

					}

				}

				if (p == null) {
					if (b.size() != 0) {
						i.setVarTable(b);
						for (Entry<Character, Integer> entry : b.entrySet()) {
							if (entry.getValue() == null) {
								Character a = entry.getKey();

								fileSc.close();
								sc.close();
								throw new UnassignedVariableException("Variable " + a + " was not assigned a value");

							}

						}

						System.out.println("Expression value: " + i.evaluate());
						System.out.println();
						b.clear();
					}
				} else if (i == null) {
					if (b.size() != 0) {
						p.setVarTable(b);
						for (Entry<Character, Integer> entry : b.entrySet()) {
							if (entry.getValue() == null) {
								Character a = entry.getKey();
								fileSc.close();
								sc.close();
								throw new UnassignedVariableException("Variable " + a + " was not assigned a value");

							}

						}
						System.out.println("Expression value: " + p.evaluate());
						System.out.println();
						b.clear();
					}
				}

				System.out.print("Trial " + trial + ": ");
				key = userInput.nextInt();
				fileSc.close();

			}

		}

		// TODO

		// helper methods if needed
	}

}
