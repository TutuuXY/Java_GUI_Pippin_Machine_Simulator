package Program;
/**
 * Class DIVI provides an implementation of the Instruction class that
 * divides the accumulator of the processor by an argument. 
 * @author CS 140
 *
 */
public class DIVI extends Instruction {
	
	/**
	 * Implementation of the DIVI Instruction.
	 * The indirect flag is ignored. 
	 * The accumulator is divided by the argument (integer division).
	 * Use processor.getAccumulator and processor.setAccumulator
	 * to change the accumulator.
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
	public void execute(Processor processor, 
			Program program, int arg) {
		if (arg != 0) {
			processor.setAccumulator(processor.getAccumulator() / arg);
			processor.setProgramCounter(processor.getProgramCounter() + 1);
		} else {
			throw new DivideZeroException("Divided by Zero!");
		}
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
		return Instruction.DIVI;
	}
}