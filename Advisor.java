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
	static JButton students, records, Graduation, Edit, View, Delete, Add;	
	
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
	
	static JLabel lblMajorGpa = new JLabel("Major GPA");
	
	static JLabel lblTotalGpa = new JLabel("Total GPA");
	
	
	private static  JLabel lblNewLabel = new JLabel("");
	private static  JLabel label_Upp_Lev_Credits = new JLabel("Upper Level Credits");
	private static  JLabel label_Major_Credits = new JLabel("Major Credits");
	private static  JLabel label_Total_Credits = new JLabel("Total Credits");
	
	//labels used for positioning and allignment purposes:
	//think of it like a measurement guide to position certain components in relative to others
	//these are basically reference components to get the spacing for certain components correct
	//there are set to invisible so they dont show, but still take up space...and the space was what I needed
	private static  JLabel lblNewLabel_8 = new JLabel("");
	private static  JLabel lblNewLabel_9 = new JLabel("");
	private static  JLabel label_1 = new JLabel("");
	private static  JLabel label_2 = new JLabel("");
	
	// list containing the student information
	static DefaultListModel<String> nameModel = new DefaultListModel<>();
	static DefaultListModel<Integer> idModel = new DefaultListModel<>();
	static DefaultListModel<String> gradeModel = new DefaultListModel<>();
	static DefaultListModel<Integer> majorGPAModel = new DefaultListModel<>();
	static DefaultListModel<Integer> totGPAModel = new DefaultListModel<>();
	static DefaultListModel<Integer> MajorCredModel = new DefaultListModel<>();
	static DefaultListModel<Integer> UPPLevModel = new DefaultListModel<>();
	static DefaultListModel<Integer> totCredModel = new DefaultListModel<>();
	static JList list_name = new JList<String>(nameModel);
	static JList list_id = new JList<Integer>(idModel);
	static JList list_grade = new JList<String>(gradeModel);
	
	/*
	 * 	Below are the listModels already Set up for you
	 * 
	 */
	static  JList list_major_gpa = new JList<Integer>(majorGPAModel);
	static  JList list_tot_gpa = new JList<Integer>(totGPAModel);
	static  JList list_Major_Credits = new JList<Integer>(MajorCredModel);
	static  JList list_UPP_Lev_Cred = new JList<Integer>(UPPLevModel);
	static 	JList list_tot_Credits = new JList<Integer>(totCredModel);

	
	// text fields used for input to add/edit students
	private static  JTextField textField_Name = new JTextField();
	private static  JTextField textField_ID = new JTextField();
	private static  JTextField textField_Grade = new JTextField();
	private static  JTextField textField_total_gpa = new JTextField();
	private static  JTextField textField_2 = new JTextField();
	private static  JTextField textField_total_Credits = new JTextField();
	private static  JTextField textField_Major_GPA = new JTextField();
	private static  JTextField textField_Major_Credits = new JTextField();
	private static  JTextField textField_UPP_Credits = new JTextField();
	static boolean adding = false; // whether to take the input as adding or editing a student
	
	static studentList studs; // queue containing students and their information
	
	//temporary fix to close the application as the setDefaultClose opertion is not working for some reason.....
	private static JButton close = new JButton("Close");
	
	
	
	
	
	
	

	//main method--> main entry to application
	public static void main(String[] args) throws FileNotFoundException{
		
		//Call to Advisor--> the name of the GUI: once Advisor is called:: The UI loads and adds all 
		// relevant components
		Advisor();
	}
	//laods Advisor GUI
	public static void Advisor() throws FileNotFoundException{
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		textField_UPP_Credits.setColumns(10);
		textField_Major_Credits.setColumns(10);
		textField_Major_GPA.setColumns(10);
		textField_total_Credits.setColumns(10);
		textField_2.setColumns(10);
		textField_total_gpa.setColumns(10);
		
		 
		/**
		 * 				 
		 *
		 *				Setup Working Frame
		 *			To Implement: --> individual Panels for each respective button category
		 *
		 */
		frame = new JFrame();
		frame.setSize(1080,780);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setInVisible();
		
		//studs = new studentList();
		
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
				setInVisible();
				redrawList();
				
				selected.setText("students selected");
				
				
			}
		});//student menu button
		
		// records menu button
		records = new JButton("Records");
		records.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			setInVisible();
			selected.setText("records selected");
			redrawList();
			}
		});//records menu button
		
		// graduation menu button
		Graduation = new JButton("Graduation");
		Graduation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("graduation selected");
				setVisible();
				
				
			}
		}); //graduation menu button
		
		// add students button
		Add = new JButton("Add");
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInVisible();
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
				setInVisible();
				
				adding = false;
			
				selected.setText("Edit Selected");
			}
		}); //edit students button
		
		// view button (may remove)
		View = new JButton("View");
		View.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInVisible();
				redrawList();
				
				selected.setText("View Selected");
			}
		}); //view button
		
		// delete students button
		Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInVisible();
			/*	scan = new Scanner(list_name.getSelectedValue().toString());
				scan.useDelimiter("Name:| ID:| Grade:|\\n|\\r");
				
				scan.next();
				studs.removeNode(scan.nextInt());
				
				redrawList();*/
				
				selected.setText("Delete Selected");
			}
		});
		
		
		/**
		 * 
		 * 
		 * Layout Section:
		 * 	Defines the Position within the Layout of all the Components
		 * 
		 * 
		 */
		
		
		
		
		
		
		//Layout for all the button, labels, and other UI stuff
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
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
								.addComponent(list_name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel_8))
							.addGap(41)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_9)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_1))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(label_ID)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(textField_ID, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
											.addComponent(list_id, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)))
									.addGap(41)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textField_Grade, 0, 0, Short.MAX_VALUE)
										.addComponent(list_grade, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label_Grade, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(label_2)
								.addComponent(lblMajorGpa, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(list_major_gpa, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(textField_Major_GPA, 0, 0, Short.MAX_VALUE))
							.addGap(36)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblTotalGpa, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel)
								.addComponent(list_tot_gpa, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(textField_total_gpa, 0, 0, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(21)
							.addComponent(close)
							.addGap(27)
							.addComponent(records)
							.addGap(32)
							.addComponent(students, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addGap(21)
							.addComponent(Graduation)))
					.addGap(19)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(textField_total_Credits, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 274, Short.MAX_VALUE)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
							.addGap(8))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(label_Major_Credits)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(6)
									.addComponent(list_Major_Credits, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(label_Upp_Lev_Credits, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(list_UPP_Lev_Cred, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
								.addComponent(textField_Major_Credits, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(label_Total_Credits, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(12)
									.addComponent(textField_UPP_Credits, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
								.addComponent(list_tot_Credits, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(4)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(782))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(records)
						.addComponent(students)
						.addComponent(Graduation)
						.addComponent(close))
					.addGap(43)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(label_ID)
							.addComponent(label_Name)
							.addComponent(selected))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(label_Grade)
							.addComponent(lblMajorGpa)
							.addComponent(lblTotalGpa)
							.addComponent(label_Major_Credits)
							.addComponent(label_Total_Credits)
							.addComponent(label_Upp_Lev_Credits)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(list_major_gpa, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(list_grade, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
							.addComponent(list_tot_gpa, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
							.addComponent(list_Major_Credits, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
						.addComponent(list_id, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
						.addComponent(list_name, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(Add)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Edit)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(View)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Delete))
						.addComponent(list_UPP_Lev_Cred, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
						.addComponent(list_tot_Credits, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_ID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(textField_Grade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(textField_total_gpa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(textField_Major_GPA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_Major_Credits, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_UPP_Credits, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(textField_total_Credits, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(151))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(49)
					.addComponent(lblNewLabel_8)
					.addContainerGap(703, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(49)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_9)
						.addComponent(label_1)
						.addComponent(label_2)
						.addComponent(lblNewLabel))
					.addContainerGap(703, Short.MAX_VALUE))
		);
		
		///grabs the current panes content and adds it to the Frame, then makes the frame visible
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
		
	}
	//sets certain components visible when need or in use
	public static void setVisible(){

		label_Upp_Lev_Credits.setVisible(true);
		label_Major_Credits.setVisible(true); 
		label_Total_Credits.setVisible(true);
		textField_UPP_Credits.setVisible(true);
		textField_Major_Credits.setVisible(true);
		textField_Major_GPA.setVisible(true);
		textField_total_Credits.setVisible(true);
		textField_total_gpa.setVisible(true);
		list_major_gpa.setVisible(true);
		list_tot_gpa.setVisible(true);
		list_Major_Credits.setVisible(true);
		list_UPP_Lev_Cred.setVisible(true);
		list_tot_Credits.setVisible(true);
		lblMajorGpa.setVisible(true);	
		lblTotalGpa.setVisible(true);
	}
	//set certain components to invisible when not in use or needed
	public static void setInVisible(){
		label_Upp_Lev_Credits.setVisible(false);
		label_Major_Credits.setVisible(false); 
		label_Total_Credits.setVisible(false);
		textField_UPP_Credits.setVisible(false);
		textField_Major_Credits.setVisible(false);
		textField_Major_GPA.setVisible(false);
		textField_total_Credits.setVisible(false);
		textField_total_gpa.setVisible(false);
		list_major_gpa.setVisible(false);
		list_tot_gpa.setVisible(false);
		list_Major_Credits.setVisible(false);
		list_UPP_Lev_Cred.setVisible(false);
		list_tot_Credits.setVisible(false);
		lblMajorGpa.setVisible(false);	
		lblTotalGpa.setVisible(false);
	
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

