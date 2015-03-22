
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;








import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.xml.soap.Text;

public class Textfilter extends JFrame {
	
 
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JTextField jTextField = null;
    private JLabel Label= null;
    
    private JTextField getJTextField() {
    	
        if (jTextField == null) {
            jTextField = new JTextField();
            jTextField.setBounds(new Rectangle(30, 105, 223, 26));
            
            jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                
            	public void keyTyped(KeyEvent e) {
                    char key = e.getKeyChar();
                                        
                    if( key == 27){ 
                    	int numberOfCharsToRemove = 2; //
                    	String current =  jTextField.getText();
                    	String currentMinus = current.substring(0,current.length()-numberOfCharsToRemove);
                    	 jTextField.setText(currentMinus);
                    }
                    
                }  
            	
            });
            jTextField.addActionListener(new ActionListener() {
            	
            	    public void actionPerformed(ActionEvent e) {
            	    	String current =  jTextField.getText();
            	    	try {
							if(Check.commandCheck(current) == false){
								String currentMinus = current.substring(0,current.length()-current.length());
								jTextField.setText(currentMinus);
								// Пока убирает всю строку, если false
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
            	        System.out.println(current);// ну и принтит ее
            	        Label.setText(current);// еще на экран выводитб надо будет сделать чтоб скроллилось
            	
            	    }
            	
            	});

        }
        return jTextField;
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Textfilter thisClass = new Textfilter();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
                
            }
        });
    }
    
    
    //void addl(countLabel, BorderLayout.NORTH);
    private Textfilter() {    	
        super();
        
        initialize();
    }
 
    private void initialize() {
        this.setSize(300, 200);
        this.setContentPane(getJContentPane());
        this.setTitle("Textgame");
        
        
        
        
    }
    
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            Label = new JLabel();
			Label.setBounds(new Rectangle(15,15, 205, 16));
			Label.setText("Comleted commands");
            jContentPane.add(Label, BorderLayout.NORTH);
            jContentPane.add(getJTextField(), null);
            
            
           
            
            
        }
        return jContentPane;
    }
 
}
