package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.DateLabelFormatter;
import controller.StateMachine;
import database.SQLManager;
import object.Calendar;
import object.Event;
import object.User;
import views.calendar.CalChooseList;
import views.calendar.addedit.CalAddEdit;

public class WindowPanel extends JPanel {

	private JPanel overviewPanel1, overviewPanel2, notificationPanel, main, center, centerEvent, mainPanel, leftPanel,
			addEventButtonPanel, overviewPanel, CalendarChoicePanel, rightPanel, upperLeftPanel, upperRightPanel, top,
			centerLeft, centerRight, editEventMain, editEventTop, editEventCenter, editEventLeft, editEventRight;

	private JLabel nameLabel, locationLabel, startTimeLabel, endTimeLabel, eventDescLabel, editEventNameLabel,
			editEventLocationLabel, editEventStartLabel, editEventEndLabel, editEventDescLabel, editEventInfoLabel,
			eventInfoLabel;
	private JTextField nameField, locationField, editEventNameField, editEventLocationField, userSearchField,
			userEditSearchField;
	private DefaultListModel listModel, editListModel;
	private Calendar[] calArray, eventCalArray;
	private JList<Object> userList, userEditList;
	private Event[] editEventArray;
	private JButton addEventButton, eventCreate, editEventButton, deleteEventButton, userSearchButton, userEditSearchButton;
	private JTextArea eventDescArea, editEventDescArea;
	private JCheckBox fullDayActivity, editFullDayActivity;
	private JSpinner startTimeSpinner, endTimeSpinner, editStartTimeSpinner, editEndTimeSpinner;
	private JSpinner.DateEditor startTimeEditor, endTimeEditor, editStartTimeEditor, editEndTimeEditor;
	private JDatePanelImpl startDatePanel, endDatePanel, editStartDatePanel, editEndDatePanel;
	private JDatePickerImpl startDatePicker, endDatePicker, editStartDatePicker, editEndDatePicker;
	private UtilDateModel startModel, endModel, editStartModel, editEndModel;
	private Properties startProperties, endProperties, editStartProperties, editEndProperties;
	private ListenForButton lForButton;
	private GridBagConstraints gbc, gbcLeft;
	private JComboBox<String> calDropDown, editEventDropDown;
	private String eventCreateCalArray, editEventObjectArray;
	private GregorianCalendar editStartGregCal, editEndGregCal;
	private Window window;
	private User user;
	private StateMachine SM;
	private CalChooseList calChooseList;
	private ViewChoice viewChoice;
	private ArrayList checkList, checkEditList;
	private MonthOverview monthOverview;
	private MonthYearPanel monthYearPanel;

	public WindowPanel(Window window) {
		this.window = window;
		gbc = new GridBagConstraints();
		lForButton = new ListenForButton();
		center = new JPanel();
		center.setLayout(new GridLayout(1, 1));
		center.setPreferredSize(new Dimension(1400, 800));
		center.setBackground(new Color(255, 0, 0));
		// center.add(new Login(this));
		center.setVisible(true);
		add(center);
		SM = new StateMachine(user);
		viewChoice = null;
		/*
		 * TODO FIX ORGANISATION
		 */

		checkList = new ArrayList();
		checkEditList = new ArrayList();

		getLoginPage();
	}

	public void getIndexPage() {
		gbc = new GridBagConstraints();
		MenuList menu = new MenuList();
		window.setJMenuBar(menu.createMenuBar(window, this, user));
		center.removeAll();
		gbcLeft = new GridBagConstraints();
		// Main panel
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(1400, 800));
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setVisible(true);
		mainPanel.setBackground(new Color(0, 0, 255));
		center.add(mainPanel);

		// Upper left panel
		upperLeftPanel = new JPanel();
		upperLeftPanel.setPreferredSize(new Dimension(200, 50));
		upperLeftPanel.setLayout(new GridLayout(2, 2));
		upperLeftPanel.setVisible(true);
		upperLeftPanel.setBackground(new Color(255, 0, 0));

		gbc.gridx = 0;
		gbc.gridy = 0;

		mainPanel.add(upperLeftPanel, gbc);

		// Upper right panel
		upperRightPanel = new JPanel();
		upperRightPanel.setPreferredSize(new Dimension(1200, 50));
		upperRightPanel.setLayout(new GridBagLayout());
		upperRightPanel.setVisible(true);
		// upperRightPanel.setBackground(new Color(0, 255, 0));

		gbc.gridx = 1;
		gbc.gridy = 0;

		mainPanel.add(upperRightPanel, gbc);
		getViewChoice();

		// Left panel
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(200, 750));
		leftPanel.setLayout(new GridBagLayout());
		leftPanel.setVisible(true);
		// leftPanel.setBackground(new Color(0, 0, 255));

		gbc.gridx = 0;
		gbc.gridy = 1;

		mainPanel.add(leftPanel, gbc);

		notificationPanel = new JPanel();
		notificationPanel.setPreferredSize(new Dimension(200, 150));
		notificationPanel.setVisible(true);
		// notificationPanel.setBackground(new Color(255, 0, 255));

		gbc.gridx = 0;
		gbc.gridy = 0;

		leftPanel.add(notificationPanel, gbc);

		getNotificationPage();

		addEventButtonPanel = new JPanel();
		addEventButtonPanel.setPreferredSize(new Dimension(200, 60));
		addEventButtonPanel.setLayout(new GridBagLayout());
		addEventButtonPanel.setVisible(true);
		// addEventButtonPanel.setBackground(new Color(0, 255, 0));

		gbc.gridx = 0;
		gbc.gridy = 1;

		leftPanel.add(addEventButtonPanel, gbc);

		addEventButton = new JButton("Skapa Event");
		addEventButton.addActionListener(lForButton);
		addEventButton.setFont(new Font("Serif", Font.PLAIN, 25));
		gbc.gridx = 0;
		gbc.gridy = 0;
		addEventButtonPanel.add(addEventButton, gbc);

		overviewPanel = new JPanel();
		overviewPanel.setPreferredSize(new Dimension(200, 240));
		overviewPanel.setVisible(true);
		overviewPanel.setLayout(new GridBagLayout());
		// overviewPanel.setBackground(new Color(100, 100, 100));

		gbc.gridx = 0;
		gbc.gridy = 2;

		leftPanel.add(overviewPanel, gbc);

		overviewPanel1 = new JPanel();
		overviewPanel1.setPreferredSize(new Dimension(200, 40));
		overviewPanel1.setVisible(true);
		// overviewPanel.setBackground(new Color(100, 100, 100));

		gbc.gridx = 0;
		gbc.gridy = 0;

		overviewPanel.add(overviewPanel1, gbc);

		overviewPanel2 = new JPanel();
		overviewPanel2.setPreferredSize(new Dimension(200, 200));
		overviewPanel2.setVisible(true);
		// overviewPanel.setBackground(new Color(100, 100, 100));

		gbc.gridx = 0;
		gbc.gridy = 1;

		overviewPanel.add(overviewPanel2, gbc);

		CalendarChoicePanel = new JPanel();
		CalendarChoicePanel.setPreferredSize(new Dimension(200, 300));
		CalendarChoicePanel.setVisible(true);
		// CalendarChoicePanel.setBackground(new Color(255, 0, 255));

		gbc.gridx = 0;
		gbc.gridy = 3;

		leftPanel.add(CalendarChoicePanel, gbc);

		calArray = user.getCalArray();

		calChoiceList();

		getOverview();
		// Right panel
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(1200, 750));
		rightPanel.setLayout(new GridBagLayout());
		rightPanel.setVisible(true);
		rightPanel.setBackground(new Color(150, 150, 150));

		gbc.gridx = 1;
		gbc.gridy = 1;

		mainPanel.add(rightPanel, gbc);

		getViewViewer();

		center.updateUI();
	}

	public void getOverview() {
		overviewPanel1.removeAll();
		overviewPanel2.removeAll();

		if (calChooseList != null) {
			remove(calChooseList);
		}
		DateFormat tempFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date tempDate = null;
		try {
			tempDate = tempFormat.parse(SM.getFocusedDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		monthYearPanel = new MonthYearPanel(SM,this);
		overviewPanel1.add(monthYearPanel);
		monthOverview = new MonthOverview(SM, user, tempDate, false, this);
		overviewPanel2.add(monthOverview);
		overviewPanel1.updateUI();
		overviewPanel2.updateUI();

	}

	public void getNotificationPage() {
		notificationPanel.removeAll();
		notificationPanel.add(new NotificationView());
		notificationPanel.updateUI();
	}

	public void getRegisterPage() {
		center.removeAll();
		center.add(new Register(window, this));
		center.updateUI();
	}

	public void getLoginPage() {
		center.removeAll();
		center.add(new Login(window, this, user));
		center.updateUI();
	}

	public void getAddEventPage() {

		rightPanel.removeAll();

		gbc = new GridBagConstraints();
		gbcLeft = new GridBagConstraints();
		// Panel management

		main = new JPanel();
		main.setPreferredSize(new Dimension(1175, 725));
		main.setLayout(new GridBagLayout());
		// main.setBackground(new Color(0, 255, 255));
		main.setVisible(true);

		rightPanel.add(main);

		top = new JPanel();
		top.setPreferredSize(new Dimension(1175, 50));
		top.setLayout(new GridBagLayout());
		// top.setBackground(new Color(255, 0, 0));
		top.setVisible(true);

		gbc.gridx = 0;
		gbc.gridy = 0;

		main.add(top, gbc);

		eventCalArray = user.getCalArray();
		calDropDown = new JComboBox<String>();

		for (int i = 0; i < eventCalArray.length; i++) {
			eventCreateCalArray = eventCalArray[i].getName() + " " + eventCalArray[i].getCal_id();
			calDropDown.addItem(eventCreateCalArray);
		}

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 0;
		gbcLeft.insets = new Insets(0, -385, 0, 10);
		gbcLeft.anchor = GridBagConstraints.WEST;

		top.add(calDropDown, gbcLeft);

		centerEvent = new JPanel();
		centerEvent.setPreferredSize(new Dimension(1175, 655));
		centerEvent.setLayout(new GridBagLayout());
		// center.setBackground(new Color(0, 255, 0));
		centerEvent.setVisible(true);

		gbc.gridx = 0;
		gbc.gridy = 1;

		main.add(centerEvent, gbc);

		eventCreate = new JButton("Skapa event");
		// eventCreate.setPreferredSize(new Dimension(70, 20));

		gbcLeft.gridx = 1;
		gbcLeft.gridy = 0;
		gbcLeft.insets = new Insets(0, -190, 0, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		top.add(eventCreate, gbcLeft);

		centerLeft = new JPanel();
		centerLeft.setPreferredSize(new Dimension(700, 655));
		centerLeft.setLayout(new GridBagLayout());
		// centerLeft.setBackground(new Color(0, 255, 0));
		centerLeft.setVisible(true);

		gbc.gridx = 0;
		gbc.gridy = 0;

		centerEvent.add(centerLeft, gbc);

		centerRight = new JPanel();
		centerRight.setPreferredSize(new Dimension(475, 655));
		centerRight.setLayout(new GridBagLayout());
		// centerRight.setBackground(new Color(0, 0, 255));
		centerRight.setVisible(true);

		gbc.gridx = 1;
		gbc.gridy = 0;

		centerEvent.add(centerRight, gbc);

		eventInfoLabel = new JLabel();
		eventInfoLabel.setText("Bjud in anv�ndare!");
		eventInfoLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 0;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		centerRight.add(eventInfoLabel, gbcLeft);

		userSearchField = new JTextField();
		userSearchField.setPreferredSize(new Dimension(300, 30));

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 1;
		gbcLeft.insets = new Insets(0, 0, 10, 10);
		gbcLeft.anchor = GridBagConstraints.WEST;

		centerRight.add(userSearchField, gbcLeft);

		userSearchButton = new JButton("S�k!");
		userSearchButton.setPreferredSize(new Dimension(60, 29));

		gbcLeft.gridx = 1;
		gbcLeft.gridy = 1;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		centerRight.add(userSearchButton, gbcLeft);

		userList = new JList();
		listModel = new DefaultListModel();
		userList.setPreferredSize(new Dimension(300, 410));
		userList.setModel(listModel);

		JScrollPane listScrollPane = new JScrollPane(userList);

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 2;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		centerRight.add(userList, gbcLeft);

		// Everything on panels

		// Event label + event field
		nameLabel = new JLabel("Event namn");
		nameLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);

		centerLeft.add(nameLabel, gbc);

		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(300, 30));

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 10, 0);

		centerLeft.add(nameField, gbc);

		// Event location label + location field

		locationLabel = new JLabel("Plats");
		locationLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 10, 0);

		centerLeft.add(locationLabel, gbc);

		locationField = new JTextField();
		locationField.setPreferredSize(new Dimension(300, 30));

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 0, 10, 0);

		centerLeft.add(locationField, gbc);

		// Event start time + end time

		startTimeLabel = new JLabel("Start p� event");
		startTimeLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(0, 0, 10, 0);

		centerLeft.add(startTimeLabel, gbc);

		fullDayActivity = new JCheckBox();
		fullDayActivity.setText("Heldags aktivitet?");

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 5;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		centerLeft.add(fullDayActivity, gbcLeft);

		startModel = new UtilDateModel();
		// model.setDate(20,04,2014);
		// Need this...
		startProperties = new Properties();
		startProperties.put("text.today", "Today");
		startProperties.put("text.month", "Month");
		startProperties.put("text.year", "Year");

		startDatePanel = new JDatePanelImpl(startModel, startProperties);
		startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
		startDatePicker.setTextEditable(false);
		// startDatePicker.setPreferredSize(new Dimension(300, 30));

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 6;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		centerLeft.add(startDatePicker, gbcLeft);

		startTimeSpinner = new JSpinner(new SpinnerDateModel());
		startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
		startTimeSpinner.setEditor(startTimeEditor);
		startTimeEditor.setPreferredSize(new Dimension(50, 23));

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 6;
		gbcLeft.insets = new Insets(0, 225, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		centerLeft.add(startTimeSpinner, gbcLeft);

		endTimeLabel = new JLabel("Slut p� event");
		endTimeLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.insets = new Insets(0, 0, 10, 0);

		centerLeft.add(endTimeLabel, gbc);

		endModel = new UtilDateModel();
		// model.setDate(20,04,2014);
		// Need this...
		endProperties = new Properties();
		endProperties.put("text.today", "Today");
		endProperties.put("text.month", "Month");
		endProperties.put("text.year", "Year");

		endDatePanel = new JDatePanelImpl(endModel, endProperties);
		endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
		endDatePicker.setTextEditable(false);
		// endDatePicker.setPreferredSize(new Dimension(200, 30));

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 8;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		centerLeft.add(endDatePicker, gbcLeft);

		endTimeSpinner = new JSpinner(new SpinnerDateModel());
		endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
		endTimeSpinner.setEditor(endTimeEditor);
		endTimeEditor.setPreferredSize(new Dimension(50, 23));

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 8;
		gbcLeft.insets = new Insets(0, 225, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		centerLeft.add(endTimeSpinner, gbcLeft);

		eventDescLabel = new JLabel("Beskriv eventet h�r!");
		eventDescLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.insets = new Insets(0, 0, 10, 0);

		centerLeft.add(eventDescLabel, gbc);

		eventDescArea = new JTextArea();
		eventDescArea.setPreferredSize(new Dimension(300, 100));
		eventDescArea.setLineWrap(true);
		eventDescArea.setWrapStyleWord(true);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.insets = new Insets(0, 0, 10, 0);

		centerLeft.add(eventDescArea, gbc);

		rightPanel.updateUI();

		fullDayActivity.addActionListener(lForButton);
		eventCreate.addActionListener(lForButton);
		userSearchButton.addActionListener(lForButton);
		gbc.insets = new Insets(0, 0, 0, 0);
	}

	public void getDeleteAndEditEventPage() {

		rightPanel.removeAll();

		editEventMain = new JPanel();
		editEventMain.setPreferredSize(new Dimension(1175, 725));
		editEventMain.setLayout(new GridBagLayout());
		editEventMain.setVisible(true);

		rightPanel.add(editEventMain);

		editEventTop = new JPanel();
		editEventTop.setPreferredSize(new Dimension(1175, 50));
		editEventTop.setLayout(new GridBagLayout());
		editEventTop.setBackground(new Color(255, 0, 0));
		editEventTop.setVisible(true);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);

		editEventMain.add(editEventTop, gbc);

		editEventCenter = new JPanel();
		editEventCenter.setPreferredSize(new Dimension(1175, 675));
		editEventCenter.setLayout(new GridBagLayout());
		// center.setBackground(new Color(0, 255, 0));
		editEventCenter.setVisible(true);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 0);

		editEventMain.add(editEventCenter, gbc);

		editEventLeft = new JPanel();
		editEventLeft.setPreferredSize(new Dimension(700, 675));
		editEventLeft.setLayout(new GridBagLayout());
		// editEventLeft.setBackground(new Color(0, 255, 0));
		editEventLeft.setVisible(true);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);

		editEventCenter.add(editEventLeft, gbc);

		editEventRight = new JPanel();
		editEventRight.setPreferredSize(new Dimension(475, 675));
		editEventRight.setLayout(new GridBagLayout());
		// editEventRight.setBackground(new Color(0, 0, 255));
		editEventRight.setVisible(true);

		editEventInfoLabel = new JLabel();
		editEventInfoLabel.setText("Bjud in anv�ndare!");
		editEventInfoLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 0;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventRight.add(editEventInfoLabel, gbcLeft);

		userEditSearchField = new JTextField();
		userEditSearchField.setPreferredSize(new Dimension(300, 30));

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 1;
		gbcLeft.insets = new Insets(0, 0, 10, 10);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventRight.add(userEditSearchField, gbcLeft);

		userEditSearchButton = new JButton("S�k!");
		userEditSearchButton.setPreferredSize(new Dimension(60, 29));

		gbcLeft.gridx = 1;
		gbcLeft.gridy = 1;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventRight.add(userEditSearchButton, gbcLeft);

		userEditList = new JList();
		editListModel = new DefaultListModel();
		userEditList.setModel(editListModel);
		userEditList.setPreferredSize(new Dimension(300, 410));

		JScrollPane listScrollPane = new JScrollPane(userEditList);

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 2;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventRight.add(userEditList, gbcLeft);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);

		editEventCenter.add(editEventRight, gbc);

		editEventArray = user.getEventArray();
		editEventDropDown = new JComboBox<String>();

		for (int i = 0; i < editEventArray.length; i++) {
			editEventObjectArray = editEventArray[i].getName() + " " + editEventArray[i].getCal_id();
			editEventDropDown.addItem(editEventObjectArray);
		}

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 0;
		gbcLeft.insets = new Insets(0, 0, 0, 10);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventTop.add(editEventDropDown, gbcLeft);

		gbcLeft.gridx = 1;
		gbcLeft.gridy = 0;
		gbcLeft.insets = new Insets(0, 0, 0, 10);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventButton = new JButton("Spara");

		editEventTop.add(editEventButton, gbcLeft);

		deleteEventButton = new JButton("Ta bort!");

		gbcLeft.gridx = 2;
		gbcLeft.gridy = 0;
		gbcLeft.insets = new Insets(0, 0, 0, 10);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventTop.add(deleteEventButton, gbcLeft);

		// Event label + event field
		editEventNameLabel = new JLabel("Event namn");
		editEventNameLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);

		editEventLeft.add(editEventNameLabel, gbc);

		editEventNameField = new JTextField();
		editEventNameField.setPreferredSize(new Dimension(300, 30));

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 10, 0);

		editEventLeft.add(editEventNameField, gbc);

		// Event location label + location field

		editEventLocationLabel = new JLabel("Plats");
		editEventLocationLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 10, 0);

		editEventLeft.add(editEventLocationLabel, gbc);

		editEventLocationField = new JTextField();
		editEventLocationField.setPreferredSize(new Dimension(300, 30));

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 0, 10, 0);

		editEventLeft.add(editEventLocationField, gbc);

		// Event start time + end time

		editEventStartLabel = new JLabel("Start p� event");
		editEventStartLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(0, 0, 10, 0);

		editEventLeft.add(editEventStartLabel, gbc);

		editFullDayActivity = new JCheckBox();
		editFullDayActivity.setText("Heldags aktivitet?");

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 5;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventLeft.add(editFullDayActivity, gbcLeft);

		editStartModel = new UtilDateModel();
		// model.setDate(20,04,2014);
		// Need this...
		editStartProperties = new Properties();
		editStartProperties.put("text.today", "Today");
		editStartProperties.put("text.month", "Month");
		editStartProperties.put("text.year", "Year");

		editStartDatePanel = new JDatePanelImpl(editStartModel, editStartProperties);
		editStartDatePicker = new JDatePickerImpl(editStartDatePanel, new DateLabelFormatter());
		editStartDatePicker.setTextEditable(false);
		// startDatePicker.setPreferredSize(new Dimension(300, 30))

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 6;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventLeft.add(editStartDatePicker, gbcLeft);

		editStartTimeSpinner = new JSpinner(new SpinnerDateModel());
		editStartTimeEditor = new JSpinner.DateEditor(editStartTimeSpinner, "HH:mm");
		editStartTimeSpinner.setEditor(editStartTimeEditor);
		editStartTimeEditor.setPreferredSize(new Dimension(50, 23));

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 6;
		gbcLeft.insets = new Insets(0, 225, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventLeft.add(editStartTimeSpinner, gbcLeft);

		editEventEndLabel = new JLabel("Slut p� event");
		editEventEndLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.insets = new Insets(0, 0, 10, 0);

		editEventLeft.add(editEventEndLabel, gbc);

		editEndModel = new UtilDateModel();
		// model.setDate(20,04,2014);
		// Need this...
		editEndProperties = new Properties();
		editEndProperties.put("text.today", "Today");
		editEndProperties.put("text.month", "Month");
		editEndProperties.put("text.year", "Year");

		editEndDatePanel = new JDatePanelImpl(editEndModel, editEndProperties);
		editEndDatePicker = new JDatePickerImpl(editEndDatePanel, new DateLabelFormatter());
		editEndDatePicker.setTextEditable(false);
		// endDatePicker.setPreferredSize(new Dimension(200, 30));

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 8;
		gbcLeft.insets = new Insets(0, 0, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventLeft.add(editEndDatePicker, gbcLeft);

		editEndTimeSpinner = new JSpinner(new SpinnerDateModel());
		editEndTimeEditor = new JSpinner.DateEditor(editEndTimeSpinner, "HH:mm");
		editEndTimeSpinner.setEditor(editEndTimeEditor);
		editEndTimeEditor.setPreferredSize(new Dimension(50, 23));

		gbcLeft.gridx = 0;
		gbcLeft.gridy = 8;
		gbcLeft.insets = new Insets(0, 225, 10, 0);
		gbcLeft.anchor = GridBagConstraints.WEST;

		editEventLeft.add(editEndTimeSpinner, gbcLeft);

		editEventDescLabel = new JLabel("Beskriv eventet h�r!");
		editEventDescLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.insets = new Insets(0, 0, 10, 0);

		editEventLeft.add(editEventDescLabel, gbc);

		editEventDescArea = new JTextArea();
		editEventDescArea.setPreferredSize(new Dimension(300, 100));
		editEventDescArea.setLineWrap(true);
		editEventDescArea.setWrapStyleWord(true);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.insets = new Insets(0, 0, 10, 0);

		editEventLeft.add(editEventDescArea, gbc);

		rightPanel.updateUI();

		editFullDayActivity.addActionListener(lForButton);
		editEventDropDown.addActionListener(lForButton);
		editEventButton.addActionListener(lForButton);
		deleteEventButton.addActionListener(lForButton);
		userEditSearchButton.addActionListener(lForButton);
		gbc.insets = new Insets(0, 0, 0, 0);

	}

	public void getAddCalendarPage() {
		rightPanel.removeAll();
		gbc.gridx = 0;
		gbc.gridy = 0;
		rightPanel.add(new CalAddEdit(SM, this, user), gbc);
		rightPanel.updateUI();
	}

	public void calChoiceList() {
		CalendarChoicePanel.removeAll();
		if (calChooseList != null) {
			remove(calChooseList);
		}
		gbc.gridx = 0;
		gbc.gridy = 6;
		calChooseList = new CalChooseList(calArray, SM, this);
		CalendarChoicePanel.add(calChooseList, gbc);
	}

	public void getViewViewer() {
		rightPanel.removeAll();
		gbc.gridx = 0;
		gbc.gridy = 0;
		switch (SM.getActiveview()) {
		case 1:
			rightPanel.add(new DayView(SM, user), gbc);
			break;
		case 2:
			rightPanel.add(new WeekView(SM, user), gbc);
			break;
		case 3:
			rightPanel.add(new MonthView(SM, user, this), gbc);
			break;
		case 4:
			rightPanel.add(new YearView(SM, user, this), gbc);
			break;
		default:
			rightPanel.add(new MonthView(SM, user, this), gbc);
			break;
		}
		rightPanel.updateUI();
	}

	public void getViewChoice() {
		upperRightPanel.removeAll();
		if (viewChoice != null) {
			remove(viewChoice);
		}
		viewChoice = new ViewChoice(SM, this);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		upperRightPanel.add(viewChoice, gbc);
		upperRightPanel.updateUI();
	}

	public void getSharedEventPage() {
		rightPanel.removeAll();
		rightPanel.add(new SharedEventView(this));
		rightPanel.updateUI();
	}

	public void getSharedCalPage() {
		rightPanel.removeAll();
		rightPanel.add(new SharedCalView(this));
		rightPanel.updateUI();
	}

	public void sendUser(User user) {

		this.user = user;
	}

	public String addZero(int value) {
		if (value <= 9) {
			if (value == 0) {
				return "01";
			} else {
				return "0" + value;
			}

		} else {
			return "" + value;
		}

	}

	private class ListenForButton implements ActionListener {

		// This method is called when an event occurs

		public void actionPerformed(ActionEvent e) {
			// Check if the source of the event was the button
			if (e.getSource() == addEventButton) {
				getAddEventPage();
			}

			if (e.getSource() == editFullDayActivity) {

				if (editFullDayActivity.isSelected()) {
					// centerLeft.remove(startTimeSpinner);
					// centerLeft.remove(endTimeSpinner);
					editStartDatePicker.setPreferredSize(new Dimension(300, 30));
					editEndDatePicker.setPreferredSize(new Dimension(300, 30));
					editStartTimeSpinner.setVisible(false);
					editEndTimeSpinner.setVisible(false);
					editEventLeft.updateUI();

				} else {

					editStartDatePicker.setPreferredSize(new Dimension(202, 30));
					editEndDatePicker.setPreferredSize(new Dimension(202, 30));
					editStartTimeSpinner.setVisible(true);
					editEndTimeSpinner.setVisible(true);
					editEventLeft.updateUI();
				}
			}

			if (e.getSource() == editEventDropDown) {
				String inputEditEventName = user.getEventArray()[editEventDropDown.getSelectedIndex()].getName();
				String inputEditEventLocation = user.getEventArray()[editEventDropDown.getSelectedIndex()].getLoc();
				String inputEditEventTextArea = user.getEventArray()[editEventDropDown.getSelectedIndex()]
						.getDescription();
				String inputEditEventStartDate = user.getEventArray()[editEventDropDown.getSelectedIndex()]
						.getStart_time();
				String inputEditEventEndDate = user.getEventArray()[editEventDropDown.getSelectedIndex()].getEnd_time();
				String inputEditEventStartTime = user.getEventArray()[editEventDropDown.getSelectedIndex()]
						.getStart_time();
				String inputEditEventEndTime = user.getEventArray()[editEventDropDown.getSelectedIndex()].getEnd_time();
				int inputEditEventFullDay = user.getEventArray()[editEventDropDown.getSelectedIndex()]
						.getEvent_full_day();
				boolean inputTempBool = false;

				String tempStartDateYear = inputEditEventStartDate.substring(0, 4);
				String tempStartDateMonth = inputEditEventStartDate.substring(5, 7);
				String tempStartDateDay = inputEditEventStartDate.substring(8, 10);

				String tempEndDateYear = inputEditEventEndDate.substring(0, 4);
				String tempEndDateMonth = inputEditEventEndDate.substring(5, 7);
				String tempEndDateDay = inputEditEventEndDate.substring(8, 10);

				String tempStartTimeHour = inputEditEventStartTime.substring(11, 13);
				String tempStartTimeMinute = inputEditEventStartTime.substring(14, 16);

				String tempEndTimeHour = inputEditEventEndTime.substring(11, 13);
				String tempEndTimeMinute = inputEditEventEndTime.substring(14, 16);

				editStartGregCal = new GregorianCalendar();
				editEndGregCal = new GregorianCalendar();

				editStartGregCal.set(java.util.Calendar.YEAR, Integer.parseInt(tempStartDateYear));
				editStartGregCal.set(java.util.Calendar.MONTH, Integer.parseInt(tempStartDateMonth));
				editStartGregCal.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(tempStartDateDay));

				editEndGregCal.set(java.util.Calendar.YEAR, Integer.parseInt(tempEndDateYear));
				editEndGregCal.set(java.util.Calendar.MONTH, Integer.parseInt(tempEndDateMonth));
				editEndGregCal.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(tempEndDateDay));

				editStartGregCal.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(tempStartTimeHour));
				editStartGregCal.set(java.util.Calendar.MINUTE, Integer.parseInt(tempStartTimeMinute));

				editEndGregCal.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(tempEndTimeHour));
				editEndGregCal.set(java.util.Calendar.MINUTE, Integer.parseInt(tempEndTimeMinute));

				if (inputEditEventFullDay == 0) {
					inputTempBool = false;
				} else {
					inputTempBool = true;
				}

				editEventNameField.setText(inputEditEventName);
				editEventLocationField.setText(inputEditEventLocation);
				editEventDescArea.setText(inputEditEventTextArea);
				editFullDayActivity.setSelected(inputTempBool);

				if (editFullDayActivity.isSelected()) {
					// centerLeft.remove(startTimeSpinner);
					// centerLeft.remove(endTimeSpinner);
					editStartDatePicker.setPreferredSize(new Dimension(300, 30));
					editStartDatePicker.getModel().setYear(Integer.parseInt(tempEndDateYear));
					editStartDatePicker.getModel().setMonth(Integer.parseInt(tempEndDateMonth));
					editStartDatePicker.getModel().setDay(Integer.parseInt(tempEndDateDay));
					editStartDatePicker.getModel().setSelected(true);

					editEndDatePicker.setPreferredSize(new Dimension(300, 30));
					editEndDatePicker.getModel().setYear(Integer.parseInt(tempEndDateYear));
					editEndDatePicker.getModel().setMonth(Integer.parseInt(tempEndDateMonth));
					editEndDatePicker.getModel().setDay(Integer.parseInt(tempEndDateDay));
					editEndDatePicker.getModel().setSelected(true);

					editStartTimeSpinner.setVisible(false);
					editStartTimeSpinner.setValue(editStartGregCal.getTime());

					editEndTimeSpinner.setVisible(false);
					editEndTimeSpinner.setValue(editEndGregCal.getTime());

					editEventLeft.updateUI();
				} else {

					editStartDatePicker.setPreferredSize(new Dimension(202, 30));
					editStartDatePicker.getModel().setYear(Integer.parseInt(tempEndDateYear));
					editStartDatePicker.getModel().setMonth(Integer.parseInt(tempEndDateMonth));
					editStartDatePicker.getModel().setDay(Integer.parseInt(tempEndDateDay));
					editStartDatePicker.getModel().setSelected(true);

					editEndDatePicker.setPreferredSize(new Dimension(202, 30));
					editEndDatePicker.getModel().setYear(Integer.parseInt(tempEndDateYear));
					editEndDatePicker.getModel().setMonth(Integer.parseInt(tempEndDateMonth));
					editEndDatePicker.getModel().setDay(Integer.parseInt(tempEndDateDay));
					editEndDatePicker.getModel().setSelected(true);

					editStartTimeSpinner.setVisible(true);
					editEndTimeSpinner.setVisible(true);

					editStartTimeSpinner.setValue(editStartGregCal.getTime());
					editEndTimeSpinner.setValue(editEndGregCal.getTime());

					editEventLeft.updateUI();

				}

			}

			if (e.getSource() == userEditSearchButton) {
				String inputEditSearch = userEditSearchField.getText();
				Object[][] inputEditResult = SQLManager.searchForUser(inputEditSearch);

				editListModel.removeAllElements();

				for (int i = 0; i < inputEditResult.length; i++) {

					editListModel.addElement(inputEditResult[i][1]);

					checkEditList.add(inputEditResult[i][0]);

					System.out.println(inputEditResult[i][0]);
				}
			}

			if (e.getSource() == editEventButton) {

				String inputEventName = editEventNameField.getText();
				String inputEventLocation = editEventLocationField.getText();
				String inputEventTextArea = editEventDescArea.getText();
				int inputFullDayEvent;
				int inputEventStartDay = 0;
				int inputEventStartMonth = 0;
				int inputEventStartYear = 0;
				int inputEventEndDay = 0;
				int inputEventEndMonth = 0;
				int inputEventEndYear = 0;
				int inputEventId = user.getEventArray()[editEventDropDown.getSelectedIndex()].getEvent_id();
				String inputEventStartTime = "";
				String inputEventEndTime = "";

				String formatStartDate = "";
				String formatEndDate = "";

				if (editFullDayActivity.isSelected()) {

					inputFullDayEvent = 1;

					inputEventStartYear = editStartDatePicker.getModel().getYear();
					inputEventStartMonth = editStartDatePicker.getModel().getMonth();
					inputEventStartDay = editStartDatePicker.getModel().getDay();

					inputEventEndYear = editEndDatePicker.getModel().getYear();
					inputEventEndMonth = editEndDatePicker.getModel().getMonth();
					inputEventEndDay = editEndDatePicker.getModel().getDay();

					formatStartDate = inputEventStartYear + "-" + addZero(inputEventStartMonth) + "-"
							+ inputEventStartDay + " 01:01:01";
					formatEndDate = inputEventEndYear + "-" + addZero(inputEventEndMonth) + "-" + inputEventEndDay
							+ " 01:01:01";
				} else {

					inputFullDayEvent = 0;

					inputEventStartYear = editStartDatePicker.getModel().getYear();
					inputEventStartMonth = editStartDatePicker.getModel().getMonth();
					inputEventStartDay = editStartDatePicker.getModel().getDay();

					inputEventEndYear = editEndDatePicker.getModel().getYear();
					inputEventEndMonth = editEndDatePicker.getModel().getMonth();
					inputEventEndDay = editEndDatePicker.getModel().getDay();

					inputEventStartTime = editStartTimeEditor.getFormat().format(editStartTimeSpinner.getValue());
					inputEventEndTime = editEndTimeEditor.getFormat().format(editEndTimeSpinner.getValue());

					formatStartDate = inputEventStartYear + "-" + addZero(inputEventStartMonth) + "-"
							+ inputEventStartDay + " " + inputEventStartTime + ":01";
					formatEndDate = inputEventEndYear + "-" + addZero(inputEventEndMonth) + "-" + inputEventEndDay + " "
							+ inputEventEndTime + ":01";
				}

				// System.out.println(inputEventStartDay);
				// System.out.println(inputEventStartMonth);
				// System.out.println(inputEventStartYear);

				// System.out.println("Start date: " + formatStartDate);
				// System.out.println("End date: " + formatEndDate);
				//
				// System.out.println(inputCreateEventForCalendarId);

				SQLManager.editEvent(inputEventName, inputEventLocation, inputEventTextArea, inputFullDayEvent,
						inputEventId, formatStartDate, formatEndDate);

				user.reloadarrays();

				if (editListModel.size() > 0) {
					for (int i = 0; i < userEditList.getSelectedIndices().length; i++) {

						Object selectedUserId = checkEditList.get(userEditList.getSelectedIndices()[i]);
						int tempSelectedUserId = Integer.parseInt((String) selectedUserId);
						// System.out.println(
						// tempSelectedUserId + " ---------------------- Users
						// id ------------------------ ");

						SQLManager.sendEventInvite(tempSelectedUserId);

					}
				}

				getDeleteAndEditEventPage();

				notificationPanel.removeAll();

				notificationPanel.add(new NotificationView());

				notificationPanel.updateUI();

			}

			if (e.getSource() == deleteEventButton) {

				int eventId = user.getEventArray()[editEventDropDown.getSelectedIndex()].getEvent_id();

				SQLManager.deleteEvent(eventId);

				user.reloadarrays();

				getDeleteAndEditEventPage();

				notificationPanel.removeAll();

				notificationPanel.add(new NotificationView());

				notificationPanel.updateUI();
			}

			if (e.getSource() == userSearchButton) {
				String inputSearch = userSearchField.getText();
				Object[][] inputResult = SQLManager.searchForUser(inputSearch);

				listModel.removeAllElements();

				for (int i = 0; i < inputResult.length; i++) {
					listModel.addElement(inputResult[i][1]);

					checkList.add(inputResult[i][0]);

					System.out.println(inputResult[i][0]);
				}
			}

			if (e.getSource() == fullDayActivity) {

				if (fullDayActivity.isSelected()) {
					// centerLeft.remove(startTimeSpinner);
					// centerLeft.remove(endTimeSpinner);
					startDatePicker.setPreferredSize(new Dimension(300, 30));
					endDatePicker.setPreferredSize(new Dimension(300, 30));
					centerLeft.updateUI();
					startTimeSpinner.setVisible(false);
					endTimeSpinner.setVisible(false);
					centerLeft.updateUI();
				} else {
					startDatePicker.setPreferredSize(new Dimension(202, 30));
					endDatePicker.setPreferredSize(new Dimension(202, 30));
					startTimeSpinner.setVisible(true);
					endTimeSpinner.setVisible(true);
					centerLeft.updateUI();
				}

			}

			if (e.getSource() == eventCreate) {

				String inputEventName = nameField.getText();
				String inputEventLocation = locationField.getText();
				String inputEventTextArea = eventDescArea.getText();
				int inputFullDayEvent;
				int inputEventStartDay = 0;
				int inputEventStartMonth = 0;
				int inputEventStartYear = 0;
				int inputEventEndDay = 0;
				int inputEventEndMonth = 0;
				int inputEventEndYear = 0;
				int inputCreateEventForCalendarId = user.getCalArray()[calDropDown.getSelectedIndex()].getCal_id();
				String inputEventStartTime = "";
				String inputEventEndTime = "";

				String formatStartDate = "";
				String formatEndDate = "";

				if (fullDayActivity.isSelected()) {

					inputFullDayEvent = 1;

					inputEventStartYear = startDatePicker.getModel().getYear();
					inputEventStartMonth = startDatePicker.getModel().getMonth();
					inputEventStartDay = startDatePicker.getModel().getDay();

					inputEventEndYear = endDatePicker.getModel().getYear();
					inputEventEndMonth = endDatePicker.getModel().getMonth();
					inputEventEndDay = endDatePicker.getModel().getDay();

					formatStartDate = inputEventStartYear + "-" + (inputEventStartMonth + 1) + "-" + inputEventStartDay
							+ " 01:01:01";
					formatEndDate = inputEventEndYear + "-" + (inputEventEndMonth + 1) + "-" + inputEventEndDay
							+ " 01:01:01";
				} else {

					inputFullDayEvent = 0;

					inputEventStartYear = startDatePicker.getModel().getYear();
					inputEventStartMonth = startDatePicker.getModel().getMonth();
					inputEventStartDay = startDatePicker.getModel().getDay();

					inputEventEndYear = endDatePicker.getModel().getYear();
					inputEventEndMonth = endDatePicker.getModel().getMonth();
					inputEventEndDay = endDatePicker.getModel().getDay();

					inputEventStartTime = startTimeEditor.getFormat().format(startTimeSpinner.getValue());
					inputEventEndTime = endTimeEditor.getFormat().format(endTimeSpinner.getValue());

					formatStartDate = inputEventStartYear + "-" + (inputEventStartMonth + 1) + "-" + inputEventStartDay
							+ " " + inputEventStartTime + ":01";
					formatEndDate = inputEventEndYear + "-" + (inputEventEndMonth + 1) + "-" + inputEventEndDay + " "
							+ inputEventEndTime + ":01";
				}

				// System.out.println(inputEventStartDay);
				// System.out.println(inputEventStartMonth);
				// System.out.println(inputEventStartYear);

				// System.out.println("Start date: " + formatStartDate);
				// System.out.println("End date: " + formatEndDate);
				//
				// System.out.println(inputCreateEventForCalendarId);

				// Create the event

				SQLManager.addEvent(inputEventName, inputEventLocation, inputEventTextArea, inputFullDayEvent,
						inputCreateEventForCalendarId, formatStartDate, formatEndDate);

				user.reloadarrays();

				// Send invite request to people in the list

				if (listModel.size() > 0) {
					for (int i = 0; i < userList.getSelectedIndices().length; i++) {

						Object selectedUserId = checkList.get(userList.getSelectedIndices()[i]);
						int tempSelectedUserId = Integer.parseInt((String) selectedUserId);
						System.out.println(
								tempSelectedUserId + " ---------------------- Users id ------------------------ ");

						SQLManager.sendEventInvite(tempSelectedUserId);

					}
				}

				getAddEventPage();

				notificationPanel.removeAll();

				notificationPanel.add(new NotificationView());

				notificationPanel.updateUI();

			}

		}

	}
}
