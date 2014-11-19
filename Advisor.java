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
	static JFrame frame;
	static JPanel panel;
	static JButton students, records, Graduation, Edit, View, Delete, Add, submit_changes;
	static boolean isClicked = false;
	static String temp="test";
	static File user_cred;
	static Scanner scan;
	static BufferedReader reader;
	static FileWriter file_writer;
	static JLabel selected = new JLabel("None Selected");
	private static JButton reset;
	static DefaultListModel<String> model = new DefaultListModel<>();
	static JList list = new JList<String>(model);
	private static JTextField name_textField;
	private static JTextField ID_textField;
	private static JTextField grade_textField;;
	static JScrollBar scrollBar = new JScrollBar(); ////sets up a scroll bar to srcoll through all the students
	static int grade_parser;// to parse the grade from String to int to do calculations with
	static int ID_parser;// to parse the ID value
	private static JLabel name;
	
	static studentList studs;
	
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
		
		students = new JButton("Students");
		 
		students.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isClicked = true;
				if(isClicked ==true){
					records.setVisible(false);
					Graduation.setVisible(false);
					Add.setVisible(true);
					View.setVisible(true);
					Delete.setVisible(true);
					Edit.setVisible(true);
				}
				selected.setText("students selected");
				
			}
		});
		
		records = new JButton("Records");
		records.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			selected.setText("records selected");
			}
		});
		
		Graduation = new JButton("Graduation");
		Graduation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("graduation selected");
			}
		});
		
		
		JButton Logout = new JButton("Logout");
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
		
		Edit = new JButton("Edit");
		Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit_changes.setText("Submit Changes");
			
				selected.setText("Edit Selected");
			}
		});
		
		View = new JButton("View");
		View.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redrawList();
				
				selected.setText("View Selected");
			}
		});
		
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
		
		Add = new JButton("Add");
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit_changes.setText("Add Student");
			
				selected.setText("Add Selected");
			}
		});
		//set Add, View, Delete, Edit, to invisible upon start
		Add.setVisible(false);
		View.setVisible(false);
		Delete.setVisible(false);
		Edit.setVisible(false);		
	    
		grade_textField = new JTextField();
		grade_textField.setColumns(10);
		
		ID_textField = new JTextField();
		ID_textField.setColumns(10);
		
		JLabel ID_Label = new JLabel("ID");
		
		JLabel grade_Label = new JLabel("Grade");
		
		submit_changes = new JButton("Submit Changes");
		submit_changes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = name_textField.getText();
				int id = Integer.parseInt(ID_textField.getText());
				String grade = grade_textField.getText();
				
				if(!studs.contains(id))
					studs.addNode(name, id, grade);
				
				redrawList();
			}
		});
		
		name = new JLabel("Name");
		
		name_textField = new JTextField();
		name_textField.setColumns(10);
		
		
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
		model.clear();
		
		for(int i = 0; i < studs.getSize(); i++){
			try{
				model.addElement(studs.getNode(i, 0).toString());
			}catch(NullPointerException e){}
		}
	} // redrawList
}

