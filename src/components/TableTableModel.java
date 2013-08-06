package components;

import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

public class TableTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -6715581167543731370L;
	private JScrollPane[] tables;
	private static final String[] columns = {"Local","Remote"};
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if(tables == null) return null;
		return tables[arg0];
	}
	
	public void setTables(JScrollPane l, JScrollPane r){
		tables = new JScrollPane[]{l,r};
		fireTableDataChanged();
	}
	
	public void update(){
		fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int columnNumber){
		return columns[columnNumber];
	}

}
