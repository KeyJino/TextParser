import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Parser {


    Path fileExcuses = Paths.get("src\\main\\resources\\excuses.txt");
    Scanner in = new Scanner(System.in);

    public void parseLine() throws IOException {
        System.out.print("Введите путь: ");
        String path = in.nextLine();
        Path file = Paths.get(path);

        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(file);
        ArrayList<String> excuses = (ArrayList<String>) Files.readAllLines(fileExcuses);
        ArrayList<String> wordsList = new ArrayList<>();
        ArrayList<String> excusesList = new ArrayList<>();

        for (String line : lines) {
            StringTokenizer st = new StringTokenizer(line, " \t\n\r,.");
            while (st.hasMoreTokens()) {
                wordsList.add(st.nextToken());
            }
        }

        for (String line : excuses) {
            StringTokenizer st = new StringTokenizer(line, " \t\n\r,.");
            while (st.hasMoreTokens()) {
                excusesList.add(st.nextToken());
            }
        }

        Map<String, Long> repeats = wordsList.parallelStream()
                .filter(s -> !excusesList.contains(s))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        repeats.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEach(System.out::println);
    }
}