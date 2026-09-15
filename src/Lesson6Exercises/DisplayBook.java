import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class DisplayBook {

    public static void main(String[] args) {
        File bookFile = new File("favoriteBook.txt");

        try {
            if (bookFile.exists()) {
                FileInputStream inputStream = new FileInputStream(bookFile);
                byte[] data = inputStream.readAllBytes();
                inputStream.close();

                String title = new String(data).trim();
                System.out.println("Your favorite book is: " + title);
            } else {
                Scanner keyboard = new Scanner(System.in);
                System.out.print("Enter your favorite book title: ");
                String title = keyboard.nextLine();

                FileOutputStream outputStream = new FileOutputStream(bookFile);
                outputStream.write(title.getBytes());
                outputStream.close();

                System.out.println("Your favorite book is: " + title);
            }
        } catch (IOException e) {
            System.out.println("File error.");
        }
    }
}


