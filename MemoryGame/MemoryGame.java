import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class MemoryGame extends JFrame {
    //data
    JPanel mainPanel;
    ArrayList<JButton> listOfButtons;
    final int NUMROWS = 3;
    final int NUMCOLS = 4;
    ArrayList<Color> listOfColors;
    JButton flipped = null;

    //methods
    public static void shuffleColors(ArrayList<Color> listOfColors)
    {
        for(int i=0; i<listOfColors.size(); i++)
        {
            int j = (int)(Math.random()*listOfColors.size());
            //swap values in the array/arraylist at pos i and j
            Color tmp = listOfColors.get(i);
            listOfColors.set(i, listOfColors.get(j));
            listOfColors.set(j, tmp);
        }
    }

    //ctors
    public MemoryGame()
    {
        
        GridLayout gl = new GridLayout(NUMROWS, NUMCOLS);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(gl);
        this.setContentPane(mainPanel);

        listOfButtons = new ArrayList<JButton>();
        for(int i=0; i<NUMCOLS*NUMROWS; i++)
        {
            listOfButtons.add(new JButton());
        }

        //add buttons to grid layout - mainPanel
        for(JButton btn : listOfButtons)
        {
            mainPanel.add(btn);
        }

        listOfColors = new ArrayList<Color>();
        listOfColors.add(Color.BLACK);
        listOfColors.add(Color.BLACK);
        listOfColors.add(Color.RED);
        listOfColors.add(Color.RED);
        listOfColors.add(Color.MAGENTA);
        listOfColors.add(Color.MAGENTA);
        listOfColors.add(Color.PINK);
        listOfColors.add(Color.PINK);
        listOfColors.add(Color.BLUE);
        listOfColors.add(Color.BLUE);
        listOfColors.add(Color.GREEN);
        listOfColors.add(Color.GREEN);

        //randomize the array
        shuffleColors(listOfColors);

        //add a click event listener to each of our twelve buttons
        for(JButton btn : listOfButtons)
        {
            btn.addActionListener(e -> buttonClicked(e));
        }

        setSize(400,400);
        setTitle("CSC205 the memory game");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void buttonClicked(ActionEvent e)
    {
        /*((JButton)e.getSource()).setBackground(Color.RED);
        ((JButton)e.getSource()).setEnabled(false);*/
        JButton theButtonClickedOn = (JButton)e.getSource();

        int i = listOfButtons.indexOf(theButtonClickedOn);
        //set color of button using corresponding color from list of colors
        theButtonClickedOn.setBackground(listOfColors.get(i) );

        if(flipped == null)//first button clicked on
        {
        //find which button from list of buttons generates event
        flipped = theButtonClickedOn;
        theButtonClickedOn.setEnabled(false);
        }
        else //the second button clicked on
        {
            //compare colors to first one flipped
            if(theButtonClickedOn.getBackground().equals(flipped.getBackground()))
            {
                //if match disable both buttons
                theButtonClickedOn.setEnabled(false);
            }
            else
            {
                //flip them back over
                JOptionPane.showMessageDialog(null, "not a match");
                theButtonClickedOn.setBackground(null);
                theButtonClickedOn.setEnabled(true);

                flipped.setBackground(null);
                flipped.setEnabled(true);
            }

            flipped = null;
        }
    }
}
