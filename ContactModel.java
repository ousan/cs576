package cs576;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;


public class ContactModel {

	private String searchData;
	private String searchType;
	private ResultSet rs;
	private String result = "";
	
	public void openAndSearchDatabase() throws SQLException{
	    Connection conn=DriverManager.getConnection("jdbc:ucanaccess://rehber.MDB");
		Statement s = conn.createStatement();
		System.out.println("Opened database successfully");
		String query;
		
		if(searchType == "name")
		{
			query ="select * from fihrist where field1 like \"*"+ this.searchData +"*\"";
		    System.out.println(query);	
		}
		else
		{
			query ="select * from fihrist where field2 like \"*"+ this.searchData +"*\"";
		    System.out.println(query);
		}

		rs = s.executeQuery(query);
		result = "";
		while (rs.next()) {
            result += String.format("%s %s %s\n",rs.getString(1),rs.getString(2),rs.getString(3));
		}
		System.out.println("query finished");
		rs.close(); 
		s.close();
	}
	
	public boolean checkDatabaseExist(){
		File rehberDB = new File("/home/oguzhaner/Desktop/rehber.MDB");
		return rehberDB.exists();
	}

	public boolean downloadDatabase(){
		String user = "oguzhaner";
    	String pass = "0usan_35";
    	String src_file = "smb://manfile1/veistek/fihrist/New folder/rehber.MDB"; 
    	String dest_file = "/home/oguzhaner/Desktop/rehber.MDB";
    	InputStream in = null;
        OutputStream out = null;
        
    	NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
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
		return (checkDatabaseExist() ? true : downloadDatabase());
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
