package Program;

import java.util.Map;
import java.util.TreeMap;

public class Program {
	public static final int MEMORY_MAX_SIZE = 256;
	private Map<Integer,Integer> data = new TreeMap<Integer,Integer>();
	private boolean halted = false;
	private CodeLine[] code = new CodeLine[64];

	public void execute(Processor processor)
	throws DataAccessException, CodeAccessException {
		try {
			if ( processor.getProgramCounter() < 0 ||
					processor.getProgramCounter() > MEMORY_MAX_SIZE ) {
				throw new CodeAccessException("Program counter out of range!");
			}
			CodeLine line = code[processor.getProgramCounter()];
			System.out.println(line);
			line.execute(processor, this, line.getArg());
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new CodeAccessException("Array Index Out Of Bounds!");
		} catch (NullPointerException e) {
			throw new CodeAccessException("Null Pointer Exception!");
		}
	}
	
	public boolean getIndirect(int programCounter)
	throws CodeAccessException {
		try {
			return code[programCounter].isIndirect();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new CodeAccessException(e.getMessage());
		} catch (NullPointerException e) {
			throw new CodeAccessException(e.getMessage());	
		}
	}
	
	public void setIndirect(int programCounter, boolean ind) {
		try {
			code[programCounter].setIndirect(ind);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new CodeAccessException(e.getMessage());
		} catch (NullPointerException e) {
			throw new CodeAccessException(e.getMessage());	
		}
	}
	
	public CodeLine[] getLines() {
		return code;
	}

	public void setLines(CodeLine[] code) {
		this.code = code;
	}
	
	public void setMemoryClear() {
		data.clear();
	}

	public int getMemory(int location) throws DataAccessException {
		if (!data.containsKey(location)) {
			throw new DataAccessException("Memory access exception");
		}
		return data.get(location);
	}
	
	public void setMemory(int location, int value) throws DataAccessException  {
		if (0 <= location && location < MEMORY_MAX_SIZE) {
			data.put(location, value);
		} else {
			throw new DataAccessException("Memory access exception");
		}
	}
	
	public boolean isHalted() {
		return halted;
	}

	public void setHalted(boolean b) {
		halted = b;
	}
	
	public void printMemory() {
		System.out.println(data);
	}
	
	public void colorChange() {
		
	}
}
