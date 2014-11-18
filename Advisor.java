import javax.swing.*;
import java.awt.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;


public class Advisor {

	static JFrame frame;
	static JPanel panel;
	// various menu buttons
	static JButton students, records, Graduation, Edit, View, Delete, Logout;
	static boolean isClicked = false;
	static String temp = "test";

	// label declaring what button was clicked
	static JLabel selected = new JLabel("None Selected");
	private static JButton reset;
	
	private static studentList studs; // queue containing students and their info

	public static void main(String args[]){
		Advisor();
		
		studs = new studentList();
		studs.importStudents("studentList.txt");
		//studs.importStudents("newStudents.txt");
	}
	
	public static void Advisor(){	
		
		frame = new JFrame("Advisor");
		frame.setSize(800, 600);
		
		// students button
		students = new JButton("Students");
		students.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				records.setVisible(false);
				Graduation.setVisible(false);
				Edit.setVisible(true);
				View.setVisible(true);
				Delete.setVisible(true);
				
				selected.setText("students selected");
				
			}
		});
		
		// records button
		records = new JButton("Records");
		records.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("records selected");
			}
		});
		
		// graduation button
		Graduation = new JButton("Graduation");
		Graduation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("graduation selected");
			}
		});
		
		JList list = new JList();
		
		// logout button
		Logout = new JButton("Logout");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studs.close(); // close the student information queue
				
				Login.Login(); // return to the login screen
				
			}
		});
		
		// edit students button
		Edit = new JButton("Edit");
		Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("Edit Selected");
			}
		});
		
		// view students button
		View = new JButton("View");
		View.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("View Selected");
			}
		});
		
		// delete button
		Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			selected.setText("Delete Selected");
			}
		});
		Edit.setVisible(false);
		View.setVisible(false);
		Delete.setVisible(false);
		
		// reset button
		reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				records.setVisible(true);
				Graduation.setVisible(true);
				Edit.setVisible(false);
				View.setVisible(false);
				Delete.setVisible(false);
			}
		});
		
		// arrange menu
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
							.addGap(26)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(Delete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(View, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(Edit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(Logout)
							.addGap(18)
							.addComponent(reset)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(selected)
						.addComponent(list, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(122, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(94)
					.addComponent(selected)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(records)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(students)
								.addComponent(Edit))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(Graduation)
								.addComponent(View))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Delete)
							.addPreferredGap(ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(Logout)
								.addComponent(reset))
							.addGap(80))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(list, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
							.addContainerGap())))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
		
	}
}
