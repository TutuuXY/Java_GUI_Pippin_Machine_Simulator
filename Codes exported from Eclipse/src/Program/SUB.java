package Program;
/**
 * Class SUB provides an implementation of the Instruction class that 
 * subtracts an argument from the accumulator of the processor, using 
 * either direct or indirect addressing.
 * @author CS 140
 *
 */
public class SUB extends Instruction {

	/**
	 * Implementation of the SUB Instruction.
	 * If the indirect flag for this instruction in the program
	 * (use program.getIndirect) is false then the data value stored 
	 * at location arg in the data of the program is subtracted from 
	 * the contents of the accumulator.
	 * 
	 * Use program.getMemory(arg) to read the data of the program at
	 * location arg 
	 * Use processor.getAccumulator and processor.setAccumulator
	 * to change the accumulator.
	 * 
	 * If the indirect flag true, the value stored at the location arg 
	 * of the data of the program is itself a location. The value 
	 * stored at this new location in the data of the program is 
	 * subtracted from the contents of the accumulator. 
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
	public void execute(Processor processor, 
			Program program, int arg) throws DataAccessException {
		if ( program.getIndirect(processor.getProgramCounter()) ) {
			processor.setAccumulator( processor.getAccumulator() - program.getMemory(program.getMemory(arg)) );
		} else {
			processor.setAccumulator( processor.getAccumulator() - program.getMemory(arg) );
		}
		processor.setProgramCounter( processor.getProgramCounter() + 1 );
	}
	
	/**
	 * The method that gives the opcode of this instruction
	 * @return the opcode of this instruction
	 */
	@Override
	public int getOpCode() {
		return Instruction.SUBI;
	}
}