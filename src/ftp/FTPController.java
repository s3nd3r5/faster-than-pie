package ftp;

import java.io.File;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import org.apache.commons.net.ftp.FTPClient;

import components.FTPTableModel;
import components.LocalTableModel;

import view.Constants;
import view.View;

public class FTPController {
	protected View view;
	protected FTPClient client;
	public FTPController(View view, FTPClient client) {
		this.view = view;
		this.client = client;
	}
	public void navigateTo(int row, JTable table) throws IOException{
		int nameColumn = 0;
		int typeColumn = 1;
		if(!table.getColumnName(0).equalsIgnoreCase("Name")){
			nameColumn = 1;
			typeColumn = 0;
		}
		String type = (String)table.getValueAt(row,typeColumn);
		if(!type.equalsIgnoreCase("folder") && !type.equalsIgnoreCase("previous directory")) return; //TODO HANDLE FILE DOUBLE CLICK
		String value = (String)table.getValueAt(row, nameColumn);
		if(table.getModel() instanceof FTPTableModel){
			String path = makePath_remote(value,view.current_remote_path);
			if(!path.equalsIgnoreCase(view.current_remote_path)){
				((FTPTableModel)table.getModel()).setData(client.listFiles(path));
				updatePath_remote(path,1);
			}
		}else{
			String path = makePath(value,view.current_local_path);
			if(!path.equalsIgnoreCase(view.current_remote_path)){
				((LocalTableModel)table.getModel()).setData(new File(path).listFiles());
				updatePath(path,0);
			}
		}
		table.updateUI();
	}
	public String makePath(String append,String currentPath){
		if(currentPath.equalsIgnoreCase(Constants.BASE) && append.equalsIgnoreCase("..")){
			return currentPath;
		}
		if(View.isWindows()) return makePath_Windows(append,currentPath);
		else return makePath_nonWindows(append,currentPath);
	}
	public String makePath_remote(String append, String currentPath){
		if(Constants.REMOTE_OS.toLowerCase().contains("win")){
			return makePath_Windows(append,currentPath);
		}else{
			return makePath_nonWindows(append,currentPath);
		}
	}
	public String makePath_Windows(String append, String currentPath){
		if(!append.endsWith("\\")) append += "\\";
		if(!currentPath.endsWith("\\")){
			currentPath += "\\" + append;
		}else{
			currentPath += append;
		}
		return currentPath;
	}
	public String makePath_nonWindows(String append, String currentPath){
		if(!append.endsWith("/")) append += "/";
		if(!currentPath.endsWith("/")){
			currentPath += "/" + append;
		}else{
			currentPath += append;
		}
		return currentPath;
	}
	public void updatePath(String path, int left0_right1){
		if(View.isWindows()){
			updatePath_Windows(path,left0_right1);
		}else{
			updatePath_nonWindows(path, left0_right1);
		}
	}
	public void updatePath_remote(String path, int right){
		if(Constants.REMOTE_OS.toLowerCase().contains("win")){
			 updatePath_Windows(path,right);
		}else{
			updatePath_nonWindows(path,1);
		}
	}
	public void updatePath_Windows(String path, int left0_right1){
		if(path.endsWith("..\\")){
			path = path.substring(0,path.lastIndexOf("\\..\\"));
			if(path.contains("\\")){
				path = path.substring(0,path.lastIndexOf("\\"));
			}
		}
		switch(left0_right1){
			case 0:{ 
					view.current_local_path = path;
					((TitledBorder)view.getLeft_panel().getBorder()).setTitle(view.current_local_path); 
					view.getLeft_panel().updateUI(); 
					break;
					}
			case 1:{ 
					view.current_remote_path = path;
					((TitledBorder)view.getRight_panel().getBorder()).setTitle(view.current_remote_path); 
					view.getRight_panel().updateUI(); 
					break;
				}
		}
	}
	public void updatePath_nonWindows(String path,int left0_right1) {
		if(path.endsWith("../")){
			path = path.substring(0,path.lastIndexOf("/../"));
			if(path.contains("/")){
				path = path.substring(0,path.lastIndexOf("/"));
			}
		}
		switch(left0_right1){
			case 0:{ 
					view.current_local_path = path;
					((TitledBorder)view.getLeft_panel().getBorder()).setTitle(view.current_local_path); 
					view.getLeft_panel().updateUI(); 
					break;
					}
			case 1:{ 
					view.current_remote_path = path;
					((TitledBorder)view.getRight_panel().getBorder()).setTitle(view.current_remote_path); 
					view.getRight_panel().updateUI(); 
					break;
				}
		}
		
	}
	
	public View getView(){return view;}
}
