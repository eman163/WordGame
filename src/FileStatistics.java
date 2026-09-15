import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStatistics {

	public static void main(String[] args) {
		if (args.length == 0) {
			return;
		}

		Path filePath = Paths.get(args[0]);

		try {
			long fileSize = Files.size(filePath);

			System.out.println("File name: " + filePath.getFileName());
			System.out.println("File size: " + fileSize + " bytes");
			System.out.println("Last modified: " + Files.getLastModifiedTime(filePath));
		} catch (IOException ignored) {
		}
	}
}
