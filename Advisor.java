import javax.swing.*;
import javax.swing.table.*;

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
	static DefaultTableModel tableModel;
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
	
	static studentList studs; // queue containing students and their information
	
	//temporary fix to close the application as the setDefaultClose opertion is not working for some reason.....
	private static JButton close = new JButton("Close");
	
	
	
	
	
	
	

	//main method--> main entry to application
	public static void main(String[] args) throws FileNotFoundException{
		
		//Call to Advisor--> the name of the GUI: once Advisor is called:: The UI loads and adds all 
		// relevant components
		Advisor();
	}
	//loads Advisor GUI
	public static void Advisor() throws FileNotFoundException{
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studs.close();
				
				System.exit(0);
			}
		});
		textField_Major_Credits.setColumns(10);
		textField_Major_GPA.setColumns(10);
		textField_2.setColumns(10);
		textField_total_gpa.setColumns(10);
		textField_Name.setVisible(false);
		
		 
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
		//setInVisible();
		
		studs = new studentList();
		
		/*
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				studs.close();
			}
		});
		*/
		
		/**********************************************
		* 
		* 
		* Table
		* 
		***********************************************/
		tableModel = new DefaultTableModel();
		table = new JTable( tableModel );
		scrollPane = new JScrollPane(table);
		initTableStuds();
		
		
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
				selected.setText("students selected");
				
				initTableStuds();
			}
		});//student menu button
		
		// records menu button
		records = new JButton("Records");
		records.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			initTableRecords();

			selected.setText("records selected");
			}
		});//records menu button
		
		// graduation menu button
		Graduation = new JButton("Graduation");
		Graduation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("graduation selected");
				
				initTableGrad();
			}
		}); //graduation menu button
		
		// add students button
		Add = new JButton("Add");
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.addRow(new Object[]{});
				
				selected.setText("Add Selected");
			}
		});//add students button 
		
		// edit students button
		Edit = new JButton("Edit");
		Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				int id = (int)tableModel.getValueAt(row, 1);
				Node node;
				
				tableModel.removeRow(row);
				
				studs.removeNode(id);
			
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
		//p
		
		
		/*********************************
		 * 
		 * 
		 * 		Adds Table to panel
		 * 
		 * 
		 ***********************************/
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
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
	
		tableModel.addColumn("Name");
		tableModel.addColumn("ID");
		tableModel.addColumn("Grade");
		tableModel.addColumn("Total GPA");
		tableModel.addColumn("Major GPA");
		tableModel.addColumn("Total Credits");
		tableModel.addColumn("Major Credits");
		tableModel.addColumn("Upper-Level Credits");
		
		for(int i = 0; i < studs.getSize(); i++){
			node = studs.getNode(i, 0);
		
			tableModel.addRow(new Object[]{node.getName(), node.getID(), node.getGrade(), node.getTotalGPA(), node.getMajorGPA(), node.getTotalCreds(), node.getMajorCreds(), node.getUpperCreds()});
		}
	} // initTableStuds
}

