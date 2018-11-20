public interface BaseConverterInterface {

    /**
     * Break up the input from the command line, check for syntax errors,
     * and run the corresponding BaseConverter commands.
     * @param arguments[] A String array representing the command line arguments.
     */
    public void parseInput(String[] arguments);

    /**
     * Use a mathematical operation with the two numbers.
     * @param operation A string representing the operation (mult, add, sub, div).
     * @param first A string representing the first number.
     * @param firstBase A string representing the base of the first number
     * @param second A string representing the second number.
     * @return A string representing the result of the mathematical operation and
     * arg1 and arg2. The number will be returned in the base of the first number.
     */
    public String calculate(String operation, int first, int firstBase, int second);

    /**
     * Convert a number from one base to another
     * @param num A string representing the number to be converted.
     * @param base1 An int representing the original base of the number
     * @param base2 An int representing the new base of the converted number.
     * @return A String representing the converted.
     */
    public String convert (String num, int base1, int base2);

    /**
     * Convert a number from one base to base 10.
     * @param num A string representing the original number.
     * @param base A string representing the base of num.
     * @return An int representing the number in base 10.
     */
    public int toBaseTen (String num, int base);

    /**
     * Convert a integer of base 10 to another base.
     * @param num An int representing the original number in base 10.
     * @param base An int representing the base of the new number.
     * @return A String representing the converted number and base.
     */
    public String tenToOther (int num, int base);

    /**
     * Return the appropriate character value for a base 10 Integer.
     * @param c A base 10 integer.
     * @return A String representing an appropriate character value for c.
     */
    public String getCharValue (int c);

}

