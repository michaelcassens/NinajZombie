/*
 * Michael Cassens
 */
import javax.swing.JFrame;

public class Driver {

	/**
	 * This is where the program starts
	 */
	public static void main(String[] args) {

		// create the frame
		JFrame myFrame = new JFrame("Fighter");
		// set up the close operation
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// create panel
		MainPanel myPanel = new MainPanel();
		// add panel
		myFrame.getContentPane().add(myPanel);
		// pack
		myFrame.pack();
		// set visibility to true
		myFrame.setVisible(true);
	}

}
