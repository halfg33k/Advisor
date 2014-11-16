import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Login {
static JFrame Login;
static JPanel panel;
private static JTextField txtUserId;
private static JTextField txtPassword;
static JLabel validation_label; //this must global as it can change throughout the class and its state needs to be saved throughout

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Login();
		
	}
	//Set up the login frame
	public static void Login(){
		Login = new JFrame("Login");	
		Login.setSize(800, 600);
		
		
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1)); // 3 rows, 1 column
		
		// make the window close when the X is pressed
		Login.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		// sets up the login panel with the gridbag layout contraints
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		
		//Create a login label
		JLabel login_label = new JLabel("Login");
		
		//create textfield for user to input ID
		txtUserId = new JTextField();
		txtUserId.setText("user ID");
		txtUserId.setColumns(10);
		
		
		//create textfield for user to input password
		txtPassword = new JTextField();
		txtPassword.setText("Password");
		txtPassword.setColumns(10);
		
		//sets up the login button
		JButton login_button = new JButton("Login");
		login_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp_userId= txtUserId.getText();
				String temp_userpsswd= txtUserId.getText();
				
				// Was supposed to impliment login validation, but doesnot work
				/*while(!(temp_userId.equals("Advisor") && temp_userpsswd.equals("password"))){
				 * 
				 * 
				validation_label.setText("INVALIN LOGIN: User or Password Incorrect! Plase try again!");
				
				}*/
				Advisor advisor = new Advisor();
				advisor.menu(); 
				
				
				
				
				
			}
		});
		
		//sets up a validation label to print and error if the current credentials are not valid
		validation_label = new JLabel("test");
		GroupLayout groupLayout = new GroupLayout(Login.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(335)
							.addComponent(login_button))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(223)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtUserId, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)
								.addComponent(validation_label)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(339)
							.addComponent(login_label, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(262, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(258)
					.addComponent(login_label)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtUserId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(login_button)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(validation_label)
					.addGap(185))
		);
		Login.getContentPane().setLayout(groupLayout);
		Login.setVisible(true);	// show the mainFrame
		
	}

}
