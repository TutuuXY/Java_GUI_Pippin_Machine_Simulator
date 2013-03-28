package ProgramView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Program.PippinMachine;

/**
 * This class is the main class of Pippin Machine GUI,
 * which implemented Observer to act immediately after
 * the change of PippinMachine.
 * @author Yuan Xu
 *
 */
public class BigFrame implements Observer {
	private PippinMachine machine;
	private MemoryViewPanel mView = new MemoryViewPanel();
	private FieldsPanel fields = new FieldsPanel();
	private CodeViewPanel cView = new CodeViewPanel();
	private ButtonPanel buttons = new ButtonPanel();
	private int colorRow = -1;
	
	/**
	 * Explicit constructor set the machine object
	 * @param machine the machine reference
	 */
	public BigFrame(PippinMachine machine) {
		this.machine = machine;
		machine.addObserver(this);
	}
	
	/**
	 * This method create the GUI and add all the 
	 * panels to the GUI.
	 */
	private void createAndShowGUI() {
		JFrame frame = new JFrame("Pippin Simulation");
		
		frame.setLayout(new BorderLayout(4, 4));
		frame.setBackground(Color.BLUE);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setJMenuBar(MenubarSetup.getMenuBar(machine));
		JPanel centre = new JPanel();
		centre.setLayout(new GridLayout(1, 2));
		centre.add(cView.getCodeView(machine));
		centre.add(mView.getMemoryView(machine));
		frame.add(centre, BorderLayout.CENTER);
		frame.add(fields.createFields(machine), BorderLayout.PAGE_START);
		
		frame.add(buttons.createButtons(machine), BorderLayout.PAGE_END);
		frame.setSize(1000, 650);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * This update method takes advantage of the fact that notifyObservers
	 * can pass an informational object to the observers using its 
	 * optional argument. Here an Actions object is passed and the BigFrame 
	 * object can direct the corresponding request to a method in one of the 
	 * components that it contains 
	 * @param arg0 the observable object generating the notification
	 * @param arg1 the Actions object indicating the request
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}

	/**
	 * The main method of the GUI.
	 * @param args is not used.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PippinMachine machine = new PippinMachine();
				BigFrame mainFrame = new BigFrame( machine );
				mainFrame.createAndShowGUI();
			}
		});
	}
}
