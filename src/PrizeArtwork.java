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

        return null;
    }


    public static String getAttributionText() {
        return "Prize Images\n\n"
                + "The images below comes from https://pixabay.com/\n"
                + "   - cruise.png - https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=4460493\n"
                + "   - tahiti.png - https://pixabay.com/photos/tahiti-mountains-paradise-2823883/\n"
                + "   - ski_trip.png - https://pixabay.com/photos/mountains-nature-tourism-hike-6486093/\n"
                + "   - safari.png - https://pixabay.com/photos/stalk-africa-binoculars-bush-863823/\n"
                + "   - spa.png - https://pixabay.com/illustrations/ai-generated-spa-massage-meditation-8970246/\n"
                + "   - gas.png - https://pixabay.com/photos/fuel-gas-station-refueling-gas-6999650/\n\n"
                + "All Pixabay images are free to use.";
    }
}





