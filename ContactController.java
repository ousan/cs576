package cs576;

import java.sql.SQLException;

public class ContactController {
	private ContactModel model;
	private ContactView view;
	private String searchData;
	private String searchType;
	
	public ContactController(ContactModel model,
			                 ContactView view) {
		this.model = model;
		this.view = view;
	}

	public void initialise(){
		boolean is_database_ready = model.initialiseDatabase();
		if(is_database_ready)
		{
			System.out.println("Db is READY");
            initialiseUI();
	        updateView();
		}
		else
		{
			System.out.println("Db could NOT initialised");
		}	
	}
	
	public void initialiseUI(){
		try {
		    model.setSearchData(" ");
		    model.setSearchType("name");
			model.openAndSearchDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public void searchStarted(){
        getFromView();
        setModelParameters();
        try {
			model.openAndSearchDatabase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        updateView();
	}
	
	public void updateView() {
		view.updateView(model.getResult()); 
	}
	
	public void updateDB(String username, String password){
		model.setInputsForDBupdate(username, password);
		model.downloadDatabase();
	}
	
	public void getFromView(){
		this.searchData = view.getSearchData();
		this.searchType = view.getSearchType();
		System.out.println(searchData);
		System.out.println(searchType);
	}
	
	public void setModelParameters(){
		model.setSearchData(this.searchData);
		model.setSearchType(this.searchType);
	}
	
}
