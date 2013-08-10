package components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JTable;

import ftp.FTPController;

public class TableMouseEventHandler implements MouseListener {

	JTable table;
	FTPController ftpc;
	public TableMouseEventHandler(FTPController ftpc, JTable table) {
		this.table = table;
		this.ftpc = ftpc;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getClickCount() >= 2){
			int row = table.rowAtPoint(me.getPoint());
			try {
				ftpc.navigateTo(row,table);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub

	}

}
