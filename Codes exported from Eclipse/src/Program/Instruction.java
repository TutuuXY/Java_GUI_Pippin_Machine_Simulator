package Program;
abstract public class Instruction {
	public static final int ADD = 4;
	public static final int ADDI = 8;
	public static final int AND = 16;
	public static final int ANDI = 17;
	public static final int CMPL = 20;
	public static final int CMPZ = 19;
	public static final int DIV = 7;
	public static final int DIVI = 11;
	public static final int HALT = 31;
	public static final int JMPZ = 27;
	public static final int JUMP = 26;
	public static final int LOD = 1;
	public static final int LODI = 2;
	public static final int MUL = 6;
	public static final int MULI = 10;
	public static final int NOP = 0;
	public static final int NOT = 18;
	public static final int STO = 3;
	public static final int SUB = 5;
	public static final int SUBI = 9;
	/**
	 * Method to return the name of this instruction, e.g. "NOP" or 
	 * "LODI"
	 * @return the name of the instruction
	 */
	public String getName() {
		return getClass().getSimpleName();
	}
	
	/**
	 * Method to return the numeric opcode of this instruction
	 * e.g. for NOP it is 0, for LOD it is 1 
	 * @return the opcode of this instruction
	 */
	public int getOpCode() {
		return 0;
	}
	/**
	 * Method to indicate if this is an immediate instruction, 
	 * e.g. "LODI" "ADDI"
	 * @return true if the instruction is immediate otherwise false
	 */
	public boolean isImmediate() {
		return false; // ONLY override this
		// method for the classes that return true
	}
	/**
	 * Method to indicate if this is instruction requires an argument, 
	 * only NOP, NOT and HALT do not
	 * @return true if the instruction is requires an argument 
	 * otherwise false
	 */
	public boolean requiresArgument() {
		return true; // ONLY override this
		// method for the classes that return false
	}

	/**
	 * Method to execute this instruction for the given argument. 
	 * The details are explained in list of instructions for this 
	 * version of the Pippin computer.
	 * NOTE: If the instruction does not use an argument, then the 
	 * argument is passed as 0
	 * 
	 * @param processor a reference to the processor the instruction
	 * will execute on
	 * @param program a reference to the program where this 
	 * instruction occurs
	 * @param arg the argument for this instruction
	 * @throws DataAccessException if access to the data of the 
	 * program causes an exception
	 */
	public abstract void execute(Processor processor, 
			Program program, int arg) throws DataAccessException;
}
