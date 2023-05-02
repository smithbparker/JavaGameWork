import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class PuzzleGame extends JFrame {
    //instance members

    final int HEIGHT = 400; //keep height final
    int WIDTH = 400;// allow width to be resized
    int NUMROWS = 4;
    int NUMCOLS = 3;
    JPanel mainPanel = new JPanel();
    ArrayList<FancyButton> buttonsSolution;
    ArrayList<FancyButton> allButtons = new ArrayList<FancyButton>(); //stores buttons
    BufferedImage imageSource; //holds the original image
    BufferedImage imageResized; //holds the original image shrinked to desired size
    int moveCount = 0; //keeps track of how many moves the user has performed

    //methods
    public BufferedImage LoadImage(String filePath) throws IOException
    {
        return ImageIO.read(new File(filePath));

    }

    public void showOriginal() {
        ImageIcon icon = new ImageIcon(imageSource);
        JOptionPane.showMessageDialog(this, icon, "Original Image", JOptionPane.PLAIN_MESSAGE);
    }

    //ctors
    public PuzzleGame()
    {

        setContentPane(mainPanel);//adds the panel to the frame
        //create and set layout of grid
        mainPanel.setLayout( new GridLayout(NUMROWS, NUMCOLS));


        try
        {
            imageSource = LoadImage("gameimage.jpg");
            //fing height and width of original image
            int sourceHeight = imageSource.getHeight();
            int sourceWidth = imageSource.getWidth();

            WIDTH = (int) (((double)sourceWidth*HEIGHT)/sourceHeight);//resize window to match image

            //create resized image to match our window
            imageResized = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            var g = imageResized.createGraphics();
            g.drawImage(imageSource, 0, 0, WIDTH, HEIGHT, null);
            g.dispose();
        }
        catch(Exception e)
        {
            // System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this, e.getMessage(), "Critical Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        createButtons();
        updateButtons();

        setTitle("The Puzzle Game");
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
    }

    private void createButtons() {
        allButtons.clear(); //clear the existing buttons

        for(int i=0; i<NUMROWS*NUMCOLS; i++) //buttons
        {
            //get row and col from i
            int row = i/NUMCOLS, col = i% NUMCOLS;

            Image imageSlice = createImage(new FilteredImageSource(imageResized.getSource(),
                new CropImageFilter(col*WIDTH/NUMCOLS, row*HEIGHT/NUMROWS, WIDTH/NUMCOLS, HEIGHT/NUMROWS)));

            FancyButton btn = new FancyButton();//create the button
            if(i==NUMCOLS*NUMROWS-1)// for last button
            {
                btn.setBorderPainted(false); //set no border
                btn.setContentAreaFilled(false); //set no fill
            }
            else
            {
                btn.setIcon(new ImageIcon(imageSlice));
            }

            allButtons.add(btn);//add button to array list
            btn.addActionListener(e -> ClickEventHandler(e));
        }
        Collections.shuffle(allButtons);//shuffle the buttons
    }

    private void ClickEventHandler(ActionEvent e)
    {
        FancyButton btnClicked = (FancyButton) e.getSource();//which button was clicked on
        int i = allButtons.indexOf(btnClicked);//find the buttons index
        int row = i / NUMCOLS;
        int col = i % NUMCOLS;

        int iempty = -1; //find the empty button in the list
        for(int j=0; j< allButtons.size(); j++)
        {
            if(allButtons.get(j).getIcon()==null)//the empty button
            {
                iempty = j;
                break;
            }
        }
        int rowempty = iempty / NUMCOLS;//find its row and column
        int colempty = iempty % NUMCOLS;

        //check if clicked button is adjacent to the empty one
        if((row==rowempty && Math.abs(col-colempty)==1) || (col==colempty && Math.abs(row-rowempty)==1))
        {
            Collections.swap(allButtons, i, iempty);
            updateButtons();
            moveCount++; //increment the move counter
        }

        //check for solution
        if(buttonsSolution.equals(allButtons))
        {
            JOptionPane.showMessageDialog(null, "Well done. You solved the puzzle in " + moveCount + " moves."); //display the move counter
        }
    }

    private void updateButtons()
    {
        mainPanel.removeAll();//remove all buttons from panel
        //re add them to the panel
        for(var btn : allButtons)
        {
            mainPanel.add(btn);
        }
        //reload panel
        mainPanel.validate();
    }

//reset the game with new tile configuration and original dimensions
private void resetGame() {
    createButtons();
    updateButtons();
    moveCount = 0; //reset the move counter
    NUMROWS = 4; //revert back to original number of rows
    NUMCOLS = 3; //revert back to original number of columns
    setSize(WIDTH, HEIGHT); //resize the window to match original dimensions 
}

    //add a menu with options, one to reset and one to choose the number of rows and columns
    void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);

        JMenuItem resetItem = new JMenuItem("Reset");
        resetItem.addActionListener(e -> resetGame());
        optionsMenu.add(resetItem);

        JMenuItem resizeItem = new JMenuItem("Resize");
        optionsMenu.add(resizeItem);

        JMenuItem showAnswerItem = new JMenuItem("Show Original Image");
        showAnswerItem.addActionListener(e -> showOriginal());
        optionsMenu.add(showAnswerItem);

        JMenuItem checkMovesItem = new JMenuItem("Check Number of Moves");
        checkMovesItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "You have made " + moveCount + " moves so far.");
        });
        optionsMenu.add(checkMovesItem);

        final int MIN_ROWS = 2;
        final int MAX_ROWS = 4;
        final int MIN_COLS = 2;
        final int MAX_COLS = 6;

        resizeItem.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(null, "Enter new number of rows and columns (e.g. 3x4):");

            if (input != null) { // user clicked OK
                String[] parts = input.split("x");
                try {
                    int newRows = Integer.parseInt(parts[0]);
                    int newCols = Integer.parseInt(parts[1]);                     

                    if (newRows >= MIN_ROWS && newRows <= MAX_ROWS && newCols >= MIN_COLS && newCols <= MAX_COLS && newRows * newCols <= 24) { // check for valid input
                        NUMROWS = newRows;
                        NUMCOLS = newCols;
                        
                        resetGame();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number of rows and columns between " + MIN_ROWS + " and " + MAX_ROWS + " and " + MIN_COLS + " and " + MAX_COLS + " that results in a total of at most 24 cells.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter two integers separated by 'x'.");
                }
            }
        });

        setJMenuBar(menuBar);
    }
}

class FancyButton extends JButton
{
    public FancyButton()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.CYAN));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        });
    }
}
