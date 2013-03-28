package ProgramView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Program.CodeLine;
import Program.PippinMachine;

/**
 * This class is used to bulid the CodeViewPanel
 * @author Yuan Xu
 *
 */
public class CodeViewPanel implements Observer {
	private PippinMachine machine;
	private JPanel centerPanel = new JPanel();
	private CodeRow[] rows = new CodeRow[PippinMachine.MAX_CODES];
	int num = -1;
	
	/**
	 * Getter of CodeRow object
	 * @return the value of CodeRow
	 */
	public CodeRow[] getRows() {
		return rows;
	}
	
	/**
	 * Getter of CodeViewPanel to build CodeViewPanel
	 * @param machine the machine reference
	 * @return the value of CodeViewPanel object
	 */
	public JPanel getCodeView(PippinMachine machine) {
		this.machine = machine;
		machine.addObserver(this);

		for(int i = 0; i < rows.length; i++) {
			rows[i] = new CodeRow(i, machine);
		}
		
		JPanel retVal = new JPanel();
		
		retVal.setLayout(new BorderLayout());
		retVal.setBackground(Color.ORANGE);
		retVal.add(tableHeader(), BorderLayout.PAGE_START);
		
		JPanel codeArea = new JPanel();
		codeArea.setLayout(new BorderLayout());
		codeArea.setBackground(Color.CYAN);
		retVal.add(new JScrollPane(codeArea), BorderLayout.CENTER);

		JPanel numbers = new JPanel();
		numbers.setLayout(new GridLayout(0, 1));
		numbers.setBackground(Color.MAGENTA);
		
		for (CodeRow r : rows) {
			numbers.add(r.getLabel());
		}

		codeArea.add(numbers, BorderLayout.LINE_START);

		centerPanel.setBackground(Color.GREEN);
		buildMainComponent(centerPanel);
		codeArea.add(centerPanel);
		return retVal;
	}

	/**
	 * Bulid the GUI of CodeViewPanel
	 * @param centerPanel the content object of the class
	 */
	private void buildMainComponent(JPanel centerPanel) {
		centerPanel.setLayout(new BorderLayout());
		
		for(int i = 0; i < rows.length; i++) {
			rows[i] = new CodeRow(i, machine);
		}			
		
		JPanel left = new JPanel();
		left.setLayout(new GridLayout(0,1));
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(0,1));
		
		for (CodeRow r : rows) {
			left.add(r.getCodeField());
			right.add(r.getBiField());
		}
		centerPanel.add(left, BorderLayout.LINE_START);
		centerPanel.add(right, BorderLayout.CENTER);
	}
	
	/**
	 * This method build the header of the panel
	 * @return
	 */
	public JComponent tableHeader() {
		JPanel wholeHeader = new JPanel();
		wholeHeader.setLayout(new GridLayout(1,0));
		wholeHeader.add(new JLabel("Code Memory View", JLabel.CENTER));
		return wholeHeader;
	}

	/**
	 * Initialization of the panel
	 */
	public void initializeFields() {
		doUpdate();
	}

	/**
	 * Called by update method to update the panel
	 */
	private void doUpdate() {
		CodeLine[] codes = machine.getProgram().getLines();
		
		if ( codes==null ) {
			for (int i=0; i<rows.length; i++) {
				rows[i].setCodeField("");
				rows[i].setBiField("");
				rows[num].resetColor();
				rows[i].setEditable(false);
			}
		} else {
			for(int i = 0; i < codes.length && i < rows.length-1; i++) {
				String setVal1 = PippinMachine.insNam.get(codes[i].getInstr()) + " ";
				if (codes[i].isIndirect()) setVal1 += "%";
				setVal1 = setVal1 + codes[i].getArg();
				rows[i].setCodeField(setVal1);
				
				
				String setVal2 = Integer.toBinaryString(codes[i].getInstr().getOpCode());
		        if(setVal2.length() < 7) {
		            setVal2 = "0000000" + setVal2;
		            setVal2 = setVal2.substring(setVal2.length()-7);
		        }
		        
		        if (codes[i].isIndirect()) {
		        	setVal2 += "1";
		        } else {
		        	setVal2 += "0";
		        }
		        
		        String arg = Integer.toBinaryString(codes[i].getArg());
		        if(arg.length() < 32) {
		            arg = "00000000000000000000000000000000" + arg;
		            arg = arg.substring(arg.length()-32);
		        }
		        setVal2 += arg;
		        
				rows[i].setBiField(setVal2);
				rows[i].setEditable(false);
			}
			
			for(int i = codes.length; i < rows.length-1; i++) {
				rows[i].setCodeField("");			
				rows[i].setBiField("");
				rows[i].setEditable(false);
			}
			if (num!=-1) rows[num].resetColor();
			num = machine.getProcessor().getProgramCounter();
			rows[num].setColor();
		}
	}

	/**
	 * This update method takes advantage of the fact that notifyObservers
	 * can pass an informational object to the observers using its 
	 * optional argument. Here an Actions object is passed and the CodeViewPanel
	 * object can direct the corresponding request to a method in one of the 
	 * components that it contains 
	 * @param arg0 the observable object generating the notification
	 * @param arg1 the Actions object indicating the request
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		doUpdate();
	}

	/**
	 * this main is for testing only
	 * @param args is not used
	 */
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "You are calling a method " +
		"that tests CourseViewPanel only");
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CodeViewPanel view = new CodeViewPanel();
		PippinMachine machine = new PippinMachine();
		test.add(view.getCodeView(machine));
		test.setSize(610,450);
		test.setVisible(true);
		view.initializeFields();
	}
}
