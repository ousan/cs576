package cs576;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
    private JPanel textPanel;
    private JLabel headerLabel;    
    private JTextField searchDataTextField;
    private Button openUpdateFrameButton;
    private Button userManualButton;
    private Button aboutButton;
    private JScrollPane TextAreaScroll;
    private Style style;
    private DefaultStyledDocument doc;
    private JTextPane textArea ;
    private StyleContext sc;
    private String searchType = "name";
    private static String searchData = "";
    
    private JFrame userManualFrame;
    private JPanel userManualPanel;
    private JLabel userManualDescLabel1;
    private JLabel userManualDescLabel2;
    private JLabel userManualDescLabel3;
    
    private JFrame aboutFrame;
    private JPanel aboutPanel;
    private JLabel aboutDescLabel1;
    private JLabel aboutDescLabel2;
    private JLabel aboutDescLabel3;
    
    private JFrame updateFrame;
    private JPanel updatePanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private Button updateButton;
    private static String username = "";
    private static String password = "";
    
    public ContactView(){
    	prepareUI();
        openUpdateFrameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFrame.setVisible(true);
			}
		});
        
        updateButton.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				username=usernameTextField.getText();
				password=passwordTextField.getText();
				m_controller.updateDB(username, password);
			}
		});
        
        userManualButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            	userManualFrame.setVisible(true);
            }
        });
        
        aboutButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            	aboutFrame.setVisible(true);
            }
        });
    }

    public void prepareUI(){
    	GridBagLayout gbl = new GridBagLayout();
    	mainFrame = new JFrame("Fihrist");
        mainFrame.setSize(500, 480);
        mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        mainFrame.setLayout(gbl);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.addWindowListener(new WindowAdapter() 
        
        {
            public void windowClosing(WindowEvent windowEvent) {
               System.exit(0);
            }        
        });   
        
        
        
        headerLabel = new JLabel("Search:");
        searchDataTextField = new JTextField(17);
        searchDataTextField.addKeyListener(this);
        
        openUpdateFrameButton = new Button("Update List");
        aboutButton = new Button("About");
        userManualButton = new Button("Manual");
        
        textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout());
        textPanel.add(headerLabel);
        textPanel.add(searchDataTextField);
        textPanel.add(openUpdateFrameButton);
        textPanel.add(userManualButton);
        textPanel.add(aboutButton);
        
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
        
        makeConstraints(gbl, textPanel,      2, 1, 0, 0, 2.0, 0.5);
        makeConstraints(gbl, TextAreaScroll, 2, 1, 0, 1, 2.0, 7.0);
        
        mainFrame.add(textPanel);
        mainFrame.add(TextAreaScroll);
        
        usernameLabel = new JLabel("username:");
        usernameTextField = new JTextField(10);
        passwordLabel = new JLabel("password");
        passwordTextField = new JPasswordField(10);
        updateButton = new Button("Update");
               
        updatePanel = new JPanel();
        updatePanel.setLayout(new FlowLayout());
        updatePanel.add(usernameLabel);
        updatePanel.add(usernameTextField);
        updatePanel.add(passwordLabel);
        updatePanel.add(passwordTextField);
        updatePanel.add(updateButton);
        
        updateFrame = new JFrame("Update List");
        updateFrame.setSize(450, 85);
        updateFrame.setLayout(new GridLayout(1,1));
        updateFrame.setLocationRelativeTo(null);
        updateFrame.add(updatePanel);
        
        userManualDescLabel1 = new JLabel("If update warning has recieved, user must log-in.");
        userManualDescLabel2 = new JLabel("Search operation can be done by name, surname or phone number.");
        userManualDescLabel3 = new JLabel("To keep list up to date, user can login periodically.");     
        userManualPanel = new JPanel();
        userManualPanel.setLayout(new GridLayout(3,1));
        userManualPanel.add(userManualDescLabel1);
        userManualPanel.add(userManualDescLabel2);
        userManualPanel.add(userManualDescLabel3);
        
        userManualFrame = new JFrame();
        userManualFrame = new JFrame("User Manual");
        userManualFrame.setSize(500, 100);
        userManualFrame.setLayout(new FlowLayout());
        userManualFrame.setLocationRelativeTo(null);
        userManualFrame.add(userManualPanel);
        
        aboutDescLabel1 = new JLabel("Oğuzhan İren EROL");
        aboutDescLabel2 = new JLabel("Deniz Sinanoğlu");
        aboutDescLabel3 = new JLabel("Çağrı Durak");     
        aboutPanel = new JPanel();
        aboutPanel.setLayout(new GridLayout(3,1));
        aboutPanel.add(aboutDescLabel1);
        aboutPanel.add(aboutDescLabel2);
        aboutPanel.add(aboutDescLabel3);
        
        aboutFrame = new JFrame();
        aboutFrame = new JFrame("About");
        aboutFrame.setSize(500, 100);
        aboutFrame.setLayout(new FlowLayout());
        aboutFrame.setLocationRelativeTo(null);
        aboutFrame.add(aboutPanel);
        
        mainFrame.setVisible(true);
    }
    public void checkTextAndStartSearch(String searchData){
    	if(isAlpha(searchData)){
    		searchType="name";
    	}
    	else{
    		searchType="number"; 
    	}
    	if(checkContainTRCharacters(searchData)){
    		searchData = convertTRCharacters(searchData);
    		System.out.println("found");
    	}
    	m_controller.searchStarted();
    }

    public boolean checkContainTRCharacters(String toExamine) {
        Pattern pattern = Pattern.compile("[~#@*+%{}<>\\[\\]|\"\\_^öçşiğüÖÇŞİĞÜ]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }
    
    public boolean isAlpha(String searchData){
		return searchData.matches("[a-zA-Z ]+");
    }
    
	public void makeConstraints(GridBagLayout gbl, JComponent comp, int w, int h, int x, int y,
			double weightx, double weighty) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		gbl.setConstraints(comp, constraints);
	}  
    
    public String convertTRCharacters(String originalString){
    	String convertedString = null;
    	byte[] utf8Bytes = originalString.getBytes(StandardCharsets.UTF_8);
    	printBytes(utf8Bytes, "viewBytes");        
    	return originalString;
    }
    
	public static void printBytes(byte[] array, String name) {
	    for (int k = 0; k < array.length; k++) {
	        System.out.println(name + "[" + k + "] = " + "0x" +
	            UnicodeFormatter.byteToHex(array[k]));
	    }
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
