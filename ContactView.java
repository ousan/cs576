package cs576;

import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ContactView implements  KeyListener{

	private ContactController m_controller;
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel textPanel;
    final JTextField searchDataTextField;
    private String searchType = "name";
    private static String searchData = "";
    
    private JScrollPane TextAreaScroll;
    private Style style;
    private DefaultStyledDocument doc;
    private JTextPane textArea ;
    private StyleContext sc;
    
    public ContactView(){
    	
    	mainFrame = new JFrame("Fihrist");
        mainFrame.setSize(500, 200);
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
        
        headerLabel = new JLabel("Search:");
        searchDataTextField = new JTextField(30);
        searchDataTextField.addKeyListener(this);
        
        textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout());
        textPanel.add(headerLabel);
        textPanel.add(searchDataTextField);
        

        mainFrame.add(textPanel);
        mainFrame.add(TextAreaScroll);
        mainFrame.setVisible(true);  
          
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
    
    public void assignController(ContactController controller){
    	this.m_controller = controller;
    }
    
    public String getSearchType(){
		return searchType;
    }
    
    public String getSearchData(){
        return searchData;
    }
    
    public boolean isAlpha(String searchData){
		return searchData.matches("[a-zA-Z]+");
    }

	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		char c = (char)keyCode;
		
		if(arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			System.out.println("backspace");
			if(searchData.length() > 0){
				searchData = removeCharAt(searchData, searchData.length()-1);
			}
			else{
				searchData = "";
				return;
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
		else{
		    searchData += c;			
		}
		System.out.println(searchData);
		checkTextAndStartSearch(searchData);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
    private static String removeCharAt(String s, int i) 
    {
        StringBuffer buf = new StringBuffer(s.length() -1);
        buf.append(s.substring(0, i)).append(s.substring(i+1));
        return buf.toString();
    }
    
    public void updateView(String results){
    }
}
