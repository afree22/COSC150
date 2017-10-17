package paint;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;

/*
 * Provides the GUI and encapsulates the DrawPanel
 * It creates 3 buttons save, load, and clear.
 * It creates 3 ComboBox colors, shapes, and modes
 * Has two private inner classes:
 * One for handling Button events and another for ComboBox events
 */
public class DrawFrame extends JFrame {
	private DrawPanel panel; // draw panel for the shapes
	private JButton save; // button to undo last drawn shape
	private JButton load; // button to redo an undo
	private JButton reset; // button to reset panel
	private JComboBox colors; // combobox with color options
	private JComboBox mode; // combobox with mode option (draw or edit)
	private JComboBox shapes; // combobox with shape options

	// array of strings containing color options for JComboBox colors
	private String colorOptions[] = { "Black", "Blue", "Green", "Orange", "Pink", "Red", "Yellow" };

	// aray of Color objects contating Color constants
	private Color colorArray[] = { Color.BLACK, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK, Color.RED,
			Color.YELLOW };

	// array of strings containing the shape options for JComboBox shapes
	private String shapeOptions[] = { "Line", "Rectangle", "Oval", "Triangle", "Text" };

	// array of strings containing the mode options -- draw or edit -- for JComboBox
	// mod
	private String modeOptions[] = { "Draw Shapes", "Edit Shapes" };

	private JPanel widgetJPanel; // holds the widgets: buttons, comboboxes and checkbox
	private JPanel widgetPadder; // encapsulates widgetJPanel and adds padding around the edges

	/*
	 * Draw frame constructor - it sets the name of the JFrame. It also creates a
	 * DrawPanel object that extends JPanel for drawing the shapes It initializes
	 * widgets for buttons and ComboBoxes and adds the even handlers for the widgets
	 */
	public DrawFrame() {
		super("Paint Project"); // sets the name of DrawFrame

		panel = new DrawPanel(); // create draw panel and pass in JLabel

		// create buttons for Save, Load, and Reset
		save = new JButton("Save");
		load = new JButton("Load");
		reset = new JButton("Reset");

		// create comboboxes for selecting Color, Shape, and Mode
		colors = new JComboBox(colorOptions);
		shapes = new JComboBox(shapeOptions);
		mode = new JComboBox(modeOptions);

		// JPanel object, widgetJPanel, with grid layout for widgets
		widgetJPanel = new JPanel();
		widgetJPanel.setLayout(new GridLayout(1, 6, 10, 10)); // sets padding between widgets in gridlayout

		// JPanel object, widgetPadder, with flowlayout to encapsulate and pad the
		// widgetJPanel
		widgetPadder = new JPanel();
		widgetPadder.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5)); // sets padding around the edges

		// add widgets to widgetJPanel
		widgetJPanel.add(save);
		widgetJPanel.add(load);
		widgetJPanel.add(reset);
		widgetJPanel.add(colors);
		widgetJPanel.add(shapes);
		widgetJPanel.add(mode);

		// add widgetJPanel to widgetPadder
		widgetPadder.add(widgetJPanel);

		// add widgetPadder and panel to JFrame
		add(widgetPadder, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);

		// create new ButtonHandler for button event handling
		ButtonHandler buttonHandler = new ButtonHandler();
		save.addActionListener(buttonHandler);
		load.addActionListener(buttonHandler);
		reset.addActionListener(buttonHandler);

		// create handlers for comboboxes
		ItemListenerHandler handler = new ItemListenerHandler();
		colors.addItemListener(handler);
		shapes.addItemListener(handler);
		mode.addItemListener(handler);

		// edit JFrame so it exits on close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Pack the JFrame
		pack();
		// Set the size of the JFrame, so the Buttons are visible
		setSize(1000, 700);
		// Make the JFrame visible
		setVisible(true);

	} // end DrawFrame constructor

	/*
	 * private inner class for Button handling
	 */
	private class ButtonHandler implements ActionListener {
		// handles button events
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals("Save")) {
				try {
					panel.savePaint();
				} catch (IOException e) {
					System.out.println("Error reading file");
				}
			} else if (event.getActionCommand().equals("Load")) {
				panel.loadPaint();

			} else if (event.getActionCommand().equals("Reset")) {
				panel.clearDrawing();
			}
		}
	} // end class ButtonHandler

	
	/*
	 * private inner class for ComboBox handling Tests to see if a new item has been
	 * selected Responds appropriately based on the ComboBox changed
	 */
	private class ItemListenerHandler implements ItemListener {
		public void itemStateChanged(ItemEvent event) {
			// determine whether combo box is changed
			if (event.getStateChange() == ItemEvent.SELECTED) {
				// if selected -- the currentShapeObject's color is set to
				// the color from the colorArray at index selected
				if (event.getSource() == colors) {
					panel.setCurrentShapeColor(colorArray[colors.getSelectedIndex()]);
				}
				// if selected -- the currentShapeType is set to the
				// index of the shapes array
				else if (event.getSource() == shapes) {
					panel.setCurrentShapeType(shapes.getSelectedIndex());
				}
				// if selected -- the shapeMode is set to the
				// index of the mode array
				else if (event.getSource() == mode) {
					// 0 means draw mode
					// 1 means edit mode
					panel.setShapeMode(mode.getSelectedIndex());
				}
			}
		}
	} // end class ItemListenerHandler

} // end class DrawFrame