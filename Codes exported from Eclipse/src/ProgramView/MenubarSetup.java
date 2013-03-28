package ProgramView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import Program.PippinMachine;

/**
 * This class is used to build the menu bar for loading program
 * @author Yuan Xu
 *
 */
public class MenubarSetup {
	private static PippinMachine machine;
	
	/**
	 * Getter of MenuBar to build the GUI
	 * @param machine the reference of machine model
	 * @return the MenuBar object
	 */
	public static JMenuBar getMenuBar(PippinMachine machine) {
		
		MenubarSetup.machine = machine;
		JMenuBar bar = new JMenuBar();
		JMenu genMenu = new JMenu("File");
		genMenu.setMnemonic(KeyEvent.VK_F);
		bar.add(genMenu);

		JMenuItem trans = new JMenuItem("Translate Source...");
		trans.setMnemonic(KeyEvent.VK_T);
		trans.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		genMenu.add(trans);
		trans.addActionListener(new TranslateListener());
		
		JMenuItem load = new JMenuItem("Load Source...");
		load.setMnemonic(KeyEvent.VK_L);
		load.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		genMenu.add(load);
		load.addActionListener(new LoadListener());
		
		JMenuItem ex = new JMenuItem("Exit");
		ex.setMnemonic(KeyEvent.VK_E);
		ex.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		genMenu.add(ex);
		ex.addActionListener(new ExitListener());
		
		// set up a similar JMenuItem "Generate Students" with S as the 
		// for the shortcuts, add the RandomStudentListener to this JMenuItem
		return bar;
	}
	
	/**
	 * This is the actionListener of translate action
	 * @author Yuan Xu
	 *
	 */
	private static class TranslateListener implements ActionListener {
		@Override
		/**
		 * methods called once an event happened.
		 */
		public void actionPerformed(ActionEvent arg0) {
				
		}
	}

	/**
	 * This is the actionListener of load action
	 * @author Yuan Xu
	 *
	 */
	private static class LoadListener implements ActionListener {
		@Override
		/**
		 * methods called once an event happened.
		 */
		public void actionPerformed(ActionEvent arg0) {
			try {
				machine.getFile();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This is the actionListener of exit action
	 * @author Yuan Xu
	 *
	 */
	private static class ExitListener implements ActionListener {
		@Override
		/**
		 * methods called once an event happened.
		 */
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
	
}
