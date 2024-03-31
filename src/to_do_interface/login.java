package to_do_interface;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;

import com.mysql.jdbc.PreparedStatement;

@SuppressWarnings("serial")
public class login extends JFrame {

	private JFrame frmLogin;
	private JTextField user_name;
	private JPasswordField password;
	JLabel alert ;
	JButton btn_Login  ;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void check_inputs() {
		if(user_name.getText().length() <= 2) {
			btn_Login.setEnabled(false);
			alert.setText("user Name must be at least 3 characters ");
		}else if(password.getText().length() < 8) {
			btn_Login.setEnabled(false);
			alert.setText("password must be at least 8 characters ");
		}else {
			alert.setText("...!" );
			btn_Login.setEnabled(true);
		}
	}
	/**
	 * Create the application.
	 */
	public login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		CnxDB.Connect();
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\HSSN\\Pictures\\checkform.png"));
		frmLogin.setBounds(100, 100, 437, 300);
		frmLogin.setResizable(false); 
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("USER_Name");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Rockwell Extra Bold", lblNewLabel.getFont().getStyle(), 11));
		lblNewLabel.setBackground(new Color(0, 0, 51));
		lblNewLabel.setForeground(UIManager.getColor("CheckBox.darkShadow"));
		lblNewLabel.setBounds(152, 54, 98, 14);
		frmLogin.getContentPane().add(lblNewLabel);
		
		
		alert = new JLabel("...!");
		alert.setForeground(new Color(0, 0, 0));
		alert.setFont(new Font("Rockwell Extra Bold", alert.getFont().getStyle(), alert.getFont().getSize()));
		alert.setHorizontalAlignment(SwingConstants.CENTER);
		alert.setBounds(46, 201, 313, 14);
		frmLogin.getContentPane().add(alert);
		
		
		user_name = new JTextField();
		user_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				check_inputs() ;
			}
		});
		user_name.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		user_name.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) {
			}
			public void inputMethodTextChanged(InputMethodEvent event) {
			}
		});
		user_name.setBounds(116, 69, 170, 20);
		frmLogin.getContentPane().add(user_name);
		user_name.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password :");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Rockwell Extra Bold", lblNewLabel_1.getFont().getStyle(), 13));
		lblNewLabel_1.setForeground(UIManager.getColor("CheckBox.darkShadow"));
		lblNewLabel_1.setBounds(152, 100, 98, 14);
		frmLogin.getContentPane().add(lblNewLabel_1);
		
		password = new JPasswordField();
		password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				check_inputs() ;
			}
		});
		password.setBounds(116, 117, 170, 20);
		frmLogin.getContentPane().add(password);
		
		btn_Login = new JButton("Login ");
		btn_Login.setEnabled(false);
		btn_Login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btn_Login.isEnabled()){
				
				String query = "SELECT * FROM user WHERE user_name = ? AND psw = ?";

				try {
		            PreparedStatement pstmt = (PreparedStatement) CnxDB.cnx.prepareStatement(query);;
		            pstmt.setString(1, user_name.getText());
		            pstmt.setString(2, password.getText());
		            ResultSet rs = pstmt.executeQuery();
		            
		            if (rs.next()) {
		            	 String userName = rs.getString("user_name");
		            	 
		            	 Main_page window = new Main_page(userName) ;
		            	 
		            	 window.frmMain.setVisible(true);
		            	 
		            }else {
		            	alert.setText("NOT found !! ");
		            }
		            
				} catch (SQLException e2) {
					// TODO Auto-generated catch bloc
					alert.setText("ERROR !! ");
					e2.printStackTrace();
					
				}
				
			}
				}
		});
		btn_Login.setBackground(SystemColor.inactiveCaption);
		btn_Login.setForeground(new Color(0, 64, 128));
		btn_Login.setFont(new Font("Copperplate Gothic Bold", btn_Login.getFont().getStyle() | Font.ITALIC, 12));
		btn_Login.setBounds(152, 167, 98, 23);
		frmLogin.getContentPane().add(btn_Login);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(78, 38, 224, 131);
		frmLogin.getContentPane().add(separator);
		
		JLabel Title = new JLabel("TO_DO_List App");
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 18));
		Title.setBackground(new Color(255, 255, 255));
		Title.setBounds(92, 11, 194, 33);
		frmLogin.getContentPane().add(Title);
		

		
		JButton btnNewButton = new JButton("Sign Up");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SingUp SingUp_popup = new SingUp() ;
				SingUp_popup.frmSignUp.setVisible(true);
			}
		});
		btnNewButton.setForeground(new Color(0, 64, 128));
		btnNewButton.setFont(new Font("Arial Black", btnNewButton.getFont().getStyle(), btnNewButton.getFont().getSize()));
		btnNewButton.setIcon(new ImageIcon("D:\\icons\\codeoutlinedprogrammingsigns_81143.ico"));
		btnNewButton.setBounds(152, 227, 98, 23);
		frmLogin.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setSize(new Dimension(0, 7));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(new Color(224, 224, 224));
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\HSSN\\Pictures\\wallpeper\\agri3.jpg"));
		lblNewLabel_2.setBounds(0, 0, 424, 261);
		frmLogin.getContentPane().add(lblNewLabel_2);
	}
}
