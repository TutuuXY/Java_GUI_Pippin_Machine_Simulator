package ProgramView;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import Program.PippinMachine;

/**
 * This class is the object of a single row
 * of the codeViewPanel and all the data and 
 * methods provided to support the GUI.
 * @author Yuan Xu
 *
 */
public class CodeRow {
	private JLabel label;
	private JTextField CodeField;
	private JTextField BiField;
	private PippinMachine machine;

	/**
	 * Explicit constructor
	 * Initialization of instance field.
	 * @param i the order of the line
	 * @param machine the machine object
	 */
	public CodeRow(int i, PippinMachine machine) {
		this.machine = machine;
		label = new JLabel(" " + i + ": ", JLabel.RIGHT);
		label.setOpaque(true);

		CodeField = new JTextField(8);
		BiField = new JTextField(20);
		
		resetColor();
	}
	
	/**
	 * Getter of label
	 * @return value of label
	 */
	public JLabel getLabel() {
		return label;
	}
	
	/**
	 * Getter of CodeField
	 * @return value of CodeField
	 */
	public JTextField getCodeField() {
		return CodeField;
	}
	
	/**
	 * Getter of BiField
	 * @return value of BiField
	 */
	public JTextField getBiField() {
		return BiField;
	}
	
	/**
	 * Get the content of CodeField
	 * @return the content of CodeField
	 */
	public String getCode() {
		return CodeField.getText();
	}
	
	/**
	 * Get the content of BiField
	 * @return the content of BiField
	 */
	public String getBinary() {
		return BiField.getText();
	}
	
	/**
	 * Set the content of BiField
	 * @param the content of BiField to set
	 */
	public void setCodeField(String codeCtt) {
		CodeField.setText(codeCtt);
	}
	
	/**
	 * Set the content of BiField
	 * @param string the value to set to BiField
	 */
	public void setBiField(String string) {
		BiField.setText("" + string);
	}
	
	/**
	 * Clear the content of Both Fields
	 */
	public void clear() {
		CodeField.setText("");			
		BiField.setText("");
	}
	
	/**
	 * Reset the colors of the row
	 */
	public void resetColor() {
		label.setBackground(Color.WHITE);
		CodeField.setBackground(Color.WHITE);
		BiField.setBackground(Color.WHITE); 
	}
	
	/**
	 * Set the colors of the row.
	 */
	public void setColor() {
		label.setBackground(Color.YELLOW);
		CodeField.setBackground(Color.YELLOW); 
		BiField.setBackground(Color.YELLOW);  			
	}
	
	/**
	 * Set the editable feature of the row
	 * @param b
	 */
	public void setEditable(boolean b) {
		CodeField.setEditable(b); 
		BiField.setEditable(b); 		
	}
}
