package Program;

public class CodeLine {
    private Instruction instr;
    private int arg;
    private boolean indirect;
    
	public CodeLine(Instruction instr, int arg, boolean indirect) {
		this.instr = instr;
		this.arg = arg;
		this.indirect = indirect;
	}
	
	public CodeLine(Instruction instr, int arg) {
		this(instr, arg, false);
	}

	public CodeLine(Instruction instr) {
		this(instr, 0, false);
	}

	public Instruction getInstr() {
		return instr;
	}
	public int getArg() {
		return arg;
	}
	public boolean isIndirect() {
		return indirect;
	}

	@Override
	public String toString() {
		return instr.getName() 
		+ (indirect?" %":" ") 
		+ Integer.toHexString(arg);
	}

	public void execute(Processor processor, Program program, int arg) 
	throws DataAccessException {
		instr.execute(processor, program, arg);
	}

	public void setIndirect(boolean ind) {
		indirect = ind;		
	}
} 