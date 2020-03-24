
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author zeyad
 */
public class event extends JFrame implements ActionListener{ 

    private JLabel youWin, youLose;
    private JButton playAgain, quit;
    
    public event(boolean winOrLose)
    {
        this.setSize(500, 200);
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        youWin = new JLabel("The computer picked the soap piece! You Win! Would you like to play again or quit?");
        youLose = new JLabel("You picked the soap piece. You Lose. Would you like to play again or quit?");
        playAgain = new JButton("Play Again");
        playAgain.addActionListener(this);
        quit = new JButton("Quit");
        quit.addActionListener(this);
  
        if (winOrLose == true){
            this.add(youWin);
        }
        else if (winOrLose == false){
            this.add(youLose);
        }
        this.add(playAgain);
        this.add(quit);
        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event){
        Object source = event.getSource();
        if(source.equals(playAgain)){
            //open a new game. Old one closes from game class.
            new Game(10,10);
            this.dispose();
        } 
        else if(source.equals(quit)){
            this.dispose();
        }    
    }    
}