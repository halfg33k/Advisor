import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//import Advisor.ButtonClickListener;


public class Student {

	
		
		JFrame mainFrame;
		JPanel mainPanel;
		JLabel headerLabel;
		
		
		
		public static void main(String[] args){
			Student student = new Student();
			//student.menu(); // add the buttons
		} // main
		
		
		public Student(){
			prepareGUI(); // setup the GUI
		} // Advisor constructor
		
		// setup the GUI
		private void prepareGUI(){
			mainFrame = new JFrame("Student"); // 'Student' is what will be displayed at the top of the window
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
			JButton view_assignment = new JButton("View Assignments");
			JButton view_grades = new JButton("View Grades");
			JButton print_report = new JButton("Print Report");
			
			// sett the command that will be sent when the button is pressed
			view_assignment.setActionCommand("View Assignments");
			view_grades.setActionCommand("View Grades");
			print_report.setActionCommand("Print Report");
			
			// set the object that will listen for the button press
			view_assignment.addActionListener(new ButtonClickListener());
			view_grades.addActionListener(new ButtonClickListener());
			print_report.addActionListener(new ButtonClickListener());
			
			// add the buttons to mainPanel
			mainPanel.add(view_assignment);
			mainPanel.add(view_grades);
			mainPanel.add(print_report);
			
			mainFrame.setVisible(true); // redraw mainFrame
		} // menu
		
		// listen for the buttons being pressed
		private class ButtonClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				// find out what command was sent
				String command = e.getActionCommand();
				
				// handle each button differently
				switch(command){
					case "View Assignments":
						headerLabel.setText("View Assignment button pressed");
						break;
					case "View Grades":
						headerLabel.setText("View Grades button pressed");
						break;
					case "Print Report":
						headerLabel.setText("Print Report button pressed");
						break;
					default:
						headerLabel.setText("Default");
						break;
				}
			} // actionPerformed
		} // class ButtonClickListener
	} // class Advisor
