package cs576;

import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;


public class ContactModel {

	private String searchData;
	private String searchType;
	private ResultSet rs;
	private String result = "";
	private String m_username = "";
	private String m_password ="";
	
	public void openAndSearchDatabase() throws SQLException{
	    Connection conn=DriverManager.getConnection("jdbc:ucanaccess://rehber.MDB");
		Statement s = conn.createStatement();
		System.out.println("Connection to database has been done successfully");
		String query;
		
		if(searchType == "name")
		{
			query ="select * from fihrist where field1 like \"*"+ this.searchData +"*\"";
			//query ="select * from fihrist where field1 like \"*durak*\"";
		    System.out.println(query);	
		}
		else
		{
			query ="select * from fihrist where field2 like \"*"+ this.searchData +"*\"";
		    System.out.println(query);
		}
		byte[] searchDataBytes = searchData.getBytes(StandardCharsets.UTF_8);
		//printBytes(searchDataBytes, "searchData");
		rs = s.executeQuery(query);
		result = "";
		while (rs.next()) {
		    byte[] utf8Bytes = rs.getString(2).getBytes(StandardCharsets.UTF_8);
			//printBytes(utf8Bytes, "databaseBytes");
			//System.out.println(rs.getString(2));
            result += String.format("%s %s %s\n",rs.getString(1),rs.getString(2),rs.getString(3));
		}
		System.out.println("query finished");
		rs.close(); 
		s.close();
	}
	
	public boolean checkDatabaseExist(){
		File rehberDB = new File("./rehber.MDB");
		return rehberDB.exists();
	}
	
	public static void printBytes(byte[] array, String name) {
	    for (int k = 0; k < array.length; k++) {
	        System.out.println(name + "[" + k + "] = " + "0x" +
	            UnicodeFormatter.byteToHex(array[k]));
	    }
	}
	
	public boolean downloadDatabase(){
    	System.out.println("username: " + m_username + " password: " + m_password);
		String src_file = "smb://manfile1/veistek/fihrist/New folder/rehber.MDB"; 
    	String dest_file = "./rehber.MDB";
    	InputStream in = null;
        OutputStream out = null;
        
    	NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",m_username, m_password);
		try {
			SmbFile source;
			source = new SmbFile(src_file, auth);
		    in = new BufferedInputStream(new SmbFileInputStream(source));
		    out = new BufferedOutputStream(new FileOutputStream(dest_file));
		    
		    byte[] buffer = new byte[source.getContentLength()];
		    int len = 0; 
            while ((len = in.read(buffer, 0, buffer.length)) != -1) {
                      out.write(buffer, 0, len);
            }
            out.flush(); 
            
		} catch (IOException e ) {
			e.printStackTrace();
			System.out.println("could not connected");
			return false;
		} finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(in != null) {
                    in.close();
                }
            }
            catch (Exception e) {
            	return false;
            }
        }
		System.out.println("DB file is copied to local succesfully");
		return true;
    }
	
	public boolean initialiseDatabase(){
		return (checkDatabaseExist() ? true : alertNonExistDB());
	}
	
	public boolean alertNonExistDB(){
		System.out.println("Db couldn't be found");
	    setWarningMsg("Database couldn't be found please update database by clicking \"Update List\" button");
		return false;
	}
	
	public static void setWarningMsg(String text){
	    Toolkit.getDefaultToolkit().beep();
	    JOptionPane optionPane = new JOptionPane(text,JOptionPane.WARNING_MESSAGE);
	    JDialog dialog = optionPane.createDialog("Warning!");
	    dialog.setAlwaysOnTop(true);
	    dialog.setVisible(true);
	}
	
	public void setUsername(String username){
		this.m_username = username;
	}
	
	public void setPassword(String password){
		this.m_password = password;
	}
	
	public void setInputsForDBupdate(String username, String password){
		m_username = username;
		m_password = password;
	}
	
	public void setSearchData(String search_data){
		this.searchData = search_data;
		byte [] b = this.searchData.getBytes(StandardCharsets.ISO_8859_1);
		this.searchData = new String(b,StandardCharsets.UTF_8);
	}
	
	public void setSearchType(String search_type){
		this.searchType = search_type;
	}
	
	public String getResult(){
		return this.result;
	}
}