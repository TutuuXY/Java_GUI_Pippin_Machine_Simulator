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
import Program.DataAccessException;
import Program.PippinMachine;

/**
 * This class is used to bulid the CodeViewPanel
 * @author Yuan Xu
 *
 */
public class MemoryViewPanel implements Observer {
	private PippinMachine machine;
	private JPanel centerPanel = new JPanel();
	private MemoryRow[] rows = new MemoryRow[PippinMachine.MAX_CODES];

	/**
	 * Getter of CodeViewPanel to build the MemoryView
	 * @param machine the machine reference
	 * @return the value of CodeViewPanel object
	 */
	public JPanel getMemoryView(PippinMachine machine) {
		this.machine = machine;
		machine.addObserver(this);

		for(int i = 0; i < rows.length; i++) {
			rows[i] = new MemoryRow(i, machine);
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
		
		for (MemoryRow r : rows) {
			numbers.add(r.getLabel());
		}

		codeArea.add(numbers, BorderLayout.LINE_START);

		centerPanel.setBackground(Color.GREEN);
		buildMainComponent(centerPanel);
		codeArea.add(centerPanel);
		return retVal;
	}

	/**
	 * Bulid the GUI of MemoryPanel
	 * @param centerPanel the content object of the class
	 */
	private void buildMainComponent(JPanel centerPanel) {
		centerPanel.setLayout(new BorderLayout());
		
		for(int i = 0; i < rows.length; i++) {
			rows[i] = new MemoryRow(i, machine);
		}	
		JPanel left = new JPanel();
		left.setLayout(new GridLayout(0,1));
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(0,1));
		
		for (MemoryRow r : rows) {
			left.add(r.getCodeField());
			right.add(r.getBiField());
		}
		centerPanel.add(left, BorderLayout.LINE_START);
		centerPanel.add(right, BorderLayout.CENTER);
		
	}
	
	/**
	 * This method build the header of the panel
	 * @return the panel object
	 */
	public JComponent tableHeader() {
		JPanel wholeHeader = new JPanel();
		wholeHeader.setLayout(new GridLayout(1,0));
		wholeHeader.add(new JLabel("Data Memory View", JLabel.CENTER));
		return wholeHeader;
	}

	/**
	 * Initialization of the panel
	 */
	public void initializeFields() {
		doUpdate();
	}

	/**
	 * Called by update to set the change
	 */
	private void doUpdate() {
		CodeLine[] codes = machine.getProgram().getLines();
		for(int i = 0; i < rows.length-1; i++) {
			int val=Integer.MAX_VALUE;
			try {
				val = machine.getProgram().getMemory(i);
				rows[i].setCodeField("" + val);
		        String arg = Integer.toBinaryString(val);
		        if(arg.length() < 32) {
		            arg = "00000000000000000000000000000000" + arg;
		            arg = arg.substring(arg.length()-32);
		        }
				rows[i].setBiField(arg);
				rows[i].setEditable(false);
			} catch(DataAccessException e) {
				rows[i].setCodeField("");
				rows[i].setBiField(""+"");
				rows[i].setEditable(false);
			}
		}
	}


	/**
	 * This update method takes advantage of the fact that notifyObservers
	 * can pass an informational object to the observers using its 
	 * optional argument. Here an Actions object is passed and the MemoryViewPanel
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
	 * This method is used to test MemoryViewPanel
	 * @param args
	 */
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "You are calling a method " +
		"that tests CourseViewPanel only");
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MemoryViewPanel view = new MemoryViewPanel();
		PippinMachine machine = new PippinMachine();
		test.add(view.getMemoryView(machine));
		test.setSize(610,450);
		test.setVisible(true);
		view.initializeFields();
	}
}
