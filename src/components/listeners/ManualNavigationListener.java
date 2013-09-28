package components.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JTable;

import ftp.FTPController;

public class ManualNavigationListener implements ItemListener {
	FTPController controller;
	JTable table;
	public ManualNavigationListener(FTPController controller, JTable table){
		this.controller = controller;
		this.table = table;
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		String navto = (String)ie.getItem();
		try {
			controller.navigateTo(navto, table);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
