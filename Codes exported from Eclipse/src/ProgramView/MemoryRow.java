package ProgramView;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import Program.PippinMachine;

/**
 * This class is the object of a single row
 * of the MemoryPanel and all the data and 
 * methods provided to support the GUI.
 * @author Yuan Xu
 *
 */
public class MemoryRow {
	private JLabel label;
	private JTextField ValField;
	private JTextField BiField;
	private PippinMachine machine;

	/**
	 * Explicit constructor
	 * Initialization of instance field.
	 * @param i the order of the line
	 * @param machine the machine object
	 */
	public MemoryRow(int i, PippinMachine machine) {
		this.machine = machine;
		label = new JLabel(" " + i + ": ", JLabel.RIGHT);
		label.setOpaque(true);
		RowFocusListener listener = new RowFocusListener();

		ValField = new JTextField(8);
		BiField = new JTextField(20);

		ValField.addFocusListener(listener);
		BiField.addFocusListener(listener);

		resetColor();
	}
	
	/**
	 * Set the color of the row
	 */
	public void resetColor() {
		label.setBackground(Color.WHITE);
		ValField.setBackground(Color.WHITE);
		BiField.setBackground(Color.WHITE);
	}
	
	/**
	 * Getter of the label
	 * @return the value of the label
	 */
	public JLabel getLabel() {
		return label;
	}
	
	/**
	 * Getter of the value of value field
	 * @return the value of the value field
	 */
	public JTextField getCodeField() {
		return ValField;
	}
	
	/**
	 * Getter of the value of Bi field
	 * @return the value of the Bi field
	 */
	public JTextField getBiField() {
		return BiField;
	}
	
	/**
	 * Getter of the content of CodeField
	 * @return the content of CodeField
	 */
	public String getCode() {
		return ValField.getText();
	}
	
	/**
	 * Getter of BiField
	 * @return the content of BiField
	 */
	public String getBinary() {
		return BiField.getText();
	}
	
	/**
	 * Setter of the content of CodeField
	 * @param the content of CodeField to set to
	 */
	public void setCodeField(String codeCtt) {
		ValField.setText(codeCtt);
	}
	
	/**
	 * Setter of the content of BiField
	 * @param the content of BiField to set to
	 */
	public void setBiField(String BiCd) {
		BiField.setText("" + BiCd);
	}
	
	/**
	 * Clear the content of the fields
	 */
	public void clear() {
		ValField.setText("");			
		BiField.setText("");
	}
	
	/**
	 * Set the feature of editable of the fields
	 * @param b the value to decide the feature 
	 */
	public void setEditable(boolean b) {
		ValField.setEditable(b); 
		BiField.setEditable(b); 		
	}
	
	/**
	 * listener of the row
	 * @author Yuan Xu
	 *
	 */
	private class RowFocusListener extends FocusAdapter {
		@Override
		/**
		 * Once the focus happened
		 */
		public void focusGained(FocusEvent arg0) {
			//setColor();
			
		}
		/**
		 * Once focus lost
		 */
		@Override
		public void focusLost(FocusEvent arg0) {
			//resetColor();
		}
	}
}
