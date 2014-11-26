import javax.swing.*;
import javax.swing.table.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.util.*;


public class Advisor {
	
	
	/**
	 * 
	 * 		Component Section:
	 * 		Declares and Instantiates all relevant UI Components:
	 * 		Labels, Button, textFields, JList, and any other Relevant UI components
	 * 
	 * 
	 */
	// main frame and panel of the menu
	static JFrame frame;
	static JPanel panel;
	static JPanel panel_1;
	
	// all of the buttons in the menu
	static JButton students, records, Graduation, Edit, View, Delete, Add;	
	
	static File user_cred; // user credentials file
	
	// reading and writing variables
	static Scanner scan; 
	static BufferedReader reader;
	static FileWriter file_writer;
	
	// table variables
	static JTable table;
	static MyTableModel tableModel;
	static JScrollPane scrollPane; // scrollPane for table
	
	
	//all labels for the components
	static JLabel selected = new JLabel("None Selected"); // label declaring which button has been pressed (for testing purposes)
	
	
	private static  JLabel lblNewLabel = new JLabel("");
	
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

	
	// text fields used for input to add/edit students
	private static  JTextField textField_Name = new JTextField();
	private static  JTextField textField_ID = new JTextField();
	private static  JTextField textField_Grade = new JTextField();
	private static  JTextField textField_total_gpa = new JTextField();
	private static  JTextField textField_2 = new JTextField();
	private static  JTextField textField_Major_GPA = new JTextField();
	private static  JTextField textField_Major_Credits = new JTextField();
	
<<<<<<< HEAD
	//scroll bars for JLIst--> Name, ID, Grade
	private static JScrollBar scrollBar_name = new JScrollBar(); //sets up a scroll bar to scroll through all the students
	private static JScrollBar scrollBar_grade = new JScrollBar();
	private static JScrollBar scrollBar_id = new JScrollBar();
=======
	static studentList studs; // queue containing students and their information
	
	//temporary fix to close the application as the setDefaultClose operation is not working for some reason.....
	private static JButton close = new JButton("Close");
	
	/*
	 * The purpose of this variable is to keep track of which tab was just left.
	 * e.g. If I am on the "Students" tab and I click on "Graduation," then I just left "Students"
	 * 0 = Records
	 * 1 = Students
	 * 2 = Graduation
	 */
	private static int lastTab = 1;
	
	
>>>>>>> master
	
	
	
	
	private static class MyTableModel extends DefaultTableModel{
		public Class<?> getColumnClass(int index){
			return getValueAt(0, index).getClass();
		}
	} // class MyTableModel

	//main method--> main entry to application
	public static void main(String[] args) throws FileNotFoundException{
		
		//Call to Advisor--> the name of the GUI: once Advisor is called:: The UI loads and adds all 
		// relevant components
		Advisor();
	}
<<<<<<< HEAD
	//loads Advisor GUI
	public static void Advisor() throws FileNotFoundException{	
=======
	
	//loads Advisor GUI
	public static void Advisor() throws FileNotFoundException{
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTable();
				studs.close();
				
				System.exit(0);
			}
		});
		
		textField_Major_Credits.setColumns(10);
		textField_Major_GPA.setColumns(10);
		textField_2.setColumns(10);
		textField_total_gpa.setColumns(10);
		textField_Name.setVisible(false);
		
		 
>>>>>>> master
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
		
		studs = new studentList();
		
		/**********************************************
		* 
		* 
		* Table
		* 
		***********************************************/
		tableModel = new MyTableModel();
		table = new JTable( tableModel );
		scrollPane = new JScrollPane(table);
		initTableStuds();
		
		table.setFillsViewportHeight(true); // fills out the JScrollPane
		
		
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
		
		
		
		
		
		// records menu button
		records = new JButton("Records");
		records.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTable();
				lastTab = 0;
				
				initTableRecords();

				selected.setText("records selected");
			}
		});//records menu button
		
		// students menu button
		students = new JButton("Students");
		students.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTable();
				lastTab = 1;
				
				selected.setText("students selected");
				
				initTableStuds();
			}
		});//student menu button
		
		// graduation menu button
		Graduation = new JButton("Graduation");
		Graduation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("graduation selected");
				
				saveTable();
				lastTab = 2;
				
				initTableGrad();
			}
		}); //graduation menu button
		
		// add students button
		Add = new JButton("Add");
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.addRow(new Object[]{"", "", ""});
				
<<<<<<< HEAD
				
				if(!studs.contains(newID) && newName.length() > 0 && newGrade.length() > 0 && newID > 0)
					studs.addNode(newName, newID, newGrade);
				
				
				textField_Name.setText("");
				textField_ID.setText("");
				textField_Grade.setText("");
				
				redrawList();
			
=======
>>>>>>> master
				selected.setText("Add Selected");
			}
		});//add students button
		
		// edit students button
		Edit = new JButton("Edit");
		Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
				submit_changes.setText("Edit Student");
			
=======
>>>>>>> master
				selected.setText("Edit Selected");
			}
		}); //edit students button
		
		// view button (may remove)
		View = new JButton("View");
		View.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("View Selected");
			}
		}); //view button
		
		// delete students button
		Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String id = (String)tableModel.getValueAt(row, 1);
				Node node;
				
				tableModel.removeRow(row);
				
				studs.removeNode(id);
			
				selected.setText("Delete Selected");
			}
		});
		
<<<<<<< HEAD
		// button to submit the information in the input fields
		/*submit_changes = new JButton("Edit Student");
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
		*/
=======
>>>>>>> master
		
		/**
		 * 
		 * 
<<<<<<< HEAD
		 * 		Layout Section:
		 * 					Defines a GroupLayout for each UI Component including:
		 * 						Labels, Button, TextFields, JList, and any other relevant UI component
=======
		 * Layout Section:
		 * 	Defines the Position within the Layout of all the Components
>>>>>>> master
		 * 
		 * 
		 */
		
		
		// Adds table to panel
		panel_1 = new JPanel();
		panel_1.add(scrollPane);
		
		
		
		
		
		
		
		//Layout for all the button, labels, and other UI stuff
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(21)
							.addComponent(close)
							.addGap(27)
							.addComponent(records))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(55)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(Add, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(Edit, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(View, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(Delete, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(selected))))
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(students, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addGap(21)
							.addComponent(Graduation))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(26)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(textField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_8))
								.addGap(41)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblNewLabel_9)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(label_1))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(textField_ID, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
										.addGap(55)
										.addComponent(textField_Grade, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(label_2)
									.addComponent(textField_Major_GPA, 0, 0, Short.MAX_VALUE))
								.addGap(36)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(lblNewLabel)
									.addComponent(textField_total_gpa, 0, 0, Short.MAX_VALUE)))
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 560, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
							.addGap(8))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(113)
							.addComponent(textField_Major_Credits, GroupLayout.DEFAULT_SIZE, 4, Short.MAX_VALUE)
							.addGap(107)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(782))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(49)
					.addComponent(lblNewLabel_8)
					.addContainerGap(703, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(records)
								.addComponent(students)
								.addComponent(Graduation)
								.addComponent(close)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(49)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_9)
								.addComponent(label_1)
								.addComponent(label_2)
								.addComponent(lblNewLabel))))
					.addGap(43)
					.addComponent(selected)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(Add)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Edit)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(View)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Delete))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
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
							.addComponent(textField_Major_Credits, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(151))
		);
		
		///grabs the current panes content and adds it to the Frame, then makes the frame visible
		frame.getContentPane().setLayout(groupLayout);
	
		frame.setVisible(true);
		
	}
	
	// save the contents of the table before switching tabs
	private static void saveTable(){
		String name, id, grade;
		boolean advised, submitted;
		String advDate;
		String gradDate, totalGPA, majorGPA, totalCreds, majorCreds, upperCreds;
	
		switch(lastTab){
			case 0: // Records tab
				for(int i = 0; i < tableModel.getRowCount(); i++){
					name = (String)tableModel.getValueAt(i, 0);
					id = (String)tableModel.getValueAt(i, 1);
					grade = (String)tableModel.getValueAt(i, 2);
					advised = (boolean)tableModel.getValueAt(i, 3);
					advDate = (String)tableModel.getValueAt(i, 4);
					
					
					studs.getNode(i, 0).setName(name);
					studs.getNode(i, 0).setID(id);
					studs.getNode(i, 0).setGrade(grade);
					studs.getNode(i, 0).setAdvised(advised);
					studs.getNode(i, 0).setAdvDate(advDate);
				}
				
				studs.rewrite();
				break;
			case 1: // Students tab
				for(int i = 0; i < tableModel.getRowCount(); i++){
					name = (String)tableModel.getValueAt(i, 0);
					id = (String)tableModel.getValueAt(i, 1);
					grade = (String)tableModel.getValueAt(i, 2);
					boolean add = true; // whether to add or edit
					
					for(int j = 0; j < tableModel.getRowCount(); j++){
						if(tableModel.getValueAt(i, 1).equals(id) && j != i)
							add = false;
						
						System.out.println(i + " " + j + " " + add);
					}
					
					System.out.println(add);
					/*
					if(add)
						studs.addNode(name, id, grade);
					else{
						studs.getNode(i, 0).setName(name);
						studs.getNode(i, 0).setID(id);
						studs.getNode(i, 0).setGrade(grade);
					}
					*/
				}
				
				studs.rewrite();
				break;
			case 2: // Graduation tab
				for(int i = 0; i < tableModel.getRowCount(); i++){
					name = (String)tableModel.getValueAt(i, 0);
					id = (String)tableModel.getValueAt(i, 1);
					grade = (String)tableModel.getValueAt(i, 2);
					submitted = (boolean)tableModel.getValueAt(i, 3);
					totalGPA = (String)tableModel.getValueAt(i, 4);
					majorGPA = (String)tableModel.getValueAt(i, 5);
					totalCreds = (String)tableModel.getValueAt(i, 6);
					majorCreds = (String)tableModel.getValueAt(i, 7);
					upperCreds = (String)tableModel.getValueAt(i, 8);
					
					studs.getNode(i, 0).setName(name);
					studs.getNode(i, 0).setID(id);
					studs.getNode(i, 0).setGrade(grade);
					studs.getNode(i, 0).setSubmitted(submitted);
					studs.getNode(i, 0).setTotalGPA(totalGPA);
					studs.getNode(i, 0).setMajorGPA(majorGPA);
					studs.getNode(i, 0).setTotalCreds(totalCreds);
					studs.getNode(i, 0).setMajorCreds(majorCreds);
					studs.getNode(i, 0).setUpperCreds(upperCreds);
				}
				
				studs.rewrite();
				break;
			default:
				System.out.println("ERROR: saveTable");
		}
	} // saveTable
	
	private static void initTableRecords(){
		Node node;
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
	
		tableModel.addColumn("Name");
		tableModel.addColumn("ID");
		tableModel.addColumn("Grade");
		tableModel.addColumn("Advised");
		tableModel.addColumn("Date");
		
		for(int i = 0; i < studs.getSize(); i++){
			node = studs.getNode(i, 0);
		
			tableModel.addRow(new Object[]{node.getName(), node.getID(), node.getGrade(), node.getAdvised(), node.getAdvDate()});
		}
	} // initTableStuds
	
	private static void initTableStuds(){
		Node node;
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
		
		tableModel.addColumn("Name");
		tableModel.addColumn("ID");
		tableModel.addColumn("Grade");
		
		for(int i = 0; i < studs.getSize(); i++){
			node = studs.getNode(i, 0);
		
			tableModel.addRow(new Object[]{node.getName(), node.getID(), node.getGrade()});
		}
	} // initTableStuds
	
	private static void initTableGrad(){
		Node node;
		boolean submitted;
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
	
		tableModel.addColumn("Name");
		tableModel.addColumn("ID");
		tableModel.addColumn("Grade");
		tableModel.addColumn("Submitted");
		tableModel.addColumn("Total GPA");
		tableModel.addColumn("Major GPA");
		tableModel.addColumn("Total Credits");
		tableModel.addColumn("Major Credits");
		tableModel.addColumn("Upper-Level Credits");
		
		for(int i = 0; i < studs.getSize(); i++){
			node = studs.getNode(i, 0);
			submitted = node.getSubmitted();
			
			if(submitted)
				tableModel.addRow(new Object[]{node.getName(), node.getID(), node.getGrade(), Boolean.TRUE, node.getTotalGPA(), node.getMajorGPA(), node.getTotalCreds(), node.getMajorCreds(), node.getUpperCreds()});
			else
				tableModel.addRow(new Object[]{node.getName(), node.getID(), node.getGrade(), Boolean.FALSE, node.getTotalGPA(), node.getMajorGPA(), node.getTotalCreds(), node.getMajorCreds(), node.getUpperCreds()});
		}
	} // initTableStuds
}

