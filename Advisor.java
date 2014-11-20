import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import java.io.*;
import java.util.*;


public class Advisor {
	// main frame and panel of the menu
	static JFrame frame;
	static JPanel panel;
	
	// all of the buttons in the menu
	static JButton students, records, Graduation, Edit, View, Delete, Add, submit_changes, Logout, reset;
	static boolean isClicked = false;
	
	static File user_cred; // user credentials file
	
	// reading and writing variables
	static Scanner scan; // scanner for misc use
	static BufferedReader reader;
	static FileWriter file_writer;
	
	// label declaring which button has been pressed (for testing purposes)
	static JLabel selected = new JLabel("None Selected");
	
	// list containing the student information
	static DefaultListModel<String> model = new DefaultListModel<>();
	static JList list = new JList<String>(model);
	
	// text fields used for input to add/edit students
	private static JTextField name_textField;
	private static JTextField ID_textField;
	private static JTextField grade_textField;;
	
	static JScrollBar scrollBar = new JScrollBar(); //sets up a scroll bar to scroll through all the students
	static boolean adding = false; // whether to take the input as adding or editing a student
	
	static studentList studs; // queue containing students and their information
	
	public static void main(String[] args) throws FileNotFoundException{
		Advisor();
	}

	public static void Advisor() throws FileNotFoundException{	
		frame = new JFrame();
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		studs = new studentList();
		
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				studs.close();
			}
		});
		
		// students menu button
		students = new JButton("Students");
		students.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				records.setVisible(false);
				Graduation.setVisible(false);
				Add.setVisible(true);
				View.setVisible(true);
				Delete.setVisible(true);
				Edit.setVisible(true);
				
				redrawList();
				
				selected.setText("students selected");
				
			}
		});
		
		// records menu button
		records = new JButton("Records");
		records.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			selected.setText("records selected");
			}
		});
		
		// graduation menu button
		Graduation = new JButton("Graduation");
		Graduation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("graduation selected");
			}
		});
		
		// logout button
		Logout = new JButton("Logout");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					studs.close();
					frame.setVisible(false);
					Login.Login();// close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
		});
		
		// reset menu button
		reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.clear();
				
				name_textField.setText("");
				ID_textField.setText("");
				grade_textField.setText("");
			
				records.setVisible(true);
				Graduation.setVisible(true);
				Add.setVisible(false);
				Edit.setVisible(false);
				View.setVisible(false);
				Delete.setVisible(false);
			}
		});
		
		// add students button
		Add = new JButton("Add");
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit_changes.setText("Add Student");
				
				adding = true;
			
				selected.setText("Add Selected");
			}
		});
		
		// edit students button
		Edit = new JButton("Edit");
		Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit_changes.setText("Edit Student");
				
				adding = false;
			
				selected.setText("Edit Selected");
			}
		});
		
		// view button (may remove)
		View = new JButton("View");
		View.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redrawList();
				
				selected.setText("View Selected");
			}
		});
		
		// delete students button
		Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scan = new Scanner(list.getSelectedValue().toString());
				scan.useDelimiter("Name:| ID:| Grade:|\\n|\\r");
				
				scan.next();
				studs.removeNode(scan.nextInt());
				
				redrawList();
				
				selected.setText("Delete Selected");
			}
		}); 
		
		//set Add, View, Delete, Edit, to invisible upon start
		Add.setVisible(false);
		View.setVisible(false);
		Delete.setVisible(false);
		Edit.setVisible(false);		
	    
		// input field for name
		name_textField = new JTextField();
		name_textField.setColumns(10);
		
		// input field for id
		ID_textField = new JTextField();
		ID_textField.setColumns(10);
		
		// input field for grade
		grade_textField = new JTextField();
		grade_textField.setColumns(10);
		
		// labels above the input fields
		JLabel ID_Label = new JLabel("ID");
		JLabel grade_Label = new JLabel("Grade");
		JLabel name = new JLabel("Name");
		
		// button to submit the information in the input fields
		submit_changes = new JButton("Edit Student");
		submit_changes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = null;
				int newID = -1;
				String newGrade = null;
				int id;
				
				// set these variables using the input text fields
				try{
					newName = name_textField.getText();
				} catch(Exception ex){ System.out.println("Exception: submit_changes button; newName"); }
				try{
					newID = Integer.parseInt(ID_textField.getText());
				} catch(Exception ex){ System.out.println("Exception: submit_changes button; newID"); }
				try{
					newGrade = grade_textField.getText();
				} catch(Exception ex){ System.out.println("Exception: submit_changes button; newGrade"); }
				
				if(adding){
					if(!studs.contains(newID) && newName.length() > 0 && newGrade.length() > 0 && newID > 0)
						studs.addNode(newName, newID, newGrade);
				}
				else{ // edit student information if a corresponding field is not blank
					try{
						scan = new Scanner(list.getSelectedValue().toString());
						scan.useDelimiter("Name:| ID:| Grade:|\\n|\\r");
						scan.next();
						id = scan.nextInt();
					
						if(newName != null && newName.length() > 0)
							studs.editNode(id, newName);
						if(ID_textField.getText() != null && newID > 0)
							studs.editNode(id, newID);
						if(newGrade != null && newGrade.length() > 0)
							studs.editNode(id, newGrade, 0);
					} catch(NullPointerException npe){}
				}
				
				name_textField.setText("");
				ID_textField.setText("");
				grade_textField.setText("");
				
				redrawList();
			}
		});
		
		//Layout for all the button, labels, and other UI stuff
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(55)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(records, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(students, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(Graduation, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(Edit, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
								.addComponent(Add, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
								.addComponent(View, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
								.addComponent(Delete, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(Logout)
							.addGap(18)
							.addComponent(reset)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(selected)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(32)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(submit_changes, Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
													.addGap(12)
													.addComponent(name))
												.addComponent(name_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(ID_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGroup(groupLayout.createSequentialGroup()
													.addGap(12)
													.addComponent(ID_Label)))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(grade_Label)
													.addGap(71))
												.addComponent(grade_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addComponent(list, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE))
									.addGap(6)
									.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addGap(85))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(93, Short.MAX_VALUE)
					.addComponent(selected)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(records)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(students)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(Graduation)
										.addComponent(Edit)))
								.addComponent(Add))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(View)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Delete)
							.addPreferredGap(ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(Logout)
								.addComponent(reset))
							.addGap(80))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(list, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(ID_Label)
										.addComponent(grade_Label)
										.addComponent(name))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(9)
											.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(ID_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(grade_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(name_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(submit_changes)
							.addContainerGap())))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
		
	}
	
	// clear the list and rewrite the contents
	private static void redrawList(){
		model.clear(); // clear the list
		
		// add each element of the student queue to the list
		for(int i = 0; i < studs.getSize(); i++){
			try{
				model.addElement(studs.getNode(i, 0).toString());
			}catch(NullPointerException e){}
		}
	} // redrawList
}

