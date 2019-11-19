package teldir;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class TelephoneDirectorySystem extends JFrame implements ActionListener, DisplayDataModes {
    private JMenuBar jmenu_bar;
    private JMenu menu;
    private JMenuItem jmenitem_about, jmenitem_creators;
    private JLabel jlab_id, jlab_last_name, jlab_first_name, jlab_middle_name, jlab_tel_no;
    private JTextField jtxt_id, jtxt_last_name, jtxt_first_name, jtxt_middle_name, jtxt_tel_no, jtxt_search;
    private JComboBox jcom_category;
    private JTable jtab_tel_directory;
    private JButton jbut_search_contact, jbut_add_contact, jbut_sort_contact , jbut_edit_contact, jbut_delete_contact;
    private JScrollPane tableScroll;
    private JPanel panel;
    private DefaultTableModel model;
    private String[] column;
    public static ArrayList contact_id_list, contact_tel_no_list;
    public static String db_url, db_username, db_password;
    public static Connection con;
    public static Statement stmt;
    public static ResultSet res;
    
    public TelephoneDirectorySystem(String username, String password) {
        // sets the title, size, layout, and default close operation of JFrame
        super("Telephone Directory System");
        setSize(900,720);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // initializes the database url, username, and password
        db_url = "jdbc:oracle:thin:@localhost:1521:TPHONE";
        db_username = username;
        db_password = password;
        
        // initializes an ArrayList of contact id
        contact_id_list = new ArrayList();
        contact_tel_no_list = new ArrayList();
        
        // menu bar
        jmenu_bar = new JMenuBar();
        
        // menu title
        menu = new JMenu("Menu");
        
        // menu items
        jmenitem_about = new JMenuItem("About this");
        jmenitem_creators = new JMenuItem("Creators of this program");
        
        // contact labels
        jlab_id = new JLabel ("ID : ");
        jlab_last_name = new JLabel ("Last Name: ");
        jlab_first_name = new JLabel ("First Name: ");
        jlab_middle_name = new JLabel ("Middle Name: ");
        jlab_tel_no = new JLabel ("Telephone Number: ");
        
        // set bounds for contact labels
        jlab_id.setBounds(20,20,90,30);
        jlab_last_name.setBounds(20,60,90,30);
        jlab_first_name.setBounds(20,100,90,30);
        jlab_middle_name.setBounds(20,140,150,30);
        jlab_tel_no.setBounds(20,180,150,30);
            
        // contact text fields
        jtxt_id = new JTextField();
        jtxt_last_name = new JTextField();
        jtxt_first_name = new JTextField();
        jtxt_middle_name = new JTextField();
        jtxt_tel_no = new JTextField();
        jtxt_search= new JTextField();
        
        // set bounds for contact text fields
        jtxt_id.setBounds(250,20,400,30);
        jtxt_last_name.setBounds(250,60,400,30);
        jtxt_first_name.setBounds(250,100,400,30);
        jtxt_middle_name.setBounds(250,140,400,30);
        jtxt_tel_no.setBounds(250,180,400,30);
        jtxt_search.setBounds(50,240,500,40);  
            
        // add contact, search contact, sort contact, edit contact, & delete contact buttons
        jbut_add_contact = new JButton("Add Contact");
        jbut_search_contact = new JButton("Search");
        jbut_sort_contact = new JButton("Sort");
        jbut_edit_contact = new JButton("Edit Contact");
        jbut_delete_contact = new JButton("Delete Contact");
        
        // set bounds for buttons
        jbut_add_contact.setBounds(700,100,150,40);
        jbut_search_contact.setBounds(700,240,100,40);
        jbut_sort_contact.setBounds(75,600,150,40);
        jbut_edit_contact.setBounds(375,600,150,40);
        jbut_delete_contact.setBounds(675,600,150,40);
        
        // initializes the telephone directory table
        column = new String[] {"Contact ID", "Last Name", "First Name", "Middle Name", "Telephone No."};
        model = new DefaultTableModel(column, 0);
        jtab_tel_directory = new JTable();
        jtab_tel_directory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableScroll = new JScrollPane(jtab_tel_directory);
        panel = new JPanel(new BorderLayout()); 
        panel.add(jtab_tel_directory.getTableHeader());
        
        
        // set bounds for the telephone directory table
        jtab_tel_directory.setBounds(20,320,850,250);
        panel.setBounds(20,300,900,20);
        
        //item_panel = new JPanel(new);
        jcom_category = new JComboBox(new String[]{"CONTACT_ID", "LAST_NAME", "FIRST_NAME", "MIDDLE_NAME", "TEL_NUMBER"});
        jcom_category.setBounds(570,240,100,40);
        
        // add menu bar
        menu.add(jmenitem_about);
        menu.add(jmenitem_creators);
        jmenu_bar.add(menu);
        setJMenuBar(jmenu_bar);
        
        // add contact labels
        add(jlab_id);
        add(jlab_last_name);
        add(jlab_first_name);
        add(jlab_middle_name);
        add(jlab_tel_no);
        
        // add contact text fields
        add(jtxt_id);
        add(jtxt_last_name);
        add(jtxt_first_name);
        add(jtxt_middle_name);
        add(jtxt_tel_no);
        add(jtxt_search);
        
        // add buttons
        add(jbut_add_contact);
        add(jbut_sort_contact);
        add(jbut_edit_contact);
        add(jbut_delete_contact);
        add(jbut_search_contact);
        
        // add telephone directory data table
        add(panel);
        add(jtab_tel_directory);
        add(tableScroll);
        
        // add drop-down list
        add(jcom_category);
        
        // try-catch block
        try {
            // initializes the database driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // attemps to get a connection to the database
            con = DriverManager.getConnection(db_url, db_username, db_password);
            
            // creates a Statement object for conveying SQL statements to the database
            stmt = con.createStatement();
        }
        
        // exception handler for Exception
        catch(Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Connecting to the database failed.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        // add action listeners for the buttons
        jbut_add_contact.addActionListener(this);
        jbut_search_contact.addActionListener(this);
        jbut_edit_contact.addActionListener(this);
        jbut_sort_contact.addActionListener(this);
        jbut_delete_contact.addActionListener(this);
        
        // add action listeners for the menu items
        jmenitem_about.addActionListener(this);
        jmenitem_creators.addActionListener(this);
        
        this.displayDefaultData();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // add contact button is clicked
        if(e.getActionCommand().equals("Add Contact")) {
            String contact_id, contact_last_name, contact_first_name, contact_middle_name, contact_tel_no;

            // read text fields
            contact_id = jtxt_id.getText();
            contact_last_name = jtxt_last_name.getText().toUpperCase();
            contact_first_name = jtxt_first_name.getText().toUpperCase();
            contact_middle_name = jtxt_middle_name.getText().toUpperCase();
            contact_tel_no = jtxt_tel_no.getText();
            
            try{
                // checks if the contact id input is valid or not
                if(isNotValid(contact_id)) {
                    // throws ContactIdFormatException the contact id input is not valid
                    throw new ContactIdFormatException();
                }
                
                // checks if the contact id already exists in the telephone directory
                else if(contact_id_list.contains(contact_id)) {
                    // throws UniqueContactIdException if the contact id already exists in the telephone directory
                    throw new UniqueContactIdException();
                }
                
                // checks if the contact last name input is valid or not
                else if(isNotValidName(contact_last_name)) {
                    // throws LastNameFormatException if the contact last name input is not valid
                    throw new LastNameFormatException();
                }
                
                // checks if the contact last name input is empty or not
                else if(contact_last_name.equals("")) {
                    // throws NullLastNameException if the contact last name input is empty
                    throw new NullLastNameException();
                }
                
                // checks if the contact first name input is valid or not
                else if(isNotValidName(contact_first_name)) {
                    // throws FirstNameFormatException if the contact first name input is not valid
                    throw new FirstNameFormatException();
                }
                
                // checks if the contact first name input is empty or not
                else if(contact_first_name.equals("")) {
                    // throws NullFirstNameException if the contact first name input is empty
                    throw new NullFirstNameException();
                }
                
                // checks if the contact middle name input is valid or not
                else if(isNotValidName(contact_middle_name)) {
                    // throws MiddleNameFormatException if the contact middle name input is not valid
                    throw new MiddleNameFormatException();
                }
                
                // checks if the contact telephone number input is valid or not
                else if(isNotValid(contact_tel_no)) {
                    // throws TelephoneNumberFormatException of the contact telephone number input is not valid
                    throw new TelephoneNumberFormatException();
                }
                
                // checks if the contact telephone number already exists in the telephone directory
                else if(contact_tel_no_list.contains(contact_tel_no)) {
                    // throws UniqueTelephoneNumberException if the contact telephone number already exists in the telephone directory
                    throw new UniqueTelephoneNumberException();
                }
                
                // if contact id does not exist yet in the telephone directory
                else {
                    // adds contact_id to ArrayList contact_id_list
                    contact_id_list.add(Integer.parseInt(contact_id));
                    contact_tel_no_list.add(contact_tel_no);
                    
                    // adds the new contact in the telephone directory
                    res = stmt.executeQuery("INSERT INTO CONTACT VALUES(" + contact_id + ", '" + 
                                            contact_last_name + "', '" + contact_first_name + "', '" + 
                                            contact_middle_name + "', '" + contact_tel_no + "')");
                    
                    // shows a message dialog that a contact was successfully added
                    JOptionPane.showMessageDialog(rootPane, "Contact successfully added.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // clears all the text fields for adding another contact
                    clearFields();
                    
                    // displays the latest data
                    displayLatestData();
                }
            }
            
            // exception handleer for ContactIdFormatException
            catch(ContactIdFormatException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Oops!", JOptionPane.WARNING_MESSAGE);
            }
            
            // exception handler for UniqueContactIdException
            catch(UniqueContactIdException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Hey!", JOptionPane.WARNING_MESSAGE);
            }
            
            // exception handler for LastNameFormatException
            catch(LastNameFormatException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Warning!", JOptionPane.WARNING_MESSAGE);
            }
            
            // exception handler for NullLastNameException
            catch(NullLastNameException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "What?", JOptionPane.WARNING_MESSAGE);
            }
            
            // exception handler for FirstNameFormatException
            catch(FirstNameFormatException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Warning!", JOptionPane.WARNING_MESSAGE);
            }
            
            // exception handler for NullFirstNameException
            catch(NullFirstNameException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "What?", JOptionPane.WARNING_MESSAGE);
            }
            
            // exception handler for MiddleNameFormatException
            catch(MiddleNameFormatException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Warning!", JOptionPane.WARNING_MESSAGE);
            }
            
            // exception handler for TelephoneNumberFormatException
            catch(TelephoneNumberFormatException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Wrong format!", JOptionPane.WARNING_MESSAGE);
            }
            
            // exception handler for UniqueTelephoneNumberException
            catch(UniqueTelephoneNumberException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Stop!", JOptionPane.WARNING_MESSAGE);
            }
            
            // exception handler for SQLException
            catch(SQLException ex) {
                // do nothing when an SQLException is caught
            }
        }
        
        
        // search button is clicked
        else if(e.getActionCommand().equals("Search")) {
            
            // read the selected category and search input
            String selected_category = jcom_category.getSelectedItem().toString();
            String input = jtxt_search.getText().toUpperCase();
            
            // checks if the search field is empty or not
            if(input.equals("")) {
                // displays all the data if the search field is empty
                displayLatestData();
            }
            
            else {
                // displays latest data according to the selected category and search input
                displayLatestData(selected_category, input);            
            }
        }
        
        
        // sort button is clicked 
        else if(e.getActionCommand().equals("Sort")) {
            // reads the selected category
            String selected_category = jcom_category.getSelectedItem().toString();
            
            // displays the latest data according to the selected category
            displayLatestData(selected_category);
        }
        
        
        // edit contact button is clicked  
        else if(e.getActionCommand().equals("Edit Contact")) {
            try {
                // reads data to be edited in selected row
                int edit_row = jtab_tel_directory.getSelectedRow();
                String edit_contact_id = model.getValueAt(edit_row, 0).toString();
                String edit_tel_no = model.getValueAt(edit_row, 4).toString();
                
                // remove the contact id and telephone number of the selected row from the ArrayLists contact_id_list and contact_tel_no_list
                contact_id_list.remove(edit_contact_id);
                contact_tel_no_list.remove(edit_tel_no);
                
                // opens an edit form
                EditForm edit_form = new EditForm(edit_contact_id); 
                edit_form.run();
            }
            
            // exception handler for ArrayIndexOutOfBoundsException
            catch(ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(rootPane, "Please select a contact.", "ERROR", JOptionPane.ERROR_MESSAGE); 
            }
        }
        
        
        // delete contact button is clicked
        else if(e.getActionCommand().equals("Delete Contact")) {
            try{
                // reads data to be deleted in selected row
                int delete_row = jtab_tel_directory.getSelectedRow();
                String delete_contact_id = model.getValueAt(delete_row, 0).toString();
                String delete_tel_no = model.getValueAt(delete_row, 4).toString();
                
                // remove the contact id and telephone number of the selected row from the ArrayLists contact_id_list and contact_tel_no_list
                contact_id_list.remove(delete_contact_id);
                contact_tel_no_list.remove(delete_tel_no);
                
                // shows a confirmation dialog that asks if the selected contact is to be deleted or not
                int response = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete contact?", "Delete Contact", JOptionPane.OK_CANCEL_OPTION);

                // if yes
                if(response == 0) {
                    // deletes the selected contact from the table
                    res = stmt.executeQuery("DELETE FROM CONTACT WHERE CONTACT_ID = " + delete_contact_id);
                    
                    // shows a message dialog that the contact has been successfully deleted
                    JOptionPane.showMessageDialog(rootPane, "Contact successfully deleted.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    
                    // displays the latest data
                    displayLatestData();
                }
            }
            
            // exception handler for SQLException
            catch(SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            // exception handler for ArrayIndexOutOfBounds
            catch(ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(rootPane, "No contact selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        
        // about the program menu item is clicked
        else if(e.getActionCommand().equals("About this")) {
            JOptionPane.showMessageDialog(rootPane, "This program is made to show a directory of telephone numbers.", "About Telephone Directory System", JOptionPane.INFORMATION_MESSAGE);
        }
        
        
        // creators of the program menu item is clicked
        else if(e.getActionCommand().equals("Creators of this program")) {
            JOptionPane.showMessageDialog(rootPane, "Made by: Rikiya Yamamoto & Mark Danhill Egana", "Creators", JOptionPane.INFORMATION_MESSAGE);
        }
    }    

    
    // -------------------- USER-DEFINED FUNCTIONS -------------------- //
    
    
    // function that clears all the fields
    public void clearFields() {
        jtxt_id.setText("");
        jtxt_last_name.setText("");
        jtxt_first_name.setText("");
        jtxt_middle_name.setText("");
        jtxt_tel_no.setText("");
    }
    
    
    // function that runs the telephone directory system
    public void run() {
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    
    // function to check if the contact id or telephone number is valid or not
    public static boolean isNotValid(String input) {
        // convert String to character array
        char[] field_input = input.toCharArray();
        
        // check if each character is a digit
        for(int i = 0; i < field_input.length; i++) {
            if(!Character.isDigit(field_input[i])) {
                // returns true if every character is a digit
                return true;
            }
        }
        
        // returns false when a character is not a digit
        return false;
    }
    
    
    // function to check if the last name, first name, or middle name is valid or not
    public static boolean isNotValidName(String name) {
        // convert String to character array
        char[] field_input = name.toCharArray();
        
        // check if each character is a letter
        for(int i  = 0; i < field_input.length; i++) {
            if(!Character.isLetter(field_input[i]))
                // returns true if every character is a letter
                return true;
        }
        
        // returns false when a character is not a letter
        return false;
    }
    
    // function that adds contact id to ArrayList contact_id_list
    public void addContactId(String contact_id) {
        contact_id_list.add(contact_id);
    }
    
    // function that adds telephone number to ArrayList contact_tel_no_list
    public void addTelephoneNo(String contact_tel_no) {
        contact_tel_no_list.add(contact_tel_no);
    }
    
    @Override
    // function that displays the default data
    public void displayDefaultData() {
        try {
            model = new DefaultTableModel(column, 0);
            res = stmt.executeQuery("SELECT * FROM CONTACT");
            while(res.next()) {
                String id = res.getString("CONTACT_ID");
                String lname = res.getString("LAST_NAME");
                String fname = res.getString("FIRST_NAME");
                String mname = res.getString("MIDDLE_NAME");
                String telnum = res.getString("TEL_NUMBER");
                model.addRow(new Object[]{id, lname, fname, mname, telnum});
                addContactId(id);
                addTelephoneNo(telnum);
            }
            jtab_tel_directory.setModel(model);
        }
        
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    // function that displays the latest data (when the search field is empty and search button is clicked)
    public void displayLatestData() {
        try {
            model = new DefaultTableModel(column, 0);
            res = stmt.executeQuery("SELECT * FROM CONTACT");
            while(res.next()) {
                String id = res.getString("CONTACT_ID");
                String lname = res.getString("LAST_NAME");
                String fname = res.getString("FIRST_NAME");
                String mname = res.getString("MIDDLE_NAME");
                String telnum = res.getString("TEL_NUMBER");
                model.addRow(new Object[]{id, lname, fname, mname, telnum});
            }
            jtab_tel_directory.setModel(model);
        }
        
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    // function that displays the latest data according to selected category (when sort button is clicked)
    public void displayLatestData(String selected_category) {
        try {
            model = new DefaultTableModel(column, 0);
            res = stmt.executeQuery("SELECT * FROM CONTACT ORDER BY " + selected_category);

            while(res.next()) {
                String id = res.getString("CONTACT_ID");
                String lname = res.getString("LAST_NAME");
                String fname = res.getString("FIRST_NAME");
                String mname = res.getString("MIDDLE_NAME");
                String telnum = res.getString("TEL_NUMBER");
                model.addRow(new Object[]{id, lname, fname, mname, telnum});
            }

            jtab_tel_directory.setModel(model);
        }

        catch(SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    // function that displays the latest data according to the selected category (when the search field is not empty and search button is clicked)
    public void displayLatestData(String selected_category, String input) {
        try {
            // if search field is not empty
             if(!input.equals("")) {
                 model = new DefaultTableModel(column, 0);
                 res = stmt.executeQuery("SELECT * FROM CONTACT WHERE " + selected_category + " = '" + input + "'");

                 while(res.next()) {
                    String id = res.getString("CONTACT_ID");
                    String lname = res.getString("LAST_NAME");
                    String fname = res.getString("FIRST_NAME");
                    String mname = res.getString("MIDDLE_NAME");
                    String telnum = res.getString("TEL_NUMBER");
                    model.addRow(new Object[]{id, lname, fname, mname, telnum});
                 }

                 jtab_tel_directory.setModel(model);
             }
        
            // if search field is empty
            else {
                // displays data sorted by default category (contact_id)
                displayLatestData();
            }
        }
        
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
}
