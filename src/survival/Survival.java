
package survival;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 *
 * @author kchao
 */
public class Survival extends JPanel implements ActionListener {
    
    JFrame choiceFrame;
    static JFrame mainFrame;
    JButton christina, stephen, garrett, kai;
    
    public static String player_name;       //choosen player, accessible by all classes
    
    
    public static void main(String[] args) {
        new Survival();
    }
    
    public Survival()
    {
        choiceScreen();
    }
    
    public void choiceScreen()
    {
        choiceFrame = new JFrame("Welcome to Survival v01");
        choiceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        choiceFrame.setSize(600, 600);
        
        christina = new JButton("Christina the Dark Witch", 
                new ImageIcon(this.getClass().getResource(("images/christina/button.png"))));
        stephen = new JButton("Stephen the Elven Warior of Justice", 
                new ImageIcon(this.getClass().getResource(("images/stephen/button.png"))));
        garrett = new JButton("Garrett the Rogue Barbarian", 
                new ImageIcon(this.getClass().getResource(("images/garrett/button.png"))));
        kai = new JButton("Kai the Senior Troll", 
                new ImageIcon(this.getClass().getResource(("images/kai/button.png"))));
        
        choiceFrame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        //choiceFrame.getContentPane().add(christina);
        //choiceFrame.getContentPane().add(stephen);
        choiceFrame.getContentPane().add(garrett);
        //choiceFrame.getContentPane().add(kai);
        
        christina.addActionListener(this);
        kai.addActionListener(this);
        garrett.addActionListener(this);
        stephen.addActionListener(this);
        
        choiceFrame.setVisible(true);
        
    }
    
    public void setMain()
    {
        mainFrame = new JFrame("Survival");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1280, 860);
        
        
        mainFrame.add(new Board());  
        mainFrame.setVisible(true);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == christina)
        {
            player_name = "christina";
        }
        else if(e.getSource() == kai)
        {
            player_name = "kai";
        }
        else if(e.getSource() == stephen)
        {
            player_name = "stephen";
        }
        else if(e.getSource() == garrett)
        {
            player_name = "garrett";
        }
        
        choiceFrame.setVisible(false);
        choiceFrame.dispose();
        setMain();
    }
}
