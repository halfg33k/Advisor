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
	
	
	/**
	 * 
	 * 		Component Section:
	 * 		Declares and Instantiates all relevant UI Componnets:
	 * 		Labels, Button, textFields, JList, and any other Relevant UI components
	 * 
	 * 
	 */
	// main frame and panel of the menu
	static JFrame frame;
	static JPanel panel;

	// all of the buttons in the menu
	static JButton students, records, Graduation, Edit, View, Delete, Add, submit_changes;	
	
	static File user_cred; // user credentials file
	
	// reading and writing variables
	static Scanner scan; 
	static BufferedReader reader;
	static FileWriter file_writer;
	
	
	//all labels for the components
	static JLabel selected = new JLabel("None Selected"); // label declaring which button has been pressed (for testing purposes)
	static JLabel label_Name = new JLabel("Name");
	static JLabel label_ID = new JLabel("ID");
	static JLabel label_Grade = new JLabel("Grade");
	
	// list containing the student information
	static DefaultListModel<String> nameModel = new DefaultListModel<>();
	static DefaultListModel<Integer> idModel = new DefaultListModel<>();
	static DefaultListModel<String> gradeModel = new DefaultListModel<>();
	static JList list_name = new JList<String>(nameModel);
	static JList list_id = new JList<Integer>(idModel);
	static JList list_grade = new JList<String>(gradeModel);
	
	// text fields used for input to add/edit students
	private static JTextField textField_Name = new JTextField();
	private static JTextField textField_ID = new JTextField();
	private static JTextField textField_Grade = new JTextField();
	
	
	
	//scroll bars for JLIst--> Name, ID, Grade
	private static JScrollBar scrollBar_name = new JScrollBar(); //sets up a scroll bar to scroll through all the students
	private static JScrollBar scrollBar_grade = new JScrollBar();
	private static JScrollBar scrollBar_id = new JScrollBar();
	static boolean adding = false; // whether to take the input as adding or editing a student
	
	static studentList studs; // queue containing students and their information
	
	

	//main method--> main entry to application
	public static void main(String[] args) throws FileNotFoundException{
		
		//Call to Advisor--> the name of the GUI: once Advisor is called:: The UI loads and adds all 
		// relevant components
		Advisor();
	}
	//laods Advisor GUI
	public static void Advisor() throws FileNotFoundException{	
		/**
		 * 				 
		 *
		 *				Setup Working Frame
		 *			To Impliment: --> individual Panels for each respective button category
		 *
		 */
		frame = new JFrame();
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		studs = new studentList();
		
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				studs.close();
			}
		});
		
		/**
		 * 
		 * 			Component Modification Section:
		 * 			Any modification or change to a UI component will be put and located in
		 * 			the following section
		 * 		
		 * 
		 */
		
		
		
		
		
		//Text fields set parameters --> Must be here, it breaks otherwise--> Dont know why
		textField_Name.setColumns(10);
		textField_ID.setColumns(10);
		textField_Grade.setColumns(10);
		
		
		
		
		
		
		
		// students menu button
		students = new JButton("Students");
		students.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				redrawList();
				
				selected.setText("students selected");
				
			}
		});//student menu button
		
		// records menu button
		records = new JButton("Records");
		records.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			selected.setText("records selected");
			}
		});//records menu button
		
		// graduation menu button
		Graduation = new JButton("Graduation");
		Graduation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("graduation selected");
			}
		}); //graduation menu button
		
		// add students button
		Add = new JButton("Add");
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = null;
				int newID = -1;
				String newGrade = null;
				int id;
				
				// set these variables using the input text fields
				try{
					newName = textField_Name.getText();
				} catch(Exception ex){ System.out.println("Exception: Add button --> newName"); }
				try{
					newID = Integer.parseInt(textField_ID.getText());
				} catch(Exception ex){ System.out.println("Exception: Add button --> newID"); }
				try{
					newGrade = textField_Grade.getText();
				} catch(Exception ex){ System.out.println("Exception: Add button --> newGrade"); }
				
				
				if(!studs.contains(newID) && newName.length() > 0 && newGrade.length() > 0 && newID > 0)
					studs.addNode(newName, newID, newGrade);
				
				textField_Name.setText("");
				textField_ID.setText("");
				textField_Grade.setText("");
				
				redrawList();
			
				selected.setText("Add Selected");
			}
		}); //add students button 
		
		// edit students button
		Edit = new JButton("Edit");
		Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit_changes.setText("Edit Student");
				
				adding = false;
			
				selected.setText("Edit Selected");
			}
		}); //edit students button
		
		// view button (may remove)
		View = new JButton("View");
		View.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redrawList();
				
				selected.setText("View Selected");
			}
		}); //view button
		
		// delete students button
		Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			/*	scan = new Scanner(list_name.getSelectedValue().toString());
				scan.useDelimiter("Name:| ID:| Grade:|\\n|\\r");
				
				scan.next();
				studs.removeNode(scan.nextInt());
				
				redrawList();*/
				
				selected.setText("Delete Selected");
			}
		});
		JLabel name_Label = new JLabel("Name");  
		
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
					newName = textField_Name.getText();
				} catch(Exception ex){ System.out.println("Exception: submit_changes button; newName"); }
				try{
					newID = Integer.parseInt(textField_ID.getText());
				} catch(Exception ex){ System.out.println("Exception: submit_changes button; newID"); }
				try{
					newGrade = textField_Grade.getText();
				} catch(Exception ex){ System.out.println("Exception: submit_changes button; newGrade"); }
				
				if(adding){
					if(!studs.contains(newID) && newName.length() > 0 && newGrade.length() > 0 && newID > 0)
						studs.addNode(newName, newID, newGrade);
				}
				else{ // edit student information if a corresponding field is not blank
					try{
						scan = new Scanner(list_name.getSelectedValue().toString());
						scan.useDelimiter("Name:| ID:| Grade:|\\n|\\r");
						scan.next();
						id = scan.nextInt();
					
						if(newName != null && newName.length() > 0)
							studs.editName(id, newName);
						if(textField_ID.getText() != null && newID > 0)
							studs.editID(id, newID);
						if(newGrade != null && newGrade.length() > 0)
							studs.editGrade(id, newGrade);
					} catch(NullPointerException npe){}
				}
				
				textField_Name.setText("");
				textField_ID.setText("");
				textField_Grade.setText("");
				
				redrawList();
			}
		});// button to submit the information in the input fields
		
	
		/**
		 * 
		 * 
		 * 		Layout Section:
		 * 					Defines a GroupLayout for each UI Component including:
		 * 						Labels, Button, TextFields, JList, and any other relvant UI component
		 * 
		 * 
		 */
		
		
		
		
		
		
		
		
		
		//Layout for all the button, labels, and other UI stuff
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(55)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(Add, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(Edit, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(View, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(Delete, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(selected))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(label_Name)
						.addComponent(textField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(records, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
						.addComponent(list_name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollBar_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(list_id, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollBar_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_ID)
						.addComponent(textField_ID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(students, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(submit_changes, Alignment.TRAILING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(12)
										.addComponent(name_Label))
									.addComponent(textField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addComponent(label_Grade)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(Graduation, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(list_grade, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollBar_grade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(textField_Grade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(records)
						.addComponent(students)
						.addComponent(Graduation))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_ID)
						.addComponent(label_Name)
						.addComponent(selected)
						.addComponent(label_Grade))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(list_name, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(Add)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Edit)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(View)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Delete))
						.addComponent(scrollBar_id, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
						.addComponent(scrollBar_grade, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
						.addComponent(list_grade, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollBar_name, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
						.addComponent(list_id, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_ID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_Grade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(113)
					.addComponent(name_Label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(submit_changes))
		);
		
		///grabs the current panes content and adds it to the Frame, then makes the frame visible
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
		
	}
	
	// clear the list and rewrite the contents
	private static void redrawList(){
		nameModel.clear(); // clear the name list
		idModel.clear(); // clear the id list
		gradeModel.clear(); // clear the grade list
		
		// add each element of the student queue to the list
		for(int i = 0; i < studs.getSize(); i++){
			try{
				nameModel.addElement(studs.getNode(i, 0).getName());
				idModel.addElement(studs.getNode(i, 0).getID());
				gradeModel.addElement(studs.getNode(i, 0).getGrade());
			}catch(NullPointerException e){}
		}
	} // redrawList
}

