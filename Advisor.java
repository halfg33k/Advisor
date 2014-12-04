import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.FileDialog;
import java.io.*;
import java.util.*;


public class Advisor {
	// main frame and panel of the menu
	static JFrame frame;
	static JPanel panel;
	
	// all of the buttons in the menu
	static JButton studentsButton, advisingButton, graduationButton, viewButton, deleteButton, addButton, importButton, selectAllButton;		
	
	// table variables
	static MyTableModel tableModel;
	static JTable table;
	static JScrollPane scrollPane; // scrollPane for table
<<<<<<< HEAD
	static ListSelectionModel listModel;
	
<<<<<<< HEAD
	static JLabel selected = new JLabel("Students"); // label declaring which tab the user is currently on
	
<<<<<<< HEAD
	//scroll bars for JLIst--> Name, ID, Grade
	private static JScrollBar scrollBar_name = new JScrollBar(); //sets up a scroll bar to scroll through all the students
	private static JScrollBar scrollBar_grade = new JScrollBar();
	private static JScrollBar scrollBar_id = new JScrollBar();
=======
	static studentList studs; // queue containing students and their information
	
	//temporary fix to close the application as the setDefaultClose operation is not working for some reason.....
	private static JButton close = new JButton("Close");
=======
	static JLabel selected = new JLabel("Students", SwingConstants.RIGHT); // label declaring which tab the user is currently on
	static studentList studs; // queue containing studentsButton and their information
>>>>>>> master
=======
	static ListSelectionModel listModel; // used for table selection management
>>>>>>> master
	
	/*
	 * The purpose of this variable is to keep track of which tab the user is currently on.
	 * 0 = Advising
	 * 1 = Students
	 * 2 = Graduation
	 */
	private static int currentTab = 1;
	private static final int selectCol = 0; // column which contains selection check boxes
	private static final int idCol = 2; // column which contains student ID
	private static final int adSubCol = 4; // column which contains advising or submitted check boxes
	
	// variables for the student reports
	static JFrame reportFrame;
	static JTextArea reportTextArea = new JTextArea();
	static JScrollPane reportScrollPane = new JScrollPane(reportTextArea);
	
	// misc variables
	static JLabel selectedLabel = new JLabel("Students"); // label declaring which tab the user is currently on
	static studentList studs; // queue containing studentsButton and their information
	private static FileDialog chooseFile = new FileDialog(frame, "Select file...", FileDialog.LOAD); // a window to browse for the selected file
	static Scanner scan;
	
	
>>>>>>> master
	
	
	
	/*
	 * This class was made to allow the use of certain variable types in the table.
	 * In particular, this allows the use of booleans, because it forces the table to 
	 * return variable classes rather than "Object." 
	 */
	private static class MyTableModel extends DefaultTableModel{
		public Class<?> getColumnClass(int index){
			Class<?> temp = String.class;
			
			try{
				temp = getValueAt(0, index).getClass();
			} catch(NullPointerException npe){  }
			
			return temp;
		}
		
		@SuppressWarnings("unchecked")
		public void setValueAt(Object value, int row, int col) {  
			// default code
			Vector rowVector = (Vector)dataVector.elementAt(row);  
			rowVector.setElementAt(value, col);  
			fireTableCellUpdated(row, col);  
			
			// update the table when the id is changed or Advised/Submitted is checked
			if(col == idCol || col == adSubCol){
				saveTable();
				
				switch(currentTab){
					case 0:
						initTableAdvising();
						break;
					case 1:
						initTableStuds();
						break;
					case 2:
						initTableGrad();
						break;
					default:
				} 
			}
			
			// select rows which have Select checked
			if(col == selectCol){
				for(int i = 0; i < table.getRowCount(); i++){
					if((boolean)table.getValueAt(i, selectCol)){
						listModel.addSelectionInterval(i, i);
					}
					 else if(!(boolean)table.getValueAt(i, selectCol)){
						try{
							listModel.removeSelectionInterval(i, i);
						}catch(Exception e){ System.out.println("Exception: Advisor>MyTableModel>setValueAt>if(col == selectCol)"); }
					}
				}
			}
		}
		
		/*
		 * This method contains the default code for setValueAt(). It will set
		 * set a value in the table without attempting to save any information
		 * or rewrite the table. This was done to avoid conflicts the List Selection
		 * Listener was having with the above SetValueAt() method.
		 */
		 @SuppressWarnings("unchecked")
		public void setValueAt(Object value, int row, int col, int unused){
			try{
				Vector rowVector = (Vector)dataVector.elementAt(row);  
				rowVector.setElementAt(value, col);  
				fireTableCellUpdated(row, col); 
			} catch(ArrayIndexOutOfBoundsException e){}
		}
	} // class MyTableModel

	public static void main(String[] args) throws FileNotFoundException{
<<<<<<< HEAD
		
		//Call to Advisor--> the name of the GUI: once Advisor is called: The UI loads and adds all 
		// relevant components
		Advisor();
<<<<<<< HEAD
	}
<<<<<<< HEAD
	//loads Advisor GUI
	public static void Advisor() throws FileNotFoundException{	
=======
=======
	} // main
>>>>>>> master
	
	// loads Advisor GUI
	public static void Advisor() throws FileNotFoundException{
=======
>>>>>>> master
		frame = new JFrame();
		frame.setSize(1070,800);
		
		// set up the frame to show the student reports
		reportFrame = new JFrame();
		reportFrame.setSize(800, 600);
		reportFrame.add(reportScrollPane);
		
		// perform certain actions when the window is closed
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				saveTable();
				studs.close();
				
				System.exit(0);
			}
		});
		
		
		
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		 
>>>>>>> master
		/**
		 * 				 
		 *
		 *				Setup Working Frame
		 *			To Implement: --> individual Panels for each respective button category
		 *
		 */
		
=======
		// instantiate a new list of students
>>>>>>> master
=======
		// instantiate a new list of studentsButton
>>>>>>> master
=======
		// instantiate a new list of students
>>>>>>> master
		studs = new studentList();
		
		// set up the table
		tableModel = new MyTableModel();
		table = new JTable( tableModel );
		table.setFillsViewportHeight(true); // the table fills out the JScrollPane
		
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1000,465));
		
		initTableStuds(); // initialize the table for the studentsButton tab
		
		
		listModel = table.getSelectionModel();
		// reset all check boxes when a row is clicked
		listModel.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent lse){
				if(table.getSelectedColumn() != selectCol){
					for(int i = 0; i < table.getRowCount(); i++){
						tableModel.setValueAt(Boolean.FALSE, i, selectCol, 0);
					}
				}
			}
		});
		
		// advising menu button
		advisingButton = new JButton("Advising");
		advisingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedLabel.setText("Advising Report");
				
				saveTable();
				currentTab = 0;
				
				initTableAdvising();
				
				table.setAutoResizeMode(1); // make the table auto-resize the columns

				layout(); // redraw the frame
			}
		});//advising menu button
		
		// students menu button
		studentsButton = new JButton("Students");
		studentsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedLabel.setText("Students");
				
				saveTable();
				currentTab = 1;
				
				initTableStuds();
				
				table.setAutoResizeMode(1); // make the table auto-resize the columns
				
				layout(); // redraw the frame
			}
		});//students menu button
		
		// graduation menu button
		graduationButton = new JButton("Graduation");
		graduationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedLabel.setText("Graduation Report");
				
				saveTable();
				currentTab = 2;
				
				initTableGrad();
				
				layout(); // redraw the frame
			}
		}); //graduation menu button
		
		// add studentsButton button
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
=======
				// add a blank row to the table
<<<<<<< HEAD
>>>>>>> master
				tableModel.addRow(new Object[]{});
>>>>>>> master
=======
				tableModel.addRow(new Object[]{Boolean.FALSE,"","","",Boolean.FALSE,""});
>>>>>>> master
			}
		});
		
		// delete studentsButton button
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					int[] rows = table.getSelectedRows(); // indices of the selected rows
					int confirm = -1; // yes or no
					String id; // id of the node in the selected row
					
					// confirm decision when user tries to delete a student
					if(rows.length > 0)
						confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete these students?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
					
					// only delete the selected students if the user confirms
					if(confirm == JOptionPane.YES_OPTION){
						for(int i = 0; i <= rows.length; i++){
							id = (String)tableModel.getValueAt(rows[i] - i, idCol);
							
							tableModel.removeRow(rows[i] - i);
						
							studs.removeNode(id);
						}
					}					
				} catch(ArrayIndexOutOfBoundsException ex){}
				
				saveTable();
			}
		}); // delete button
		
		// opens a file browser for the user to select a text file to import
		importButton = new JButton("Import");
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName;
				
				chooseFile.setVisible(true);
				fileName = chooseFile.getFile();
				
				if(fileName != null)
					studs.importStudents(fileName);
				
				// reload table
				switch(currentTab){
					case 0:
						initTableAdvising();
						break;
					case 1:
						initTableStuds();
						break;
					case 2:
						initTableGrad();
						break;
					default:
				} 
				
				saveTable();
			}
		}); // import button
		
		// view button
		viewButton = new JButton("View Report");
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTable();
				
				String reports = ""; // String containing information about all selected students
				String id; // id of the selected student
				int[] rows = table.getSelectedRows(); // array containing indices of selected rows
				Node node;
				
				// choose all if none are selected
				if(rows.length <= 0){
					rows = new int[studs.getSize()];
					for(int i = 0; i < rows.length; i++){
						rows[i] = i;
					}
				}
				
				// show report depending on the current tab
				switch(currentTab){
					case 0: // advising tab
						for(int i = 0; i < rows.length; i++){
							id = (String)tableModel.getValueAt(rows[i], idCol); // id of the node in the selected row
							node = studs.getNode(id);
							
							reports += "\nName: " + node.getName() 
								+ "\nID: " + id + " Grade: " + node.getGrade() 
								+ "\nAdvised: " + node.getAdvised();
								
							if(node.getAdvised())
								reports += "\nDate Advised: " + node.getAdvDate();
								
							reports += "\n";
						}
						
						reportTextArea.setText(reports);
						reportFrame.setVisible(true);
						
						break;
					case 1: // students tab
						for(int i = 0; i < rows.length; i++){
							id = (String)tableModel.getValueAt(rows[i], idCol); // id of the node in the selected row
							node = studs.getNode(id);
							
							reports += "\nName: " + node.getName() 
								+ "\nID: " + id + " Grade: " + node.getGrade();
								
							if(node.getAdvised())
								reports += "\nDate Advised: " + node.getAdvDate();
								
							if(node.getSubmitted())
								reports += "\nDate Submitted: " + node.getGradDate()
									+ "\nTotal GPA: " + node.getTotalGPA() + " Major GPA: " + node.getMajorGPA()
									+ "\nTotal Credits: " + node.getTotalCreds() + " Major Credits: " + node.getMajorCreds() + " Upper-Level Credits: " + node.getUpperCreds();
								
							reports += "\n";
						}
						
						reportTextArea.setText(reports);
						reportFrame.setVisible(true);
						
						break;
					case 2: // graduation tab
						reports += "==============================\n===========Qualified============\n==============================";
						
						for(int i = 0; i < rows.length; i++){
							id = (String)tableModel.getValueAt(rows[i], idCol); // id of the node in the selected row
							node = studs.getNode(id);
							
							if(node.getQualified() && node.getSubmitted()){
								reports += "\nName: " + node.getName() 
									+ "\nID: " + id + " Grade: " + node.getGrade() 
									+ "\nApplication Submitted: " + node.getSubmitted();
									
								reports += "\nDate Submitted: " + node.getGradDate()
									+ "\nTotal GPA: " + node.getTotalGPA() + " Major GPA: " + node.getMajorGPA()
									+ "\nTotal Credits: " + node.getTotalCreds() + " Major Credits: " + node.getMajorCreds() + " Upper-Level Credits: " + node.getUpperCreds();
								
								reports += "\n";
							}
						}
						
						reports += "\n==============================\n==========Unqualified===========\n==============================";
						
						for(int i = 0; i < rows.length; i++){
							id = (String)tableModel.getValueAt(rows[i], idCol); // id of the node in the selected row
							node = studs.getNode(id);
							
							if(!node.getQualified() && node.getSubmitted()){
								reports += "\nName: " + node.getName() 
									+ "\nID: " + id + " Grade: " + node.getGrade() 
									+ "\nApplication Submitted: " + node.getSubmitted();
									
								reports += "\nDate Submitted: " + node.getGradDate()
									+ "\nTotal GPA: " + node.getTotalGPA() + " Major GPA: " + node.getMajorGPA()
									+ "\nTotal Credits: " + node.getTotalCreds() + " Major Credits: " + node.getMajorCreds() + " Upper-Level Credits: " + node.getUpperCreds();
								
								reports += "\nReasons:\n" + node.getReasons() + "\n";
							}
						}
						
						reports += "\n==============================\n=========Not Submitted==========\n==============================";
						
						for(int i = 0; i < rows.length; i++){
							id = (String)tableModel.getValueAt(rows[i], idCol); // id of the node in the selected row
							node = studs.getNode(id);
							
							if(!node.getQualified() && !node.getSubmitted()){
								reports += "\nName: " + node.getName() 
									+ "\nID: " + id + " Grade: " + node.getGrade() 
									+ "\nApplication Submitted: " + node.getSubmitted();
									
								reports += "\n";
							}
						}
						
						reportTextArea.setText(reports);
						reportFrame.setVisible(true);
						
						break;
					default:
				}
			}
		}); //view button
		
		// select all menu button
		selectAllButton = new JButton("Select All");
		selectAllButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// select and deselect all rows; allows selectAllButton to work
				table.selectAll();
				listModel.removeSelectionInterval(0, table.getRowCount() - 1);
				
				for(int i = 0; i < table.getRowCount(); i++){
					tableModel.setValueAt(Boolean.TRUE, i, selectCol); // check all check boxes in the first column
				}
			}
<<<<<<< HEAD
		});
<<<<<<< HEAD
		
<<<<<<< HEAD
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
=======
		//takes the name in the textfield above and when clicked loads the specified textfile information into the Table
		Import = new JButton("Import");
		Import.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName;
				
				chooseFile.setVisible(true);
				fileName = chooseFile.getFile();
=======
>>>>>>> master
				
=======
		}); // select all button
>>>>>>> master
		
		//set the advisingButton, studentsButton, and graduationButton buttons to visible
		advisingButton.setVisible(true);
		studentsButton.setVisible(true);
		graduationButton.setVisible(true);
<<<<<<< HEAD
		
		
>>>>>>> master
		
		
		
		/*
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
		
		
=======
				
>>>>>>> master
		// Adds table to panel
		panel = new JPanel();
		panel.add(scrollPane);
		
		layout(); // define the layout of the frame
	} // main
	
	//Layout for all the buttons, labels, and other UI stuff
	private static void layout(){
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(35)
					.addComponent(advisingButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(studentsButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(graduationButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(598 - (int)selectedLabel.getPreferredSize().getWidth()) // right-align selectedLabel
					.addComponent(selectedLabel))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(385)
					.addComponent(addButton, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(importButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(viewButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(selectAllButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(37))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 1047, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(advisingButton)
						.addComponent(studentsButton)
						.addComponent(graduationButton)
						.addComponent(selectedLabel)
					)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 469, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(addButton)
						.addComponent(deleteButton)
						.addComponent(importButton)
						.addComponent(viewButton)
						.addComponent(selectAllButton))
					.addGap(195))
		);
		
		// grabs the current panes content and adds it to the Frame, then makes the frame visible
		frame.getContentPane().setLayout(groupLayout);
	
		frame.setVisible(true);
	}
	
	// save the contents of the table before switching tabs
	private static void saveTable(){
		String name, id, grade;
		boolean advised, submitted,isSelected;
		String advDate;
		String gradDate, totalGPA, majorGPA, totalCreds, majorCreds, upperCreds;
	
		try{
			switch(currentTab){
				case 0: // Advising tab
					for(int i = 0; i < tableModel.getRowCount(); i++){
						name = (String)tableModel.getValueAt(i, 1);
						id = (String)tableModel.getValueAt(i, idCol);
						grade = (String)tableModel.getValueAt(i, 3);
						advised = (boolean)tableModel.getValueAt(i, adSubCol);
						advDate = (String)tableModel.getValueAt(i, 5);
						
						// prevent the user from setting a duplicate ID
						if(studs.contains(id, i) && !(id.length() < 1) && !id.equals(null)){
							JOptionPane.showMessageDialog(null, "That ID is already in use.");
						}
						else if(id.length() < 1 || id.equals(null)){
							// Ensure nothing happens if these conditions are met
						}
						else{
							try{
								studs.getNode(i, 0).setName(name);
								studs.getNode(i, 0).setID(id);
								studs.getNode(i, 0).setGrade(grade);
							} catch(NullPointerException npe){ studs.addNode(name, id, grade); }
							
							studs.getNode(i, 0).setAdvised(advised, advDate);
							/*if(advised)
								studs.getNode(i, 0).setAdvDate(advDate);
							else
								studs.getNode(i, 0).setAdvDate("--/--/----");
							*/
						}
					}
					
					studs.rewrite();
					break;
				case 1: // Students tab
					for(int i = 0; i < tableModel.getRowCount(); i++){
						name = (String)tableModel.getValueAt(i, 1);
						id = (String)tableModel.getValueAt(i, idCol);
						grade = (String)tableModel.getValueAt(i, 3);
						
						// prevent the user from setting a duplicate ID
						if(studs.contains(id, i) && !(id.length() < 1) && !id.equals(null)){
							JOptionPane.showMessageDialog(null, "That ID is already in use.");
						}
						else if(id.length() < 1 || id.equals(null)){
							// Ensure nothing happens if these conditions are met
						}
						else{
							try{
								studs.getNode(i, 0).setName(name);
								studs.getNode(i, 0).setID(id);
								studs.getNode(i, 0).setGrade(grade);
							} catch(NullPointerException npe){ studs.addNode(name, id, grade); }
						}
					}
					
					studs.rewrite();
					break;
				case 2: // Graduation tab
					for(int i = 0; i < tableModel.getRowCount(); i++){
						name = (String)tableModel.getValueAt(i, 1);
						id = (String)tableModel.getValueAt(i, idCol);
						grade = (String)tableModel.getValueAt(i, 3);
						submitted = (boolean)tableModel.getValueAt(i, adSubCol);
						totalGPA = (String)tableModel.getValueAt(i, 5);
						majorGPA = (String)tableModel.getValueAt(i, 6);
						totalCreds = (String)tableModel.getValueAt(i, 7);
						majorCreds = (String)tableModel.getValueAt(i, 8);
						upperCreds = (String)tableModel.getValueAt(i, 9);
						
						// prevent the user from setting a duplicate ID
						if(studs.contains(id, i) && !(id.length() < 1) && !id.equals(null)){
							JOptionPane.showMessageDialog(null, "That ID is already in use.");
						}
						else if(id.length() < 1 || id.equals(null)){
							// Ensure nothing happens if these conditions are met
						}
						else{
							try{
								studs.getNode(i, 0).setName(name);
								studs.getNode(i, 0).setID(id);
								studs.getNode(i, 0).setGrade(grade);
							} catch(NullPointerException npe){ studs.addNode(name, id, grade); }
							
							studs.getNode(i, 0).setSubmitted(submitted);
							studs.getNode(i, 0).setTotalGPA(totalGPA);
							studs.getNode(i, 0).setMajorGPA(majorGPA);
							studs.getNode(i, 0).setTotalCreds(totalCreds);
							studs.getNode(i, 0).setMajorCreds(majorCreds);
							studs.getNode(i, 0).setUpperCreds(upperCreds);
							
							if(!submitted)
								studs.getNode(i, 0).setGradDate("--/--/----");
						}
					}
					
					studs.rewrite();
					break;
				default:
					System.out.println("ERROR: Advisor>saveTable");
			}
		} catch(NullPointerException e){}
	} // saveTable
	
	// redraw the table for the Advising tab
	private static void initTableAdvising(){
		Node node;
		// clear the table
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
	
		tableModel.addColumn("Select");
		tableModel.addColumn("Name");
		tableModel.addColumn("ID");
		tableModel.addColumn("Grade");
		tableModel.addColumn("Advised");
		tableModel.addColumn("Date");
		
		try{
			for(int i = 0; i < studs.getSize(); i++){
				node = studs.getNode(i, 0); // get node by index
			
				// add a row with the current node's information
				tableModel.addRow(new Object[]{Boolean.FALSE, node.getName(), node.getID(), node.getGrade(), node.getAdvised(), node.getAdvDate()});
			}
		} catch(NullPointerException npe){}
		
		// keep the Select column at a certain size
		table.getColumnModel().getColumn(selectCol).setMaxWidth(50);
	} // initTableAdvising
	
	// redraw the table for the Students tab
	private static void initTableStuds(){
		Node node;
		// clear the table
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
		
		tableModel.addColumn("Select");
		tableModel.addColumn("Name");
		tableModel.addColumn("ID");
		tableModel.addColumn("Grade");
		
		try{
			for(int i = 0; i < studs.getSize(); i++){
				node = studs.getNode(i, 0);
				
				// add a row with the current node's information
				tableModel.addRow(new Object[]{Boolean.FALSE, node.getName(), node.getID(), node.getGrade()});
			}
		} catch(NullPointerException npe){}
		
		// keep the Select column at a certain size
		table.getColumnModel().getColumn(selectCol).setMaxWidth(50);
	} // initTableStuds
	
	// redraw the table for the Graduation tab
	private static void initTableGrad(){
		Node node;
		// clear the table
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
		
		tableModel.addColumn("Select");
		tableModel.addColumn("Name");
		tableModel.addColumn("ID");
		tableModel.addColumn("Grade");
		tableModel.addColumn("Submitted");
		tableModel.addColumn("Total GPA");
		tableModel.addColumn("Major GPA");
		tableModel.addColumn("Total Credits");
		tableModel.addColumn("Major Credits");
		tableModel.addColumn("Upper-Level Credits");
		
		try{
			for(int i = 0; i < studs.getSize(); i++){
				node = studs.getNode(i, 0);
				
				// add a row with the current node's information
				tableModel.addRow(new Object[]{Boolean.FALSE, node.getName(), node.getID(), node.getGrade(), node.getSubmitted(), node.getTotalGPA(), node.getMajorGPA(), node.getTotalCreds(), node.getMajorCreds(), node.getUpperCreds()});
			}
		} catch(NullPointerException npe){}
		
		// resize the columns to properly accommodate certain headers
		table.getColumnModel().getColumn(selectCol).setMaxWidth(50); // first column
		table.getColumnModel().getColumn(1).setMinWidth(150); // name column
		table.getColumnModel().getColumn(9).setMinWidth(100); // last column
	} // initTableGrad
<<<<<<< HEAD
}
=======
} // class Advisor
>>>>>>> master
