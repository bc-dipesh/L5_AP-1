import javax.swing.JFrame;

/**
 * The main driver program for the GUI based conversion program.
 * 
 * @author Dipesh B.C.
 */
public class Converter {

	// Sets up the GUI and displays the frame.
	public static void main(String[] args) {

		// Sets up frame and title for the frame.
		JFrame frame = new JFrame("Metric Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MainPanel panel = new MainPanel();

		frame.setJMenuBar(panel.setupMenu());

		frame.getContentPane().add(panel);

		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}
