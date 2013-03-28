package Program;
/**
 * Class LODI provides an implementation of the Instruction class that 
 * loads an immediate argument into the accumulator of the processor.
 * @author CS 140
 *
 */
public class LODI extends Instruction {

	/**
	 * Implementation of the LODI Instruction.
	 * If the indirect flag is ignored.
	 * The value of the arg becomes the contents of the accumulator.
	 * 
	 * Use processor.setAccumulator to change the accumulator.
	 * 
	 * This instruction adds 1 to the program counter in the
	 * processor.
	 * Use processor.getProgramCounter and processor.setProgramCounter
	 * to change the program counter.  
	 *
	 * @param processor a reference to the processor the instruction
	 * will execute on
	 * @param program a reference to the program where this 
	 * instruction occurs
	 * @param arg the argument for this instruction
	 * @throws DataAccessException if access to the data of the 
	 * program causes an exception
	 */
	@Override
	public void execute(Processor processor, 
			Program program, int arg) {
		processor.setAccumulator(arg);
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
		return Instruction.LODI;
	}
}