package components;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
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
				try {
					Icon i = getIcon(table,row,column);
					setIcon(i!=null?i:getEmptyIcon());
				} catch (IOException e) {
					setIcon(new ImageIcon(this.getClass().getResource("/folder.png")));
				}
				return this;
		}
	private Icon getIcon(JTable table, int row, int column) throws IOException {
		if(table.getModel() instanceof FTPTableModel){
			FTPFile value = ((FTPTableModel)table.getModel()).getData()[row];
			if(table.getColumnName(column).equalsIgnoreCase("Name")){
				Icon i = null;
				if(value.getType() == 1){
					if(value.getSize() == -1) return new ImageIcon(this.getClass().getResource("/back.png"));
					Path f = Files.createTempDirectory(value.getName(), new FileAttribute<?>[0]);
					i = FileSystemView.getFileSystemView().getSystemIcon(f.toFile());
					f.toFile().delete();
					f = null;
				}else{
					File f = File.createTempFile(getName(), getExtension(value.getName()));
					i =  FileSystemView.getFileSystemView().getSystemIcon(f);
					f.delete();
				}
				return i;
			}
		}else if(table.getModel() instanceof LocalTableModel){
			File value = ((LocalTableModel)table.getModel()).getData()[row];
			if(table.getColumnName(column).equalsIgnoreCase("Name")){
				if(value == null)return new ImageIcon(this.getClass().getResource("/back.png"));
				return FileSystemView.getFileSystemView().getSystemIcon(value);
			}
		}
		return null;
	}
	private String getExtension(String f){
		return f.substring(f.lastIndexOf("."));
	}
	private Icon getEmptyIcon() throws IOException{
		File f = File.createTempFile("temp","");
		Icon i = FileSystemView.getFileSystemView().getSystemIcon(f);
		f.delete();
		return i;
	}
}
