import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class AdminView {

	private JFrame frame;
	private static JTable table;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminView window = new AdminView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet resSet = null;
	static ResultSetMetaData myMeta = null;
	private JTextField EID_textField;
	private JTextField Name_textField;
	private JTextField Surname_textField;
	private JTextField Username_textField;
	private JTextField Password_textField;
	private JTextField Infected_textField;
	public AdminView() throws SQLException {
		initialize();
		conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Max\\workspace\\UserBakara\\employeeDB.sqlite");
		updateTable();
		
	}
	
	public static void updateTable() throws SQLException
	{
		//Second commidfghdfght!
		String query = "select * from EmployeeInfo";
		stmt = conn.createStatement();
		resSet = stmt.executeQuery(query);
		myMeta = resSet.getMetaData();
		table.setModel(DbUtils.resultSetToTableModel(resSet));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 641, 455);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(168, 0, 364, 172);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EID", "Name", "Surname", "Username", "Password", "Infected"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					updateTable();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
			}
		});
		btnNewButton.setBounds(312, 183, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		EID_textField = new JTextField();
		EID_textField.setBounds(72, 23, 86, 20);
		frame.getContentPane().add(EID_textField);
		EID_textField.setColumns(10);
		
		Name_textField = new JTextField();
		Name_textField.setColumns(10);
		Name_textField.setBounds(72, 54, 86, 20);
		frame.getContentPane().add(Name_textField);
		
		Surname_textField = new JTextField();
		Surname_textField.setColumns(10);
		Surname_textField.setBounds(72, 85, 86, 20);
		frame.getContentPane().add(Surname_textField);
		
		Username_textField = new JTextField();
		Username_textField.setColumns(10);
		Username_textField.setBounds(72, 116, 86, 20);
		frame.getContentPane().add(Username_textField);
		
		Password_textField = new JTextField();
		Password_textField.setColumns(10);
		Password_textField.setBounds(72, 147, 86, 20);
		frame.getContentPane().add(Password_textField);
		
		Infected_textField = new JTextField();
		Infected_textField.setColumns(10);
		Infected_textField.setBounds(72, 178, 86, 20);
		frame.getContentPane().add(Infected_textField);
		
		JLabel lblEid = new JLabel("EID");
		lblEid.setBounds(10, 26, 46, 14);
		frame.getContentPane().add(lblEid);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 57, 52, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 88, 61, 14);
		frame.getContentPane().add(lblSurname);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 119, 61, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 150, 61, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblInfected = new JLabel("Infected");
		lblInfected.setBounds(10, 183, 46, 14);
		frame.getContentPane().add(lblInfected);
		
		JButton btnNewButton_1 = new JButton("Update");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "Update EmployeeInfo set EID='" + EID_textField.getText()+"' ,Name='" + Name_textField.getText()+"' ,Surname='" + Surname_textField.getText()+"' ,Username='"+Username_textField.getText()+"' ,Password='"+Password_textField.getText()+"' ,Infected='"+Infected_textField.getText()+"' where EID='" + EID_textField.getText()+"'" ;
				try {
					PreparedStatement pst = conn.prepareStatement(query);
					pst.execute();
					pst.close();
					updateTable();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		btnNewButton_1.setBounds(20, 209, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query  ="insert into EmployeeInfo (EID,Name,Surname,Username,Password,Infected) values (?,?,?,?,?,?)";
				try {
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, EID_textField.getText());
					pst.setString(2, Name_textField.getText());
					pst.setString(3, Surname_textField.getText());
					pst.setString(4, Username_textField.getText());
					pst.setString(5, Password_textField.getText());
					pst.setString(6, Infected_textField.getText());
					pst.execute();
					pst.close();
					updateTable();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnAddUser.setBounds(119, 209, 89, 23);
		frame.getContentPane().add(btnAddUser);
		
		JButton btnNewButton_2 = new JButton("Delete User");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "delete from EmployeeInfo where EID='"+ EID_textField.getText()+"'";
				PreparedStatement pst;
				try {
					pst = conn.prepareStatement(query);
					pst.execute();
					pst.close();
					updateTable();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		});
		btnNewButton_2.setBounds(49, 243, 122, 23);
		frame.getContentPane().add(btnNewButton_2);
	}
}
