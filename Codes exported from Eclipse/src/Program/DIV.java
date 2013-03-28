package Program;
/**
 * Class DIV provides an implementation of the Instruction class that 
 * divides the accumulator of the processor by an argument, using 
 * either direct or indirect addressing.
 * @author CS 140 *
 */
public class DIV extends Instruction {

	/**
	 * Implementation of the DIV Instruction.
	 * If the indirect flag for this instruction in the program
	 * (use program.getIndirect) is false then the contents of the 
	 * accumulator is divided by the data value stored at location 
	 * arg in the data of the program (using integer division).
	 * 
	 * Use processor.getAccumulator and processor.setAccumulator
	 * to change the accumulator.
	 * Use program.getMemory(arg) to read the data of the program. 
	 * 
	 * If the indirect flag true, the value stored at the location arg 
	 * of the data of the program is itself a location. The contents 
	 * of the accumulator is divided by the value stored at this 
	 * new location in the data of the program. 
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
	public void execute(Processor processor, Program program, int arg) 
	throws DataAccessException {
		if ( program.getIndirect(processor.getProgramCounter()) ) {
			int divisor = program.getMemory(program.getMemory(arg));
			if ( divisor != 0 ) {
				processor.setAccumulator( processor.getAccumulator() / divisor );
				processor.setProgramCounter( processor.getProgramCounter() + 1 );
			} else {
				throw new DivideZeroException("Divided by Zero!");
			}
		} else {
			int divisor = program.getMemory(arg);
			if ( divisor != 0 ) {
				processor.setAccumulator( processor.getAccumulator() / divisor );
				processor.setProgramCounter( processor.getProgramCounter() + 1 );
			} else {
				throw new DivideZeroException("Divided by Zero!");
			}
		}
	}
	
	/**
	 * The method that gives the opcode of this instruction
	 * @return the opcode of this instruction
	 */
	@Override
	public int getOpCode() {
		return Instruction.DIV;
	}
}