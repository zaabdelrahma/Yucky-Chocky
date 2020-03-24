

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import java.util.Random;

public class Game extends JFrame implements ActionListener, MouseListener{
    private JPanel topPanel, bottomPanel;
    private JLabel selectSize;
    private JButton easy, normal, hard;
    private JTextArea update;
    private GridSquare [][] gridSquares;
    private int x, y, xCoordinate, yCoordinate;
    private boolean validMove = false;
    
    public Game(int x, int y){
        
        this.x = x;
        this.y = y;
        this.setSize(800, 800);

        //1. Create the frame.
//         JFrame frame = new JFrame("YUCKY CHOCKY");

// //2. Optional: What happens when the frame closes?
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// //3. Create components and put them in the frame.
// //...create emptyLabel...
//         JLabel emptyLabel = new JLabel("");
//         emptyLabel.setPreferredSize(new Dimension(175, 100));
//         emptyLabel.add(frame);
//         frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

// //4. Size the frame.
//         frame.pack();

//5. Show it.
        //frame.setVisible(true);        
        // set up top panel with labels and chocolate bar size buttons then set up bottom panel with the game graphics.
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout( x, y));
        
        
        //top panel
        //title = new JLabel("Welcome to Yucky Chocky!");
        selectSize = new JLabel("Select size:");
        
        easy = new JButton("Easy (5 X 5)");
        easy.addActionListener(this);
        
        normal = new JButton("Advanced (7 X 7)");
        normal.addActionListener(this);
        
        hard = new JButton("Hard (9 X 9)");
        hard.addActionListener(this);
        update = new JTextArea();
        update.setEditable(false);
        update.setRows(1);
        
        
        //topPanel.add(title);
        topPanel.add(selectSize);
        topPanel.add(easy);
        topPanel.add(normal);
        topPanel.add(hard);
        topPanel.add(update);
        
        //bottom panel.
        gridSquares = new GridSquare [x][y];
        for( int column = 0; column < x; column ++)
        {
            for( int row = 0; row < y; row ++)
            {
                if (row == 0 || column == 0){
                    gridSquares [column][row] = new GridSquare(column,row); 
                    Color color = new Color(102, 51, 0);
                    gridSquares [column][row].setBackground(color);
                    gridSquares [column][row].setBorder(BorderFactory.createLineBorder(Color.yellow)); //borders between gridsquares
                    gridSquares [column][row].addMouseListener(this);       
                    bottomPanel.add(gridSquares [column][row]);
                }
                else {
                    gridSquares [column][row] = new GridSquare(column,row); 
                    Color color = new Color(102, 51, 0);
                    gridSquares [column][row].setBackground(color);
                    gridSquares [column][row].setBorder(BorderFactory.createLineBorder(Color.yellow)); //borders between gridsquares      
                    bottomPanel.add(gridSquares [column][row]);
                }
            }
        }  
        gridSquares [0][0].setBackground(Color.green);// the top left square that represents the soap
            
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
         
        if(new Random().nextInt(100)%2 == 0){
            update.setText("Computer goes first");
            computerTurn();
        }
        else{
            update.setText("You go first");
        }    
    }   
    //actions performed when JButtons are pressed.
    public void actionPerformed(ActionEvent event){
        Object source = event.getSource();
        if (source.equals(easy)) {
            new Game(5, 5);
            this.dispose();
        }
        else if (source.equals(normal)){
            new Game(7, 7);
            this.dispose();
        }
        else if (source.equals(hard)) {
            new Game(9, 9);
            this.dispose();
        }
    }      
    //what happens when gridsquares are clicked on.          
    public void mouseClicked(MouseEvent evnt){
        // get the object that was selected in the gui
        Color color = new Color(102, 51, 0);
        Object selected = evnt.getSource();
        if (selected instanceof GridSquare)
        {
            if(((GridSquare) selected).getBackground().equals(Color.green)){
                //user picked the soap square so open a window that tells them they lost. Close current Game afterwards.
                new event(false);
                this.dispose();
            }    
            else if(((GridSquare) selected).getBackground().equals(color)){
                //user picked a square. turn every square to the right and below the picked square gray (including the picked square).
                for(int column = ((GridSquare) selected).getXCoord(); column < x; column ++){
                    for(int row = ((GridSquare) selected).getYCoord(); row < y; row ++){
                        //set black squares from this move to be gray.
                        if(gridSquares[column][row].getBackground().equals(color)){
                            gridSquares[column][row].setBackground(Color.gray);
                            gridSquares[column][row].setBorder(BorderFactory.createLineBorder(Color.gray));
                        }
                        else{
                            continue;
                        }    
                    }    
                }
                computerTurn();
            }
        }                    
    }
    public void mouseEntered(MouseEvent arg0){}
    public void mouseExited(MouseEvent arg0) {}
    public void mousePressed(MouseEvent arg0) {}
    public void mouseReleased(MouseEvent arg0) {}
    
    public void computerTurn(){
        Color color = new Color(102, 51, 0);
        while (validMove == false){
            xCoordinate = new Random().nextInt(this.x);
            yCoordinate = new Random().nextInt(this.y);
            if(gridSquares[xCoordinate][yCoordinate].getBackground().equals(Color.green)){
                //if computer chose soap piece then display an event to let player know they won.
                new event(true);
                this.dispose();
                validMove = true;
            }
            else if(gridSquares[xCoordinate][yCoordinate].getBackground().equals(Color.gray) || gridSquares[xCoordinate][yCoordinate].getBackground().equals(Color.white)){
                continue;
            }
            else if(gridSquares[xCoordinate][yCoordinate].getBackground().equals(color)){
                update.setText("Computer chose the piece at [" + yCoordinate + "][" + xCoordinate + "]" +" It is now your turn.");
                for(int column = xCoordinate; column < x; column ++){
                    for( int row = yCoordinate; row < y; row ++){
                        //set black squares from this move to be gray.
                        if(gridSquares[column][row].getBackground().equals(color)){
                            gridSquares[column][row].setBackground(Color.gray);
                            gridSquares[column][row].setBorder(BorderFactory.createLineBorder(Color.gray));
                        }   
                    }    
                }
                validMove = true;
            }    
            
        }
        validMove = false;
    }    
}