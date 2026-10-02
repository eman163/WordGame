import javax.swing.ImageIcon;
import java.io.File;

public final class PrizeArtwork {
    private PrizeArtwork() {
    }

    // Look for prize images in the "images" folder
    public static ImageIcon createPrizeIcon(String prizeName) {
        String filename = getPrizeFilename(prizeName);

        if (filename == null) {
            return null; // Prize name not recognized
        }

        File imageFile = new File("images", filename);

        if (!imageFile.exists()) {
            return null; // Image file not found
        }

        try {
            return new ImageIcon(imageFile.getAbsolutePath());
        } catch (Exception exception) {
            return null; // Error loading image
        }
    }

    // Match prize names to image filenames
    private static String getPrizeFilename(String prizeName) {
        if (prizeName == null) {
            return null;
        }

        if (prizeName.equals("a cruise for two")) {
            return "cruise.png";
        }
        if (prizeName.equals("a vacation to Tahiti")) {
            return "tahiti.png";
        }
        if (prizeName.equals("a ski trip to the Alps")) {
            return "ski_trip.png";
        }
        if (prizeName.equals("a safari adventure in Africa")) {
            return "safari.png";
        }
        if (prizeName.equals("a spa retreat in Bali")) {
            return "spa.png";
        }
        if (prizeName.equals("a year of gas")) {
            return "gas.png";
        }

        return null; // Unknown prize
    }

    // Return information about image credits
    public static String getAttributionText() {
        return "Prize Images\n\n"
                + "These images come from your 'images' folder.\n\n"
                + "To add images:\n"
                + "1. Visit https://pixabay.com/\n"
                + "2. Download free images\n"
                + "3. Save them to the 'images' folder with these names:\n"
                + "   - cruise.png\n"
                + "   - tahiti.png\n"
                + "   - ski_trip.png\n"
                + "   - safari.png\n"
                + "   - spa.png\n"
                + "   - gas.png\n\n"
                + "All Pixabay images are free to use.";
    }
}





