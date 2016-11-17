package cs576;

import java.sql.*;

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
			query ="select * from fihrist where field1 like \"*"+ searchData +"*\"";
		    System.out.println(query);	
		}
		else
		{
			query ="select * from fihrist where field2 like \"*"+ searchData +"*\"";
		    System.out.println(query);
		}

		rs = s.executeQuery(query);
		result = "";
		while (rs.next()) {
			//System.out.println(String.format("%s %s %s",rs.getString(1),rs.getString(2),rs.getString(3)));
            result += String.format("%s %s %s\n",rs.getString(1),rs.getString(2),rs.getString(3));
		}
		System.out.println("query finished");
		rs.close();
		s.close();
	}
	public void setSearchData(String search_data){
		this.searchData = search_data;
	}
	
	public void setSearchType(String search_type){
		this.searchType = search_type;
	}
	
	public String getResult(){
		return this.result;
	}
}