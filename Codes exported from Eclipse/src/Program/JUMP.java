package Program;
/**
 * Class JUMP provides an implementation of the Instruction class that 
 * which is an unconditional jump. The argument indicates the address 
 * of the next instruction. Either direct or indirect addressing is 
 * used.
 * @author CS 140
 *
 */
public class JUMP extends Instruction {

	/**
	 * Implementation of the JUMP Instruction.
	 * If the indirect flag for this instruction in the program
	 * (use program.getIndirect) is false, then arg is the new value 
	 * of the program counter.
	 *   
	 * If the indirect flag is true, then the value stored at the 
	 * location arg of the data of the program is the new value of the 
	 * program counter.
	 * 
	 * Use program.getMemory(arg) to read the data of the program at
	 * location arg 
	 * Use processor.setProgramCounter to change the program counter.  

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
			processor.setProgramCounter( program.getMemory(arg) );
		} else {
			processor.setProgramCounter( arg );
		}
	}
	
	/**
	 * The method that gives the opcode of this instruction
	 * @return the opcode of this instruction
	 */
	@Override
	public int getOpCode() {
		return Instruction.JUMP;
	}
}