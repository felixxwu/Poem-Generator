import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * poem generator
 */
public class poemGenerator {

    public static void main(String[] args) {
        boolean poemEnd = false;

        System.out.print("Scanning archive file... ");

        ArrayList<String> lines = readLines("poem archive.txt");

        System.out.println(lines.size() + " lines scanned.");

        ArrayList<String> newPoem = new ArrayList<String>();


        Scanner reader = new Scanner(System.in); // Reading from System.in
        while (poemEnd == false) {

            String randomLine = getRandomLine(lines);
            printPoem(newPoem);
            System.out.println("New Line:");
            System.out.println(randomLine);
            System.out.println();

            System.out.println("1: Add to poem\n2: Next Random Line\n3: Save Poem");
            int option = reader.nextInt(); // Scans the next token of the input as an int.

            if (option == 1) {
                newPoem.add(randomLine);
            }
            if (option == 3) {
                poemEnd = true;
            }
        }
        reader.close();

        writePeomToFile(newPoem);
    }

    public static void writePeomToFile(ArrayList<String> poem) {
        try {
            String fileName = poem.get(0) + " ("
                    + (new java.text.SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new java.util.Date())) + ")";
            FileWriter writer = new FileWriter(fileName + ".txt", true);

            for (int i = 0; i < poem.size(); i++) {
                writer.write(poem.get(i));
                writer.write("\r\n"); // write new line
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printPoem(ArrayList<String> poem) {
        System.out.println("\nMy Poem:\n-----------------------");
        for (String line : poem) {
            System.out.println(line);
        }
        System.out.println("-----------------------");
    }

    public static String getRandomLine(ArrayList<String> lines) {
        Random rand = new Random();
        int lineIndex = rand.nextInt(lines.size());
        return lines.get(lineIndex);
    }

    public static ArrayList<String> readLines(String file) {
        ArrayList<String> lines = new ArrayList<String>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 4) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return lines;
    }
}