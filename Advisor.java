import javax.swing.*;
import javax.swing.table.*;
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
	
	private static FileDialog chooseFile = new FileDialog(frame, "Choose a file.", FileDialog.LOAD);
	
	
	
	
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
			// overridden code
			Vector rowVector = (Vector)dataVector.elementAt(row);  
			rowVector.setElementAt(value, col);  
			fireTableCellUpdated(row, col);  
			
			// my code
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
	} // class MyTableModel

	public static void main(String[] args) throws FileNotFoundException{
		
		//Call to Advisor--> the name of the GUI: once Advisor is called: The UI loads and adds all 
		// relevant components
		Advisor();
	} // main
	
	// loads Advisor GUI
	public static void Advisor() throws FileNotFoundException{
		frame = new JFrame();
		frame.setSize(1070,575);
		
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
		scrollPane.setPreferredSize(new Dimension(808,400));
		initTableStuds(); // initialize the table for the students tab
		
		table.setFillsViewportHeight(true); // the table fills out the JScrollPane
		
		
		
		
		
		
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
				tableModel.addRow(new Object[]{});
			}
		});
		
		// view button (may remove)
		View = new JButton("View");
		View.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// unused and invisible at the moment
			}
		}); //view button
		View.setVisible(false);
		
		// delete students button
		Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					int row = table.getSelectedRow();
					String id = (String)tableModel.getValueAt(row, 1);
					Node node;
					
					tableModel.removeRow(row);
					
					studs.removeNode(id);
				} catch(ArrayIndexOutOfBoundsException ex){}
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
		
		
		// Adds table to panel
		panel = new JPanel();
		panel.add(scrollPane);
		
		
		
		//Layout for all the button, labels, and other UI stuff
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(55)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(Import, 0, 0, Short.MAX_VALUE)
						.addComponent(Add, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(View, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(Delete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(42)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 914, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(229)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(selected)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(records)
							.addGap(29)
							.addComponent(students, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(Graduation, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
							.addGap(60)
							.addComponent(close)))
					.addGap(345))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(close)
						.addComponent(records)
						.addComponent(Graduation)
						.addComponent(students))
					.addGap(55)
					.addComponent(selected)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(Add)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Delete)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(View)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Import))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE))
					.addGap(151))
		);
		
		// grabs the current panes content and adds it to the Frame, then makes the frame visible
		frame.getContentPane().setLayout(groupLayout);
	
		frame.setVisible(true);
		
	}
	
	// save the contents of the table before switching tabs
	private static void saveTable(){
		String name, id, grade;
		boolean advised, submitted;
		String advDate;
		String gradDate, totalGPA, majorGPA, totalCreds, majorCreds, upperCreds;
	
		try{
			switch(lastTab){
				case 0: // Records tab
					for(int i = 0; i < tableModel.getRowCount(); i++){
						name = (String)tableModel.getValueAt(i, 0);
						id = (String)tableModel.getValueAt(i, 1);
						grade = (String)tableModel.getValueAt(i, 2);
						advised = (boolean)tableModel.getValueAt(i, 3);
						advDate = (String)tableModel.getValueAt(i, 4);
						
						
						try{
							studs.getNode(i, 0).setName(name);
							studs.getNode(i, 0).setID(id);
							studs.getNode(i, 0).setGrade(grade);
						} catch(NullPointerException npe){ studs.addNode(name, id, grade); }
						
						studs.getNode(i, 0).setAdvised(advised);
						if(!advised)
							studs.getNode(i, 0).setAdvDate("");
					}
					
					studs.rewrite();
					break;
				case 1: // Students tab
					for(int i = 0; i < tableModel.getRowCount(); i++){
						name = (String)tableModel.getValueAt(i, 0);
						id = (String)tableModel.getValueAt(i, 1);
						grade = (String)tableModel.getValueAt(i, 2);
						
						
						try{
							studs.getNode(i, 0).setName(name);
							studs.getNode(i, 0).setID(id);
							studs.getNode(i, 0).setGrade(grade);
						} catch(NullPointerException npe){ studs.addNode(name, id, grade); }
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
	
		tableModel.addColumn("Name");
		tableModel.addColumn("ID");
		tableModel.addColumn("Grade");
		tableModel.addColumn("Advised");
		tableModel.addColumn("Date");
		
		try{
			for(int i = 0; i < studs.getSize(); i++){
				node = studs.getNode(i, 0);
			
				tableModel.addRow(new Object[]{node.getName(), node.getID(), node.getGrade(), node.getAdvised(), node.getAdvDate()});
			}
		} catch(NullPointerException npe){}
	} // initTableStuds
	
	private static void initTableStuds(){
		Node node;
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
		
		tableModel.addColumn("Name");
		tableModel.addColumn("ID");
		tableModel.addColumn("Grade");
		
		try{
			for(int i = 0; i < studs.getSize(); i++){
				node = studs.getNode(i, 0);
			
				tableModel.addRow(new Object[]{node.getName(), node.getID(), node.getGrade()});
			}
		} catch(NullPointerException npe){}
	} // initTableStuds
	
	private static void initTableGrad(){
		Node node;
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
		
		try{
			for(int i = 0; i < studs.getSize(); i++){
				node = studs.getNode(i, 0);
				
				tableModel.addRow(new Object[]{node.getName(), node.getID(), node.getGrade(), node.getSubmitted(), node.getTotalGPA(), node.getMajorGPA(), node.getTotalCreds(), node.getMajorCreds(), node.getUpperCreds()});
			}
		} catch(NullPointerException npe){}
		
		// resize the columns to properly accommodate each header
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(75);
		table.getColumnModel().getColumn(3).setPreferredWidth(75);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(75);
		table.getColumnModel().getColumn(6).setPreferredWidth(90);
		table.getColumnModel().getColumn(7).setPreferredWidth(90);
		table.getColumnModel().getColumn(8).setPreferredWidth(130);
	} // initTableStuds
}

