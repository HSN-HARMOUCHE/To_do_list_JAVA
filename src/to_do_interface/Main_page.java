package to_do_interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import java.awt.ComponentOrientation;
import java.awt.Container;

import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.PreparedStatement;

import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main_page {

	protected JFrame frmMain;
	private String user ;
	JLabel to_do_label ;
	private JTextField new_task;
	JLabel msg ;
	JButton ADD_btn ;
	
	public void set_font(Component component) {
		try {
			File file_font_earth = new File("src/Fonts/earthorbitersemital.ttf");
			Font font_earth = Font.createFont(Font.TRUETYPE_FONT, file_font_earth).deriveFont(15f) ;
			component.setFont(font_earth);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Main_page window = new Main_page();
//					window.frmMain.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public Main_page(String user_name) {
		user = user_name ;
		initialize();
	}


	private String[] get_task(String state) {
		
		List<String> tasks = new ArrayList<>();
		
		String query = "SELECT id, title, date_start FROM task WHERE state = ? AND user_name = ? ORDER BY date_start ";

        try {
            PreparedStatement pstmt = (PreparedStatement) CnxDB.cnx.prepareStatement(query);
            
            pstmt.setString(1, state);
            pstmt.setString(2, user);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                Date dateStart = rs.getDate("date_start");
                
                // Format the data as needed and add to the list
                String taskInfo = "#" + id + " : " + title + " //  " + dateStart ;
                tasks.add(taskInfo);
     
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return tasks.toArray(new String[0]);
		
	};
	
	@SuppressWarnings("unchecked")
	private void set_model(JList<?> list_component , String type_list) {
//		create model and insert data in it 
		list_component.setModel(new AbstractListModel() {
			String[] values = get_task(type_list);
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
	}
	

	
	private void supp_task(JList selected_list, String type_list ) {		
		Object selectedValue = selected_list.getSelectedValue();	
		
		String[] parts = selectedValue.toString().split(" : ");
		// Extract the ID portion from the first part
		String idString = parts[0].substring(1); // Remove "#" from the beginning
		int id = Integer.parseInt(idString);
		
		try {
			String query = "DELETE FROM task WHERE id=?";
			
			PreparedStatement pstmt = (PreparedStatement) CnxDB.cnx.prepareStatement(query);
			
			pstmt.setInt(1, id);     
 
			pstmt.executeUpdate();
			
				msg.setText("Task Deleted !");
				
				set_model(selected_list, type_list);
				
				
		} catch (SQLException e2) {
			// TODO Auto-generated catch bloc
			msg.setText("ERROR !! ");
			e2.printStackTrace();
			
		}
		
	}
	
	private void update_task(JList selected_list ,JList new_task_list, String type_list, String new_type_list  ) {
		Object selectedValue = selected_list.getSelectedValue();	
		
		String[] parts = selectedValue.toString().split(" : ");
		// Extract the ID portion from the first part
		String idString = parts[0].substring(1); // Remove "#" from the beginning
		int id = Integer.parseInt(idString);
		
		try {
			String query = "UPDATE task SET state=? WHERE id=?";
			
			PreparedStatement pstmt = (PreparedStatement) CnxDB.cnx.prepareStatement(query);
			
			pstmt.setString(1, new_type_list);
			pstmt.setInt(2, id);     
 
			pstmt.executeUpdate();
			
				msg.setText("task moved !");
				set_model(selected_list, type_list );
				set_model(new_task_list, new_type_list);
				
				
		} catch (SQLException e2) {
			// TODO Auto-generated catch bloc
			msg.setText("ERROR !! ");
			e2.printStackTrace();
			
		}
		
	}


	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	private void initialize() {

		CnxDB.Connect();
		frmMain = new JFrame();
		frmMain.setMinimumSize(new Dimension(1220, 500));
		frmMain.getContentPane().setSize(new Dimension(1024, 0));
		frmMain.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\HSSN\\Pictures\\checkform.png"));
		frmMain.getContentPane().setBackground(new Color(128, 128, 128));
		frmMain.getContentPane().setLayout(null);
		
		@SuppressWarnings("rawtypes")
		JList<?> To_do_list = new JList();
		To_do_list.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		To_do_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		To_do_list.setName("tab_tasks_to_do");
		To_do_list.setFont(new Font("MS Reference Sans Serif", To_do_list.getFont().getStyle(), 11));
		To_do_list.setBorder(new LineBorder(new Color(0, 0, 128), 2, true));
		set_model(To_do_list,"To_do");
		To_do_list.setBounds(10, 100, 370, 309);
		frmMain.getContentPane().add(To_do_list);
		
		JList<?> on_doing_list = new JList();
		
//		create model and insert data in it 
		set_model(on_doing_list,"on_doing");
		
		on_doing_list.setToolTipText("");
		on_doing_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		on_doing_list.setName("tab_tasks");
		on_doing_list.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		on_doing_list.setBorder(new LineBorder(new Color(0, 0, 128), 2, true));
		on_doing_list.setBounds(415, 101, 370, 308);
		frmMain.getContentPane().add(on_doing_list);
		
		JList<?> fini_list = new JList();
		
		set_model(fini_list,"finished");
		
		fini_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fini_list.setName("tab_tasks");
		fini_list.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		fini_list.setBorder(new LineBorder(new Color(0, 0, 128), 2, true));
		fini_list.setBounds(822, 101, 370, 308);
		frmMain.getContentPane().add(fini_list);
		
		JButton do_doing = new JButton("");
		do_doing.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(To_do_list.getSelectedValue() != null) {
					update_task(To_do_list ,on_doing_list,"To_do","on_doing");
					
				}else {
					msg.setText("select 1 task from To_Do");
				}
				
			}
		});
		do_doing.setIcon(new ImageIcon("D:\\JaVa\\To_do_list\\imgs\\to_right.png"));
		do_doing.setBounds(378, 128, 38, 32);
		frmMain.getContentPane().add(do_doing);
		
		JButton doing_fini = new JButton("");
		doing_fini.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(on_doing_list.getSelectedValue() != null) {
					update_task( on_doing_list, fini_list , "on_doing" , "finished");
					
				}else {
					msg.setText("select 1 task from on_doing");
				}
			}
		});
		doing_fini.setIcon(new ImageIcon("D:\\JaVa\\To_do_list\\imgs\\to_right.png"));
		doing_fini.setBounds(784, 128, 38, 32);
		frmMain.getContentPane().add(doing_fini);
		
		JButton fini_doing = new JButton("");
		fini_doing.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(fini_list.getSelectedValue() != null) {
					update_task(fini_list,on_doing_list  , "finished" , "on_doing");
					
				}else {
					msg.setText("select 1 task from finished list");
				}
			}
		});
		fini_doing.setIcon(new ImageIcon("D:\\JaVa\\To_do_list\\imgs\\To_left.png"));
		fini_doing.setBounds(784, 286, 38, 32);
		frmMain.getContentPane().add(fini_doing);
		
		JButton doing_do = new JButton("");
		doing_do.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(on_doing_list.getSelectedValue() != null) {
					update_task( on_doing_list, To_do_list , "on_doing" , "To_do");
					
				}else {
					msg.setText("select 1 task from on_doing");
				}
			}
		});
		doing_do.setIcon(new ImageIcon("D:\\JaVa\\To_do_list\\imgs\\To_left.png"));
		doing_do.setBounds(378, 286, 38, 32);
		frmMain.getContentPane().add(doing_do);
		
		JLabel lblNewLabel = new JLabel("USER :");
		lblNewLabel.setBounds(10, 11, 52, 14);
		frmMain.getContentPane().add(lblNewLabel);
		
		JLabel user_name = new JLabel("harmouche");
		user_name.setFont(new Font("Rockwell Extra Bold", user_name.getFont().getStyle(), user_name.getFont().getSize()));
		user_name.setBounds(60, 2, 196, 32);
		frmMain.getContentPane().add(user_name);
		
		to_do_label = new JLabel("TO_DO");
		set_font(to_do_label);
		to_do_label.setHorizontalAlignment(SwingConstants.CENTER);
		to_do_label.setBounds(10, 86, 370, 14);
		frmMain.getContentPane().add(to_do_label);
		
		JLabel on_doing_label = new JLabel("On_DOING");
		set_font(on_doing_label);
		on_doing_label.setHorizontalAlignment(SwingConstants.CENTER);
		on_doing_label.setBounds(415, 86, 370, 14);
		frmMain.getContentPane().add(on_doing_label);
		
		JLabel finishied_label = new JLabel("finished");
		set_font(finishied_label);
		finishied_label.setHorizontalAlignment(SwingConstants.CENTER);
		finishied_label.setBounds(822, 86, 370, 14);
		frmMain.getContentPane().add(finishied_label);
		
		JLabel lblNewLabel_1 = new JLabel("Add new Task : ");
		lblNewLabel_1.setBounds(10, 50, 105, 14);
		frmMain.getContentPane().add(lblNewLabel_1);
		
		new_task = new JTextField();
		new_task.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(new_task.getText().length()>3) {
					ADD_btn.setEnabled(true);
				}else {
					ADD_btn.setEnabled(false);
				}
			}
		});
		new_task.setBounds(102, 44, 278, 20);
		frmMain.getContentPane().add(new_task);
		new_task.setColumns(10);
		
		ADD_btn = new JButton("");
		ADD_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					String query = "INSERT INTO task (title, user_name) VALUES (?, ?)";
					
					PreparedStatement pstmt = (PreparedStatement) CnxDB.cnx.prepareStatement(query);			
					pstmt.setString(1, new_task.getText());     
					pstmt.setString(2, user);   
					pstmt.executeUpdate();
						msg.setText("Task added !");
						new_task.setText("");
						set_model(To_do_list,"To_do");
						
						
				} catch (SQLException e2) {
					// TODO Auto-generated catch bloc
					msg.setText("ERROR !! ");
					e2.printStackTrace();
					
				}
			}
		});
		ADD_btn.setEnabled(false);
		ADD_btn.setToolTipText("ADD TASK");
		ADD_btn.setBackground(new Color(204, 204, 204));
		ADD_btn.setIcon(new ImageIcon("D:\\JaVa\\To_do_list\\imgs\\Add.png"));
		ADD_btn.setBounds(392, 44, 38, 20);
		frmMain.getContentPane().add(ADD_btn);
		
		JButton Supp = new JButton("Delete the selected task");
		Supp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(To_do_list.getSelectedValue()!= null && on_doing_list.getSelectedValue() == null && fini_list.getSelectedValue() == null) {
					
					supp_task(To_do_list , "To_do");
					
				}else if(on_doing_list.getSelectedValue()!= null && To_do_list.getSelectedValue() == null && fini_list.getSelectedValue() == null) {
					
					supp_task(To_do_list , "on_doing");
				
				}else if(fini_list.getSelectedValue()!= null  && on_doing_list.getSelectedValue() == null && To_do_list.getSelectedValue() == null) {
					
					supp_task(fini_list , "finished");
				
				}else {
					msg.setText("U need to select 1 item !! ");
					To_do_list.clearSelection();
					on_doing_list.clearSelection();
					fini_list.clearSelection();
				}

			}
		});
		set_font(Supp);
		Supp.setForeground(new Color(220, 20, 60));
		Supp.setIcon(new ImageIcon("D:\\JaVa\\To_do_list\\imgs\\User-trash_16.png"));
		Supp.setBounds(903, 3, 289, 32);
		frmMain.getContentPane().add(Supp);
		
		msg = new JLabel("...!");
		msg.setFont(new Font("Verdana", msg.getFont().getStyle() | Font.BOLD, 11));
		msg.setHorizontalAlignment(SwingConstants.CENTER);
		msg.setBounds(495, 49, 278, 15);
		frmMain.getContentPane().add(msg);
		frmMain.setSize(new Dimension(932, 519));
		frmMain.setTitle("Main");
		frmMain.setBounds(100, 100, 820, 519);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.setResizable(false);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
	
}
