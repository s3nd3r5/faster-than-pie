package components;

import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.net.ftp.FTPFile;

public class IconTextCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	public Component getTableCellRendererComponent(JTable table,
             Object value,
             boolean isSelected,
             boolean hasFocus,
             int row,
             int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setText((String) value);
				setIcon(getIcon(table,row,column));
				return this;
		}
	private Icon getIcon(JTable table, int row, int column) {
		if(table.getModel() instanceof FTPTableModel){
			FTPFile value = ((FTPTableModel)table.getModel()).getData()[row];
			if(table.getColumnName(column).equalsIgnoreCase("Name")){
				if(value.getType() == 1){
					if(value.getSize() == -1) return new ImageIcon(this.getClass().getResource("/back.png"));
					return new ImageIcon(this.getClass().getResource("/folder.png"));
				}else{
					return new ImageIcon(this.getClass().getResource("/video.png"));
				}
			}
		}else if(table.getModel() instanceof LocalTableModel){
			File value = ((LocalTableModel)table.getModel()).getData()[row];
			if(table.getColumnName(column).equalsIgnoreCase("Name")){
				if(value == null)return new ImageIcon(this.getClass().getResource("/back.png"));
				if(value.isDirectory()){
					return new ImageIcon(this.getClass().getResource("/folder.png"));
				}else{
					return new ImageIcon(this.getClass().getResource("/video.png"));
				}
			}
		}

		return null;
	}
}
