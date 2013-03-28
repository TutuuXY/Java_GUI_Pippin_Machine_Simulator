package ProgramView;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Program.PippinMachine;

/**
 * This method uses create the fields in the up of the GUI
 * to show the content of accumulator, program counter and
 * the indirect checkbox.
 * @author Yuan Xu
 *
 */
public class FieldsPanel implements Observer {
	private PippinMachine machine = new PippinMachine();
	private JTextField acc = new JTextField();
	private JTextField prc = new JTextField();
	private JCheckBox cBox;
	
	/**
	 * This method create FieldsPanel to be part of GUI
	 * @param machine the reference of machine model
	 * @return the Component for building GUI
	 */
	public JComponent createFields(PippinMachine machine) {
		this.machine = machine;
		machine.addObserver(this);
		
		JPanel retVal = new JPanel();
		retVal.setLayout(new GridLayout(1, 0));
		retVal.add(new JLabel("Accumulator: ", JLabel.RIGHT));
		retVal.add(acc);
		acc.setEnabled(false);
		
		retVal.add(new JLabel("ProgramCounter: ", JLabel.RIGHT));
		retVal.add(prc);
		prc.setEnabled(false);
		
		retVal.add(new JLabel("", JLabel.RIGHT));
		cBox = new JCheckBox("Indirect");
		retVal.add(cBox);
		cBox.setEnabled(false);
		
		return retVal;
	}
	
	/**
	 * This update method takes advantage of the fact that notifyObservers
	 * can pass an informational object to the observers using its 
	 * optional argument. Here an Actions object is passed and the FieldsPanel
	 * object can direct the corresponding request to a method in one of the 
	 * components that it contains 
	 * @param arg0 the observable object generating the notification
	 * @param arg1 the Actions object indicating the request
	 */
	@Override
	public void update(Observable o, Object arg) {
		acc.setText(""+machine.getProcessor().getAccumulator());
		prc.setText(""+machine.getProcessor().getProgramCounter());
		if (machine.getProgram().getLines() == null) {
			cBox.setSelected(false);
		} else {
			cBox.setSelected(machine.getProgram().getIndirect(
					machine.getProcessor().getProgramCounter()));
		}
		
	}

}
