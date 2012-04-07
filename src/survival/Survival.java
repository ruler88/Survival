
package survival;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;


/**
 *
 * @author kchao
 */
public class Survival extends JPanel implements ActionListener {
    
    JFrame choiceFrame;
    static JFrame mainFrame;
    JButton christina, stephen, garrett, kai, yena;
    
    public static String player_name;       //choosen player, accessible by all classes
    
    String background_file = "sounds/background.wav";
    
    static Clip clip;
            
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Survival();
        
    }
    
    public Survival() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        choiceScreen();
        
        //background music
        AudioInputStream background = AudioSystem.getAudioInputStream(this.getClass().getResource(background_file));
        clip = AudioSystem.getClip();
        clip.open(background);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void choiceScreen()
    {
        choiceFrame = new JFrame("Welcome to Survival v01");
        choiceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        choiceFrame.setSize(600, 800);
        
        christina = new JButton("Christina the Dark Witch", 
                new ImageIcon(this.getClass().getResource(("images/christina/down.png"))));
        stephen = new JButton("Stephen the Elven Warior of Justice", 
                new ImageIcon(this.getClass().getResource(("images/stephen/down.png"))));
        garrett = new JButton("Garrett the Rogue Barbarian", 
                new ImageIcon(this.getClass().getResource(("images/garrett/down.png"))));
        kai = new JButton("Kai the Troll                                     ", 
                new ImageIcon(this.getClass().getResource(("images/kai/down.png"))));
        yena = new JButton("Yennie the Trapster               ",
                new ImageIcon(this.getClass().getResource(("images/yena/down.png"))));
        
        choiceFrame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        choiceFrame.getContentPane().add(christina);
        choiceFrame.getContentPane().add(stephen);
        choiceFrame.getContentPane().add(garrett);
        choiceFrame.getContentPane().add(kai);
        choiceFrame.getContentPane().add(yena);
        
        
        choiceFrame.getContentPane().add(new JLabel("All revenue generated will go straight to penguin fund"));
        
        christina.addActionListener(this);
        kai.addActionListener(this);
        garrett.addActionListener(this);
        stephen.addActionListener(this);
        yena.addActionListener(this);
        
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
        else if(e.getSource() == yena)
        {
            player_name = "yena";
        }
        
        choiceFrame.setVisible(false);
        choiceFrame.dispose();
        setMain();
    }
}
