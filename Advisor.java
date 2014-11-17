import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

class Advisor{
	JFrame mainFrame;
	JPanel mainPanel;
	JLabel headerLabel;
	public Advisor(){
		prepareGUI(); // setup the GUI
	} // Advisor constructor
	
	public static void main(String[] args){
		Advisor advisor = new Advisor();
	} // main
	
	// setup the GUI
	private void prepareGUI(){
		mainFrame = new JFrame("Advisor"); // 'Advisor' is what will be displayed at the top of the window
		mainFrame.setSize(800, 600);
		headerLabel = new JLabel("", JLabel.CENTER); // starts blank
		// make the window close when the X is pressed
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		mainPanel = new JPanel();
		JButton records = new JButton("Records");
		records.setActionCommand("Records");
		records.addActionListener(new ButtonClickListener());
		JButton submissions = new JButton("Graduation Submissions");
		submissions.setActionCommand("Submissions");
		// create the buttons
		JButton students = new JButton("Students");
		
		// sett the command that will be sent when the button is pressed
		students.setActionCommand("Students");
		
		// set the object that will listen for the button press
		students.addActionListener(new ButtonClickListener());
		
		JList list = new JList();
		
		JList list_1 = new JList();
		
		JList list_2 = new JList();
		
		JList list_3 = new JList();
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.Login();
			}
		});
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_mainPanel.createSequentialGroup()
								.addGap(326)
								.addComponent(list_2, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE))
							.addGroup(Alignment.TRAILING, gl_mainPanel.createSequentialGroup()
								.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(students)
									.addComponent(records, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
									.addComponent(submissions, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_mainPanel.createSequentialGroup()
										.addGap(44)
										.addComponent(list_1, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
										.addGap(55)
										.addComponent(list, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_mainPanel.createSequentialGroup()
										.addGap(18)
										.addComponent(list_3, GroupLayout.PREFERRED_SIZE, 509, GroupLayout.PREFERRED_SIZE)))
								.addGap(26)))
						.addComponent(btnLogout))
					.addContainerGap(578, Short.MAX_VALUE))
		);
		gl_mainPanel.setVerticalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainPanel.createSequentialGroup()
							.addComponent(students)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(records)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(submissions))
						.addComponent(list_3, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
					.addGap(40)
					.addComponent(btnLogout)
					.addGap(208)
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(list_2, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_mainPanel.createSequentialGroup()
							.addGroup(gl_mainPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(list, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
								.addComponent(list_1, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE))
							.addGap(31)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		mainPanel.setLayout(gl_mainPanel);
		GroupLayout groupLayout = new GroupLayout(mainFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(281, Short.MAX_VALUE)
					.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
					.addGap(42))
		);
		mainFrame.getContentPane().setLayout(groupLayout);
		submissions.addActionListener(new ButtonClickListener());
		mainFrame.setVisible(true);	// show the mainFrame
	} // prepareGUI
	
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