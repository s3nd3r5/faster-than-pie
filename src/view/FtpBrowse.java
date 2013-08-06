package view;

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
		for(FTPFile f : files) System.out.println(f.getName());
		view.remote_tableModel.setData(files);
	}
		
	public void show(){
		view.frame.setVisible(true);
	}
}
