
/**
 * This class implements the Java GUI Componenets and sets them up to display a simple GUI
 * 
 * @author Dipesh B.C.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	/**
	 * Instance variables to help setup the GUI
	*/
	private final static String[] conversion_list = { "inches/cm", "Miles/Kilometres", "Pounds/Kilograms",
			"Gallons/Litres", "Feet/Metres", "Celsius/Kelvin", "Acres/Hectare" };
	private JTextField txtfl_input;
	private JLabel lbl_result;
	private JLabel lbl_ccount;
	private JComboBox<String> combo_actions;
	JCheckBox chkbox_reverse;
	private int ccount;

	/**
	 * setsup the JMenu, JMenuItem, JMenuBar, Mnemonic and ImageIcon
	 * 
	 * @return the JMenuBar
	 */
	JMenuBar setupMenu() {

		// JMenuItem initialization
		JMenuItem item_exit = new JMenuItem("Exit");
		JMenuItem item_about = new JMenuItem("About");

		// JMenu initialization
		JMenu mn_file = new JMenu("File");
		JMenu mn_help = new JMenu("Help");

		// Add JMenuItems to JMenu
		mn_file.add(item_exit);
		mn_help.add(item_about);

		// Setup ActionListener for JMenuItem
		item_about.addActionListener(new ActionListener() { // Show message dialog about our information.
			String about = "This application converts distance, weight, volume, temperature and area.\n"
					+ "Author Dipesh B.C.\n" + "Copyright © 2019 B.C.softwares.";

			@Override
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(null, about, "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		item_exit.addActionListener(new ActionListener() { // Show confirmation message dialog for quitting the program
			@Override
			public void actionPerformed(ActionEvent ae) {
				int a = JOptionPane.showConfirmDialog(null, "Are you sure?");

				if (a == JOptionPane.YES_OPTION) {
					System.exit(0);
				}

			}
		});

		// Some extra functionality
		// Setup shortcut keys
		mn_file.setMnemonic(KeyEvent.VK_F4);
		mn_help.setMnemonic(KeyEvent.VK_H);

		// Setup icons for JMenuItem
		item_exit.setIcon(new ImageIcon("Icons/exit.png"));
		item_about.setIcon(new ImageIcon("Icons/about.png"));

		// ToolTip setup for JMenu and JMenuItem
		mn_file.setToolTipText("File menu");
		mn_help.setToolTipText("Help menu");
		item_exit.setToolTipText("Exit the program");
		item_about.setToolTipText("Display information about the program");


		// JMenuBar Setup
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(mn_file);
		menuBar.add(mn_help);

		return menuBar;
	}

	/**
	 * Sets up the GUI Components and adds them to the JPanel.
	 * 
	 */
	MainPanel() {
		// initiliza counter for totalConversion.
		ccount = 0;

		// Listener for btn_convert
		ActionListener listener = new ConvertListener();

		// JCheckBox initialization
		chkbox_reverse = new JCheckBox("Reverse conversion");
		chkbox_reverse.setBackground(Color.LIGHT_GRAY);

		// Initialize JComboBox with list of Strings from the array.
		combo_actions = new JComboBox<String>(conversion_list);

		// JLabel initialization
		JLabel inputLabel = new JLabel("Enter value:");
		lbl_result = new JLabel("---");
		lbl_ccount = new JLabel("---");

		// JTextField initialization
		txtfl_input = new JTextField(5);

		// JButton initialization
		JButton btn_convert = new JButton("Convert");
		JButton btn_clear = new JButton("Clear");

		// Setup ActionListener
		combo_actions.addActionListener(listener); // convert value when option changed
		btn_convert.addActionListener(listener); // convert value when pressed
		chkbox_reverse.addActionListener(listener);	// convert value when checked
		txtfl_input.addActionListener(listener); // convert value when enter button is pressed
		btn_clear.addActionListener(new ActionListener() { // reset the JLabel(lbl_result, lbl_ccount) and ccount;
			@Override
			public void actionPerformed(ActionEvent ae) {
				txtfl_input.setText("");
				lbl_result.setText("---");
				lbl_ccount.setText("---");
				ccount = 0;
			}
		});

		// Some extra functionality
		// Setup ToolTipText
		chkbox_reverse.setToolTipText("Reverse the conversion mode, for e.g. inches/cm to cm/inches");
		combo_actions.setToolTipText("Available conversion modes");
		txtfl_input.setToolTipText("Input value to convert");
		btn_convert.setToolTipText("Button to convert the value inside the textfield");
		btn_clear.setToolTipText("Button to clear the textfield and output labels");
		lbl_result.setToolTipText("converted value");
		lbl_ccount.setToolTipText("conversion count");

		// Add GUI components to the JPanel
		add(chkbox_reverse);
		add(combo_actions);
		add(inputLabel);
		add(txtfl_input);
		add(btn_convert);
		add(btn_clear);
		add(lbl_result);
		add(lbl_ccount);

		// Setup JPanel
		setPreferredSize(new Dimension(800, 80));
		setBackground(Color.LIGHT_GRAY);
	}

	// Represents action listener for convert button
	private class ConvertListener implements ActionListener {

		// implements program logic
		@Override
		public void actionPerformed(ActionEvent event) {

			String inputValue = txtfl_input.getText().trim();
			DecimalFormat df = new DecimalFormat(".##"); // Formatter to format value with two decimal places
			String lbl = "Conversion: ";
			double value = 0.0;
			double result = 0.0;

			// Do nothing if JTextfield is empty and event is generated from JCheckBox or JComboBox 
			if ((event.getSource() == combo_actions || event.getSource() == chkbox_reverse) && inputValue.isEmpty()) {
			}

			else if (inputValue.isEmpty() == false) {

				try {
					value = Double.parseDouble(inputValue);

					// the factor applied during the conversion
					double factor = 0;

					// the offset applied during the conversion.
					double offset = 0;

					// Setup the correct factor/offset values depending on required conversion
					switch (combo_actions.getSelectedIndex()) {

					case 0: // inches/cm
						factor = 2.54;
						break;

					case 1: // Miles/Km
						factor = 1.60934;
						break;
					case 2: // Pounds/Kilogram
						factor = 0.453592;
						break;
					case 3: // Gallons/Litre
						factor = 3.78541;
						break;
					case 4: // Feet/Metres
						factor = 0.3048;
						break;
					case 5: // Celsius/Kelvin
						factor = 1;
						offset = 273.15;
						break;
					case 6: // Acres/Hectares
						factor = 0.404686;
						break;
					}

					if (ccount > 1) {
						lbl = "Conversions: ";
					} else {
						lbl = "Conversion: ";
					}

					// Check if reverse conversion is checked
					if (chkbox_reverse.isSelected()) {
						result = (value - offset) / factor;
						++ccount;

					} else {
						result = factor * value + offset;
						++ccount;
					}
					// Sets up value for JLabel
					lbl_result.setText(df.format(result));
					lbl_ccount.setText(lbl + Integer.toString(ccount));

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Only numbers allowed", "Error: " + ex.getMessage(),
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please input a value", "Error[0x0]: Empty field",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
