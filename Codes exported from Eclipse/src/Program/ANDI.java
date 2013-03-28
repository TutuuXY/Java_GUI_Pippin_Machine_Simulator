package Program;
/**
 * Class ANDI provides an implementation of the Instruction class that 
 * performs a logical "and" of an immediate argument and the value in 
 * the accumulator of the processor.
 * @author CS 140
 *
 */
public class ANDI extends Instruction {
	
	/**
	 * Implementation of the ANDI Instruction.
	 * The indirect flag is ignored. 
	 * If the value of arg and the contents of the accumulator are 
	 * both not zero, then the accumulator is set to 1. If either or 
	 * both of these two values are 0, then the accumulator is set 
	 * to 0.

	 * Use processor.getAccumulator and processor.setAccumulator
	 * to change the accumulator.
	 * 
	 * This instruction adds 1 to the program counter. Use
	 * Use processor.getProgramCounter and processor.setProgramCounter
	 * to change the program counter.  
	 * Since data is not accessed for the argument, DataAccessException
	 * cannot be thrown
	 *  
	 * @param processor a reference to the processor the instruction
	 * will execute on
	 * @param program a reference to the program where this 
	 * instruction occurs
	 * @param arg the argument for this instruction
	 */
	@Override
	public void execute(Processor processor, Program program, int arg) {
		if (processor.getAccumulator() !=0 && arg!=0) {
			processor.setAccumulator(1);
		} else{
			processor.setAccumulator(0);
		}
		processor.setProgramCounter(processor.getProgramCounter() + 1);
	}
	
	/**
	 * The method that indicates that this instruction uses immediate 
	 * addressing. 
	 * @return true since this instruction is immediate
	 */
	@Override
	public boolean isImmediate() {
		return true;
	}

	/**
	 * The method that gives the opcode of this instruction
	 * @return the opcode of this instruction
	 */
	@Override
	public int getOpCode() {
		return Instruction.ANDI;
	}
}