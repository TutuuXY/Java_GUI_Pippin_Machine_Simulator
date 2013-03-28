package Program;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * A class load a assembly program.
 * @author Yuan Xu
 */
public class ExecProgram {
	/**
	 * The method loads a assembly from a text file, and it
	 * store the codes and data into the a instance of the class
	 * of Program.
	 * 
	 * @param args is not used
	 * @throws FileNotFoundException
	 * @throws DataAccessException
	 */
	public static void main(String[] args) throws FileNotFoundException, DataAccessException {
		Map<String, Instruction> insMap = new HashMap<String, Instruction>();
		insMap.put("ADD",  new ADD());
		insMap.put("ADDI", new ADDI());
		insMap.put("AND",  new AND());
		insMap.put("ANDI", new ANDI());
		insMap.put("CMPL", new CMPL());
		insMap.put("CMPZ", new CMPZ());
		insMap.put("DIV",  new DIV());
		insMap.put("DIVI", new DIVI());
		insMap.put("HALT", new HALT());
		insMap.put("JMPZ", new JMPZ());
		insMap.put("JUMP", new JUMP());
		insMap.put("LOD",  new LOD());
		insMap.put("LODI", new LODI());
		insMap.put("MUL",  new MUL());
		insMap.put("MULI", new MULI());
		insMap.put("NOP",  new NOP());
		insMap.put("NOT",  new NOT());
		insMap.put("STO",  new STO());
		insMap.put("SUB",  new SUB());
		insMap.put("SUBI", new SUBI());

		BigInteger lim = BigInteger.valueOf(Integer.MAX_VALUE);
		Scanner scn = new Scanner(new FileReader("testfile.txt"));
		ArrayList<CodeLine> codes = new ArrayList<CodeLine>();
		boolean indirect = true;
		Program prg = new Program();
		boolean isCode = true;
		int lineNO = 0;
		int lineTot = 0;
		int lineLim = -1;
		ArrayList<String> codeArrList = new ArrayList<String>();

		while(scn.hasNextLine()) {
			String temp = scn.nextLine();

			codeArrList.add(temp);
			lineTot++;
			if (temp.length() > 0) {
				lineLim = lineTot;
			}
		}

		for (int lineZ=0; lineZ<codeArrList.size(); lineZ++) {
			lineNO = lineZ + 1;
			// it prints a System.out.println with the line number and the line itself.
			String lineOri = codeArrList.get(lineZ);
			String line = lineOri.toUpperCase();

			if (line.startsWith(" ") || line.startsWith("\t")) {
				System.out.println("Error! Line "+lineNO+" "+lineOri+"\n"+"A non-blank line that starts with whitespace.");
				System.exit(0);
			}

			if ( line.length() == 0 ) {
				if ( lineNO < lineLim) {
					System.out.println("Error! Line "+lineNO+" "+lineOri+"\n"+"Blank line in the program.");
					System.exit(0);
				} else {
					continue;
				}
			}

			if (line.startsWith("//DATA")) {
				if (isCode == false) {
					System.out.println("Error! Line "+lineNO+" "+lineOri+"\n"+"More than one //data line.");
					System.exit(0);
				}

				isCode = false;
				if (line.trim().compareTo("//DATA") != 0) {
					System.out.println("Error! Line "+lineNO+" "+lineOri+"\n"+": Extra characters/elements on //data line.");
					System.exit(0);
				}
				continue;
			}

			if ( !isCode ) { // data part
				String[] result = new String[3];
				result = line.split("\\s+");
				int location = 0;

				try {
					location = Integer.parseInt(result[0].split("\\s+")[0], 16);
				} catch (NumberFormatException e) {
					System.out.println("Error! Line "+lineNO+" "+lineOri+"\n"+": Location not hexadecimal numeric.");
					System.exit(0);
				}

				int argInd = -1;
				for (int i=1; i<result.length; i++) {
					if (result[i].compareTo("")!=0) {
						argInd = i;
						break;
					}
				}

				if (argInd != -1) {
					for (int i=argInd+1; i<result.length; i++) {
						if (result[i].compareTo("")!=0) {
							System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Too many arguments.");
							System.exit(0);
						}
					}
				}

				if (argInd == -1) {
					System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Missing value part.");
					System.exit(0);
				} else {
					int val = 0;
					try {
						BigInteger tmpnum = BigInteger.valueOf(Integer.parseInt(result[argInd], 16));
						if (tmpnum.compareTo(lim)<=0) {
							val = Integer.parseInt(result[argInd], 16);
						} else {
							System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Immediate argument out of Integer bound.");
							System.exit(0);
						}						
					} catch (NumberFormatException e) {
						System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Value not hexadecimal numeric.");
						System.exit(0);
					}

					try {
						prg.setMemory(location, val); // set value to location
					} catch (DataAccessException e) {
						System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Erroneous memory address.");
						System.exit(0);
					}
				}

			} else { // code part
				indirect = true;
				String[] result = new String[3];
				result = line.split("\\s+");

				Instruction ins = null;

				if ( insMap.containsKey(result[0].toUpperCase()) ) { 
					ins = insMap.get( result[0].toUpperCase() );
				} else {
					System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Unknown instruction.");
					System.exit(0);
				}


				int argInd = -1;
				for (int i=1; i<result.length; i++) {
					if (result[i].compareTo("")!=0) {
						argInd = i;
						break;
					}
				}

				String strtmp = result[0].toUpperCase();
				if ( strtmp.compareTo("HALT")!=0 && strtmp.compareTo("NOT")!=0 && strtmp.compareTo("NOP")!=0 && argInd == -1) {
					System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Missing argument.");
					System.exit(0);
				}

				if (strtmp.compareTo("HALT")==0 || strtmp.compareTo("NOT")==0 || strtmp.compareTo("NOP")==0) {
					for (int i=1; i<result.length; i++) {
						if (result[i].compareTo("")!=0) {
							System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Too many arguments.");
							System.exit(0);
						}
					}
				}

				if (argInd != -1) {
					for (int i=argInd+1; i<result.length; i++) {
						if (result[i].compareTo("")!=0) {
							System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Too many arguments.");
							System.exit(0);
						}
					}
				}

				if ( ins!=null ) {
					int arg = 0;
					if ( result.length > 1 ) {
						if (result[1].startsWith("%") ){
							try {
								arg = Integer.parseInt(result[argInd].substring(1), 16);
							} catch (NumberFormatException e) {
								System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Argument not hexadecimal numeric.");
								System.exit(0);
							}
						} else {
							try {
								BigInteger tmpnum = BigInteger.valueOf(Integer.parseInt(result[argInd], 16));
								if (tmpnum.compareTo(lim)<=0) {
									arg = Integer.parseInt(result[argInd], 16);
								} else {
									System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Immediate argument out of Integer bound.");
									System.exit(0);
								}
							} catch (NumberFormatException e) {
								System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+": Argument not hexadecimal numeric.");
								System.exit(0);
							}
							indirect = false;
						}
					}

					if ( result.length == 1 ) {
						codes.add( new CodeLine( ins ) );
					} else {
						codes.add( new CodeLine( ins,  arg, indirect) );
					}
				}
			}
		}

		CodeLine[] codesArr = new CodeLine[codes.size()];
		codes.toArray(codesArr);

		prg.setLines(codesArr);

		Processor pro = new Processor();
		pro.setAccumulator(0);
		pro.setProgramCounter(0);
		prg.printMemory();
		System.out.println();

		/**
		 * Program counter out of range.
		 * Memory location out of range.
		 * Divide by zero errors. 
		 */

		while (!prg.isHalted()) {
			try {
				prg.execute(pro);
			} catch (CodeAccessException e) {
				JOptionPane.showMessageDialog(null, "Memory address out of bound: " + e.getMessage());
				System.exit(0);
			} catch (DataAccessException e) {
				JOptionPane.showMessageDialog(null, "Memory address illegal : " + e.getMessage());
				System.exit(0);
			} catch (DivideZeroException e) {
				JOptionPane.showMessageDialog(null, "Memory address illegal : " + e.getMessage());
				System.exit(0);
			}

			System.out.println("The accumulator is: " + pro.getAccumulator());
			System.out.println("The program counter value is: " + pro.getProgramCounter());
			prg.printMemory();
			System.out.println();
		}

		System.out.println("The program was terminated normally");
	}
}
