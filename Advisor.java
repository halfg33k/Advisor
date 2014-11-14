import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Advisor{
	JFrame mainFrame;
	JPanel mainPanel;
	JLabel headerLabel;
	
	public Advisor(){
		prepareGUI();
	} // Advisor constructor
	
	public static void main(String[] args){
		Advisor advisor = new Advisor();
		advisor.menu();
	} // main
	
	private void prepareGUI(){
		mainFrame = new JFrame("Advisor");
		mainFrame.setSize(800, 600);
		mainFrame.setLayout(new GridLayout(3, 1));
		
		headerLabel = new JLabel("", JLabel.CENTER);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 1));
		
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		mainFrame.add(headerLabel);
		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);		
	} // prepareGUI
	
	private void menu(){
		JButton students = new JButton("Students");
		JButton records = new JButton("Records");
		JButton submissions = new JButton("Graduation Submissions");
		
		students.setActionCommand("Students");
		records.setActionCommand("Records");
		submissions.setActionCommand("Submissions");
		
		students.addActionListener(new ButtonClickListener());
		records.addActionListener(new ButtonClickListener());
		submissions.addActionListener(new ButtonClickListener());
		
		mainPanel.add(students);
		mainPanel.add(records);
		mainPanel.add(submissions);
		
		mainFrame.setVisible(true);
	} // menu
	
	private class ButtonClickListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String command = e.getActionCommand();
			
			switch(command){
				case "Students":
					headerLabel.setText("Students button pressed");
					break;
				case "Records":
					headerLabel.setText("Records button pressed");
					break;
				case "Submissions":
					headerLabel.setText("Submissions button pressed");
					break;
				default:
					headerLabel.setText("Default");
					break;
			}
		} // actionPerformed
	} // class ButtonClickListener
} // class Advisor