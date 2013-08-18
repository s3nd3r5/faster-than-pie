package view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import components.TableMouseEventHandler;

import ftp.FTPController;

public class FtpBrowse {
	View view;
	FTPClient ftpClient;
	FTPController controller;
	public FtpBrowse(String[] args) throws SocketException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Constants.initFTPConstants();
		view = new View();
		view.initialize(args.length > 0?args[0]:"TEST");		
		load();
		controller = new FTPController(view, ftpClient);
		view.remote_table.addMouseListener(new TableMouseEventHandler(controller,view.remote_table));
		view.local_table.addMouseListener(new TableMouseEventHandler(controller,view.local_table));
	}
	public static void main(String[] args){
		FtpBrowse browser;
		try {
			browser = new FtpBrowse(args);
			browser.show();
			
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	private void load() throws SocketException, IOException{
		ftpClient = new FTPClient();
		ftpClient.setDefaultTimeout(1000000);
		ftpClient.setDataTimeout(1000000);
		ftpClient.setConnectTimeout(1000000);
		ftpClient.connect(Constants.HOSTNAME);
		ftpClient.login(Constants.USERNAME, Constants.PASSWORD);
		ftpClient.enterLocalPassiveMode();
		FTPFile[] files = ftpClient.listFiles(Constants.BASE);
		
		
		view.remote_tableModel.setData(files);
		
		String home = System.getProperty("user.home");
		File homeDir = new File(home);
		File[] localFiles = homeDir.listFiles();
		view.local_tableModel.setData(localFiles);
		view.setDirectoryName(view.getLeft_panel(), homeDir.getAbsolutePath());
		view.setDirectoryName(view.getRight_panel(),Constants.BASE);
		view.frame.setIconImage(setIcon(this.getClass().getResource("/picon.png")));
	}
		
	public void show(){
		view.frame.setVisible(true);
	}
	
	private Image setIcon(URL path){
		return new ImageIcon(path).getImage();
		
	}
	
}
