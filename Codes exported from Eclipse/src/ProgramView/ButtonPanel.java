package ProgramView;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Program.PippinMachine;

public class ButtonPanel implements Observer {
	private static PippinMachine machine;
	private JButton stepButton = new JButton("Step"); 
	private JButton clearButton = new JButton("Clear"); 
	private JButton rsButton = new JButton("Run/Suspend"); 
	private JButton reloadButton = new JButton("Reload"); 

	/**
	 * Method to create the JPanel of buttons for the bottom of the main GUI
	 * including step, clear, rurn/suspend and reload buttons.
	 * The appropriate listeners are added to the buttons.
	 * @param model reference to the PippinMachine
	 * @return a JPanel configured with the buttons needed for the GUI
	 */
	public JComponent createButtons(PippinMachine machine) {
		this.machine = machine;
		machine.addObserver(this);
		
		JPanel retVal = new JPanel();
		retVal.setLayout(new GridLayout(1, 0));
		// create a JPanel called retVal, and give it a new GridLayout with
		// one row
		
		retVal.add(stepButton);
		retVal.add(clearButton);
		retVal.add(rsButton);
		retVal.add(reloadButton);
		// add createCourseButton, deleteCourseButton, createStudentButton,
		// deleteStudentButton, enrollStudentButton to retVal
		// the 5 listeners need to be defined at the end of this class, then
		
		stepButton.addActionListener(new stepListener());
		// add a new CreateCourseListener to createCourseButton
		clearButton.addActionListener(new clearListener());
		// add a new DeleteCourseListener to deleteCourseButton
		rsButton.addActionListener(new rsListener());
		// add a new CreateStudentListener to createStudentButton
		reloadButton.addActionListener(new reloadListener());
		// add a new DeleteStudentListener to deleteStudentButton
		// add a new EnrollStudentListener to enrollStudentButton
		// return retVal;
		return retVal;
	}
	
	/**
	 * This update method takes advantage of the fact that notifyObservers
	 * can pass an informational object to the observers using its 
	 * optional argument. Here an Actions object is passed and the ButtonPanel 
	 * object can direct the corresponding request to a method in one of the 
	 * components that it contains 
	 * @param arg0 the observable object generating the notification
	 * @param arg1 the Actions object indicating the request
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
	}
	
	/**
	 * This class is the actionListener of step button
	 * @author Yuan Xu
	 *
	 */
	private static class stepListener implements ActionListener {
		@Override
		/**
		 * Executing once an event happened
		 */
		public void actionPerformed(ActionEvent arg0) {
			machine.step();
		}
	}
	
	/**
	 * This class is the actionListener of clear button
	 * @author Yuan Xu
	 *
	 */
	private static class clearListener implements ActionListener {
		@Override
		/**
		 * Executing once an event happened
		 */
		public void actionPerformed(ActionEvent arg0) {
			machine.clear();
		}
	}
	
	/**
	 * This class is the actionListener of run/suspend button
	 * @author Yuan Xu
	 *
	 */
	private static class rsListener implements ActionListener {
		@Override
		/**
		 * Executing once an event happened
		 */
		public void actionPerformed(ActionEvent arg0) {
			try {
				machine.run();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This class is the actionListener of reload button
	 * @author Yuan Xu
	 *
	 */
	private static class reloadListener implements ActionListener {
		@Override
		/**
		 * Executing once an event happened
		 */
		public void actionPerformed(ActionEvent arg0) {
			try {
				machine.reload();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Tester of the ButtonPanel
	 * @param args
	 */
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "You are calling a method " +
				"that tests ButtonPanel only\n" +
				"It will NOT test the ActionListeners");
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ButtonPanel view = new ButtonPanel();
		test.add(view.createButtons(new PippinMachine()));
		test.pack();
		test.setVisible(true);
	}
}
