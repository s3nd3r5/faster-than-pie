package view;

import java.awt.Dimension;

import javax.swing.Icon;

public class Constants {

		public static final int 	 
				width = 800
				,height = 600
				
				;
		
		public static final String 
				TITLE = "FtpBrowse"
				,VERSION_APPEND = " -v: "
				,HOSTNAME = "server.domain.com"
				,USERNAME = "USERNAME"
				,PASSWORD = "PASSWORD"
				,BASE = "~/Downloads/"
				,REMOTE_OS = "linux"
				,LOCAL_BASE = System.getProperty("user.home")
				;
		
		public static final Icon FOLDER = null, FILE = null;
		
		public static final Dimension PREFERED_SIZE = new Dimension(width,height);
		
}
