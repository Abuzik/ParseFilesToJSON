import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {

    public static ArrayList<String> listFilesUsingFileWalk(String dir, int depth) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(dir), depth)) {
            return new ArrayList<>(stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList()));
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = listFilesUsingFileWalk("path", 1);
        System.out.println(list.toString());
        list.forEach(item -> {
            File file = new File(String.format("path/%s", item));
            try {
                Scanner scanner = new Scanner(file);
                StringBuilder startedString = new StringBuilder("{");
                while (scanner.hasNextLine()) {
                    String row = scanner.nextLine();
                    if (row.contains(":")) {
                        String[] subItems = row.toLowerCase(Locale.ROOT).split(":");
                        if (!scanner.hasNextLine()) {
                            startedString.append(String.format(" \"%s\": \"%s\" ", subItems[0], subItems[1]));
                        } else {
                            startedString.append(String.format(" \"%s\": \"%s\", ", subItems[0], subItems[1]));
                        }
                    }
                }
                startedString.append("}");
                if (item.contains("_")) {
                    String path = String.format("path/%s.json", item.split("_")[0]);
                    System.out.println(path);
                    File myObj = new File(path);
                    myObj.createNewFile();
                    try {
                        FileWriter myWriter = new FileWriter(path);
                        myWriter.write(String.valueOf(startedString));
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
