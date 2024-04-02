package to_do_interface;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JSeparator;
import java.awt.Toolkit;
import java.awt.Window.Type;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.mysql.jdbc.PreparedStatement;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SingUp {

	protected JFrame frmSignUp;
	private JTextField user_name;
	private JTextField psw;
	private JTextField psw_conf;
	JLabel msg ;
	JButton btn_singUp ;
	Statement st;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SingUp window = new SingUp();
					window.frmSignUp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SingUp() {
		initialize();
	} 
	
	
	public void check_inputs() {
		
		boolean usernameExist ;
		 
		String query = "SELECT user_name FROM user WHERE user_name = ?";

		try {
            PreparedStatement pstmt = (PreparedStatement) CnxDB.cnx.prepareStatement(query);
            pstmt.setString(1, user_name.getText());
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	usernameExist = true ;
            }else {
            	usernameExist = false ;
            }
            
		} catch (SQLException e2) {
			usernameExist = false ;
			// TODO Auto-generated catch bloc
			msg.setText("ERROR !! ");
			e2.printStackTrace();
			
		}
		
		if(usernameExist || user_name.getText().length() < 2) {
			msg.setText("Username already exist");
			btn_singUp.setEnabled(false);
		}else if(psw.getText().length() < 8){
			msg.setText("password must be at least 8 characters" );
			btn_singUp.setEnabled(false);
		}else if(!psw.getText().equals(psw_conf.getText())) {
			msg.setText("password not confirmed" );
			btn_singUp.setEnabled(false);
		}else {
			msg.setText("ok" );
			btn_singUp.setEnabled(true);
		}
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		CnxDB.Connect();
		
		frmSignUp = new JFrame();
		frmSignUp.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\HSSN\\Pictures\\checkform.png"));
		frmSignUp.setAlwaysOnTop(true);
		frmSignUp.setType(Type.POPUP);
		frmSignUp.setTitle("Sign Up");
		frmSignUp.setResizable(false);
		frmSignUp.setBackground(Color.WHITE);
		frmSignUp.setBounds(100, 100, 450, 300);
		frmSignUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSignUp.getContentPane().setLayout(null);
		
		user_name = new JTextField();
		user_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
					check_inputs();
			}
		});
		user_name.setBounds(203, 49, 125, 20);
		frmSignUp.getContentPane().add(user_name);
		user_name.setColumns(10);
		
		psw = new JTextField();
		psw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				check_inputs();
			}
		});
		psw.setBounds(203, 111, 125, 20);
		frmSignUp.getContentPane().add(psw);
		psw.setColumns(10);
		
		psw_conf = new JTextField();
		psw_conf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				check_inputs();
			}
		});
		psw_conf.setBounds(203, 142, 125, 20);
		frmSignUp.getContentPane().add(psw_conf);
		psw_conf.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setFont(new Font("Rockwell Extra Bold", lblNewLabel.getFont().getStyle() | Font.ITALIC, 11));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(54, 52, 139, 14);
		frmSignUp.getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("Rockwell Extra Bold", lblPassword.getFont().getStyle() | Font.ITALIC, lblPassword.getFont().getSize()));
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setBounds(54, 114, 128, 14);
		frmSignUp.getContentPane().add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("confirm password");
		lblConfirmPassword.setFont(new Font("Rockwell Extra Bold", lblConfirmPassword.getFont().getStyle() | Font.ITALIC, lblConfirmPassword.getFont().getSize()));
		lblConfirmPassword.setForeground(new Color(255, 255, 255));
		lblConfirmPassword.setBounds(54, 145, 139, 14);
		frmSignUp.getContentPane().add(lblConfirmPassword);
		
		JRadioButton male = new JRadioButton("male");
		male.setActionCommand("male");
		male.setSelected(true);
		male.setFont(new Font("Verdana", Font.PLAIN, 11));
		male.setForeground(new Color(255, 255, 255));
		male.setBackground(new Color(91, 91, 91));
		male.setName("sexe");
		male.setBounds(54, 73, 139, 23);
		frmSignUp.getContentPane().add(male);
		
		JRadioButton female = new JRadioButton("female");
		female.setActionCommand("female");
		female.setFont(new Font("Verdana", Font.PLAIN, 11));
		female.setForeground(new Color(255, 255, 255));
		female.setBackground(new Color(91, 91, 91));
		female.setName("sexe");
		female.setBounds(203, 73, 125, 23);
		frmSignUp.getContentPane().add(female);
		
        ButtonGroup buttonGroup = new ButtonGroup();
        
        // Add radio buttons to the ButtonGroup
        buttonGroup.add(male);
        buttonGroup.add(female);
        
        btn_singUp = new JButton("Sign Up");
        btn_singUp.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(btn_singUp.isEnabled()){
            		ButtonModel selectedModel = buttonGroup.getSelection();
            		String gender = selectedModel.getActionCommand();
            		
    				String query = String.format("INSERT INTO user VALUES ('"+user_name.getText()+"', '"+psw.getText()+"', '"+gender+"')");
    				Statement st;
    				try {
    					st = CnxDB.cnx.createStatement();
    					st.executeUpdate(query);
    						msg.setText("your account was created successfully ");
    						
    				} catch (SQLException e2) {
    					// TODO Auto-generated catch bloc
    					msg.setText("ERROR !! ");
    					e2.printStackTrace();
    					
    				}
            	}
        		}

        });
        btn_singUp.setEnabled(false);
        btn_singUp.setFont(new Font("Dialog", btn_singUp.getFont().getStyle() | Font.BOLD, 11));
        btn_singUp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btn_singUp.setForeground(new Color(0, 64, 128));
        btn_singUp.setBackground(SystemColor.inactiveCaption);
        btn_singUp.setBounds(119, 216, 170, 23);
        frmSignUp.getContentPane().add(btn_singUp);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(54, 34, 274, 4);
        frmSignUp.getContentPane().add(separator);
        
        msg = new JLabel("...");
        msg.setBackground(SystemColor.info);
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        msg.setFont(new Font("Rockwell Extra Bold", msg.getFont().getStyle() | Font.ITALIC, 11));
        msg.setForeground(SystemColor.info);
        msg.setBounds(119, 11, 274, 23);
        frmSignUp.getContentPane().add(msg);
        
        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\HSSN\\Pictures\\wallpeper\\wallpaperflare.com_wallpaper (24).jpg"));
        lblNewLabel_2.setSize(new Dimension(0, 7));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setForeground(new Color(224, 224, 224));
        lblNewLabel_2.setBounds(0, 0, 434, 261);
        frmSignUp.getContentPane().add(lblNewLabel_2);
        frmSignUp.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{frmSignUp.getContentPane(), user_name, psw, psw_conf, lblNewLabel, lblPassword, lblConfirmPassword, male, female, btn_singUp, separator, lblNewLabel_2, msg}));
	}
}
