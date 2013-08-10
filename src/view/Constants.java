package view;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.Icon;

public class Constants {

		public static final int 	 
				width = 800
				,height = 600
				
				;
		
		public static String 
				TITLE = "FtpBrowse"
				,VERSION_APPEND = " -v: "
				,HOSTNAME = "server.domain.com"
				,USERNAME = "USERNAME"
				,PASSWORD = "PASSWORD"
				,BASE = "some/dir"
				,REMOTE_OS = "essentially just windows or any other string not containing 'win'"
				,LOCAL_BASE = System.getProperty("user.home")
				;
		
		public static final Icon FOLDER = null, FILE = null;
		
		public static final Dimension PREFERED_SIZE = new Dimension(width,height);
		
		public static void initFTPConstants() throws IOException{
			Properties p = new Properties();
			p.load(new FileInputStream("res/ftp.properties"));
			HOSTNAME = p.getProperty("hostname");
			USERNAME = p.getProperty("username");
			PASSWORD = p.getProperty("password");
			REMOTE_OS = p.getProperty("os");
			BASE = p.getProperty("base");
		}
		
}
