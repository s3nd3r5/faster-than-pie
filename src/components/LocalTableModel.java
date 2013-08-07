package components;

import java.io.File;

import javax.swing.table.AbstractTableModel;

public class LocalTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 2833873735851543443L;

	File[] data;
	private static final String[] columns = {"Name","Size"};

	public File[] getData(){
		return data;
	}
	
	public void setData(File[] data){
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
			case 1: value = analyizeSize(data[rowIndex].length(),data[rowIndex].isDirectory(),0) + ""; break;
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
    
    private String analyizeSize(long size,boolean dir,int num){
    	String formatted = "";
    	if(dir) return "Folder";
    	if(size > 1024){
    		return analyizeSize(size /= 1024,false,num+1);
    	}
    	switch(num){
    		case 0: formatted = " B"; break;
    		case 1: formatted = " KB"; break;
    		case 2: formatted = " MB"; break;
    		case 3: formatted = " GB"; break;
    	}
    	return size + formatted;
    }


}
