package teldir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginForm extends JFrame implements ActionListener, UserAuthentication {
    private final JLabel jlab_username, jlab_password;
    private final JTextField jtxt_username;
    private final JPasswordField jpass_password;
    private final JButton jbut_login;
    public static TelephoneDirectorySystem system;
    
    public LoginForm() {
        // sets the title, size, layout, and default close operation of JFrame
        super("Telephone Directory System Login");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // login labels
        jlab_username = new JLabel("Username :", SwingConstants.CENTER);
        jlab_password = new JLabel("Password :", SwingConstants.CENTER);
        
        
        // set bounds for login labels
        jlab_username.setBounds(30,30,120,40);
        jlab_password.setBounds(30,80,120,40);
        
        
        // login text fields
        jtxt_username = new JTextField();
        jpass_password = new JPasswordField();
        
        
        // set bounds for login text fields
        jtxt_username.setBounds(160,30,200,40); 
        jpass_password.setBounds(160,80,200,40); 
        
        // login button
        jbut_login = new JButton("Login");
        jbut_login.setBounds(135,150,120,40);
        
        // add components
        add(jlab_username);
        add(jtxt_username);
        add(jlab_password);
        add(jpass_password);
        add(jbut_login);
        
        // add action listener
        jbut_login.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Login")) {
            
            // get username and password input
            String user_input = jtxt_username.getText();
            String pass_input = String.valueOf(jpass_password.getPassword());
            
            // checks if username and password input matches the database username and password
            if(user_input.equals(DB_USERNAME) && pass_input.equals(DB_PASSWORD)) {
                // shows a message dialog that the login was successful
                JOptionPane.showMessageDialog(rootPane, "Logged in successfully.", "Login Success", JOptionPane.INFORMATION_MESSAGE);
                
                // hides the login form
                setVisible(false);
                
                // runs the telephone directory system
                system = new TelephoneDirectorySystem(user_input, pass_input);
                system.run();
            }
            
            // shows error message if username and password input does not match the database username and password
            else {
                JOptionPane.showMessageDialog(rootPane, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // main function, runs the login form
    public static void main(String[] args) {
        LoginForm login = new LoginForm();
        login.setVisible(true);
        login.setLocationRelativeTo(null);
        login.setResizable(false);
    }
}
