import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class Login {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	static Connection connection = null;
	private static JTextField textFieldUN;
	private JPasswordField passwordField;
	
	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection = sqliteConnection.dbConnector();
	}
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	public static  void changeDB() throws AddressException, MessagingException
	{
		
		try {
			String query = "update EmployeeInfo set infected = ? where username = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, 1);
			pst.setString(2, textFieldUN.getText());
			pst.executeUpdate();
			pst.close();
			///
			System.out.println("\n 1st ===> setup Mail Server Properties..");
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", "587");
			mailServerProperties.put("mail.smtp.auth", "true");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
			System.out.println("Mail Server Properties have been setup successfully..");
	 
			// Step2
			System.out.println("\n\n 2nd ===> get Mail Session..");
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("maxgerman1989@gmail.com"));
			generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("maxgerman1989@gmail.com"));
			generateMailMessage.setSubject("Greetings from Crunchify..");
			String emailBody = "Test email by Crunchify.com JavaMail API example. " + "<br><br> Regards, <br>Crunchify Admin";
			generateMailMessage.setContent(emailBody, "text/html");
			System.out.println("Mail Session has been created successfully..");
	 
			// Step3
			System.out.println("\n\n 3rd ===> Get Session and Send mail");
			Transport transport = getMailSession.getTransport("smtp");
	 
			// Enter your correct gmail UserID and Password
			// if you have 2FA enabled then provide App Specific Password
			transport.connect("smtp.gmail.com", "maxgerman1989@gmail.com", "shuki1989");
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
			
			///
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 509, 343);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(123, 90, 73, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(123, 139, 73, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textFieldUN = new JTextField();
		textFieldUN.setBounds(235, 87, 86, 20);
		frame.getContentPane().add(textFieldUN);
		textFieldUN.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				try{
					boolean correctPassword = false;
					String query = "select * from EmployeeInfo where Username=? and Password=?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldUN.getText());
					pst.setString(2, passwordField.getText());
					ResultSet rs = pst.executeQuery();
					int count=0;
					while(rs.next())
					{
						count++;
					}
						if(count==1)
						{
							JOptionPane.showMessageDialog(null, "Username and Password are correct!");
							correctPassword=true;
						}
						else if(count>1)
						{
							JOptionPane.showMessageDialog(null, "Duplicate username and password!");
						}
						else
							JOptionPane.showMessageDialog(null, "Username and password incorrect!");
			
					rs.close();
					pst.close();
					if(correctPassword)
					{
						frame.setVisible(false);
						if(!textFieldUN.getText().equals("Admin"))
							Demo.main();
						else
							adminLog();
							
						
					}
						
					
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e.toString());
				
				}
	
			}
		});
		btnLogin.setBounds(176, 194, 89, 23);
		frame.getContentPane().add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(235, 136, 86, 20);
		frame.getContentPane().add(passwordField);
	}
	
	public static void adminLog() throws IOException, SQLException
	{
		//AdminView admin = new AdminView();
		AdminView.main();
		
		
	}
}
