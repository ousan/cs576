package cs576;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ContactView {

	private ContactController controller;
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel selectionPanel;
    private JPanel textPanel;
    private JPanel buttonPanel;
    final JTextField searchDataTextField;
    final JRadioButton nameRadBut;
    final JRadioButton numberRadBut;
    final JButton startButton;
    private String searchType = "name";
    private static String searchData;
    public ContactView(){
    	
    	mainFrame = new JFrame("Fihrist");
        mainFrame.setSize(500, 200);
        mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(3,1));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
               System.exit(0);
            }        
         });   
        
        headerLabel = new JLabel("Search:");
        searchDataTextField = new JTextField(30);
    	
    	nameRadBut = new JRadioButton("Search by name",true);
    	numberRadBut = new JRadioButton("Search by number");
    	nameRadBut.setMnemonic(KeyEvent.VK_N);
        numberRadBut.setMnemonic(KeyEvent.VK_M);
        ButtonGroup buttongroup = new ButtonGroup();
        buttongroup.add(nameRadBut);
        buttongroup.add(numberRadBut);
        
        textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout());
        textPanel.add(headerLabel);
        textPanel.add(searchDataTextField);
        
        selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout());
        selectionPanel.add(nameRadBut);
        selectionPanel.add(numberRadBut);
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
    	startButton = new JButton("Search");
        buttonPanel.add(startButton);
        
        mainFrame.add(textPanel);
        mainFrame.add(selectionPanel);
        mainFrame.add(buttonPanel);
        mainFrame.setVisible(true);  
        
        nameRadBut.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == 1) searchType="name";
			}
		});

        numberRadBut.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1) searchType="number";
			}
		});        

        startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchData = searchDataTextField.getText();
				controller.searchStarted();
			}
		});	
          
        }

    public void assignController(ContactController controller) {
    	this.controller = controller;
    }
    
    public String getSearchType(){
		return searchType;
    }
    
    public String getSearchData(){
        return searchData;
    }
}

