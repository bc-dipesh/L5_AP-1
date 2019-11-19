
/**
 * The main graphical panel used to display conversion components.
 * 
 * @author Dipesh B.C.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.DefaultComboBoxModel;
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

	// Necessary variables
	private final static String[] conversion_list = { "inches/cm", "Miles/Kilometres", "Pounds/Kilograms",
			"Gallons/Litres", "Feet/Metres", "Celsius/Kelvin", "Acres/Hectare" };
	private JTextField txtfl_input;
	private JLabel lbl_result;
	private JLabel lbl_ccount;
	private JComboBox<String> combo_actions;
	JCheckBox chkbox_reverse;
	private int ccount;

	// Sets up Menubar and implement action listener
	// Returns the Menubar
	JMenuBar setupMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu mn_file = new JMenu("File");
		JMenu mn_help = new JMenu("Help");

		// Setup shortcut keys
		mn_file.setMnemonic(KeyEvent.VK_F4);
		mn_help.setMnemonic(KeyEvent.VK_H);

		menuBar.add(mn_file);
		menuBar.add(mn_help);

		JMenuItem item_exit = new JMenuItem("Exit");
		mn_file.add(item_exit);

		JMenuItem item_about = new JMenuItem("About");
		mn_help.add(item_about);

		item_about.addActionListener(new ActionListener() { // Show message dialog about our information.
			@Override
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(null,
						"This is a test about\n" + "@Copyright 2019-2020 B.C. softwares all right reserved.", "About",
						JOptionPane.INFORMATION_MESSAGE);
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

		return menuBar;
	}

	// Constructor: Sets up the GUI and implement action listener for swing
	// components
	MainPanel() {
		ccount = 0;
		ActionListener listener = new ConvertListener();

		combo_actions = new JComboBox<String>(conversion_list);
		combo_actions.setToolTipText("Available conversion units");
		combo_actions.addActionListener(listener); // convert values when option changed

		JLabel inputLabel = new JLabel("Enter value:");

		JButton btn_convert = new JButton("Convert");
		btn_convert.setToolTipText("Click here to convert the value inside the textfield");
		btn_convert.addActionListener(listener); // convert values when pressed

		JButton btn_clear = new JButton("Clear");
		btn_clear.setToolTipText("Click here to clear the textfield and reset the counter convertion");
		btn_clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				txtfl_input.setText("");
				lbl_result.setText("---");
				lbl_ccount.setText("---");
				ccount = 0;
			}
		});

		chkbox_reverse = new JCheckBox("Reverse conversion");
		chkbox_reverse.setToolTipText("Reverse the conversion");
		chkbox_reverse.setBackground(Color.LIGHT_GRAY);
		chkbox_reverse.addActionListener(listener);

		lbl_result = new JLabel("---");
		lbl_result.setToolTipText("convert value");
		lbl_ccount = new JLabel("---");
		lbl_ccount.setToolTipText("conversion count");
		txtfl_input = new JTextField(5);
		txtfl_input.setToolTipText("Input value here to convert");
		txtfl_input.addActionListener(listener);

		add(chkbox_reverse);
		add(combo_actions);
		add(inputLabel);
		add(txtfl_input);
		add(btn_convert);
		add(btn_clear);
		add(lbl_result);
		add(lbl_ccount);

		setPreferredSize(new Dimension(800, 80));
		setBackground(Color.LIGHT_GRAY);
	}

	// Represents action listener for convert button
	private class ConvertListener implements ActionListener {

		// implements program logic
		@Override
		public void actionPerformed(ActionEvent event) {

			String text = txtfl_input.getText().trim();
			DecimalFormat df = new DecimalFormat(".##");
			String lbl = "Conversion: ";
			double value = 0.0;
			double result = 0.0;

			if ((event.getSource() == combo_actions || event.getSource() == chkbox_reverse) && text.isEmpty()) {
			}

			else if (text.isEmpty() == false) {

				try {
					value = Double.parseDouble(text);

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
