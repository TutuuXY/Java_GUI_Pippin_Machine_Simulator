package Program;
/**
 * Class AND provides an implementation of the Instruction class that 
 * performs a logical "and" of an argument and the value in the 
 * accumulator of the processor, using only direct addressing.
 * @author CS 140
 *
 */
public class AND extends Instruction {

	/**
	 * Implementation of the AND Instruction.
	 * The indirect flag is ignored.
	 * If the data value stored at location arg in the data of the
	 * program and the contents of the accumulator are both not zero,
	 * then the accumulator is set to 1. If either or both of these 
	 * two values are 0, then the accumulator is set to 0.
	 *  
	 * Use program.getMemory(arg) to read the data of the program at
	 * location arg 
	 * Use processor.getAccumulator and processor.setAccumulator
	 * to change the accumulator.
	 * 
	 * This instruction adds 1 to the program counter in the
	 * processor.
	 * Use processor.getProgramCounter and processor.setProgramCounter
	 * to change the program counter.  
	 * @param processor a reference to the processor the instruction
	 * will execute on
	 * @param program a reference to the program where this 
	 * instruction occurs
	 * @param arg the argument for this instruction
	 * @throws DataAccessException 
	 * @throws DataAccessException if access to the data of the 
	 * program causes an exception
	 */
	@Override
	public void execute(Processor processor, Program program, int arg) throws DataAccessException {
		if (processor.getAccumulator() !=0 && program.getMemory(arg)!=0) {
			processor.setAccumulator(1);
		} else{
			processor.setAccumulator(0);
		}
		processor.setProgramCounter(processor.getProgramCounter() + 1);
	}

	/**
	 * The method that gives the opcode of this instruction
	 * @return the opcode of this instruction
	 */
	@Override
	public int getOpCode() {
		return Instruction.AND;
	}

}