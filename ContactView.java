package cs576;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ContactView implements  KeyListener{

	private ContactController m_controller;
    private JFrame mainFrame;
    private JFrame updateFrame;
    private JPanel updatePanel;
    private JPanel updatePanelInputRow;
    private JTextField searchDataTextField;
    private JTextField usernameTexField;
    private JTextField passwordTextField;
    private static String username = "";
    private static String password = "";
    
    private JLabel headerLabel;
    private JPanel textPanel;
    

    private String searchType = "name";
    private static String searchData = "";
    private Button updateButton;
    private Button goToUpdateButton;
    private Button userManualButton;
    private Button aboutButton;
    private JScrollPane TextAreaScroll;
    private Style style;
    private DefaultStyledDocument doc;
    private JTextPane textArea ;
    private StyleContext sc;
    public ContactView(){
    	prepareUI();
        goToUpdateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFrame.setVisible(true);
			}
		});
        
        updateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				username=usernameTexField.getText();
				password=passwordTextField.getText();
				m_controller.updateDB(username, password);
			}
		});
        
    }

    public void prepareUI(){
    	mainFrame = new JFrame("Fihrist");
        mainFrame.setSize(500, 400);
        mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(2,1));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
               System.exit(0);
            }        
         });   
        
        sc = new StyleContext();
        doc = new DefaultStyledDocument(sc);
        textArea = new JTextPane(doc);
        style = sc.getStyle(StyleContext.DEFAULT_STYLE);
        TextAreaScroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
        		                         JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        		
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
        StyleConstants.setFontSize(style, 12);
        StyleConstants.setSpaceAbove(style, 1);
        StyleConstants.setSpaceBelow(style, 1);
        
        usernameTexField = new JTextField("username");
        passwordTextField = new JTextField("password");
        updateButton = new Button("Update");
        
        updatePanel = new JPanel();
        updatePanel.setLayout(new GridLayout(1,1));
        
        updatePanelInputRow = new JPanel();
        updatePanelInputRow.setLayout(new FlowLayout());
        updatePanelInputRow.add(usernameTexField);
        updatePanelInputRow.add(passwordTextField);
        updatePanelInputRow.add(updateButton);
        
        updatePanel.add(updatePanelInputRow);
        
        headerLabel = new JLabel("Search:");
        searchDataTextField = new JTextField(17);
        searchDataTextField.addKeyListener(this);
        
        goToUpdateButton = new Button("Update List");
        aboutButton = new Button("About");
        userManualButton = new Button("Manual");
        
        textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout());
        textPanel.add(headerLabel);
        textPanel.add(searchDataTextField);
        textPanel.add(goToUpdateButton);
        textPanel.add(userManualButton);
        textPanel.add(aboutButton);
        
        mainFrame.add(updatePanel);
        mainFrame.add(textPanel);
        mainFrame.add(TextAreaScroll);
        mainFrame.setVisible(true);
        
        updateFrame = new JFrame("Update List");
        updateFrame.setSize(250, 75);
        updateFrame.setLayout(new GridLayout(1,1));
        updateFrame.add(updatePanel);
        updateFrame.setLocationRelativeTo(null);

       
    }
    public void checkTextAndStartSearch(String searchData){
    	if(isAlpha(searchData)){
    		searchType="name";
    	}
    	else{
    		searchType="number"; 
    	}
		m_controller.searchStarted();
    }

    public boolean isAlpha(String searchData){
		return searchData.matches("[a-zA-Z]+");
    }
    
    public void updateView(String result){
        try 
        {
           textArea.setText("");

		   doc.insertString(0,result , null);
		}
		catch (BadLocationException e){
			e.printStackTrace();
		}
        textArea.setCaretPosition(0);
    }
    
	@Override
	public void keyPressed(KeyEvent arg0) {
 		if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
 			System.exit(0);
 		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(searchData);
		searchData = searchDataTextField.getText();
		//byte [] bytes = searchData.getBytes(StandardCharsets.ISO_8859_1);
		//for(byte b:bytes)
        //System.out.println("From view" + b);
		//searchData = new String(b,StandardCharsets.UTF_8);
		checkTextAndStartSearch(searchData);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setSearchData(String searchdata){
		this.searchData = searchdata;
	}
	 
    public void assignController(ContactController controller){
    	this.m_controller = controller;
    }
    
    public String getSearchType(){
		return searchType;
    }
    
    public String getSearchData(){
        return searchData;
    }
    
    public String getUsername(){
    	return username;
    }
    
    public String getPasssword(){
    	return password;
    }
    
}
