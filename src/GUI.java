import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.util.ArrayList;

public class GUI extends JFrame {
    private final ArrayList<Players> players;
    private final Turn turn;
    private final JLabel playersLabel;
    private final JLabel hostLabel;
    private final JLabel phraseLabel;

    private Hosts host;
    private int currentPlayerIndex;

    public GUI() {
        players = new ArrayList<>();
        turn = new Turn();
        playersLabel = new JLabel();
        hostLabel = new JLabel();
        phraseLabel = new JLabel();
        currentPlayerIndex = 0;

        setTitle("Word Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton addPlayerButton = new JButton("Add Player");
        JButton setHostButton = new JButton("Set Host / Phrase");
        JButton startTurnsButton = new JButton("Start Player Turns");

        add(playersLabel);
        add(addPlayerButton);
        add(hostLabel);
        add(setHostButton);
        add(phraseLabel);
        add(startTurnsButton);

        addPlayerButton.addActionListener(event -> addPlayer());
        setHostButton.addActionListener(event -> setHostAndPhrase());
        startTurnsButton.addActionListener(event -> startTurns());

        updateLabels();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addPlayer() {
        JTextField firstNameField = new JTextField(15);
        JTextField lastNameField = new JTextField(15);
        JPanel panel = new JPanel(new FlowLayout());

        panel.add(new JLabel("First name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last name (optional):"));
        panel.add(lastNameField);

        int choice = JOptionPane.showConfirmDialog(this, panel, "Add Player",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();

        if (firstName.isEmpty()) {
            showWarning("First name is required.");
            return;
        }

        Players newPlayer;
        if (lastName.isEmpty()) {
            newPlayer = new Players(firstName);
        } else {
            newPlayer = new Players(firstName, lastName);
        }

        players.add(newPlayer);
        updateLabels();
    }

    private boolean setHostAndPhrase() {
        JTextField hostField = new JTextField(15);
        JTextField phraseField = new JTextField(15);
        JPanel panel = new JPanel(new FlowLayout());

        if (host != null) {
            hostField.setText(host.getDisplayName());
        }
        phraseField.setText(Phrases.getGamePhrase());

        panel.add(new JLabel("Host name:"));
        panel.add(hostField);
        panel.add(new JLabel("Game phrase:"));
        panel.add(phraseField);

        int choice = JOptionPane.showConfirmDialog(this, panel, "Set Host and Phrase",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (choice != JOptionPane.OK_OPTION) {
            return false;
        }

        String hostName = hostField.getText().trim();
        String phrase = phraseField.getText().trim();

        if (hostName.isEmpty()) {
            showWarning("Host name is required.");
            return false;
        }

        if (phrase.isEmpty()) {
            showWarning("Game phrase is required.");
            return false;
        }

        host = new Hosts(hostName);
        host.setGamePhrase(phrase);
        currentPlayerIndex = 0;
        updateLabels();
        return true;
    }

    private void startTurns() {
        if (players.isEmpty()) {
            showWarning("Add at least one player before starting turns.");
            return;
        }

        if (host == null || !Phrases.hasPhrase()) {
            showWarning("Set a host and phrase before starting turns.");
            return;
        }

        boolean keepPlaying = true;

        while (keepPlaying) {
            Players currentPlayer = players.get(currentPlayerIndex);
            String message = host.getDisplayName() + ": " + currentPlayer.getDisplayName()
                    + ", enter your guess for my phrase (one letter at a time)";
            String guess = JOptionPane.showInputDialog(this, message, "Player Turn", JOptionPane.QUESTION_MESSAGE);

            if (guess == null) {
                return;
            }

            Turn.TurnResult result = turn.takeTurn(currentPlayer, guess);
            updateLabels();

            if (!result.isValid()) {
                showWarning(result.getMessage());
                continue;
            }

            JOptionPane.showMessageDialog(this, result.getMessage(), "Turn Result", JOptionPane.INFORMATION_MESSAGE);

            if (result.isRoundWon()) {
                keepPlaying = playAgain();
                currentPlayerIndex = 0;
            } else {
                currentPlayerIndex = currentPlayerIndex + 1;
                if (currentPlayerIndex >= players.size()) {
                    currentPlayerIndex = 0;
                }
            }
        }
    }

    private boolean playAgain() {
        int choice = JOptionPane.showConfirmDialog(this, "Would you like to play again?",
                "Play Again", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            boolean newRoundReady = setHostAndPhrase();
            if (!newRoundReady) {
                clearPhrase();
            }
            return newRoundReady;
        }

        clearPhrase();
        return false;
    }

    private void clearPhrase() {
        Phrases.setGamePhrase("");
        updateLabels();
    }

    private void updateLabels() {
        playersLabel.setText(buildPlayersText());

        if (host == null) {
            hostLabel.setText("Current host: none");
        } else {
            hostLabel.setText("Current host: " + host.getDisplayName());
        }

        if (Phrases.hasPhrase()) {
            phraseLabel.setText("Current playing phrase: " + Phrases.getPlayingPhrase());
        } else {
            phraseLabel.setText("Current playing phrase: none");
        }

        pack();
    }

    private String buildPlayersText() {
        if (players.isEmpty()) {
            return "Current players: none";
        }

        String text = "<html>Current players:<br>";

        for (Players player : players) {
            text = text + player + "<br>";
        }

        text = text + "</html>";
        return text;
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Word Game", JOptionPane.WARNING_MESSAGE);
    }
}
