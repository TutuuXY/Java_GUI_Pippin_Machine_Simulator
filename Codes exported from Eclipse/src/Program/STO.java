package Program;
/**
 * Class STO provides an implementation of the Instruction class that 
 * saves the accumulator of the processor to the data of the program, 
 * using either direct or indirect addressing.
 * @author CS 140
 *
 */
public class STO extends Instruction {

	/**
	 * Implementation of the STO Instruction.
	 * If the indirect flag for this instruction in the program
	 * (use program.getIndirect) is false then the arg gives a 
	 * location in the data of the program; the content of the 
	 * accumulator is copied to that location.
	 * 
	 * Use program.setMemory() to set the data of the program at
	 * location arg 
	 * Use processor.getAccumulator to access the accumulator value.
	 * 
	 * If the indirect flag true, the value stored at the location arg 
	 * of the data of the program is itself a location. The 
	 * accumulator is copied to that new location in the data of the 
	 * program. 
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
	 * @throws DataAccessException 
	 * @throws DataAccessException if access to the data of the 
	 * program causes an exception
	 */
	@Override
	public void execute(Processor processor, 
			Program program, int arg) throws DataAccessException {
		if (program.getIndirect(processor.getProgramCounter())) {
			program.setMemory(program.getMemory(arg), processor.getAccumulator());
		} else {
			program.setMemory(arg, processor.getAccumulator());
		}
		processor.setProgramCounter(processor.getProgramCounter() + 1);
	}
	
	/**
	 * The method that gives the opcode of this instruction
	 * @return the opcode of this instruction
	 */
	@Override
	public int getOpCode() {
		return Instruction.STO;
	}
}