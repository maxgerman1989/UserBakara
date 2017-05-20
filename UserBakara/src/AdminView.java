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
import javax.swing.JOptionPane;

public class AdminView {

	private JFrame frmSupremedefenceAdmin;
	private static JTable table;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminView window = new AdminView();
					window.frmSupremedefenceAdmin.setVisible(true);
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
		conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Max\\git\\UserBakara\\employeeDB.sqlite");
		updateTable();
		
	}
	
	public static void updateTable() throws SQLException
	{
		//Third Commit
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
		frmSupremedefenceAdmin = new JFrame();
		frmSupremedefenceAdmin.setTitle("SupremeDefence - Admin");
		frmSupremedefenceAdmin.setBounds(100, 100, 641, 455);
		frmSupremedefenceAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSupremedefenceAdmin.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(168, 0, 364, 172);
		frmSupremedefenceAdmin.getContentPane().add(scrollPane);
		
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
		frmSupremedefenceAdmin.getContentPane().add(btnNewButton);
		
		EID_textField = new JTextField();
		EID_textField.setBounds(72, 23, 86, 20);
		frmSupremedefenceAdmin.getContentPane().add(EID_textField);
		EID_textField.setColumns(10);
		
		Name_textField = new JTextField();
		Name_textField.setColumns(10);
		Name_textField.setBounds(72, 54, 86, 20);
		frmSupremedefenceAdmin.getContentPane().add(Name_textField);
		
		Surname_textField = new JTextField();
		Surname_textField.setColumns(10);
		Surname_textField.setBounds(72, 85, 86, 20);
		frmSupremedefenceAdmin.getContentPane().add(Surname_textField);
		
		Username_textField = new JTextField();
		Username_textField.setColumns(10);
		Username_textField.setBounds(72, 116, 86, 20);
		frmSupremedefenceAdmin.getContentPane().add(Username_textField);
		
		Password_textField = new JTextField();
		Password_textField.setColumns(10);
		Password_textField.setBounds(72, 147, 86, 20);
		frmSupremedefenceAdmin.getContentPane().add(Password_textField);
		
		Infected_textField = new JTextField();
		Infected_textField.setColumns(10);
		Infected_textField.setBounds(72, 178, 86, 20);
		frmSupremedefenceAdmin.getContentPane().add(Infected_textField);
		
		JLabel lblEid = new JLabel("EID");
		lblEid.setBounds(10, 26, 46, 14);
		frmSupremedefenceAdmin.getContentPane().add(lblEid);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 57, 52, 14);
		frmSupremedefenceAdmin.getContentPane().add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 88, 61, 14);
		frmSupremedefenceAdmin.getContentPane().add(lblSurname);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 119, 61, 14);
		frmSupremedefenceAdmin.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 150, 61, 14);
		frmSupremedefenceAdmin.getContentPane().add(lblPassword);
		
		JLabel lblInfected = new JLabel("Infected");
		lblInfected.setBounds(10, 183, 46, 14);
		frmSupremedefenceAdmin.getContentPane().add(lblInfected);
		
		JButton btnNewButton_1 = new JButton("Update");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					if(EID_textField.getText().isEmpty())
						JOptionPane.showMessageDialog(null, "Please enter EID number");
					else
					{
						
						int count=0;
						try {
							String query2 = "select * from EmployeeInfo  where EID=?";
							PreparedStatement pst2 = conn.prepareStatement(query2);
							pst2.setString(1, EID_textField.getText());
							ResultSet rs2 = pst2.executeQuery();
							
							while(rs2.next())
							{
								count++;
							}
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if(count>0)
						{
							try {
								String query = "Update EmployeeInfo set EID='" + EID_textField.getText()+"' ,Name='" + Name_textField.getText()+"' ,Surname='" + Surname_textField.getText()+"' ,Username='"+Username_textField.getText()+"' ,Password='"+Password_textField.getText()+"' ,Infected='"+Infected_textField.getText()+"' where EID='" + EID_textField.getText()+"'" ;
								PreparedStatement pst = conn.prepareStatement(query);
								pst.execute();
								pst.close();
								updateTable();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else
							JOptionPane.showMessageDialog(null, "Employee with EID: " + EID_textField.getText()+ " does not exist.");
							
					
						
						
					}	
			}
		});
		btnNewButton_1.setBounds(20, 209, 89, 23);
		frmSupremedefenceAdmin.getContentPane().add(btnNewButton_1);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query2 = "select * from EmployeeInfo  where EID=?";
				String query3 = "select * from EmployeeInfo  where Username=?";
				try{
					PreparedStatement pst2 = conn.prepareStatement(query2);
					PreparedStatement pst3 = conn.prepareStatement(query3);
					pst2.setString(1, EID_textField.getText());
					pst3.setString(1, Username_textField.getText());
					ResultSet rs2 = pst2.executeQuery();
					ResultSet rs3 = pst3.executeQuery();
					int count=0;
					int count2 = 0;
					while(rs2.next())
					{
						count++;
					}
					while(rs3.next())
					{
						count2++;
					}
						if(count==1)
							JOptionPane.showMessageDialog(null, "Duplicate EID Number! choose different EID number");
						else if(count2==1)
							JOptionPane.showMessageDialog(null, "Duplicate Username! choose different Username");
						else
						{
							if(EID_textField.getText().isEmpty()||Name_textField.getText().isEmpty()||Username_textField.getText().isEmpty()||Password_textField.getText().isEmpty())
								JOptionPane.showMessageDialog(null, "Please dont leave empty values of the new user!");
							else
							{
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
									pst.close();
									updateTable();
									
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							

							}
							
						}
						rs2.close();
						
					
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
				
				
			}
		});
		btnAddUser.setBounds(119, 209, 89, 23);
		frmSupremedefenceAdmin.getContentPane().add(btnAddUser);
		
		JButton btnNewButton_2 = new JButton("Delete User");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(EID_textField.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Please enter EID number");
				else
				{
					try {
					String query2 = "select * from EmployeeInfo nfo where EID=?";
					PreparedStatement pst = conn.prepareStatement(query2);
					pst.setString(1, EID_textField.getText());
					ResultSet rs = pst.executeQuery();
					int count=0;
					while(rs.next()) 
					{
						count++;
					}
						if(count==0)
							JOptionPane.showMessageDialog(null, "Employee with EID: " + EID_textField.getText()+ " does not exist.");
						else
						{
							String query = "delete from EmployeeInfo where EID='"+ EID_textField.getText()+"'";
							pst = conn.prepareStatement(query);
							pst.execute();
							pst.close();
							updateTable();
							
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			
			}
		});
		btnNewButton_2.setBounds(49, 243, 122, 23);
		frmSupremedefenceAdmin.getContentPane().add(btnNewButton_2);
	}
}
