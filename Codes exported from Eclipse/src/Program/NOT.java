package Program;
/**
 * Class NOT provides an implementation of the Instruction class that 
 * performs a logical "not," which means that if the accumulator is
 * zero, then 1 is put in the accumulator and if the accumulator is 
 * not 0 then accumulator is changed to 0.
 * @author CS 140
 *
 */
public class NOT extends Instruction {

	/**
	 * Implementation of the NOT Instruction.
	 * The indirect flag is ignored.
	 * If the accumulator is zero, then the accumulator is set to 1. 
	 * If the accumulator is not zero, change the accumulator to 0.
	 *  
	 * Use processor.getAccumulator and processor.setAccumulator
	 * to change the accumulator.
	 * 
	 * This instruction adds 1 to the program counter in the
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
		if (processor.getAccumulator() !=0 ) {
			processor.setAccumulator(0);
		} else {
			processor.setAccumulator(1);
		}
		
		processor.setProgramCounter(processor.getProgramCounter() + 1);
	}

	/**
	 * The method that gives the opcode of this instruction
	 * @return the opcode of this instruction
	 */
	@Override
	public int getOpCode() {
		return Instruction.NOT;
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