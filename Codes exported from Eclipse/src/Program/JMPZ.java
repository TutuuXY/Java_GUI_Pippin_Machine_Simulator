package Program;
/**
 * Class JMPZ provides an implementation of the Instruction class that 
 * which is a conditional jump. If the accumulator is zero, the 
 * argument indicates the address of the next instruction. Otherwise
 * execution proceeds normally to the next instruction. Either direct 
 * or indirect addressing is used.
 * @author CS 140
 *
 */
public class JMPZ extends Instruction {

	/**
	 * Implementation of the JMPZ Instruction.
	 * If the indirect flag for this instruction in the program
	 * (use program.getIndirect) is false and the accumulator is 0,
	 * then arg is the new value of the program counter.
	 *   
	 * If the indirect flag is true and the accumulator is 0, then the
	 * value stored at the location arg of the data of the program is
	 * the new value of the program counter.
	 * 
	 * If accumulator not 0, then this instruction adds 1 to the 
	 * program counter.
	 * 
	 * Use program.getMemory(arg) to read the data of the program at
	 * location arg 
	 * Use processor.getAccumulator to access the accumulator.
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
		if (processor.getAccumulator() != 0) {
			processor.setProgramCounter( processor.getProgramCounter() + 1 );
		} else {
			if (program.getIndirect(processor.getProgramCounter()) == false) {
				processor.setProgramCounter(arg);
			} else {
				processor.setProgramCounter(program.getMemory(arg));
			}
		}
	}
	
	/**
	 * The method that gives the opcode of this instruction
	 * @return the opcode of this instruction
	 */
	@Override
	public int getOpCode() {
		return Instruction.JMPZ;
	}
}