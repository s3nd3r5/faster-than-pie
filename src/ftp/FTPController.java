package ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import view.Constants;
import view.View;

import components.FTPTableModel;
import components.LocalTableModel;

public class FTPController {
	protected View view;
	protected FTPClient client;
	public FTPController(View view, FTPClient client) {
		this.view = view;
		this.client = client;
	}
	public boolean navigateTo(int row, JTable table) throws IOException{
		int nameColumn = 0;
		int typeColumn = 1;
		if(!table.getColumnName(0).equalsIgnoreCase("Name")){
			nameColumn = 1;
			typeColumn = 0;
		}
		String type = (String)table.getValueAt(row,typeColumn);
		if(!type.equalsIgnoreCase("folder") && !type.equalsIgnoreCase("previous directory")) return doDownload(row, nameColumn, table); //TODO HANDLE FILE DOUBLE CLICK
		String value = (String)table.getValueAt(row, nameColumn);
		if(table.getModel() instanceof FTPTableModel){
			if(value.equals("..") && view.current_remote_path.equalsIgnoreCase(Constants.BASE)) return false;
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
		return true;
	}
	private boolean doDownload(int row, int col, JTable table) {
		try {
			final String fileName = (String)table.getValueAt(row, col);
			final String size = (String)table.getValueAt(row, 1 - col);
			final String fullPath = makePath_remote("",view.current_remote_path) + fileName;
			final FileOutputStream fos;
			final FTPClient finalClient = new FTPClient();
			finalClient.setDefaultTimeout(1000000);
			finalClient.setDataTimeout(1000000);
			finalClient.setConnectTimeout(1000000);
			finalClient.connect(Constants.HOSTNAME);
			finalClient.login(Constants.USERNAME, Constants.PASSWORD);
			finalClient.enterLocalActiveMode();
			finalClient.setBufferSize(214748364);
			finalClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
			finalClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			final FTPFile to_download = ((FTPTableModel)view.remote_table.getModel()).getData()[row];
			final long actual_size = to_download.getSize();
			File f = new File(makePath("",view.current_local_path) + fileName);
			f.createNewFile();
			fos = new FileOutputStream(f);
			final long startTime = System.nanoTime();
			final CountingOutputStream cos = new CountingOutputStream(fos){
			    protected void beforeWrite(int n){
			        super.beforeWrite(n);
			        Double percent = ((double)getCount()/(double)actual_size) * 100.00;
			        long time = System.nanoTime();
			        long delta = (time - startTime)/1000000;
			        long data = getCount();
			        long ratio =  data / (delta == 0?1:delta);
			        	System.out.println("Downloaded "+ analyizeSize(data,false,0) + "/" + size + "\t" + percent.longValue() + "%\tRatio: " + ratio+ "KB/s");
			    }
			};
			
			Thread t = new Thread(new Runnable(){
				@Override
				public void run(){
					try {
						finalClient.retrieveFile(fullPath, cos);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
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
	
    public static String analyizeSize(long size,boolean dir,int num){
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

    public static String analyizeSize(long size,int type,int num){
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
    
	public View getView(){return view;}
}
