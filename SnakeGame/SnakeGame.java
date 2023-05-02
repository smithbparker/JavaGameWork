import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SnakeGame extends JFrame implements ActionListener {
    // These variables are constants used for setting up the game board.
    private final int BOARD_WIDTH = 600;
    private final int BOARD_HEIGHT = 600;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    // These variables keep track of the delay and the score of the game.
    private int delay = 140;
    private int score = 0;

    // These arrays keep track of the position of the snake and the apple.
    private int x[] = new int[ALL_DOTS];
    private int y[] = new int[ALL_DOTS];
    private int dots;
    private int apple_x;
    private int apple_y;

    // These variables keep track of the direction of the snake and whether or not the game is in progress.
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    // This is the timer used to move the snake and update the game board.
    private Timer timer;
    // These images are used to display the snake, apple, and ball on the game board.
    private Image ball;
    private Image apple;
    private Image head;
    // This button is used to reset the game.
    private JButton resetButton;

    public SnakeGame() {
        initBoard();
    }

    private void initBoard() {
        // This sets up the game board.
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setResizable(false);
        setTitle("Snake");
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setVisible(true);
        // This loads the images used on the game board.
        loadImages();
        // This initializes the game by setting up the snake and apple positions.
        initGame();

        // This sets up the reset button.
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        add(resetButton, BorderLayout.SOUTH);
        
    }

    private void loadImages() {
        // This loads the images used on the game board.
        ImageIcon iid = new ImageIcon("dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("head.png");
        head = iih.getImage();
    }

    private void initGame() {
        // This initializes the game by setting up the snake and apple positions.
        dots = 3;
        inGame = true;
        score = 0;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        // This starts the timer used to move the snake and update the game board.
        timer = new Timer(delay, this);
        timer.start();
        // This resets the focus on the game board.
        resetFocus();
    }

    private void resetGame() {
        // Stop the timer
        timer.stop();
        
        // Reset the game state
        dots = 3;
        inGame = true;
        score = 0;
        leftDirection = false;
        rightDirection = true;
        upDirection = false;
        downDirection = false;
        
        // Reset the snake position
        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        
        // Reset the apple position
        locateApple();
        
        // Restart the timer
        timer.start();
        //this resets the focus on the game board 
        resetFocus();
    }
    //I added the reset focus after making the restart button
    private void resetFocus() {
        this.requestFocusInWindow();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()-35);
        //If the game is in progress draw the snake, apple, and score 
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            //if the game is over display the game over
            gameOver(g);
        }
        //draw the score in the top left corner
        g.setColor(Color.RED);
        g.drawString("Score: " + score, 20, 60);
    }
    //Make the Game over screen 
    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        
        g.setColor(Color.RED);
        g.setFont(small);
        g.drawString(msg, (BOARD_WIDTH - metr.stringWidth(msg)) / 2, BOARD_HEIGHT / 2);
        g.drawString("Your score: " + score, (BOARD_WIDTH - metr.stringWidth("Your score: " + score)) / 2, (BOARD_HEIGHT / 2) + 20);
    }
    //this checks if the snake head is colliding
    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            score++;
            locateApple();
        }
    }
    //this moves the snake by updating its position
    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        //updates the position of the head of the snake based on the direction moving
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }
        
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
        
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
        
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }
    //this checks if the head of the snake has collided with itself or the edge of the game
    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }
        
        if (y[0] >= BOARD_HEIGHT) {
            inGame = false;
        }
        
        if (y[0] < 0) {
            inGame = false;
        }
        
        if (x[0] >= BOARD_WIDTH) {
            inGame = false;
        }
        
        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            timer.stop();
        }
    }
    //randomly spawns a new apple on the game 
    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE) % (BOARD_WIDTH - DOT_SIZE)+20);
        
        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE) +100);

        if (apple_y < 20) {
            apple_y += 20;
        }
    }
    
// this is called every time the timer goes off to update game board
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            //move the snake, check for collisions, and if it has eaten an apple
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        // This method handles what key is pressed to control the snake 
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new SnakeGame();
            ex.setVisible(true);

        });
    }
}




          
        
        
