package components;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.net.ftp.FTPFile;

public class FTPTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -2692170403007650028L;
	
	FTPFile[] data;
	private static final String[] columns = {"Name","Size"};

	public FTPFile[] getData(){
		return data;
	}
	
	public void setData(FTPFile[] data){
		this.data = data;
		fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int columnNumber){
		return columns[columnNumber];
	}
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		if(data == null) return 0;
		return data.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String value = "";
		switch(columnIndex){
			case 0: value = data[rowIndex].getName(); break;
			case 1: value = data[rowIndex].getSize() + ""; break;
		}
		return value;
	}
	@Override
    public boolean isCellEditable(int row, int columnIndex)
    {
        return false;
    }
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columns[columnIndex].getClass();
    }


}
