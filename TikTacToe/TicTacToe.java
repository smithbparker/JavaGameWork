
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;



public class TicTacToe extends JFrame {
    JPanel panel = new JPanel();
    JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    String currentPlayer;
    Font font = new Font(Font.SERIF, Font.BOLD, 100);
    JMenu mainMenu;
    JMenuItem exitGame;
    JMenuItem resetGame;
    JMenuBar menuBar;

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
    }

    public void ButtonClicked(ActionEvent e){
        
        ((JButton) e.getSource()).setText(currentPlayer);
        ((JButton) e.getSource()).setEnabled(false);

        if(currentPlayer.equals("X")){
            ((JButton)e.getSource()).setBackground(Color.RED);
        }
        else{
            ((JButton)e.getSource()).setBackground(Color.GREEN);
        }
        if(DoWeHaveAWinner()){
            //display a message
            JOptionPane.showMessageDialog(panel, "Congratulations!"+ currentPlayer + "Wins!");
            //disable all buttons
            btn1.setEnabled(false);
            btn2.setEnabled(false);
            btn3.setEnabled(false);
            btn4.setEnabled(false);
            btn5.setEnabled(false);
            btn6.setEnabled(false);
            btn7.setEnabled(false);
            btn8.setEnabled(false);
            btn9.setEnabled(false);
        }
        //check for winners???
        SwitchPlayer();
    }

    public boolean CheckThreeButton(JButton a, JButton b, JButton c){
        return a.getText().equals(b.getText())
        && b.getText().equals(c.getText())
        && a.getText()!="";
    }

    public boolean DoWeHaveAWinner(){
        if(CheckThreeButton(btn1, btn2, btn3)){
            return true;
        }
        if(CheckThreeButton(btn4, btn5, btn6)){
            return true;
        }
        if(CheckThreeButton(btn7, btn8, btn9)){
            return true;
        }
        if(CheckThreeButton(btn1, btn4, btn7)){
            return true;
        }
        if(CheckThreeButton(btn2, btn5, btn8)){
            return true;
        }
        if(CheckThreeButton(btn3, btn6, btn9)){
            return true;
        }
        if(CheckThreeButton(btn1, btn5, btn9)){
            return true;
        }
        if(CheckThreeButton(btn3, btn5, btn7)){
            return true;
        }
        //everything else
     return false;
        
    }

    public void SwitchPlayer(){
        if(currentPlayer.equals("X")){
            currentPlayer = "O";
        }
        else{
            currentPlayer = "X";
        }
    } //currentPlayer = currentPlayer.equals("X")?"O":"x";

    //consructors
    public TicTacToe() {
        //or super("My First Java Game");
        setTitle("My first Java Game");
        setVisible(true);
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(500, 200);
        //setResizable(false);

        //add Jpanel to frame
        setContentPane(panel);

        //set a layout
        panel.setLayout(new GridLayout(3,3));

        //build and add buttons to panel
        btn1 = new JButton(); 
        panel.add(btn1); 
        btn1.addActionListener(e -> ButtonClicked(e));
        btn1.setFont(font);

        btn2 = new JButton(); 
        panel.add(btn2); 
        btn2.addActionListener(e -> ButtonClicked(e));
        btn2.setFont(font);

        btn3 = new JButton(); 
        panel.add(btn3); 
        btn3.addActionListener(e -> ButtonClicked(e));
        btn3.setFont(font);

        btn4 = new JButton(); 
        panel.add(btn4); 
        btn4.addActionListener(e -> ButtonClicked(e));
        btn4.setFont(font);

        btn5 = new JButton(); 
        panel.add(btn5); 
        btn5.addActionListener(e -> ButtonClicked(e));
        btn5.setFont(font);

        btn6 = new JButton(); 
        panel.add(btn6); 
        btn6.addActionListener(e -> ButtonClicked(e));
        btn6.setFont(font);

        btn7 = new JButton(); 
        panel.add(btn7); 
        btn7.addActionListener(e -> ButtonClicked(e));
        btn7.setFont(font);

        btn8 = new JButton(); 
        panel.add(btn8); 
        btn8.addActionListener(e -> ButtonClicked(e));
        btn8.setFont(font);

        btn9 = new JButton(); 
        panel.add(btn9); 
        btn9.addActionListener(e -> ButtonClicked(e));
        btn9.setFont(font);

        currentPlayer = "X"; //initialize current player to X

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        mainMenu = new JMenu("Game Options");
        menuBar.add(mainMenu);

        resetGame = new JMenuItem("reset");
        resetGame.addActionListener(e -> Reset());
        exitGame = new JMenuItem("exit");
        exitGame.addActionListener(e -> System.exit(EXIT_ON_CLOSE));
        mainMenu.add(resetGame);
        mainMenu.add(exitGame);

    }

    public void Reset()
    {
        //to do
    }

}