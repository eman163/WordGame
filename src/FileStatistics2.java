import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileStatistics2 {

	public static void main(String[] args) throws IOException {
		long firstFileSize = Files.size(Paths.get("Quote.txt"));
		long secondFileSize = Files.size(Paths.get("Quote.doc"));
		double ratio = (double) firstFileSize / secondFileSize;

		System.out.println("First file size: " + firstFileSize + " bytes");
		System.out.println("Second file size: " + secondFileSize + " bytes");
		System.out.println("Size ratio (first/second): " + ratio);
	}
}
