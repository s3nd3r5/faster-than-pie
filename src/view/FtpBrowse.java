package view;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpBrowse {
	View view;
	FTPClient ftpClient;
	public FtpBrowse(String[] args) throws SocketException, IOException {
		view = new View();
		view.initialize(args[0]);		
		load();
	}
	public static void main(String[] args){
		FtpBrowse browser;
		try {
			browser = new FtpBrowse(args);
			browser.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void load() throws SocketException, IOException{
		ftpClient = new FTPClient();
		ftpClient.connect(Constants.HOSTNAME);
		ftpClient.login(Constants.USERNAME, Constants.PASSWORD);
		FTPFile[] files = ftpClient.listFiles(Constants.BASE);
		view.remote_tableModel.setData(files);
		String home = System.getProperty("user.home");
		File homeDir = new File(home);
		File[] localFiles = homeDir.listFiles();
		view.local_tableModel.setData(localFiles);
		view.setDirectoryName(view.left_panel, homeDir.getAbsolutePath());
		view.setDirectoryName(view.right_panel,Constants.BASE);
	}
		
	public void show(){
		view.frame.setVisible(true);
	}
	
	
}
