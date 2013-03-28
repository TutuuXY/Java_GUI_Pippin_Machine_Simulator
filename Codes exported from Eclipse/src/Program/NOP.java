package Program;
/**
 * Class NOP provides an implementation of the Instruction class that 
 * is a no-op instruction. The only action is to advance the program 
 * counter.
 * @author CS 140
 *
 */
public class NOP extends Instruction {

	/**
	 * Implementation of the NOP Instruction.
	 * The indirect flag is ignored.
	 * This instruction only adds 1 to the program counter in the
	 * processor.
	 * Use processor.getProgramCounter and processor.setProgramCounter
	 * to change the program counter.
	 * Since data is not accessed for the argument, DataAccessException
	 * cannot be thrown
  
	 * @param processor a reference to the processor the instruction
	 * will execute on
	 * @param program a reference to the program where this 
	 * instruction occurs
	 * @param arg the argument for this instruction is ignored
	 */
	@Override
	public void execute(Processor processor, 
			Program program, int arg) {
		processor.setProgramCounter(processor.getProgramCounter() + 1);
	}

	/**
	 * The method that gives the opcode of this instruction
	 * @return the opcode of this instruction
	 */
	@Override
	public int getOpCode() {
		return Instruction.NOP;
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

}