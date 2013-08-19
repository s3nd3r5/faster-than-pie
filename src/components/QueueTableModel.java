package components;

import java.io.File;

import javax.swing.table.AbstractTableModel;

import ftp.FTPController;

public class QueueTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	Queue<String[]> data;
	private static final String[] columns = {"Name","Size"};

	public Queue<String[]> getData(){
		return data;
	}
	
	public void setData(Queue<String[]> data){
		this.data = data;
		fireTableDataChanged();
	}
	
	public void setDataAtIndex(String[] value, int index){
		this.data.updateValue(value,index);
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
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String value = "";
//		switch(columnIndex){
//			case 0:{
//
//				if(data[rowIndex] == null) return "..";
////				value = data[rowIndex].getName(); 
//				break;
//			}
//			case 1:{
//
//				if(data[rowIndex] == null) return "Previous Directory";
////				value = FTPController.analyizeSize(data[rowIndex].length(),data[rowIndex].isDirectory(),0) + ""; break;
//			}
//		}
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
