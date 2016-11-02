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

	public void searchStarted(){
        getFromView();
        setModelParameters();
        try {
			model.openAndSearchDatabase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
