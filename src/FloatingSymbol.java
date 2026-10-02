import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Frame;
import java.io.File;

public class FloatingSymbol extends JDialog {
    private static final int ANIMATION_DURATION = 5000; // 5 seconds
    private static final int UPDATE_SPEED = 50; // Update every 50 milliseconds
    private JLabel imageLabel;
    private javax.swing.Timer animationTimer;
    private int elapsedTime;

    // Show a floating checkmark or X image that animates
    public FloatingSymbol(Frame parent, boolean isCorrect) {
        super(parent);

        // Create the label to hold the image
        imageLabel = new JLabel();

        // Load the correct image file
        String filename;
        if (isCorrect) {
            filename = "Check mark.jpg"; // Use checkmark image for correct guess
        } else {
            filename = "X mark.jpg"; // Use X image for incorrect guess
        }

        File imageFile = new File("images", filename);

        if (!imageFile.exists()) {
            return;
        }

        try {
            imageLabel.setIcon(new ImageIcon(imageFile.getAbsolutePath()));
        } catch (Exception exception) {
            return;
        }

        // Set up the window
        add(imageLabel);
        setUndecorated(true); // No title bar or borders
        setSize(120, 120);

        // Random position on screen
        int randomX = 200 + (int) (Math.random() * 300);
        int randomY = 150 + (int) (Math.random() * 200);
        setLocation(randomX, randomY);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true); // Show on top of everything

        elapsedTime = 0;

        // Start a simple timer that moves the image up and then closes it
        animationTimer = new javax.swing.Timer(UPDATE_SPEED, event -> animateSymbol());
        animationTimer.start();

        setVisible(true);
    }

    private void animateSymbol() {
        elapsedTime += UPDATE_SPEED;

        int currentY = getY();
        int newY = currentY - 1;
        setLocation(getX(), newY);

        if (elapsedTime >= ANIMATION_DURATION) {
            animationTimer.stop();
            dispose();
        }
    }
}











