package cs576;

import static org.junit.Assert.*;

import java.awt.Button;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.junit.Test;

public class TestCases {
	ContactModel model = new ContactModel();
	ContactView view = new ContactView();
	ContactController controller = new ContactController(model, view);
	
	
	@Test
	public void testAssignController() {
		view.assignController(controller);
		 
	}
	
	@Test
	public void testNumber() {
	    model.setSearchData("2759");
	    model.setSearchType("number");
	    try {
			model.openAndSearchDatabase();
			assertNotEquals(model.getResult(), "oguzhsan");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testName() {
	    model.setSearchData("koray");
	    model.setSearchType("name");
	    try {
			model.openAndSearchDatabase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("test");
		}
	}
	
	@Test
	public void testCheckTextAndStartSearchNumber() {
		view.assignController(controller);
        view.checkTextAndStartSearch("123");
	}
	
	@Test
	public void testCheckTextAndStartSearchName() {
		view.assignController(controller);
        view.checkTextAndStartSearch("koray");
	}
	
	@Test
	public void testBSpaceKeyPressed(){ 
		view.assignController(controller);
		Button a = new Button("click");
		KeyEvent keyevent = new KeyEvent(a, 1, 20, 4, 8, 'a');
		view.setSearchData("test");
		view.keyPressed(keyevent);
	}

	@Test
	public void testESCKeyPressed(){ 
		view.assignController(controller); 
		Button a = new Button("click");
		KeyEvent keyevent = new KeyEvent(a, 1, 20, 4, 27, 'a');
		view.keyPressed(keyevent);
	}
	
	@Test
	public void testKeyPressed(){ 
		view.assignController(controller);
		Button a = new Button("click");
		KeyEvent keyevent = new KeyEvent(a, 1, 20, 4, 1, 'a');
		view.keyPressed(keyevent);
	}
	
	@Test
	public void testInitializeUI() {
		view.assignController(controller);
        controller.initialiseUI();
	}
	
	@Test
	public void testSearchStarted() {
		view.assignController(controller);
        controller.searchStarted();
	}
	
	@Test
	public void testUpdateView() {
		view.assignController(controller);
        controller.updateView();
	}
	
	@Test
	public void testGetFromView() {
		view.assignController(controller);
        controller.getFromView();
	}	
	
	@Test
	public void testInitialise() {
		view.assignController(controller);
        controller.initialise();
	}
	
	@Test
	public void testUpdateDB() {
		view.assignController(controller);
		controller.updateDB("denizsi", "password");
        
	}
	
	@Test
	public void testSetUsername() {
		view.assignController(controller);
		model.setUsername("denizsi"); 
	}
	
	@Test
	public void testSetPassword() {
		view.assignController(controller);
		model.setPassword("password");
	}
	
	@Test
	public void testSetInputsForDBupdate() {
		view.assignController(controller);
		model.setInputsForDBupdate("denizsi", "password"); 
	}
	
	@Test
	public void testSetSearchData() {
		view.assignController(controller);
		model.setSearchData("deniz"); 
	}
	@Test
	public void testSetSearchType() {
		view.assignController(controller);
		model.setSearchType("name"); 
	}
	@Test
	public void testSetWarningMsg() {
		view.assignController(controller);
		model.setWarningMsg("Database couldn't be found please update database by clicking"); 
	}
	
	
	
	/*	@Test
	public void testSetModelParameters() {
		view.assignController(controller);
		model.setSearchData(" ");
	    model.setSearchType("name");
		controller.setModelParameters();
	}*/

	
	
	
}
