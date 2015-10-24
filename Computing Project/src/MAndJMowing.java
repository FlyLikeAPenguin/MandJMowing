import java.awt.Color; // Import Everything I need
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
//Fix Set Days.  
@SuppressWarnings("serial")
public class MAndJMowing extends JFrame{ //FC3 Complexity of programming Language (Java) - OOP independent of GUI .

	private JPanel display = new JPanel(); //Panel for displaying logo, customer management, printing and timetable
	private JLabel sizeHolder = new JLabel(new ImageIcon(getClass().getResource("SizeHolder.jpg"))); //1x598px
	private List<Customer> customers = new ArrayList<Customer>(); //Main list of all customers
	private ArrayList<String> customerNames = new ArrayList<String>(); // FC2 Lists
	private ArrayList<String> postcodes = new ArrayList<String>();
	private String custSelected = "";
	private String allText = "";
	private String[] namesArray = new String[128];
	private int custToChange = -1;
	private Integer[][] distances = new Integer[10][10];
	final JCheckBox[] editDays = new JCheckBox[10];
	private Customer[][] ttDays = new Customer[10][10]; //FC2 2D Array - Time table days

	MAndJMowing() throws IOException{

		//Initialisation

		final JPanel menuOptions = new JPanel();
		final JPanel customerOptions = new JPanel();
		final JPanel viewOptions = new JPanel();
		final JPanel customerInput = new JPanel();
		final JPanel customerEdit = new JPanel();
		final JPanel customerDelete = new JPanel();
		final JPanel logoHolder = new JPanel();
		final JPanel customerInvoice = new JPanel();
		JLabel logoImage = new JLabel(new ImageIcon(getClass().getResource("M&JMowing.jpg"))); //Load logo image
		final JPanel timetable1 = new JPanel();
		final JPanel timetable2 = new JPanel();

		logoHolder.add(logoImage);
		ReadAllCustomers(); 
		namesArray = GetNames();
		InsertionSort(namesArray, namesArray.length); 
		final JComboBox<String> names = new JComboBox<String>(namesArray);
		names.setSelectedIndex(-1);

		for (Customer i : customers){ // FC1 List Traversal - This loop creates a customer, i, and for each postition is the list of customers, assigns it to i to be worked on.

			i.GetPostCode();

		}

		JPanel[] bothWeeks = MakeWeek(customers, timetable1, timetable2); //Fills in the timetable with customers in the order they are written.
		for (int i = 0; i < 5; i++){
			System.out.println("Adding Day: " + i + bothWeeks[i]);
			timetable1.add(bothWeeks[i]);
			bothWeeks[i].setVisible(true);
		}
		for (int i = 5; i < 10; i++){
			System.out.println(i);

			timetable2.add(bothWeeks[i]);
			bothWeeks[i].setVisible(true);

		}

		//Setting up Menus Options JPanel

		setLayout(new FlowLayout());
		final JButton manageCustomers = new JButton("Manage Customers");	
		menuOptions.add(manageCustomers);

		final JButton organiseTimetable = new JButton("Organise Timetable");
		menuOptions.add(organiseTimetable);

		final JButton viewTimetable = new JButton("View Timetable");
		menuOptions.add(viewTimetable);

		final JButton printInvoiceMenu = new JButton("Print Invoice");
		menuOptions.add(printInvoiceMenu);	



		//Setting up Manage Customer Panel

		JButton addCustomer = new JButton("Add Customer");
		customerOptions.add(addCustomer);

		JButton editCustomer = new JButton("Edit Customer");
		customerOptions.add(editCustomer);		

		JButton deleteCustomer = new JButton("Delete Customer");
		customerOptions.add(deleteCustomer);

		JButton backFromCust = new JButton("Back");
		customerOptions.add(backFromCust);

		//Setting up View Timetable Panel
		JButton switchWeek = new JButton("Switch Week");
		viewOptions.add(switchWeek);

		JButton search = new JButton("Search");
		viewOptions.add(search);

		JButton backFromViewTimetable = new JButton("Back");
		viewOptions.add(backFromViewTimetable);

		//Setting up Add Customer Input Form

		customerInput.setLayout(new GridBagLayout());

		GridBagConstraints gbcCustInput = new GridBagConstraints();
		gbcCustInput.insets = new Insets(25, 25, 25, 25);

		gbcCustInput.gridx = 0;
		gbcCustInput.gridy = 0;

		JLabel name = new JLabel("Enter Customer Name: ");
		customerInput.add(name, gbcCustInput);

		gbcCustInput.gridx = 1;
		gbcCustInput.gridy = 0;

		final JTextField enterName = new JTextField(16);
		customerInput.add(enterName, gbcCustInput);

		gbcCustInput.gridx = 0;
		gbcCustInput.gridy = 1;

		JLabel address = new JLabel("Enter Address: ");
		customerInput.add(address, gbcCustInput);

		gbcCustInput.gridx = 1;
		gbcCustInput.gridy = 1;

		final JTextField enterAddress = new JTextField(16);
		customerInput.add(enterAddress, gbcCustInput);

		gbcCustInput.gridx = 0;
		gbcCustInput.gridy = 2;

		JLabel postcode = new JLabel("Enter Post Code: ");
		customerInput.add(postcode, gbcCustInput);

		gbcCustInput.gridx = 1;
		gbcCustInput.gridy = 2;

		final JTextField enterPostcode = new JTextField(16);
		customerInput.add(enterPostcode, gbcCustInput);

		gbcCustInput.gridx = 0;
		gbcCustInput.gridy = 3;

		JLabel phoneNumber = new JLabel("Enter Phone Number: ");
		customerInput.add(phoneNumber, gbcCustInput);

		gbcCustInput.gridx = 1;
		gbcCustInput.gridy = 3;

		final JTextField enterPhoneNumber = new JTextField(16);
		customerInput.add(enterPhoneNumber, gbcCustInput);

		gbcCustInput.gridx = 0;
		gbcCustInput.gridy = 4;

		JLabel additionalInformation = new JLabel("Enter Additional Information: ");
		customerInput.add(additionalInformation, gbcCustInput);

		gbcCustInput.gridx = 1;
		gbcCustInput.gridy = 4;

		final JTextField enterAdditionalInformation = new JTextField(16);
		customerInput.add(enterAdditionalInformation, gbcCustInput);

		gbcCustInput.gridx = 0;
		gbcCustInput.gridy = 5;

		JLabel selectDays = new JLabel("Select Days:");
		customerInput.add(selectDays, gbcCustInput);

		//Select Day Check Boxes	

		gbcCustInput.gridx = 1;
		gbcCustInput.gridy = 5;

		JPanel selectDay = new JPanel();
		selectDay.setLayout(new GridLayout(2, 5));

		final JCheckBox[] days = new JCheckBox[10];

		days[0] = new JCheckBox("M", false);
		days[1] = new JCheckBox("T", false);
		days[2] = new JCheckBox("W", false);
		days[3] = new JCheckBox("T", false);
		days[4] = new JCheckBox("F", false);
		days[5] = new JCheckBox("M", false);
		days[6] = new JCheckBox("T", false);
		days[7] = new JCheckBox("W", false);
		days[8] = new JCheckBox("T", false);
		days[9] = new JCheckBox("F", false);

		for (JCheckBox i : days){
			selectDay.add(i);
		}

		customerInput.add(selectDay, gbcCustInput);

		gbcCustInput.gridx = 1;
		gbcCustInput.gridy = 6;

		final JLabel valid = new JLabel("");
		customerInput.add(valid, gbcCustInput);

		gbcCustInput.gridx = 0;
		gbcCustInput.gridy = 6;

		JPanel saveClear = new JPanel();

		JButton save = new JButton("Save");
		saveClear.add(save);

		JButton clear = new JButton("Clear");
		saveClear.add(clear);

		customerInput.add(saveClear, gbcCustInput);

		//Setting up Edit Customer Form

		customerEdit.setLayout(new GridBagLayout());

		GridBagConstraints gbcCustEdit = new GridBagConstraints();
		gbcCustEdit.insets = new Insets(25, 25, 25, 25);

		gbcCustEdit.gridx = 0;
		gbcCustEdit.gridy = 0;

		final JLabel custEditName = new JLabel("Customer Name: ");
		customerEdit.add(custEditName, gbcCustEdit);		

		gbcCustEdit.gridx = 1;
		gbcCustEdit.gridy = 0;

		//Names Combo box initialised at the top

		customerEdit.add(names, gbcCustEdit);	

		gbcCustEdit.gridx = 0;
		gbcCustEdit.gridy = 1;

		final JLabel custEditAddress = new JLabel("Address: ");
		customerEdit.add(custEditAddress, gbcCustEdit);

		gbcCustEdit.gridx = 1;
		gbcCustEdit.gridy = 1;

		final JTextField editAddress = new JTextField(16);
		customerEdit.add(editAddress, gbcCustEdit);

		gbcCustEdit.gridx = 0;
		gbcCustEdit.gridy = 2;

		final JLabel custEditPostcode = new JLabel("Post Code: ");
		customerEdit.add(custEditPostcode, gbcCustEdit);

		gbcCustEdit.gridx = 1;
		gbcCustEdit.gridy = 2;

		final JTextField editPostcode = new JTextField(16);
		customerEdit.add(editPostcode, gbcCustEdit);

		gbcCustEdit.gridx = 0;
		gbcCustEdit.gridy = 3;

		final JLabel custEditPhoneNumber = new JLabel("Phone Number: ");
		customerEdit.add(custEditPhoneNumber, gbcCustEdit);

		gbcCustEdit.gridx = 1;
		gbcCustEdit.gridy = 3;

		final JTextField editPhoneNumber = new JTextField(16);
		customerEdit.add(editPhoneNumber, gbcCustEdit);

		gbcCustEdit.gridx = 0;
		gbcCustEdit.gridy = 4;

		final JLabel custEditAdditionalInformation = new JLabel("Additional Information: ");
		customerEdit.add(custEditAdditionalInformation, gbcCustEdit);

		gbcCustEdit.gridx = 1;
		gbcCustEdit.gridy = 4;

		final JTextField editAdditionalInformation = new JTextField(16);
		customerEdit.add(editAdditionalInformation, gbcCustEdit);

		gbcCustInput.gridx = 0;
		gbcCustInput.gridy = 5;

		JLabel editSelectDays = new JLabel("Select Days:");
		customerEdit.add(editSelectDays, gbcCustInput);

		gbcCustInput.gridx = 1;
		gbcCustInput.gridy = 5;

		JPanel editSelectDay = new JPanel();
		editSelectDay.setLayout(new GridLayout(2, 5));

		editDays[0] = new JCheckBox("M", false);
		editDays[1] = new JCheckBox("T", false);
		editDays[2] = new JCheckBox("W", false);
		editDays[3] = new JCheckBox("T", false);
		editDays[4] = new JCheckBox("F", false);
		editDays[5] = new JCheckBox("M", false);
		editDays[6] = new JCheckBox("T", false);
		editDays[7] = new JCheckBox("W", false);
		editDays[8] = new JCheckBox("T", false);
		editDays[9] = new JCheckBox("F", false);

		for (JCheckBox i : editDays){
			editSelectDay.add(i);
		}

		customerEdit.add(editSelectDay, gbcCustInput);


		gbcCustEdit.gridx = 1;
		gbcCustEdit.gridy = 6;

		final JLabel custEditValid = new JLabel("");
		customerEdit.add(custEditValid, gbcCustEdit);

		gbcCustEdit.gridx = 0;
		gbcCustEdit.gridy = 6;

		JButton custEditSave = new JButton("Save");

		customerEdit.add(custEditSave, gbcCustEdit);

		//Setting Up Delete Customer

		customerDelete.setLayout(new GridLayout(2, 2, 15, 15));

		JLabel delCust = new JLabel("Select the Customer \n to Delete");
		customerDelete.add(delCust);

		final JComboBox<String> namesDel = new JComboBox<String>(namesArray);


		customerDelete.add(namesDel);

		JButton deleteCust = new JButton("Delete");
		customerDelete.add(deleteCust);

		//Setting Up Print Invoice

		customerInvoice.setLayout(new GridBagLayout());
		GridBagConstraints invGBC = new GridBagConstraints();
		invGBC.insets = new Insets(5, 5, 5, 5);

		invGBC.gridx = 0;
		invGBC.gridy = 0;

		JPanel loadPrint = new JPanel();

		JButton loadInvoice = new JButton("Load Invoice");
		loadPrint.add(loadInvoice);

		JButton printInvoice = new JButton("Print Invoice");
		loadPrint.add(printInvoice);

		customerInvoice.add(loadPrint, invGBC);

		invGBC.gridx = 0;
		invGBC.gridy = 1;

		final JTextArea invoiceArea = new JTextArea(16, 50);
		invoiceArea.setLineWrap(true);
		invoiceArea.setWrapStyleWord(true);
		invoiceArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		invoiceArea.setToolTipText("Enter the text you wish to be printed as the invoice or click on 'Load Invoice' to select a template");
		invoiceArea.setMargin(new Insets (5, 5, 5, 5));
		JScrollPane scrollPane = new JScrollPane(invoiceArea);
		customerInvoice.add(scrollPane, invGBC);

		//Adding Panels

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);

		gbc.gridx = 0;
		gbc.gridy = 0;

		add(display, gbc);

		display.add(customerInput);
		customerInput.setVisible(false);
		display.add(customerEdit);
		customerEdit.setVisible(false);
		display.add(customerDelete);
		customerDelete.setVisible(false);
		display.add(customerInvoice);
		customerInvoice.setVisible(false);
		display.add(timetable1);
		timetable1.setVisible(false);
		display.add(timetable2);
		timetable2.setVisible(false);
		display.add(sizeHolder);
		display.add(logoHolder);


		gbc.gridx = 0;
		gbc.gridy = 1;

		add(customerOptions, gbc);
		customerOptions.setVisible(false);

		add(viewOptions, gbc);
		viewOptions.setVisible(false);

		add(menuOptions, gbc);

		//Event Handlers

		manageCustomers.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				SwitchDisplay(logoHolder);
				SwitchButtons(menuOptions, customerOptions);

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		switchWeek.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				SwitchButtons(menuOptions, viewOptions);

				if (timetable1.isVisible()){
					SwitchDisplay(timetable2);
				} else {
					SwitchDisplay(timetable1);
				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		viewTimetable.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				SwitchButtons(menuOptions, viewOptions);

				JPanel[] bothWeeks = MakeWeek(customers, timetable1, timetable2);
				for (int i = 0; i < 5; i++){
					System.out.println("Adding Day: " + i + bothWeeks[i]);
					timetable1.add(bothWeeks[i]);
					bothWeeks[i].setVisible(true);
				}
				for (int i = 5; i < 10; i++){
					System.out.println(i);

					timetable2.add(bothWeeks[i]);
					bothWeeks[i].setVisible(true);

				}

				SwitchDisplay(timetable1);
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		search.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {


				String searchTerm = JOptionPane.showInputDialog(null, "Please enter the Search Term: ");


				for (Customer tempCust : customers) { //FC1

					if (tempCust.GetName().contains(searchTerm) || tempCust.GetAddress().contains(searchTerm) || tempCust.GetPostCode().contains(searchTerm)) {

						JPanel panel = new JPanel();
						System.out.println("Found: " + tempCust.GetName());

						panel.add(new JLabel("<HTML><strong>" + tempCust.GetName() + "</strong><i><ul><li>" + tempCust.GetAddress() + "</li><li>" + tempCust.GetInfo() + "<i></li></ul></HTML>")); 


						JOptionPane.showMessageDialog(null,panel,"Information",JOptionPane.INFORMATION_MESSAGE);
					}
				}

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		save.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				CreateCustomer(enterName.getText(), enterAddress.getText(), enterPostcode.getText(), enterPhoneNumber.getText(), enterAdditionalInformation.getText(), GetSelectedDays(days));

				if (customers.get(customers.size()-1).GetValid() == false){

					valid.setText("Invalid Customer Input");
					customers.remove(customers.size()-1);

				} else {

					WriteCustomersToFile();

					valid.setText("");
					enterName.setText("");
					enterAddress.setText("");
					enterPostcode.setText("");
					enterPhoneNumber.setText("");
					enterAdditionalInformation.setText("");

					for (JCheckBox i : days){
						i.setSelected(false);
					}
					
					JOptionPane.showMessageDialog(null, "Customer saved succesfully.");

				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		addCustomer.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				SwitchDisplay(customerInput);

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		editCustomer.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				try {
					ReadAllCustomers();
				} catch (IOException e) {
					e.printStackTrace();
				}

				SwitchDisplay(customerEdit);
				namesArray = GetNames();
				InsertionSort(namesArray, namesArray.length);;

				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(namesArray);
				names.setModel(model);
				names.setSelectedIndex(-1);

				editAddress.setText("");
				editPostcode.setText("");
				editPhoneNumber.setText("");
				editAdditionalInformation.setText("");
				for (JCheckBox i : editDays){
					i.setSelected(false);
				}

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		backFromCust.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				SwitchButtons(customerOptions, menuOptions);
				SwitchDisplay(logoHolder);

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		backFromViewTimetable.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				SwitchButtons(viewOptions, menuOptions);
				SwitchDisplay(logoHolder);

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		clear.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				valid.setText("");

				enterName.setText("");
				enterAddress.setText("");
				enterPostcode.setText("");
				enterPhoneNumber.setText("");
				enterAdditionalInformation.setText("");

				for (JCheckBox i : days){
					i.setSelected(false);
				}

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		custEditSave.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				//FC2 
				Customer editCust = new Customer(custSelected, editAddress.getText(), editPostcode.getText(), editPhoneNumber.getText(), editAdditionalInformation.getText(), GetSelectedDays(editDays));

				ReplaceCustomer(custToChange, editCust);

				if (customers.get(custToChange).GetValid() == false){

					valid.setText("Invalid Customer Input");
					customers.remove(custToChange);
					custEditValid.setText("Invalid Customer Input");

				} else {


					WriteCustomersToFile();

					custEditValid.setText("");

					JOptionPane.showMessageDialog(null, "Customer editted succesfully.");
				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		names.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {

				custSelected = (String) e.getItem();

				if (e.getStateChange() == ItemEvent.SELECTED){

					for (Customer i : customers){ //FC1

						if (i.GetName().equals(e.getItem())){

							custToChange = customers.indexOf(i);

							editAddress.setText(i.GetAddress());
							editPostcode.setText(i.GetPostCode());
							editPhoneNumber.setText(i.GetPhoneNumber());
							editAdditionalInformation.setText(i.GetInfo());
							SetDays(i.GetDays());

						}
					}
				}
			}
		});

		organiseTimetable.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				timetable1.removeAll();
				timetable2.removeAll();



				Thread t = new Thread(new threadle(organiseTimetable));
				t.start(); // Creates a new Thread that sets the text of the organise timetable button to "Organising..."
				synchronized (t) {
					t.notify();
				}//Notifies that thread to carry on.



				for (int i = 0; i < 5; i++){

					List<Customer> orderedCustDay = new ArrayList<Customer>();

					for (int z = 0; z < 10; z++){
						System.out.print(i);
						if (ttDays[i][z] != null){
							orderedCustDay.add(ttDays[i][z]);
							System.out.println(i + " " + z);

						}	
					}

					GetPostcodes(orderedCustDay);

					try {
						FillArray();
					} catch (Exception e) {
						e.printStackTrace();
					}
					try{
						int[] route = FindRoute(0);
						System.out.println("Route Made");
						//FC2
						Customer[] dayCopy = orderedCustDay.toArray(new Customer[orderedCustDay.size()]);

						for (int c = 0; c < dayCopy.length; c++){

							dayCopy[c] = orderedCustDay.get(c);

						}


						for (int x = 0; x < dayCopy.length; x++){
							for (int y = 0; y < route.length; y++){
								System.out.println("Route Char: " + route[y]);
							}
							System.out.println("Day Copy Length:" + dayCopy.length);
							System.out.println("Route Value(" + x + "): " + route[x]);
							System.out.println("Setting Day(" + x + ") with: " + dayCopy[route[x]].GetName());
							orderedCustDay.set(x, dayCopy[route[x]]);
						}

						timetable1.add(MakeDay(orderedCustDay));

						orderedCustDay.clear();
					} catch(Exception e) {
						JOptionPane.showMessageDialog(null, "An Error Occured while Organising - Week 1");
						JPanel[] bothWeeks = MakeWeek(customers, timetable1, timetable2);
						for (int q = 0; q < 5; q++){
							System.out.println("Adding Day: " + q + bothWeeks[q]);
							timetable1.add(bothWeeks[q]);
							bothWeeks[q].setVisible(true);
						}
						for (int q = 5; q < 10; q++){
							System.out.println(q);

							timetable2.add(bothWeeks[q]);
							bothWeeks[q].setVisible(true);

						}
						break;
					}
				}

				for (int i = 5; i < 10; i++){

					List<Customer> orderedCustDay = new ArrayList<Customer>();

					for (int z = 0; z < 10; z++){
						System.out.print(i);
						if (ttDays[i][z] != null){
							orderedCustDay.add(ttDays[i][z]);
							System.out.println(i + " " + z);

						}	
					}

					GetPostcodes(orderedCustDay);

					try {
						FillArray();
					} catch (Exception e) {
						e.printStackTrace();
					}
					try{
						int[] route = FindRoute(0);
						System.out.println("Route Made");
						Customer[] dayCopy = orderedCustDay.toArray(new Customer[orderedCustDay.size()]);

						for (int c = 0; c < dayCopy.length; c++){

							dayCopy[c] = orderedCustDay.get(c);

						}


						for (int x = 0; x < dayCopy.length; x++){
							for (int y = 0; y < route.length; y++){
								System.out.println("Route Char: " + route[y]);
							}
							System.out.println("Day Copy Length:" + dayCopy.length);
							System.out.println("Route Value(" + x + "): " + route[x]);
							System.out.println("Setting Day(" + x + ") with: " + dayCopy[route[x]].GetName());
							orderedCustDay.set(x, dayCopy[route[x]]);
						}

						timetable2.add(MakeDay(orderedCustDay));

						orderedCustDay.clear();

					} catch(Exception e) {
						JOptionPane.showMessageDialog(null, "An Error Occured while Organising - Week 2");
						JPanel[] bothWeeks = MakeWeek(customers, timetable1, timetable2);
						for (int q = 0; q < 5; q++){
							System.out.println("Adding Day: " + i + bothWeeks[i]);
							timetable1.add(bothWeeks[q]);
							bothWeeks[q].setVisible(true);
						}
						for (int q = 5; q < 10; q++){
							System.out.println(q);

							timetable2.add(bothWeeks[q]);
							bothWeeks[q].setVisible(true);

						}
						break;
					}
				}
				organiseTimetable.setText("Organise Timetable");
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		printInvoice.addMouseListener(new MouseListener() {			

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				PrintInvoice(invoiceArea);
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		printInvoiceMenu.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				SwitchDisplay(customerInvoice);	

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		loadInvoice.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				String fileName = file.getAbsolutePath();

				try {

					BufferedReader bufReader = new BufferedReader(new FileReader(fileName));
					invoiceArea.read(bufReader, null);
					bufReader.close();
					invoiceArea.requestFocus();

				} catch (Exception e){

					JOptionPane.showMessageDialog(null, e);

				}

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		deleteCustomer.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				SwitchDisplay(customerDelete);
				namesArray = GetNames();
				InsertionSort(namesArray, namesArray.length);;

				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(namesArray);
				namesDel.setModel(model);
				namesDel.setSelectedIndex(-1);

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		deleteCust.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {

				Iterator<Customer> it = customers.iterator();
				while (it.hasNext()) {
					if (it.next().GetName().equals(namesDel.getSelectedItem())) {
						it.remove();

						namesArray = GetNames();
						InsertionSort(namesArray, namesArray.length);;

						DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(namesArray);
						namesDel.setModel(model);
						namesDel.setSelectedIndex(-1);

						WriteCustomersToFile();
						try {
							ReadAllCustomers();
						} catch (IOException e) {

							e.printStackTrace();
						}
						break;
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(this.getExtendedState() | Frame.MAXIMIZED_BOTH); //Makes the window maximized
		this.pack();
		this.setTitle("M&J Mowing and Garden Services");

	}
	//Graphical
	private void SwitchButtons(JPanel oldButtons, JPanel newButtons){

		oldButtons.setVisible(false);
		newButtons.setVisible(true);

	}

	private void SwitchDisplay(JPanel newDisplay){

		for (Component i : display.getComponents()){

			i.setVisible(false);

		}

		sizeHolder.setVisible(true);
		newDisplay.setVisible(true);

	}

	private JPanel MakeDay (List<Customer> custs){

		//FC2
		Customer[] dayCusts = custs.toArray(new Customer[custs.size()]);
		JPanel day = new JPanel();
		day.setLayout(new GridLayout(custs.size(), 1));
		int labelHeight = 598/(custs.size() + 1);

		for (int i = 0; i < dayCusts.length; i++){

			JLabel custData = new JLabel("");

			String name = dayCusts[i].GetName();
			String address = dayCusts[i].GetAddress();
			String pc = dayCusts[i].GetPostCode();

			System.out.println("Setting Text.");

			//HTML
			custData.setText("<HTML><strong>" + name + "</strong><i><ul><li>" + address + "</li><li>" + pc + "<i></li></ul></HTML>");
			System.out.println("Setting Border.");
			custData.setBorder(new BevelBorder(BevelBorder.RAISED));
			custData.setPreferredSize(new Dimension(150, labelHeight));
			System.out.println("Setting Size.");

			custData.setSize(150, labelHeight);
			day.add(custData);

		}


		return day;

	}

	private JPanel[] MakeWeek (List<Customer> allCusts, JPanel tt1, JPanel tt2){

		tt1.removeAll();
		tt2.removeAll();


		JPanel[] days = new JPanel[10];

		for (int i = 0; i < 10; i++){
			System.out.println("*********DAY: " + (i+1) + "*********");
			List<Customer> day = new ArrayList<Customer>();

			Iterator<Customer> it = allCusts.iterator(); // FC1 List Traversal - Using an iterator to loop through the list.
			int count = 0;
			while (it.hasNext()) {
				Customer cust = it.next();
				int[] custDays = cust.GetDays();
				for(int x : custDays){
					System.out.println("This cust's days: " + x);
				}

				if ( custDays[i] == 1){
					System.out.println("We Got One!");

					day.add(cust);
					ttDays[i][count] = cust;
					count++;
				}	
			}


			days[i] = MakeDay(day);

		}

		for (JPanel x : days){ 
			System.out.println("JPanel: " + x);
		}


		return days;

	}

	//Customers
	private void CreateCustomer(String name, String address, String postcode, String phoneNumber, String additionalInformation, int[] days){
		//FC2 - Customer is a record.
		//FC2 - Creating objects at runtime
		Customer cust = new Customer(name, address, postcode, phoneNumber, additionalInformation, days);
		customers.add(cust);

	}

	private void WriteCustomersToFile(){

		//  G:\\Documents\\Programming Outputs\\RawStore.txt


		try{

			PrintWriter writ = new PrintWriter("C:\\Program Files\\MAndJMowing\\RawStore.txt"); // src//RawStore.txt



			for (Customer i : customers){ //FC1

				writ.append(i.GetName() + "♦");								
				writ.append(i.GetAddress() + "♦");								
				writ.append(i.GetPostCode() + "♦");
				writ.append(i.GetPhoneNumber() + "♦");
				writ.append(i.GetInfo() + "♦");//♦ = ALT + 260 | ♠  = ALT + 262
				for (int x = 0; x < 10; x++){
					writ.append(i.GetDays()[x] + "");
				}
				writ.append("♦");

			}


			writ.close();

			System.out.println("File Written.");

		}

		catch (Exception e){

			e.printStackTrace();

		}
	}

	private String ReadFullFile() throws IOException{

		
		
		FileReader frd = new FileReader("C:\\Program Files\\MAndJMowing\\RawStore.txt");

		Scanner scn = new Scanner(frd);
		scn.useDelimiter("\\A");

		String text = scn.next();

		frd.close();
		scn.close();

		return text;

	}

	private Customer ReadCustomer(){

		String[] customerBuilder = new String[5];

		for (int i = 0; i < 5; i++){

			String custField = allText.substring(0, allText.indexOf('♦'));
			customerBuilder[i] = custField;
			allText = allText.replaceFirst(allText.substring(0, allText.indexOf('♦')+1), "");

		}
		//Integer.parseInt(String.valueOf(s.charAt(i)));
		String rawDays;
		int[] daysSelected = new int[10];

		rawDays = allText.substring(0, allText.indexOf('♦'));

		for (int i = 0; i < rawDays.length(); i++)
		{
			daysSelected[i] = Character.getNumericValue(rawDays.charAt(i));
		}

		allText = allText.replaceFirst(allText.substring(0, allText.indexOf('♦')+1), "");

		System.out.println(allText);
		customerNames.add(customerBuilder[0]);
		return new Customer(customerBuilder[0], customerBuilder[1], customerBuilder[2], customerBuilder[3], customerBuilder[4], daysSelected);
		//FC2
	}

	private void ReadAllCustomers() throws IOException{

		customers.clear();

		allText = ReadFullFile();
		int occurances = (allText.length() - allText.replace("♦", "").length()) / 6;
		System.out.println(occurances);

		if (occurances != 0){
			for (int i = 0; i < occurances; i++){

				customers.add(ReadCustomer());

			}
		}
	}

	private void ReplaceCustomer(int index, Customer cust){

		customers.set(index, cust);

	}

	private int[] GetSelectedDays (JCheckBox[] days){

		int[] selectedDays = new int[10];
		int count = 0;
		for (JCheckBox i : days){ 
			if (i.isSelected()){

				selectedDays[count] =  1;
				count++;
			} else {

				selectedDays[count] = 0;				
				count++;
			}
		}
		System.out.println(selectedDays);
		return selectedDays;
	}	

	private void SetDays(int[] js){
		int[] daysToSelect = js;


		for (int i = 0; i < daysToSelect.length; i++){
			System.out.println(daysToSelect[i]);

			if (daysToSelect[i] == 1){
				System.out.println(daysToSelect[i]);
				editDays[i].setSelected(true);
			} else {
				editDays[i].setSelected(false);
			}
		}

	}	

	private String[] GetNames(){
		ArrayList<String> names = new ArrayList<String>();

		for (Customer i : customers){ //FC1
			names.add(i.GetName());
		}

		return names.toArray(new String[names.size()]);
	}
	//FC1 Route Planning - Distance Matrix
	//FC2 Time complexity / Space complexity | Polynomial / Exponential Volumetrics e.g. scheduling / timetabling problems

	private int[] FindRoute(int start){

		int[] route = new int[postcodes.size()];
		String[] locPostcodes = postcodes.toArray(new String[postcodes.size()]);
		System.out.println("LocPostCodes Length: " + locPostcodes.length);
		Integer currentY = start;

		for (int c = 0; c < locPostcodes.length -1; c++){

			for (int i = 0; i < locPostcodes.length; i++){
				distances[i][currentY] = Integer.MAX_VALUE;
			}

			currentY = CompareX(currentY);

			route[c] = currentY;

		}
		for (int i = 0; i < route.length; i++){
			System.out.println("Route(" + i +"): " + route[i]);
		}

		return route;

	}

	private Integer CompareX(int x){

		String[] locPostcodes = postcodes.toArray(new String[postcodes.size()]);

		Integer smallest = Integer.MAX_VALUE;
		Integer smallestYLoc = null;

		for (int i = 0; i < locPostcodes.length; i++){
			if (distances[x][i] < smallest){
				smallest = distances[x][i];
				smallestYLoc = i;
			}
		}
		return smallestYLoc;
	}

	private void FillArray() throws Exception{

		String[] locPostcodes = postcodes.toArray(new String[postcodes.size()]);

		//Fill Top Left
		for (int y = 0; y < locPostcodes.length; y++){
			for (int x = 0; x < y; x++){
				distances[x][y] = ParseDistance(MakeURL(locPostcodes[x], locPostcodes[y]));
				System.out.println("Distance between (" + locPostcodes[x] + ", " + locPostcodes[y] + "): " + distances[x][y]);
			}
		}
		//Mirror Top Left
		for (int x = 0; x < locPostcodes.length; x++){
			for (int y = 0; y < x; y++){
				distances[x][y] = distances[y][x];
			}
		}
		//Replace Null
		for (int x = 0; x < locPostcodes.length; x++){
			for(int y = 0; y < 10; y++){
				if (distances[x][y] == null){
					distances[x][y] = Integer.MAX_VALUE;
				}
			}
		}
	}

	private Integer ParseDistance(URL distanceURL) {

		try {

			return GetIntegerDistance(ReturnLine(readPage(distanceURL)));

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Error Occurred. Max Value Used");

			return Integer.MAX_VALUE;

		}
	}

	private String readPage(URL distanceURL) throws Exception {


		// FC7 Comunication Protocols (HTTP)

		BufferedReader reader = null;

		try {

			reader = new BufferedReader(new InputStreamReader(distanceURL.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();

		} finally {

			if (reader != null){

				reader.close();

			}
		}
	}

	private String ReturnLine(String fullJson) {

		String[] lines = new String[1024];
		int counter = 0;

		Scanner scanner = new Scanner(fullJson);

		while (scanner.hasNextLine()) {

			String line = scanner.nextLine();
			lines[counter] = line;
			counter++;

		}

		scanner.close();
		System.out.println("JSON Parsed, line: " + lines[9]);

		return lines[9];
	}

	private Integer GetIntegerDistance(String line) {

		Integer distance = Integer.parseInt(line.replaceAll("[\\D]", ""));
		System.out.println("Distance Parsed: " + distance);

		return distance;
	}

	private URL MakeURL(String start, String end) throws Exception{

		System.out.println(start);
		System.out.println(end);


		String URLBuilder = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=" + start + "&destinations=" + end + "&mode=driving&language=en-GB&sensor=false";

		URL googleDistanceRequest = new URL(URLBuilder);

		return googleDistanceRequest;

	}

	private void GetPostcodes(List<Customer> cust){
		postcodes.clear();

		for (Customer i : cust){ //FC1

			postcodes.add(i.GetPostCode().replaceAll(" ", ""));
			System.out.println(i.GetPostCode().replaceAll(" ", ""));

		}
	}

	private void InsertionSort(String lister[], int length){ //FC1

		for (int i = 1; i < length; i++){

			int temp = i;
			String str = lister[i];

			while ((temp > 0) && (lister[temp-1].compareTo(str) > 0)){

				lister[temp] = lister[temp-1];
				temp--;

			}

			lister[temp] = str;

		}
	}
	//Printing
	private void PrintInvoice(JTextArea textToPrint){
		try{

			boolean complete = textToPrint.print();
			if (complete == true){

				JOptionPane.showMessageDialog(null, "Done Printing", "Information", JOptionPane.INFORMATION_MESSAGE);

			} else {

				JOptionPane.showMessageDialog(null,  "Canceled Printing", "Printer", JOptionPane.CANCEL_OPTION);

			}
		} catch (PrinterException e){
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void main(String[] args) throws IOException{

		try 
		{ 
			UIManager.put("nimbusBase", new Color(250, 250, 250));
			UIManager.put("nimbusBlueGrey", new Color(213, 213, 202));
			UIManager.put("control", new Color(250, 250, 250)); 

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch(Exception e){ 
		}
		MAndJMowing gui = new MAndJMowing(); //start your application


		gui.setVisible(true);

	}

}