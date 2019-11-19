package teldir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EditForm extends JFrame implements ActionListener {
    private JLabel jlab_id, jlab_last_name, jlab_first_name, jlab_middle_name, jlab_tel_no;
    private JTextField jtxt_id, jtxt_last_name, jtxt_first_name, jtxt_middle_name, jtxt_tel_no;
    private JButton jbut_save;
    private String selected;
    private String contact_id, contact_last_name, contact_first_name, contact_middle_name, contact_tel_no;
    
    public EditForm (String selected){
        // set the title, size, and layout of JFrame
        super("Edit Contact");
        setSize(600,350);
        setLayout(null);

        // assign the value of selected to the current object's selected variable
        this.selected = selected;

        // contact labels
        jlab_id = new JLabel("ID:");
        jlab_last_name = new JLabel ("Last Name: ");
        jlab_first_name = new JLabel ("First Name: ");
        jlab_middle_name = new JLabel ("Middle Name: ");
        jlab_tel_no = new JLabel ("Telephone Number: ");

        // set bounds for contact labels
        jlab_id.setBounds(20,20,90,30);
        jlab_last_name.setBounds(20,60,90,30);
        jlab_first_name.setBounds(20,100,100,30);
        jlab_middle_name.setBounds(20,140,150,30);
        jlab_tel_no.setBounds(20,180,150,30);

        // contact text fields
        jtxt_id = new JTextField();
        jtxt_last_name= new JTextField();
        jtxt_first_name= new JTextField();
        jtxt_middle_name= new JTextField();
        jtxt_tel_no= new JTextField();

        // set bounds for contact text fields 
        jtxt_id.setBounds(200,20,350,30);
        jtxt_last_name.setBounds(200,60,350,30);
        jtxt_first_name.setBounds(200,100,350,30);
        jtxt_middle_name.setBounds(200,140,350,30);
        jtxt_tel_no.setBounds(200,180,350,30);

         // save button  
        jbut_save = new JButton("Save");
        jbut_save.setBounds(250,250,100,30);

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

        // add save button
        add(jbut_save);
        
        // add action listener for the button
        jbut_save.addActionListener(this);
        
        try {
            // initializes the database driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // attempts to get a connection to the database
            TelephoneDirectorySystem.con = DriverManager.getConnection(TelephoneDirectorySystem.db_url, TelephoneDirectorySystem.db_username, TelephoneDirectorySystem.db_password);
        }
        
        // exception handler for Exception
        catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        
        // save button is clicked
        if(e.getActionCommand().equals("Save")) {
            
            // read fields
            contact_id = jtxt_id.getText();
            contact_last_name = jtxt_last_name.getText().toUpperCase();
            contact_first_name = jtxt_first_name.getText().toUpperCase();
            contact_middle_name = jtxt_middle_name.getText().toUpperCase();
            contact_tel_no = jtxt_tel_no.getText();
            
            // shows a confirmation dialog that asks if the changes will be saved or not
            int response = JOptionPane.showConfirmDialog(rootPane, "Save changes?", "Save", JOptionPane.OK_CANCEL_OPTION);
            
            // if save changes
            if(response == 0) {
                try {
                    // checks if the contact id input is valid or not
                    if(TelephoneDirectorySystem.isNotValid(contact_id)) {
                        // throws ContactIdFormatException the contact id input is not valid
                        throw new ContactIdFormatException();
                    }

                    // checks if the contact id already exists in the telephone directory
                    else if(TelephoneDirectorySystem.contact_id_list.contains(contact_id)) {
                        // throws UniqueContactIdException if the contact id already exists in the telephone directory
                        throw new UniqueContactIdException();
                    }

                    // checks if the contact last name input is valid or not
                    else if(TelephoneDirectorySystem.isNotValidName(contact_last_name)) {
                        // throws LastNameFormatException if the contact last name input is not valid
                        throw new LastNameFormatException();
                    }

                    // checks if the contact last name input is empty or not
                    else if(contact_last_name.equals("")) {
                        // throws NullLastNameException if the contact last name input is empty
                        throw new NullLastNameException();
                    }

                    // checks if the contact first name input is valid or not
                    else if(TelephoneDirectorySystem.isNotValidName(contact_first_name)) {
                        // throws FirstNameFormatException if the contact first name input is not valid
                        throw new FirstNameFormatException();
                    }

                    // checks if the contact first name input is empty or not
                    else if(contact_first_name.equals("")) {
                        // throws NullFirstNameException if the contact first name input is empty
                        throw new NullFirstNameException();
                    }

                    // checks if the contact middle name input is valid or not
                    else if(TelephoneDirectorySystem.isNotValidName(contact_middle_name)) {
                        // throws MiddleNameFormatException if the contact middle name input is not valid
                        throw new MiddleNameFormatException();
                    }

                    // checks if the contact telephone number input is valid or not
                    else if(TelephoneDirectorySystem.isNotValid(contact_tel_no)) {
                        // throws TelephoneNumberFormatException of the contact telephone number input is not valid
                        throw new TelephoneNumberFormatException();
                    }

                    // checks if the contact telephone number already exists in the telephone directory
                    else if(TelephoneDirectorySystem.contact_tel_no_list.contains(contact_tel_no)) {
                        // throws UniqueTelephoneNumberException if the contact telephone number already exists in the telephone directory
                        throw new UniqueTelephoneNumberException();
                    }
                    
                    else {
                        // updates the contact details
                        TelephoneDirectorySystem.res = TelephoneDirectorySystem.stmt.executeQuery("UPDATE CONTACT SET CONTACT_ID = " + contact_id + ", LAST_NAME = '" + contact_last_name + "', FIRST_NAME = '" + 
                                contact_first_name + "', MIDDLE_NAME = '" + contact_middle_name + "', TEL_NUMBER = '" + contact_tel_no + "' WHERE CONTACT_ID = '" + selected + "'");
                        
                        // hides the edit form
                        setVisible(false);
                        
                        // displays latest data after updating
                        LoginForm.system.displayLatestData();;
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
                
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // function that runs the edit form
    public void run() {
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
