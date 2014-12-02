import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

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
	static JButton students, records, Graduation, View, Delete, Add,Import;		
	
	// reading and writing variables
	static Scanner scan; 
	static BufferedReader reader;
	static FileWriter file_writer;
	
	// table variables
	static MyTableModel tableModel;
	static JTable table;
	static JScrollPane scrollPane; // scrollPane for table
	static ListSelectionModel listModel;// = table.getSelectionModel();
	
	static JLabel selected = new JLabel("Students"); // label declaring which tab the user is currently on
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
	
	private static FileDialog chooseFile = new FileDialog(frame, "Select file...", FileDialog.LOAD); // a window to browse for the selected file
	
	
	
	
	
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
			
			// my code
			if(col == 2 || col == 4){
				saveTable();
				
				switch(lastTab){
					case 0:
						initTableRecords();
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
			
			if(col == 0){
				for(int i = 0; i < table.getRowCount(); i++){
					if(table.isRowSelected(i) || (boolean)table.getValueAt(i, 0)){
						listModel.addSelectionInterval(i, i);
					}
				}
			}
		}
	} // class MyTableModel

	public static void main(String[] args) throws FileNotFoundException{
		
		//Call to Advisor--> the name of the GUI: once Advisor is called: The UI loads and adds all 
		// relevant components
		Advisor();
	} // main
	
	// loads Advisor GUI
	public static void Advisor() throws FileNotFoundException{
		frame = new JFrame();
		frame.setSize(1070,800);
		
		// perform certain actions when the window is closed
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				saveTable();
				studs.close();
				
				System.exit(0);
			}
			
		});
		
		close.setVisible(false);
		
		
		// instantiate a new list of students
		studs = new studentList();
		
		/*******
		* Table *
		********/
		tableModel = new MyTableModel();
		table = new JTable( tableModel );
		
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1000,465));
		initTableStuds(); // initialize the table for the students tab
		
		table.setFillsViewportHeight(true); // the table fills out the JScrollPane
		
		listModel = table.getSelectionModel();
		
		//listModel.addSelectionListener(new ListSelection());
		
		
		
		
		
		
		// records menu button
		records = new JButton("Records");
		records.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTable();
				lastTab = 0;
				
				initTableRecords();
				
				table.setAutoResizeMode(1);

				selected.setText("Advising Report");
			}
		});//records menu button
		
		// students menu button
		students = new JButton("Students");
		students.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTable();
				lastTab = 1;
				
				selected.setText("Students");
				
				initTableStuds();
				
				table.setAutoResizeMode(1);
			}
		});//student menu button
		
		// graduation menu button
		Graduation = new JButton("Graduation");
		Graduation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("Graduation Report");
				
				try{
				saveTable();
				
				lastTab = 2;
				
				initTableGrad();
				
				} catch(NullPointerException npe){ System.out.println("trace"); }
			}
		}); //graduation menu button
		
		// add students button
		Add = new JButton("Add");
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// add a blank row to the table
				tableModel.addRow(new Object[]{Boolean.FALSE,"","","",Boolean.FALSE,""});
			}
		});
		
		// view button (may remove)
		View = new JButton("View Report");
		View.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTable();
				
				String reports = "";
				String id;
				int[] rows = table.getSelectedRows();
				Node node;
				
				// choose all if none are selected
				if(rows.length <= 0){
					rows = new int[studs.getSize()];
					for(int i = 0; i < rows.length; i++){
						rows[i] = i;
					}
				}
				
				switch(lastTab){
					case 0:
						for(int i = 0; i < rows.length; i++){
							id = (String)tableModel.getValueAt(rows[i], 2); // id of the node in the selected row
							node = studs.getNode(id);
							
							reports += "\nName: " + node.getName() 
								+ "\nID: " + id + " Grade: " + node.getGrade() 
								+ "\nAdvised: " + node.getAdvised();
								
							if(node.getAdvised())
								reports += "\nDate Advised: " + node.getAdvDate();
								
							reports += "\n";
						}
						
						JOptionPane.showMessageDialog(null, reports, "Academic Advising Reports", JOptionPane.INFORMATION_MESSAGE);
						break;
					case 1:
						for(int i = 0; i < rows.length; i++){
							id = (String)tableModel.getValueAt(rows[i], 2); // id of the node in the selected row
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
						
						JOptionPane.showMessageDialog(null, reports, "Student Reports", JOptionPane.INFORMATION_MESSAGE);
						break;
					case 2:
						reports += "----- Qualified -----";
						
						for(int i = 0; i < rows.length; i++){
							id = (String)tableModel.getValueAt(rows[i], 2); // id of the node in the selected row
							node = studs.getNode(id);
							
							if(node.getQualified() && node.getSubmitted()){
								reports += "\nName: " + node.getName() 
									+ "\nID: " + id + " Grade: " + node.getGrade() 
									+ "\nApplication Submitted: " + node.getSubmitted();
									
								//if(node.getSubmitted())
									reports += "\nDate Submitted: " + node.getGradDate()
										+ "\nTotal GPA: " + node.getTotalGPA() + " Major GPA: " + node.getMajorGPA()
										+ "\nTotal Credits: " + node.getTotalCreds() + " Major Credits: " + node.getMajorCreds() + " Upper-Level Credits: " + node.getUpperCreds();
								
								reports += "\n";
							}
						}
						
						reports += "\n----- Unqualified -----";
						
						for(int i = 0; i < rows.length; i++){
							id = (String)tableModel.getValueAt(rows[i], 2); // id of the node in the selected row
							node = studs.getNode(id);
							
							if(!node.getQualified() && node.getSubmitted()){
								reports += "\nName: " + node.getName() 
									+ "\nID: " + id + " Grade: " + node.getGrade() 
									+ "\nApplication Submitted: " + node.getSubmitted();
									
								//if(node.getSubmitted())
									reports += "\nDate Submitted: " + node.getGradDate()
										+ "\nTotal GPA: " + node.getTotalGPA() + " Major GPA: " + node.getMajorGPA()
										+ "\nTotal Credits: " + node.getTotalCreds() + " Major Credits: " + node.getMajorCreds() + " Upper-Level Credits: " + node.getUpperCreds();
								
								reports += "\nReasons:\n" + node.getReasons() + "\n";
							}
						}
						
						reports += "\n----- Not Submitted -----";
						
						for(int i = 0; i < rows.length; i++){
							id = (String)tableModel.getValueAt(rows[i], 2); // id of the node in the selected row
							node = studs.getNode(id);
							
							if(!node.getQualified() && !node.getSubmitted()){
								reports += "\nName: " + node.getName() 
									+ "\nID: " + id + " Grade: " + node.getGrade() 
									+ "\nApplication Submitted: " + node.getSubmitted();
									
								/*if(node.getSubmitted())
									reports += "\nDate Submitted: " + node.getGradDate()
										+ "\nTotal GPA: " + node.getTotalGPA() + " Major GPA: " + node.getMajorGPA()
										+ "\nTotal Credits: " + node.getTotalCreds() + " Major Credits: " + node.getMajorCreds() + " Upper-Level Credits: " + node.getUpperCreds();
								*/
								reports += "\n";
							}
						}
						
						JOptionPane.showMessageDialog(null, reports, "Graduation Application Reports", JOptionPane.INFORMATION_MESSAGE);
						break;
					default:
				}
			}
		}); //view button
		
		// delete students button
		Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					int row = table.getSelectedRow(); // index of the selected row
					int confirm = -1;
					String id = (String)tableModel.getValueAt(row, 1); // id of the node in the selected row
					
					if(row >= 0)
						confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
					
					if(confirm == JOptionPane.YES_OPTION){
						tableModel.removeRow(row);
					
						studs.removeNode(id);
					}					
				} catch(ArrayIndexOutOfBoundsException ex){}
				
				saveTable();
			}
		});
		
		//takes the name in the textfield above and when clicked loads the specified textfile information into the Table
		Import = new JButton("Import");
		Import.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName;
				
				chooseFile.setVisible(true);
				fileName = chooseFile.getFile();
				
				if(fileName != null)
					studs.importStudents(fileName);
				
				switch(lastTab){
					case 0:
						initTableRecords();
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
		});
		
		
		/**
		 * 
		 * 
		 * 				ComboBox
		 * 				Impliments a combo box to add a drop down menu for user selection
		 * 				Can be used to replace the JButtons --> May replace JButtons
		 * 
		 * 
		 * 
		 */
		
		
		//set the records, students, and Graduation buttons to not visible as they might be taken our  and overrode by a ComboBox
		records.setVisible(true);
		students.setVisible(true);
		Graduation.setVisible(true);
		
		
		
		
		
		/**
		 * 
		 * 
		 * Layout Section:
		 * 	Defines the Position within the Layout of all the Components
		 * 
		 * 
		 */
		
		
		// Adds table to panel
		panel = new JPanel();
		panel.add(scrollPane);
		
		JButton Select_All = new JButton("Select All");
		
		//JButton Update_Records = new JButton("Update Records");
		
	
		
		
		
		
		
		//Layout for all the button, labels, and other UI stuff
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(37)
					.addComponent(selected)
					.addGap(242)
					.addComponent(records)
					.addGap(18)
					.addComponent(students, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(Graduation, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(close)
					.addPreferredGap(ComponentPlacement.RELATED)
				)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(385)
					.addComponent(Add, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(Delete, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(Import, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(View, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(Select_All, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(37))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 1047, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(selected)
						.addComponent(records)
						.addComponent(students)
						.addComponent(Graduation)
						.addComponent(close)
					)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 469, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(Add)
						.addComponent(Delete)
						.addComponent(Import)
						.addComponent(View)
						.addComponent(Select_All)
					)
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
			switch(lastTab){
				case 0: // Records tab
					for(int i = 0; i < tableModel.getRowCount(); i++){
						isSelected = (boolean)tableModel.getValueAt(i, 0);
						name = (String)tableModel.getValueAt(i, 1);
						id = (String)tableModel.getValueAt(i, 2);
						grade = (String)tableModel.getValueAt(i, 3);
						advised = (boolean)tableModel.getValueAt(i, 4);
						advDate = (String)tableModel.getValueAt(i, 5);
						
						
						if(studs.contains(id, i) && !(id.length() < 1) && !id.equals(null)){
							JOptionPane.showMessageDialog(null, "That ID is already in use.");
						}
						else if(id.length() < 1 || id.equals(null)){
							//JOptionPane.showMessageDialog(null, "Please enter an id.");
						}
						else{
							try{
								studs.getNode(i, 0).setName(name);
								studs.getNode(i, 0).setID(id);
								studs.getNode(i, 0).setGrade(grade);
							} catch(NullPointerException npe){ studs.addNode(name, id, grade); }
							
							studs.getNode(i, 0).setAdvised(advised);
							if(!advised)
								studs.getNode(i, 0).setAdvDate("");
						}
					}
					
					studs.rewrite();
					break;
				case 1: // Students tab
					for(int i = 0; i < tableModel.getRowCount(); i++){
						isSelected = (boolean)tableModel.getValueAt(i, 0);
						name = (String)tableModel.getValueAt(i, 1);
						id = (String)tableModel.getValueAt(i, 2);
						grade = (String)tableModel.getValueAt(i, 3);
						boolean add = false;
						
						if(studs.contains(id, i) && !(id.length() < 1) && !id.equals(null)){
							JOptionPane.showMessageDialog(null, "That ID is already in use.");
						}
						else if(id.length() < 1 || id.equals(null)){
							//JOptionPane.showMessageDialog(null, "Please enter an id.");
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
						isSelected = (boolean)tableModel.getValueAt(i, 0);
						name = (String)tableModel.getValueAt(i, 1);
						id = (String)tableModel.getValueAt(i, 2);
						grade = (String)tableModel.getValueAt(i, 3);
						submitted = (boolean)tableModel.getValueAt(i, 4);
						totalGPA = (String)tableModel.getValueAt(i, 5);
						majorGPA = (String)tableModel.getValueAt(i, 6);
						totalCreds = (String)tableModel.getValueAt(i, 7);
						majorCreds = (String)tableModel.getValueAt(i, 8);
						upperCreds = (String)tableModel.getValueAt(i, 9);
						
						if(studs.contains(id, i) && !(id.length() < 1) && !id.equals(null)){
							JOptionPane.showMessageDialog(null, "That ID is already in use.");
						}
						else if(id.length() < 1 || id.equals(null)){
							//JOptionPane.showMessageDialog(null, "Please enter an id.");
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
						}
					}
					
					studs.rewrite();
					break;
				default:
					System.out.println("ERROR: saveTable");
			}
		} catch(NullPointerException e){}
	} // saveTable
	
	/*
	 * The following three methods will populate the table with the necessary
	 * information depending on which tab the user switches to.
	 */
	private static void initTableRecords(){
		Node node;
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
			
				tableModel.addRow(new Object[]{Boolean.FALSE, node.getName(), node.getID(), node.getGrade(), node.getAdvised(), node.getAdvDate()});
			}
		} catch(NullPointerException npe){}
		
		table.getColumnModel().getColumn(0).setPreferredWidth(87);
	} // initTableRecords
	
	private static void initTableStuds(){
		Node node;
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
		
		tableModel.addColumn("Select");
		tableModel.addColumn("Name");
		tableModel.addColumn("ID");
		tableModel.addColumn("Grade");
		
		try{
			for(int i = 0; i < studs.getSize(); i++){
				node = studs.getNode(i, 0);
			
				tableModel.addRow(new Object[]{Boolean.FALSE, node.getName(), node.getID(), node.getGrade()});
			}
		} catch(NullPointerException npe){}
		
		table.getColumnModel().getColumn(0).setPreferredWidth(87);
	} // initTableStuds
	
	private static void initTableGrad(){
		Node node;
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
				
				//need a  node for "select" a in order to add a select all option --> boolean checkbox 
				tableModel.addRow(new Object[]{Boolean.FALSE, node.getName(), node.getID(), node.getGrade(), node.getSubmitted(), node.getTotalGPA(), node.getMajorGPA(), node.getTotalCreds(), node.getMajorCreds(), node.getUpperCreds()});
			}
		} catch(NullPointerException npe){}
		
		// resize the columns to properly accommodate each header
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(87);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(75);
		table.getColumnModel().getColumn(4).setPreferredWidth(75);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(120);
		table.getColumnModel().getColumn(9).setPreferredWidth(130);
		
	} // initTableGrad
}

// keeps track of selected elements
class ListSelection implements ListSelectionListener{
	public void valueChanged(ListSelectionEvent lse){
		ListSelectionModel model = (ListSelectionModel)lse.getSource();
		
		int index = lse.getFirstIndex();
		boolean isAdjusting = lse.getValueIsAdjusting();
		
		if(!model.isSelectionEmpty()){
			int min = model.getMinSelectionIndex();
			int max = model.getMaxSelectionIndex();
			
			for(int i = min; i < max; i++){
			
			}
		}
		
	}
} // class ListSelection