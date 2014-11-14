import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class Advisor{
	JFrame mainFrame;
	JPanel mainPanel;
	JLabel headerLabel;
	
	public Advisor(){
		prepareGUI(); // setup the GUI
	} // Advisor constructor
	
	public static void main(String[] args){
		Advisor advisor = new Advisor();
		advisor.menu(); // add the buttons
	} // main
	
	// setup the GUI
	private void prepareGUI(){
		mainFrame = new JFrame("Advisor"); // 'Advisor' is what will be displayed at the top of the window
		mainFrame.setSize(800, 600); // size of the window
		mainFrame.setLayout(new GridLayout(3, 1)); // 3 rows, 1 column
		
		headerLabel = new JLabel("", JLabel.CENTER); // starts blank
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 1)); // 3 rows, 1 column
		
		// make the window close when the X is pressed
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		mainFrame.add(headerLabel); // add headerLabel to the mainFrame
		mainFrame.add(mainPanel); // add mainPanel to the mainFrame
		mainFrame.setVisible(true);	// show the mainFrame
	} // prepareGUI
	
	// add the buttons
	private void menu(){
		// create the buttons
		JButton students = new JButton("Students");
		JButton records = new JButton("Records");
		JButton submissions = new JButton("Graduation Submissions");
		
		// sett the command that will be sent when the button is pressed
		students.setActionCommand("Students");
		records.setActionCommand("Records");
		submissions.setActionCommand("Submissions");
		
		// set the object that will listen for the button press
		students.addActionListener(new ButtonClickListener());
		records.addActionListener(new ButtonClickListener());
		submissions.addActionListener(new ButtonClickListener());
		
		// add the buttons to mainPanel
		mainPanel.add(students);
		mainPanel.add(records);
		mainPanel.add(submissions);
		
		mainFrame.setVisible(true); // redraw mainFrame
	} // menu
	
	// listen for the buttons being pressed
	private class ButtonClickListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			// find out what command was sent
			String command = e.getActionCommand();
			
			// handle each button differently
			switch(command){
				case "Students":
					headerLabel.setText("Students button pressed");
					break;
				case "Records":
					headerLabel.setText("Records button pressed");
					break;
				case "Submissions":
					headerLabel.setText("Submissions button pressed");
					break;
				default:
					headerLabel.setText("Default");
					break;
			}
		} // actionPerformed
	} // class ButtonClickListener
} // class Advisor