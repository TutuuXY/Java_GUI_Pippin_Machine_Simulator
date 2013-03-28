package Program;
/**
 * Class HALT provides an implementation of the Instruction class that 
 * sets the program to the halted state
 * @author CS 140
 *
 */
public class HALT extends Instruction {

	/**
	 * Implementation of the HALT Instruction.
	 * The indirect flag is ignored.
	 * THIS IS THE ONLY INSTRUCTION THAT DOES NOT MODIFY THE PROGRAM
	 * COUNTER AT ALL
	 * The halted state of the Program is set to true
	 *  
	 * Since data is not accessed for the argument, DataAccessException
	 * cannot be thrown
	 * 
	 * @param processor a reference to the processor the instruction
	 * will execute on
	 * @param program a reference to the program where this 
	 * instruction occurs
	 * @param arg the argument for this instruction is ignored
	 */
	@Override
	public void execute(Processor processor, 
			Program program, int arg) {
		program.setHalted(true);
	}

	/**
	 * Method to indicate if this is instruction requires an argument.
	 * @return false since the instruction does not require an 
	 * argument
	 */
	@Override
	public boolean requiresArgument() {
        return false;
	}

	/**
	 * The method that gives the opcode of this instruction
	 * @return the opcode of this instruction
	 */
	@Override
	public int getOpCode() {
		return Instruction.HALT;
	}

}