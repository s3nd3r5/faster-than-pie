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
		FTPFile back = new FTPFile();
		back.setType(1);
		back.setName("..");
		back.setSize(-1);
		this.data = new FTPFile[data.length+1];
		this.data[0] = back;
		for(int i = 0; i < data.length; i++){
			this.data[i+1] = data[i];
		}
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
			case 1: value = analyizeSize(data[rowIndex].getSize(),data[rowIndex].getType(),0) + ""; break;
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
    
    private String analyizeSize(long size,int type,int num){
    	String formatted = "";
    	if(type == 1){ 
    		if(size == -1) return "Previous Directory";
    		return "Folder"; 
		}
    	if(size > 1024){
    		return analyizeSize(size /= 1024,type,num+1);
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
