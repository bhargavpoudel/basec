import java.util.ArrayList;
import java.util.Arrays;

public class Converter implements BaseConverterInterface {

	public Converter(String arguments[]) {
		try {
			parseInput(arguments);
		} catch (Exception e) {
			System.out.println("Error. Please use 3 arguments" +
					" in the form: \n" +
					 "java BaseConverter <sub-command> <number>_<base> [<number>]_<base>");
			System.out.println("Sub-commands include: convert, add, mult, sub, and div");
		}
	}

	/** 
	 * Parse the inputs to the converter and call the appropriate methods based on the commands.
	 * Check the syntax of the input and throw an error if the syntax or the inputs are invalid.
	 * @param arguments An array of strings representing the arguments.
	 */
	public void parseInput(String[] arguments) {

		/**  java BaseConverter <sub-command> <number>_<base> [<number>]_<base> **/

		ArrayList<String> validOps = new ArrayList<>(Arrays.asList("add", "sub","mult","div","convert"));

		/* Check the LENGTH of the arguments (i.e. has to be 3) */
		if (arguments.length == 3) {
			String operation = arguments[0].toLowerCase();
			/* Check if the OPERATION is valid */
			if (validOps.contains(operation)) {
				String first = arguments[1];
				String second = arguments[2];
				/* Check if the arguments are in the correct format (NOT complete) */
				if (formatCheck(first, second) ==  true) {
					/* Change the required arguments to integers */
					int[] firstNum = separateReturnIntArr(first);
					int[] secondNum = separateReturnIntArr(second);
					/* Send them to their respective methods */
					if (operation.equals("convert")) {
						String converted = convert(Integer.toString(firstNum[0]), firstNum[1], secondNum[1]);
						System.out.println("Result of CONVERSION:\n\n" +
                                first + " in base " + secondNum[1]+ " is " + converted);
                    } else {
						String calculated = calculate(operation, firstNum[0], firstNum[1], secondNum[0]);
						System.out.println("Result of CALCULATION:\n\n" +
                                first + " " + operation + " " + second + " = " + calculated);
                    }

				} else {
					System.out.println("Please use the format <number>_<base> for the argument.");
				}

			} else {
				System.out.println("Invalid Operation; has to be either: \n" +
						"\t a. add\n" +
						"\t b. sub\n" +
						"\t c. mult\n" +
						"\t d. div\n" +
						"\t e. convert");
			}

		} else {
			System.out.println("Incorrect number of arguments. Please use 3 arguments" +
					" in the form: \n" +
					 "java BaseConverter <sub-command> <number>_<base> [<number>]_<base> ");
		}
	}


	public boolean formatCheck (String first, String second) {
        boolean flagFirst = false;
        boolean flagSecond = false;
        String[] temp = {"", ""};

        temp = first.split("_", 2);
        if ((temp.length == 2) && (first.contains("_"))) {
        	flagFirst = true;
		}
		temp = second.split("_", 2);
		if ((temp.length == 2) && (second.contains("_"))) {
			flagSecond = true;
		}
        return (flagFirst && flagSecond);


	}

	public int[] separateReturnIntArr(String str) {
		String[] tempStr = {"",""};
		int[] tempInt = {0,0};
		tempStr = str.split("_", 2);
		tempInt[0] = Integer.parseInt(tempStr[0]);
		tempInt[1] = Integer.parseInt(tempStr[1]);
		return tempInt;
    }


	/**
	 * Perform an arithmetic operation on two numbers of base 10
	 * @param operation A string representing the operation to be performed (add, mult, div, or sub).
	 * @param first The first integer on which the operation is performed.
	 * @param firstBase The original base of the first number.
	 * @param second The second integer on which the operation is performed.
	 * @return The result of FIRST and SECOND and OPERATION. Return will be in the original base
	 * of FIRST or be returned in IEEE 754 floating point format if the result is negative or a 
	 * non-integer
	 */

	public String calculate(String operation, int first, int firstBase, int second) {
			double output = 0;
			// Perform the arithmetic operation
			if (operation.equalsIgnoreCase("add")) {
				output = first + second;
			} else if (operation.equalsIgnoreCase("mult")) {
				output = first * second;
			} else if (operation.equalsIgnoreCase("div")) {
				output = (double) first / (double) second;
			} else if (operation.equalsIgnoreCase("sub")) {
				output = first - second;
			} else {
				// we need to throw an exception here
			}
			
			// Check if the output needs to be converted to floating point.
			// Otherwise, return the output in the original base
			if (output > 0 && output % 1 == 0) {
				return tenToOther((int) output, firstBase);
			} else {
				return doubleToFloat(output);
			}
	}
	
	/**
	 * Convert a number of a any base to a different base.
	 * @param first A string representing the first number to be converted.
	 * @param baseOne An integer representing the base of the first number.
	 * @param baseTwo An integer representing the base the number should be converted to.
	 * @return A base-10 integer representing the converted number.
	 */
	public String convert(String first, int baseOne, int baseTwo) {
		int toConvert = toBaseTen(first, baseOne);
		// Convert each number to base 10 before converting to a different base
		return tenToOther(toConvert, baseTwo);
	}
	
	/**
	 * Convert a number of any base to base-10.
	 * @param input A string representing a number of a certain base.
	 * @param base The base of input
	 * @return input converted to a base-10 integer.
	 */
	public int toBaseTen(String input, int base) {
		if (base == 10) {
			// If the number is already base 10, just return itself
			return Integer.parseInt(input);
		}
		boolean cont = true;
		int output = 0;
		int initLength = input.length() - 1;
		while (cont == true) {
			// Move through the number one place at a time and add the value of each place to the output
			output += Character.getNumericValue(input.charAt(0)) * Math.pow(base, initLength);
			initLength--;
			// Continue running until no characters (places) remain
			if (input.length() >= 2) {
				input = input.substring(1);
			} else {
				cont = false;
			}
		}
		return output;
	}

	/**
	 * Get the character value of an integer
	 * @param c An integer between 0 and 35 (35 the max val we can represent).
	 * @return An alphanumeric character representing c.
	 */
	public String getCharValue(int c) {
		char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		if (c < 10) {
			return Integer.toString(c);
		} else {
			return Character.toString(letters[c - 10]);
		}
	}

	/**
	 * Convert a base 10 integer to a number of a different base.
	 * @param input A base 10 integer.
	 * @param base The base input should be converted to.
	 * @return input converted to the specified base.
	 */
	public String tenToOther(int input, int base) {
		String output = "";
		int quotient = input;
		while (quotient > 0) {
			// Using the remainder of the quotient and the base, create a string representing the number
			output = getCharValue((quotient % base)) + output;
			quotient = quotient / base;
		}
		// If the output is zero, set the output string to this value
		if (output == "") {
			output = "0";
		}
		return output + "_" + base;
	}
	
	/**
	 * Get the decimal portion of a double
	 * @param input A double.
	 * @return The decimal portion of input.
	 */
	public double getFrac(double input) {
		String convert = String.valueOf(input);
		String frac = convert.split("\\.")[1];
		return Double.parseDouble("." + frac);
	}
	
	/**
	 * Convert a base 10 double to a base-2 String.
	 * @param input A base 10 double.
	 * @return A string representing the base-2 representation of input.
	 */	
	public String decToBin(double input) {
		int nonFrac = (int) input;
		// Convert the decimal portion in to binary
		double frac = getFrac(input);
		String output = "";
		double remainder = frac * 2.0;
		// Loop until the remainder is 1 or until the fraction repeats
		while (remainder != 1 && remainder != 0 && output.length() < 23) {
			// If remainder is less than one put a 0 in this place
			// Otherwise, a 1
			if (remainder < 1.0) {
				output += "0";
			} else {
				output += "1";
			}
			remainder = 2.0 * getFrac(remainder);
		}
		if (remainder == 1.0) {
			output += 1;
		} else  {
			// The decimal portion will just be 0 if the remainder is 0
			output += 0;
		}
		return tenToOther(Math.abs(nonFrac), 2).split("_")[0] + "." + output;
	}

	/**
	 * Convert a base 10 double to an IEEE 754 32 bit base-2 floating point number.
	 * @param input A double to be converted.
	 * @return An IEEE 754 32 bit base-2 floating point number.
	 */
	public String doubleToFloat(double input) {
		String binaryRep = decToBin(input);
		// Check if number is positive or negative
		String signedBit = "0";
		if (input < 0) {
			signedBit = "1";
		}
		// Normalize the binaryRep
		if (binaryRep.contains("1")) {
			// Break the string at the decimal
			String left = binaryRep.split("\\.")[0];
			String right = binaryRep.split("\\.")[1];
			int indexDec = binaryRep.indexOf('.');
			String noDec = binaryRep.substring(0, indexDec) + binaryRep.substring(indexDec+1);
			int normExponent = 0;
			int indexOne = 0;
			String fraction = "";
			if (binaryRep.startsWith("1.")) {
				normExponent = 0;
				fraction = right;
			// See if the left contains a 1
			} else if (left.contains("1")) {
				indexOne = left.indexOf('1');
				normExponent = indexDec - indexOne - 1;
				fraction = noDec.substring(indexOne + 1);
			// See if right contains a 1
			} else {
				indexOne = right.indexOf('1');
				normExponent = 0 - indexOne - 1;
				fraction = right.substring(indexOne + 1);
			}
			
			String exponent = tenToOther(normExponent + 127, 2).split("_")[0];
			if (exponent.length() > 8) {
				System.out.println("Number cannot be represented.");
			}
			if (fraction.length() > 23) {
				fraction = fraction.substring(0, 23);
			}
			// Add zeros to pad the exponent if needed
			if (exponent.length() < 8) {
				String zeros = "";
				for (int i = 0; i < (8 - exponent.length()); i++) {
					zeros += "0";
				}
				exponent = zeros + exponent;
			}
			// Add zeros to pad the fraction if needed
			if (fraction.length() < 23) {
				String zeros = "";
				for (int i = 0; i < (23 - fraction.length()); i++) {
					zeros += "0";
				}
				fraction = fraction + zeros;
			}
			
			return signedBit + exponent + fraction + "_float";
		// If the number does not contain a 1, it is a zero
		} else {
			return "00000000000000000000000000000000_float";
		}

	}

}
