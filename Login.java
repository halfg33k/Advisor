import javax.swing.*;
import java.awt.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {
static JFrame frame;
static JPanel panel;
private static JTextField txtUserName;
private static JTextField txtPassword;
private static JButton login_button;
private static JLabel validation_label = new JLabel("");
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Login();

	}
	
	public static void Login(){
	frame = new JFrame("Login");
	frame.setSize(800,600);
	
	JLabel login = new JLabel("Login");
	
	txtUserName = new JTextField();
	txtUserName.setText("User Name");
	txtUserName.setColumns(10);
	
	txtPassword = new JTextField();
	txtPassword.setText("Password");
	txtPassword.setColumns(10);
	
	login_button = new JButton("Login");
	login_button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//Advisor advisor = new Advisor();
			if(!(txtUserName.getText().equals("Advisor") && txtPassword.getText().equals("password"))){
				validation_label.setText("INVALID Login: Please reenter your credentials!");
			}
			else 	{validation_label.setText(""); Advisor.Advisor();}
		}
	});
	

	GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
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
	frame.getContentPane().setLayout(groupLayout);
	frame.setVisible(true);
		
	}
	public String getValidationText(){
	 return validation_label.getText();
	}
	public String setValidationText(String strValidate){
		strValidate ="";
		  validation_label.setText(strValidate);
		  return getValidationText();
		}
}
