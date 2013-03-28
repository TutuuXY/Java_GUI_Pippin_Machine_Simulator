package ProgramView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.Timer;

/**
 * This class is the machine model of Pippin machine.
 * It includes methods of step, run, load, reload
 * interactivating with GUI.
 */
public class PippinErrorTester {
	public static final int NUM_CT_TESTS = 27;
	public static final int NUM_RT_TESTS = 7;
	
	public static final String[] SYNOPSIS = {
		"whitespace between instruction and argument",
		"whitespace after argument",
		"whitespace between data location and value",
		"whitespace after value",
		"blank lines at end of file",
		"Mixed case instruction names",
		"Non-blank line starts with whitespace",
		"Non-blank line starts with whitespace (data section)",
		"More that one //data line",
		"Extra stuff on //data line",
		"Instruction that does not require argument has one",
		"Instruction that requires argument does not have one",
		"Instruction line has too many parts (no argument version)",
		"Instruction line has too many parts (w/ argument version)",
		"Instruction not in map",
		"Argument not hex numeric",
		"Memory address argument out of range",
		"Immediate argument out of int range",
		"Location not hex numeric",
		"Value not hex numeric",
		"Location out of memory range (too large)",
		"Value out of int range (too large)",
		"Missing value part of data line",
		"blank in between code",
		"blank in between data",
		"location negative",
		"value out of range (too negative)",
		"Indirect Factorial8",
		"DataAccessException too large location",
		"DataAccessException negative location",
		"DIV by 0",
		"DIVI by 0",
		"CodeAccessException negative pc",
		"CodeAccessException too large pc"
	};
	
	public static final int[] GOOD_TEST_NUMS = {1,2,3,4,5,6,28};
	public static String[] commands = new String[5];
	public static Set<Integer> goodTests = new HashSet<Integer>();
	
	/**
	 * @param args not used
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		//TODO CHANGE THIS IF YOU WANT
		String names = "error_report.txt";
		
		//Do some setup
		for(int i : GOOD_TEST_NUMS)
			goodTests.add(i);
		
		commands[0] = "java";
		commands[1] = "-cp";
		commands[2] = "bin";
		//TODO This will have to be your package name and your
		//program name.
		commands[3] = "Program.PippinMachine";
		
		PrintWriter out = new PrintWriter(new FileWriter(names+"_A9_graded.txt"));
		
		out.println("WARNING:\n" +
				"line numbers in any exception messages might be\n" +
				"slightly incorrect due to minor changes I made to\n" +
				"get this tester to run.");
		out.println("COMPILE TIME TESTS");
		System.out.println("COMPILE TIME TESTS");

		
		for(int i = 1; i <= NUM_CT_TESTS; ++i)
			runAndReportTest(i, out);
		
		System.out.println("\nRUN TIME TESTS");
		System.out.println("Remove the System.exit in their code!");
		System.out.println("Press enter when done.");
		out.println("\nRUN TIME TESTS");
		new Scanner(System.in).nextLine();
		
		for(int i = NUM_CT_TESTS+1; i <= NUM_CT_TESTS+NUM_RT_TESTS; ++i){
			out.flush(); //Here b/c people often had infinite loops when executing.
			//flushing will write all of the output buffer to the file.
			runAndReportTest(i, out);
		}
		
		out.close();
	}
	
	/**
	 * Call their PippinMachine to execute a test.  Report the output
	 * in the given file.
	 * @param testNum the number of the test to execute
	 * @param out the destination of their report information
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void runAndReportTest(int testNum, final PrintWriter out) throws IOException, InterruptedException{
		commands[4] = "PippinTests/"+testNum+".pip";
		System.out.println(commands[4]+"\n==============");
		out.println(commands[4]+"\n==============");
		out.println("SYNOPSIS: "+SYNOPSIS[testNum-1]);
		out.println("EXPECTED: "+ (goodTests.contains(testNum)?"NO ERROR":"ERROR"));
		
		out.println("CONTENTS:");
		Scanner file = new Scanner(new FileReader(commands[4]));
		while(file.hasNextLine())
			out.println(file.nextLine());
		file.close();
		
		out.println("BEGIN PROGRAM OUTPUT:");
		
		ProcessBuilder builder = new ProcessBuilder();
		builder.redirectErrorStream(true);
		builder.command(commands);
		final Process proc = builder.start();
		//I have a timer so I can prevent infinite loops from
		//killing this program.
		Timer deadMan = new Timer(5000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				out.println("TERMINATING, POSSIBLE INFINITE LOOP");
				if(proc != null)
					proc.destroy();				
			}
		});
		deadMan.start();
		
		Scanner messages = new Scanner(proc.getInputStream());
		
		while(messages.hasNextLine()){
			String line = messages.nextLine();
			System.out.println(line);
			out.println(line);
		}
		
		proc.waitFor();
		deadMan.stop();
		out.println();
	}

}
