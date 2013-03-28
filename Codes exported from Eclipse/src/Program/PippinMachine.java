package Program;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * A class load a assembly program.
 * @author Yuan Xu
 */
public class PippinMachine extends Observable {
	private boolean animation = false;
	public static int MAX_CODES = 256;
	private Timer timer;
	private Processor pro;
	private Program prg;
	private String filename;
	private Map<String, Instruction> insMap;
	public static Map<Instruction, String> insNam;

	/**
	 * Getter of animation
	 * @return the value of animation
	 */
	public boolean getAnimation() {
		return animation;
	}

	/**
	 * default constructor
	 * start the Timer to enable the executing of
	 * run/suspend method.
	 */
	public PippinMachine() {
		startTimer();
	}

	/**
	 * Setter of animation
	 * @param animation the value to set
	 */
	public void setAnimation(boolean animation) {
		this.animation = animation;
	}

	/**
	 * Getter of a Processor object pro
	 * @return the processor object
	 */
	public Processor getProcessor() {
		return pro;
	}

	/**
	 * Getter of a Program object prg
	 * @return the program object
	 */
	public Program getProgram() {
		return prg;
	}

	/**
	 * Getter of insMap
	 * @return insMap
	 */
	public Map<String, Instruction> getInsMap() {
		return insMap;
	}

	/**
	 * Getter of loaded program name.
	 * Use JFileChooser to be more graphical. 
	 * @throws FileNotFoundException
	 */
	public void getFile() throws FileNotFoundException {
		JFileChooser chooser = new JFileChooser("C:\\Users\\Yuan Xu\\workspace\\final");
		chooser.showOpenDialog(null);
		load(chooser.getSelectedFile().getPath());
	}

	/**
	 * This method load a program from the filename parameter
	 * and then check compiling errors.
	 * @param filename is the name of the file to be loaded
	 * @throws FileNotFoundException
	 */
	public void load(String filename) throws FileNotFoundException {
		this.filename = filename;
		insMap = new HashMap<String, Instruction>();
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
		insNam = new HashMap<Instruction, String>();

		for (String d : insMap.keySet()) {
			insNam.put(insMap.get(d), d);
		}

		BigInteger lim = BigInteger.valueOf(Integer.MAX_VALUE);
		BigInteger limMin = BigInteger.valueOf(Integer.MIN_VALUE);

		Scanner scn = new Scanner(new FileReader(filename));

		ArrayList<CodeLine> codes = new ArrayList<CodeLine>();
		boolean indirect = true;
		prg = new Program();
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
			System.out.println(temp);
		}

		for (int lineZ=0; lineZ<codeArrList.size(); lineZ++) {
			lineNO = lineZ + 1;
			// it prints a System.out.println with the line number and the line itself.
			String lineOri = codeArrList.get(lineZ);
			String line = lineOri.toUpperCase();

			if (line.startsWith(" ") || line.startsWith("\t")) {
				System.out.println("Error! Line "+lineNO+" "+lineOri+"\n"+"A non-blank line that starts with whitespace.");
				JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"A non-blank line that starts with whitespace.");
				System.exit(0);
			}

			if ( line.length() == 0 ) {
				if ( lineNO < lineLim) {
					System.out.println("Error! Line "+lineNO+" "+lineOri+"\n"+"Blank line in the program.");
					JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Blank line in the program.");
					System.exit(0);
				} else {
					continue;
				}
			}

			if (line.startsWith("//DATA")) {
				if (isCode == false) {
					System.out.println("Error! Line "+lineNO+" "+lineOri+"\n"+"More than one //data line.");
					JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"More than one //data line.");
					System.exit(0);
				}

				isCode = false;
				if (line.trim().compareTo("//DATA") != 0) {
					System.out.println("Error! Line "+lineNO+" "+lineOri+"\n"+"Extra characters/elements on //data line.");
					JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Extra characters/elements on //data line.");
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
					System.out.println("Error! Line "+lineNO+" "+lineOri+"\n"+"Location not hexadecimal numeric.");
					JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Location not hexadecimal numeric.");
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
							System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Too many arguments.");
							JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Too many arguments.");
							System.exit(0);
						}
					}
				}

				if (argInd == -1) {
					System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Missing value part.");
					JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Missing value part.");
					System.exit(0);
				} else {
					int val = 0;
					try {
						BigInteger tmpnum = new BigInteger(result[argInd], 16);
						if (tmpnum.compareTo(lim)<=0 && tmpnum.compareTo(limMin)>=0) {
							val = Integer.parseInt(result[argInd], 16);
						} else {
							System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Immediate argument out of Integer bound.");
							JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Immediate argument out of Integer bound.");
							System.exit(0);
						}						
					} catch (NumberFormatException e) {
						System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Value not hexadecimal numeric.");
						JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Value not hexadecimal numeric.");
						System.exit(0);
					}

					try {
						prg.setMemory(location, val); // set value to location
					} catch (DataAccessException e) {
						System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Location out of memory range.");
						JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Location out of memory range.");
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
					System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Unknown instruction.");
					JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Unknown instruction.");
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
					System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Missing argument.");
					JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Missing argument.");
					System.exit(0);
				}

				if (strtmp.compareTo("HALT")==0 || strtmp.compareTo("NOT")==0 || strtmp.compareTo("NOP")==0) {
					for (int i=1; i<result.length; i++) {
						if (result[i].compareTo("")!=0) {
							System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Too many arguments.");
							JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Too many arguments.");
							System.exit(0);
						}
					}
				}

				if (argInd != -1) {
					for (int i=argInd+1; i<result.length; i++) {
						if (result[i].compareTo("")!=0) {
							System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Too many arguments.");
							JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Too many arguments.");
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
								if (arg < 0 || arg >= Program.MEMORY_MAX_SIZE) {
									System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Location out of memory range.");
									JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Location out of memory range.");
									System.exit(0);
								}
							} catch (NumberFormatException e) {
								System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Argument not hexadecimal numeric.");
								JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Argument not hexadecimal numeric.");
								System.exit(0);
							}
						} else {
							try {
								BigInteger tmpnum = new BigInteger(result[argInd], 16);
								if (tmpnum.compareTo(lim)<=0 && tmpnum.compareTo(limMin)>=0) {
									arg = Integer.parseInt(result[argInd], 16);
								} else {
									System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Immediate argument out of Integer bound.");
									JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Immediate argument out of Integer bound.");
									System.exit(0);
								}
							} catch (NumberFormatException e) {
								System.out.print("Error! Line "+lineNO+" "+lineOri+"\n"+"Argument not hexadecimal numeric.");
								JOptionPane.showMessageDialog(null, "Error! Line "+lineNO+" "+lineOri+"\n"+"Argument not hexadecimal numeric.");
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

		pro = new Processor();
		pro.setAccumulator(0);
		pro.setProgramCounter(0);
		prg.printMemory();
		System.out.println();

		setChanged();
		notifyObservers();
	}

	/**
	 * This method execute a single instruction every time
	 * and be interactive with the GUI to highlight the code.
	 */
	public void step() {
		if (!prg.isHalted()) {
			try {
				prg.execute(pro);
			} catch (CodeAccessException e) {
				String msg;
				
				if (pro.getProgramCounter() < 0 || pro.getProgramCounter() >= Program.MEMORY_MAX_SIZE) {
					JOptionPane.showMessageDialog(null, "Error! Line "+ pro.getProgramCounter() + " "+
							"\n"+"Code Access Exception: " + e.getMessage());//Memory address out of bound
					System.out.println("Error! Line "+ pro.getProgramCounter() + " "+
							"\n"+"Code Access Exception: " + e.getMessage());
					System.exit(0);
				} else {
					CodeLine curCode = prg.getLines()[pro.getProgramCounter()];
					msg = PippinMachine.insNam.get(curCode.getInstr()) + " ";
					if (curCode.isIndirect()) msg += "%";
					msg = msg + curCode.getArg();

					JOptionPane.showMessageDialog(null, "Error! Line "+ pro.getProgramCounter() + " "+
							msg+"\n"+"Code Access Exception: " + e.getMessage());//Memory address out of bound
					System.out.println("Error! Line "+ pro.getProgramCounter() + " "+
							msg+"\n"+"Code Access Exception: " + e.getMessage());
					System.exit(0);
				}
			} catch (DataAccessException e) {
				CodeLine curCode = prg.getLines()[pro.getProgramCounter()];
				String msg = PippinMachine.insNam.get(curCode.getInstr()) + " ";
				if (curCode.isIndirect()) msg += "%";
				msg = msg + curCode.getArg();


				JOptionPane.showMessageDialog(null, "Error! Line "+ pro.getProgramCounter() + " "+
						msg+"\n"+"Data Access Exception: " + e.getMessage());
				System.out.println("Error! Line "+ pro.getProgramCounter() + " "+
						msg+"\n"+"Data Access Exception: " + e.getMessage());
				System.exit(0);
			} catch (DivideZeroException e) {
				CodeLine curCode = prg.getLines()[pro.getProgramCounter()];
				String msg = PippinMachine.insNam.get(curCode.getInstr()) + " ";
				if (curCode.isIndirect()) msg += "%";
				msg = msg + curCode.getArg();

				JOptionPane.showMessageDialog(null, "Error! Line "+ pro.getProgramCounter() + " "+
						msg+"\n"+"Divided by Zero!");
				System.out.println("Error! Line "+ pro.getProgramCounter() + " "+
						msg+"\n"+"Divided by Zero!");
				System.exit(0);
			}
			System.out.println("The accumulator is: " + pro.getAccumulator());
			System.out.println("The program counter value is: " + pro.getProgramCounter());
			prg.printMemory();
			System.out.println();
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * This method clear all the GUI fields and 
	 * the loaded program data.
	 */
	public void clear() {
		prg.setHalted(true);
		pro.setAccumulator(0);
		pro.setProgramCounter(0);
		prg.setLines(null);
		prg.setMemoryClear();
		setChanged();
		notifyObservers();
	}

	/**
	 * This method reload the same program.
	 * @throws FileNotFoundException
	 */
	public void reload() throws FileNotFoundException {
		load(filename);
		setChanged();
		notifyObservers();
	}

	/**
	 * set the boolean value of animation to the opposite
	 * of it.
	 * @throws InterruptedException
	 */
	public void run() throws InterruptedException {
		setAnimation( !animation );
	}

	/**
	 * startTimer check executing condition every some
	 * time, which is used to implement the run/suspend
	 * function.
	 */
	public void startTimer() {
		timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(animation) {
					step();
				}
			}
		});
		timer.start();
	}

	/**
	 * This method is used to test the error checking of
	 * Pippin Machine Model.
	 * @param args is used to get load file name.
	 */
	public static void main(String args[]) {
		PippinMachine test = new PippinMachine();
		try {
			test.load(args[0]);
		} catch (FileNotFoundException e) {

		}
		System.exit(0); //This line will get commented out for the run-time tests
		if (test.getProgram() != null){
			Processor proc = test.getProcessor();
			while(!test.getProgram().isHalted()){
				try {
					test.step();
				} catch (CodeAccessException e) {

				}
				System.out.println("Acc:  "+proc.getAccumulator());
				System.out.println("PC:   "+proc.getProgramCounter());
				test.getProgram().printMemory();
			}
		}
	}
}
