import javax.swing.*;

import java.awt.*;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.io.*;


public class Login {
static JFrame frame;
static JPanel panel, panel_2;
private static JTextField txtUserName;
private static JTextField txtPassword;
private static JButton login_button,students, records, Graduation, Edit,View,Delete;
static boolean isClicked = false;
static String temp="test";
static File user_cred;
static Scanner scan;
static BufferedReader reader;
static FileWriter file_writer;
static JLabel selected = new JLabel("None Selected");
private static JButton reset;
static File file_items = new File("items.txt");
static String[] items;
private static JLabel validation_label = new JLabel("");
static BufferedReader br;
static FileWriter fw;
static File file_users = new File("users.txt");
static String users[], pswd[];
static boolean valid_user;
static GroupLayout groupLayout;
static Advisor advisor;
	public static void main(String[] args) throws FileNotFoundException{
		// TODO Auto-generated method stub
		
		/**
		 * Login main entry point
		 */
		Login();

	}
	// Login Method
	public static void Login() throws FileNotFoundException{
	frame = new JFrame("Login");
	frame.setSize(800,600);
	
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	panel = new JPanel();

	JLabel login = new JLabel("Login");
	
	txtUserName = new JTextField();
	txtUserName.setText("User Name");
	txtUserName.setColumns(10);
	
	txtPassword = new JTextField();
	txtPassword.setText("Password");
	txtPassword.setColumns(10);
	
	login_button = new JButton("Login");
	
	
	
	login_button.addActionListener(new ActionListener()  {
		public void actionPerformed(ActionEvent e) {
			try {
				 advisor = new Advisor();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
			try {
				if(!userCredentials(txtUserName.getText(),txtPassword.getText())){
				//if(!(txtUserName.getText().equals("Advisor") && txtPassword.getText().equals("password"))){
					validation_label.setText("INVALID Login: Please reenter your credentials!");
				}
				else 	{validation_label.setText("");frame.setVisible(false); advisor.Advisor(); //close();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,"File Not Found");
				e1.printStackTrace();
			}
			}
		});
	
	// Layout for login
	 groupLayout = new GroupLayout(frame.getContentPane());
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGap(323)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
					.addComponent(login_button)
					.addGroup(groupLayout.createSequentialGroup()
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtUserName, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(login, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE))
							.addComponent(txtPassword, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
							.addComponent(validation_label))))
				.addGap(253))
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGap(218)
				.addComponent(login)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(txtUserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(login_button)
				.addGap(18)
				.addComponent(validation_label)
				.addContainerGap(240, Short.MAX_VALUE))
	);
	/*panel.add(txtUserName);
	panel.add(txtPassword);
	panel.add(login_button);
	panel.add(validation_label);
	panel.setVisible(true);
	frame.add(panel);*/
	//frame.remove(panel);
	frame.getContentPane().setLayout(groupLayout);
	frame.setVisible(true);
	//Advisor advisor = new Advisor();
	
	
	
	panel_2 = new JPanel();
	
	
	
	
	
	
		
	}
	public String getValidationText(){
	 return validation_label.getText();
	}
	public String setValidationText(String strValidate){
		strValidate ="";
		  validation_label.setText(strValidate);
		  return getValidationText();
		}
	public static boolean userCredentials(String user_name, String psswd) throws FileNotFoundException{
		try{
		scan = new Scanner(file_users);
		String temp, parts[];
		valid_user = false;
		while(scan.hasNextLine()){
			temp= scan.nextLine();
			
			parts=temp.split(":");
			String user=parts[0];
			//JOptionPane.showMessageDialog(null,user);
			String password =parts[1];
			//OptionPane.showMessageDialog(null,password);
			if(user_name.equals(user)&&psswd.equals(password))
				valid_user=true;
			
		
		}
		} catch(Exception e){
			//JOptionPane.showMessageDialog(null,"Erorr");
			//StackTraceElement[] error =e.getStackTrace();
			//JOptionPane.showMessageDialog(null, error);
			
		}return valid_user; 
		 
	
		
		
	}
	public static void close(){
		System.exit(0);
	}
	
	
	
}
