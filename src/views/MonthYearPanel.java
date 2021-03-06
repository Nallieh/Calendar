package views;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.DateHandler;
import controller.StateMachine;

public class MonthYearPanel extends JPanel {
	private JButton forwardButton, backButton;
	private JLabel dateShowLabel;
	private ListenForButton lForButton;
	private GridBagConstraints gbc;
	private StateMachine SM;
	private WindowPanel wp;
	public MonthYearPanel(StateMachine SM, WindowPanel wp){
		this.SM=SM;
		this.wp=wp;
		String date = SM.getFocusedDate();
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		backButton = new JButton("<");
		lForButton=new ListenForButton();
		backButton.addActionListener(lForButton);
		dateShowLabel = new JLabel(date.substring(0, 7));
		dateShowLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		forwardButton = new JButton(">");
		forwardButton.addActionListener(lForButton);
		gbc.insets=new Insets(0, 10, 0, 10);
		gbc.gridx=0;
		gbc.gridy=0;
		add(backButton, gbc);
		gbc.gridx=1;
		gbc.gridy=0;
		
		add(dateShowLabel, gbc);
		gbc.gridx=2;
		gbc.gridy=0;
		add(forwardButton, gbc);
		gbc.insets=new Insets(0, 0, 0, 0);
		
		
	}
	private class ListenForButton implements ActionListener {

		// This method is called when an event occurs

		public void actionPerformed(ActionEvent e) {

			// Check if the source of the event was the button

			if (e.getSource() == forwardButton) {
				int dayOfMonth=DateHandler.getDaysOfMonth(SM.getFocusedDate());
				SM.addToFocusDate(dayOfMonth);
				wp.getViewViewer();
				wp.getViewChoice();
				wp.getOverview();
			}
			if (e.getSource() == backButton) {
				//TODO Jump back based on last month number of days not the current
				int dayOfMonth=DateHandler.getDaysOfMonth(SM.getFocusedDate());
				SM.addToFocusDate(-dayOfMonth);
				wp.getViewViewer();
				wp.getViewChoice();
				wp.getOverview();
			}

		}
	}
}
