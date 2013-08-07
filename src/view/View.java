package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import components.FTPTableModel;
import components.LocalTableModel;


public class View {
	protected JFrame frame;
	protected JPanel panel,center_panel,left_panel,right_panel;
	protected FTPTableModel remote_tableModel;
	protected LocalTableModel local_tableModel;
	protected JScrollPane local_scrollPane, remote_scrollPane;
	protected JTable local_table, remote_table,center_table;
	protected JMenuBar menu_bar;
	protected JMenu menu;
	protected JMenuItem fileMenuItem;
	protected Image icon;
	public void initialize(String build_number){
		
	//Initialize Swing Classes
		frame = new JFrame();
		panel = new JPanel();
		center_panel = new JPanel(new GridLayout(1,2));
		left_panel = new JPanel(new BorderLayout());
		right_panel = new JPanel(new BorderLayout());
		
		local_tableModel = new LocalTableModel();
		remote_tableModel = new FTPTableModel();
		
		local_table = new JTable();
		remote_table = new JTable();
		
		local_scrollPane = new JScrollPane(local_table);
		remote_scrollPane = new JScrollPane(remote_table);
		
		menu_bar = new JMenuBar();
		menu = new JMenu();
		fileMenuItem = new JMenuItem();
		
		frame.setTitle(Constants.TITLE + Constants.VERSION_APPEND + build_number);
		frame.setPreferredSize(Constants.PREFERED_SIZE);
		frame.setSize(Constants.PREFERED_SIZE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	//Add Components
		//Table
		local_table.setModel(local_tableModel);
		remote_table.setModel(remote_tableModel);
		
		
		//Panels
		left_panel.add(local_scrollPane,BorderLayout.CENTER);
		right_panel.add(remote_scrollPane,BorderLayout.CENTER);

		center_panel.add(left_panel);
		center_panel.add(right_panel);

		panel.setLayout(new BorderLayout());
		panel.add(center_panel,BorderLayout.CENTER);
		panel.add(new JPanel(),BorderLayout.SOUTH);
		
		left_panel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                 "Local Directory",
                 TitledBorder.CENTER,
                 TitledBorder.TOP));
		
		
		right_panel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Remote Directory",
                TitledBorder.CENTER,
                TitledBorder.TOP));
		
		//Menu
		menu.setText("File");
		fileMenuItem.setText("Close");
		menu.add(fileMenuItem);
		menu_bar.add(menu);
		
		//Frame
		frame.setJMenuBar(menu_bar);
		frame.setContentPane(panel);
		
	}
	//Functions
	public void setDirectoryName(JPanel p, String title){
		((TitledBorder)p.getBorder()).setTitle(title);
	}
	public void setIcon(String path) { icon = Toolkit.getDefaultToolkit().createImage(path); }
}
