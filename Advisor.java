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
	static JButton students, records, Graduation, Edit,View,Delete;
	static boolean isClicked = false;
	static String temp="test";

	static JLabel selected = new JLabel("None Selected");
	private static JButton reset;

	public static void main(String args[]){
		Advisor();
	}
	public static void Advisor(){	
		
		frame = new JFrame("Advisor");
		frame.setSize(800, 600);
		
		 students = new JButton("Students");
		 
		students.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isClicked = true;
				if(isClicked ==true){
					records.setVisible(false);
					Graduation.setVisible(false);
					Edit.setVisible(true);
					View.setVisible(true);
					Delete.setVisible(true);
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
		
		JList list = new JList();
		
		JButton Logout = new JButton("Logout");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				Login.Login();
				
			}
		});
		
		Edit = new JButton("Edit");
		Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("Edit Selected");
			}
		});
		
		 View = new JButton("View");
		 View.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected.setText("View Selected");
			}
		 });
		
		Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			selected.setText("Delete Selected");
			}
		});
		Edit.setVisible(false);
		View.setVisible(false);
		Delete.setVisible(false);
		
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
