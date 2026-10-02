import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class GUI extends JFrame {
    private final ArrayList<Players> players;
    private final Turn turn;
    private final JLabel playersLabel;
    private final JLabel hostLabel;
    private final JLabel phraseLabel;
    private final JLabel currentPlayerLabel;
    private final JTextArea messageArea;
    private final JCheckBox saveMessagesCheckBox;
    private final JTextField guessField;
    private final JButton startTurnsButton;
    private final JButton submitGuessButton;

    private Hosts host;
    private int currentPlayerIndex;
    private boolean roundActive;

    public GUI() {
        players = new ArrayList<>();
        turn = new Turn();
        playersLabel = new JLabel();
        hostLabel = new JLabel();
        phraseLabel = new JLabel();
        currentPlayerLabel = new JLabel();
        messageArea = new JTextArea(12, 35);
        saveMessagesCheckBox = new JCheckBox("Save Messages", true);
        guessField = new JTextField(12);
        startTurnsButton = new JButton("Start Player Turns");
        submitGuessButton = new JButton("Submit Guess");
        currentPlayerIndex = 0;
        roundActive = false;

        setTitle("Word Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setJMenuBar(buildMenuBar());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                SoundEffects.stopBackgroundMusic();
            }
        });

        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        saveMessagesCheckBox.setToolTipText("Keep this checked to add new messages to the log instead of replacing the last message.");

        startTurnsButton.addActionListener(event -> beginPlayerTurns());
        submitGuessButton.addActionListener(event -> handleGuessSubmission());
        guessField.addActionListener(event -> handleGuessSubmission());

        add(buildContentPanel(), BorderLayout.CENTER);

        refreshStatusLabels();
        refreshTurnControls();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        SoundEffects.startBackgroundMusic();
    }

    // User interface setup

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);

        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem addPlayerItem = new JMenuItem("Add Player");
        JMenuItem addHostItem = new JMenuItem("Add Host / Set Phrase");

        newGameItem.addActionListener(event -> createNewGame());
        addPlayerItem.addActionListener(event -> addPlayerFromDialog());
        addHostItem.addActionListener(event -> setHostAndGamePhrase());

        gameMenu.add(newGameItem);
        gameMenu.add(addPlayerItem);
        gameMenu.add(addHostItem);

        JMenu aboutMenu = new JMenu("About");
        aboutMenu.setMnemonic(KeyEvent.VK_A);

        JMenuItem layoutItem = new JMenuItem("Layout");
        JMenuItem imageAttributionItem = new JMenuItem("Images");
        JMenuItem soundAttributionItem = new JMenuItem("Sounds");
        layoutItem.addActionListener(event -> showLayoutExplanation());
        imageAttributionItem.addActionListener(event -> showImageAttribution());
        soundAttributionItem.addActionListener(event -> showSoundAttribution());
        aboutMenu.add(layoutItem);
        aboutMenu.add(imageAttributionItem);
        aboutMenu.add(soundAttributionItem);

        menuBar.add(gameMenu);
        menuBar.add(aboutMenu);
        return menuBar;
    }

    private JPanel buildContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel statusPanel = new JPanel(new GridLayout(4, 1, 6, 6));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Game Status"));
        statusPanel.add(playersLabel);
        statusPanel.add(hostLabel);
        statusPanel.add(phraseLabel);
        statusPanel.add(currentPlayerLabel);

        JPanel controlsPanel = new JPanel(new GridLayout(5, 1, 0, 8));
        controlsPanel.setBorder(BorderFactory.createTitledBorder("Turn Controls"));
        controlsPanel.add(startTurnsButton);
        controlsPanel.add(new JLabel("Enter one letter:"));
        controlsPanel.add(guessField);
        controlsPanel.add(submitGuessButton);
        controlsPanel.add(saveMessagesCheckBox);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createTitledBorder("Game Messages"));

        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messagePanel.add(messageScrollPane, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        leftPanel.add(controlsPanel, BorderLayout.NORTH);

        contentPanel.add(statusPanel, BorderLayout.NORTH);
        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(messagePanel, BorderLayout.CENTER);
        return contentPanel;
    }

    // Menu actions and dialog methods

    private void createNewGame() {
        boolean gameReady = setHostAndGamePhrase();
        if (gameReady) {
            appendMessage("Choose Start Player Turns when you are ready to begin.");
        }
    }

    private void addPlayerFromDialog() {
        JTextField firstNameField = new JTextField(15);
        JTextField lastNameField = new JTextField(15);
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
            showWarningMessage("First name is required.");
            return;
        }

        Players newPlayer;
        if (lastName.isEmpty()) {
            newPlayer = new Players(firstName);
        } else {
            newPlayer = new Players(firstName, lastName);
        }

        players.add(newPlayer);
        refreshStatusLabels();
        appendMessage("Added player: " + newPlayer.getDisplayName());
    }

    private boolean setHostAndGamePhrase() {
        JTextField hostField = new JTextField(15);
        JTextField phraseField = new JTextField(15);
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
            showWarningMessage("Host name is required.");
            return false;
        }

        if (phrase.isEmpty()) {
            showWarningMessage("Game phrase is required.");
            return false;
        }

        host = new Hosts(hostName);
        host.setGamePhrase(phrase);
        turn.startNewRound();
        currentPlayerIndex = 0;
        roundActive = false;
        clearMessages();
        refreshStatusLabels();
        refreshTurnControls();
        appendMessage("New game ready with host " + host.getDisplayName() + ".\nPhrase to solve: " + Phrases.getPlayingPhrase());
        return true;
    }

    private void showLayoutExplanation() {
        String message = "This window uses BorderLayout to separate the game into clear sections: "
                + "status at the top, turn controls on the left, and a large message log in the center. "
                + "Inside those sections, GridLayout keeps related information grouped "
                + "and makes the interface easier to read and use.";
        JOptionPane.showMessageDialog(this, message, "Layout", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showImageAttribution() {
        showAttributionDialog("Image Attribution", PrizeArtwork.getAttributionText());
    }

    private void showSoundAttribution() {
        showAttributionDialog("Sound Attribution", SoundEffects.getAttributionText());
    }

    // Show any attribution text in a simple scrollable window
    private void showAttributionDialog(String title, String text) {
        JTextArea attributionArea = new JTextArea(text);
        attributionArea.setEditable(false);
        attributionArea.setLineWrap(true);
        attributionArea.setWrapStyleWord(true);
        attributionArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(attributionArea);
        scrollPane.setPreferredSize(new Dimension(430, 260));

        JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showPhysicalPrizeDialog(Players player, String prizeName) {
        showPrizeDialog(player.getDisplayName() + " won a physical prize!", prizeName, "Physical Prize");
    }

    private void showSolvedPrizeDialog(Players player, String prizeName) {
        showPrizeDialog(player.getDisplayName() + " solved the phrase and won this prize!", prizeName, "Winner Prize");
    }

    private void showPrizeDialog(String titleText, String prizeName, String dialogTitle) {
        if (!Phrases.isPrizeWithPicture(prizeName)) {
            return;
        }

        SoundEffects.playPhysicalPrize();

        JPanel panel = new JPanel(new BorderLayout(0, 10));

        JLabel titleLabel = new JLabel(titleText, SwingConstants.CENTER);
        JLabel imageLabel = new JLabel(PrizeArtwork.createPrizeIcon(prizeName));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel descriptionLabel = new JLabel("Prize: " + prizeName, SwingConstants.CENTER);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(descriptionLabel, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(this, panel, dialogTitle, JOptionPane.INFORMATION_MESSAGE);
    }

    // Game flow methods

    private void beginPlayerTurns() {
        if (players.isEmpty()) {
            showWarningMessage("Add at least one player before starting turns.");
            return;
        }

        if (host == null || !Phrases.hasPhrase()) {
            showWarningMessage("Set a host and phrase before starting turns.");
            return;
        }

        roundActive = true;
        refreshTurnControls();
        appendMessage("Turns started. " + buildCurrentTurnPrompt());
        SoundEffects.playGameStart();
        guessField.requestFocusInWindow();
    }

    private void handleGuessSubmission() {
        if (!roundActive) {
            showWarningMessage("Start player turns before submitting a guess.");
            return;
        }

        String guess = guessField.getText();
        guessField.setText("");

        Players currentPlayer = players.get(currentPlayerIndex);
        Turn.TurnResult result = turn.takeTurn(currentPlayer, guess);
        refreshStatusLabels();

        if (!result.isValid()) {
            showWarningMessage(result.getMessage());
            guessField.requestFocusInWindow();
            return;
        }

        appendMessage(currentPlayer.getDisplayName() + " guessed '" + guess.trim() + "'.\n" + result.getMessage());

        if (result.isGuessCorrect()) {
            // Letter is in the phrase - show checkmark image
            SoundEffects.playCorrectGuess();
            new FloatingSymbol(this, true); // true = show checkmark
        } else {
            // Letter is NOT in the phrase - show X image
            SoundEffects.playIncorrectGuess();
            new FloatingSymbol(this, false); // false = show X
        }

        if (result.getPhysicalPrizeName() != null) {
            showPhysicalPrizeDialog(currentPlayer, result.getPhysicalPrizeName());
        }

        if (result.isRoundWon()) {
            showSolvedPrizeDialog(currentPlayer, Phrases.getWinPrize());
            roundActive = false;
            refreshTurnControls();
            boolean continuing = askToPlayAgain();
            if (!continuing) {
                refreshTurnControls();
            }
            return;
        }

        currentPlayerIndex = currentPlayerIndex + 1;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }

        refreshTurnControls();
        appendMessage(buildCurrentTurnPrompt());
        guessField.requestFocusInWindow();
    }

    private boolean askToPlayAgain() {
        int choice = JOptionPane.showConfirmDialog(this, "Would you like to play again?",
                "Play Again", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            boolean newRoundReady = setHostAndGamePhrase();
            if (!newRoundReady) {
                clearCurrentPhrase();
                appendMessage("The game was not restarted, so the current phrase was cleared.");
            } else {
                roundActive = true;
                refreshTurnControls();
                appendMessage("A new game has started. " + buildCurrentTurnPrompt());
            }
            return newRoundReady;
        }

        clearCurrentPhrase();
        appendMessage("The game has ended. Start a new game from the Game menu whenever you are ready.");
        return false;
    }

    private void clearCurrentPhrase() {
        Phrases.setGamePhrase("");
        roundActive = false;
        refreshStatusLabels();
        refreshTurnControls();
    }

    // Display and control helpers

    private void refreshStatusLabels() {
        playersLabel.setText(buildPlayersDisplayText());

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

        if (roundActive && !players.isEmpty()) {
            currentPlayerLabel.setText("Current turn: " + players.get(currentPlayerIndex).getDisplayName());
        } else {
            currentPlayerLabel.setText("Current turn: not started");
        }
    }

    private String buildPlayersDisplayText() {
        if (players.isEmpty()) {
            return "Current players: none";
        }

        StringBuilder text = new StringBuilder("<html>Current players:<br>");

        for (Players player : players) {
            text.append(player).append("<br>");
        }

        text.append("</html>");
        return text.toString();
    }

    private void refreshTurnControls() {
        boolean canPlay = roundActive && !players.isEmpty() && host != null && Phrases.hasPhrase();
        guessField.setEnabled(canPlay);
        submitGuessButton.setEnabled(canPlay);
        if (!canPlay) {
            guessField.setText("");
        }
        refreshStatusLabels();
    }

    private String buildCurrentTurnPrompt() {
        if (players.isEmpty()) {
            return "There are no players yet.";
        }

        Players currentPlayer = players.get(currentPlayerIndex);
        return host.getDisplayName() + ": " + currentPlayer.getDisplayName()
                + ", enter your guess for my phrase (one letter at a time).";
    }

    // Message helpers

    private void appendMessage(String message) {
        if (message == null) {
            return;
        }

        String formattedMessage = message.trim();
        if (formattedMessage.isEmpty()) {
            return;
        }

        if (!saveMessagesCheckBox.isSelected() || messageArea.getText().isBlank()) {
            messageArea.setText(formattedMessage);
        } else {
            messageArea.append("\n\n" + formattedMessage);
        }

        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

    private void clearMessages() {
        messageArea.setText("");
    }

    private void showWarningMessage(String message) {
        appendMessage("Warning: " + message);
    }
}
