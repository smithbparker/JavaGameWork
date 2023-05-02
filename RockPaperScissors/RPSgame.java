import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RPSgame extends JFrame implements ActionListener {

    private JLabel scoreLabel, computerChoiceLabel;
    private JButton rockButton, paperButton, scissorsButton, resetButton;
    private int userScore, computerScore;
    private String lastComputerChoice;
    private Font font = new Font(Font.SERIF, Font.BOLD, 50);

    public RPSgame() {
        // setting up the JFrame
        setTitle("Rock Paper Scissors");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        //make the title label
        JLabel titleLabel = new JLabel("Play Rock Paper Scissors!");
        titleLabel.setFont(new Font(Font.SERIF, Font.BOLD, 60));
        titleLabel.setForeground(Color.BLUE);
        add(titleLabel);

        // creating the buttons
        // rock button
        rockButton = new JButton("Rock");
        rockButton.addActionListener(this);
        add(rockButton);
        rockButton.setFont(font);
        
    
        // paper button
        paperButton = new JButton("Paper");
        paperButton.addActionListener(this);
        add(paperButton);
        paperButton.setFont(font);

        // scissors button
        scissorsButton = new JButton("Scissors");
        scissorsButton.addActionListener(this);
        add(scissorsButton);
        scissorsButton.setFont(font);

        // reset button
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetScore();
            }
        });
        add(resetButton);
        resetButton.setFont(font);
        resetButton.setBackground(Color.RED);

        // creating the score label
        scoreLabel = new JLabel("Score: User " + userScore + " - Computer " + computerScore);
        add(scoreLabel);
        scoreLabel.setFont(font);

        // creating the label for computer's choice
        computerChoiceLabel = new JLabel("Computer's last choice: None");
        add(computerChoiceLabel);
        computerChoiceLabel.setFont(font);

        // show the frame
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // determine the user's choice
        String userChoice = e.getActionCommand();

        // generate the computer's choice
        String[] choices = {"Rock", "Paper", "Scissors"};
        int computerIndex = (int) (Math.random() * 3);
        String computerChoice = choices[computerIndex];

        // determine the winner
        String winner = determineWinner(userChoice, computerChoice);

        // update the score
        if (winner.equals("User")) {
            userScore++;
        } else if (winner.equals("Computer")) {
            computerScore++;
        }
        scoreLabel.setText("Score: User " + userScore + " - Computer " + computerScore);

        // update the label for computer's choice
        lastComputerChoice = computerChoice;
        computerChoiceLabel.setText("Computer's last choice: " + lastComputerChoice);
    }

    private String determineWinner(String userChoice, String computerChoice) {
        if (userChoice.equals(computerChoice)) {//if they pick the same thing
            return "Tie";
        } else if (userChoice.equals("Rock")) {//rock beats scissors
            if (computerChoice.equals("Scissors")) {
                return "User";
            } else {
                return "Computer";
            }
        } else if (userChoice.equals("Paper")) {//paper beats rock
            if (computerChoice.equals("Rock")) {
                return "User";
            } else {
                return "Computer";
            }
        } else { // scissors beats paper
            if (computerChoice.equals("Paper")) {
                return "User";
            } else {
                return "Computer";
            }
        }
    }

    private void resetScore() {//reset the score 
        userScore = 0;
        computerScore = 0;
        scoreLabel.setText("Score: User " + userScore + " - Computer " + computerScore);
    }

    public static void main(String[] args) {
        new RPSgame();
    }

}