package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

import database.JavaDB;
import object.User;

public class Login extends JPanel {

	private JPanel form, main;
	private JLabel loginLabel, emailLabel, passLabel, regLabel;
	private JTextField emailField;
	private JPasswordField passField;
	private JButton loginButton, registerButton;
	private JavaDB db = new JavaDB();
	private String loginPassHashed;
	private GridBagConstraints gbc;
	private Window window;
	private User user;

	public Login(Window window) {
		user = null;
		this.window = window;

		gbc = new GridBagConstraints();

		main = new JPanel();
		main.setPreferredSize(new Dimension(1400, 800));
		main.setLayout(new GridBagLayout());

		this.add(main);

		form = new JPanel();
		form.setLayout(new GridBagLayout());
		form.setPreferredSize(new Dimension(480, 600));
		form.setVisible(true);
		form.setBorder(BorderFactory.createLineBorder(Color.black));

		gbc.gridx = 0;
		gbc.gridy = 0;

		main.add(form, gbc);

		// Login text
		loginLabel = new JLabel("Logga in h�r!", JLabel.CENTER);
		loginLabel.setFont(new Font("Serif", Font.PLAIN, 25));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);

		form.add(loginLabel, gbc);

		// Email
		emailLabel = new JLabel("Email: ");
		emailLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		emailField = new JTextField();
		emailField.setPreferredSize(new Dimension(300, 30));

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 10, 0);

		form.add(emailLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 10, 0);

		form.add(emailField, gbc);

		// Password
		passLabel = new JLabel("L�senord: ");
		passLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		passField = new JPasswordField();
		passField.setPreferredSize(new Dimension(300, 30));

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 0, 10, 0);

		form.add(passLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(0, 0, 10, 0);

		form.add(passField, gbc);

		// Login button
		loginButton = new JButton("Logga in!");

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(0, 0, 10, 0);

		form.add(loginButton, gbc);

		// Registration
		regLabel = new JLabel("Inget konto? Registrera dig h�r!", JLabel.CENTER);
		regLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		registerButton = new JButton("Registrera dig!");

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(0, 0, 10, 0);

		form.add(regLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.insets = new Insets(0, 0, 10, 0);

		form.add(registerButton, gbc);

		ListenForButton lForButton = new ListenForButton();

		loginButton.addActionListener(lForButton);
		registerButton.addActionListener(lForButton);

	}

	private class ListenForButton implements ActionListener {

		// This method is called when an event occurs

		public void actionPerformed(ActionEvent e) {

			// Check if the source of the event was the button

			if (e.getSource() == loginButton) {
				String loginEmail = emailField.getText();
				Object[][] data = db.getData("select * from user where email = '" + loginEmail + "'");
				int accfound = 0;
				try {
					loginPassHashed = (String) data[0][2];
					accfound=1;
				} catch (ArrayIndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(window, "Vi hittar inget konto kopplat till den email-adressen, var god f�rs�k igen!",
							"Inloggning misslyckades!", JOptionPane.INFORMATION_MESSAGE);
				}
				
				if(accfound==1){
					char[] loginPassCandidate = passField.getPassword();
					if (BCrypt.checkpw(String.valueOf(loginPassCandidate), loginPassHashed)) {
						System.out.println("It matches");
						user = new User(Integer.parseInt((String) data[0][0]), (String) data[0][1], (String) data[0][3],
							(String) data[0][4], (String) data[0][5]);
						// user = new user (int,string,string,string);
						user.getAll();
						user.reloadarrays();
						window.getIndexPage();
					} else {
						//System.out.println("It does not match");
						JOptionPane.showMessageDialog(window, "Du har angivit fel l�senord f�r det h�r kontot, var god f�rs�k igen!",
							"Inloggning misslyckades!", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}

			if (e.getSource() == registerButton) {
				window.getRegisterPage();
			}

		}

	}

}
