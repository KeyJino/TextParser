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

public class Parser{


    private Path fileExcuses = Paths.get("src\\main\\resources\\excuses.txt"); //файл с предлогами/местоимения/союзы на английском и русском языке
    private Scanner in = new Scanner(System.in);
    private ParsingLines pl = new ParsingLines();

    private ArrayList<String> lines;
    private ArrayList<String> excuses;
    private ArrayList<String> wordsList;
    private ArrayList<String> excusesList;

    public void parseLine() throws IOException {
        System.out.print("Введите путь: ");
        String path = in.nextLine(); //получаем путь и затем его просваиваем переменной file
        Path file = Paths.get(path);

        lines = (ArrayList<String>) Files.readAllLines(file); //сюда закидываем массив с текстом
        excuses = (ArrayList<String>) Files.readAllLines(fileExcuses); //здесь хранятся предлоги
        wordsList = new ArrayList<>(pl.pars(lines));    //коллекция, в который мы будем разбивать текст
        excusesList = new ArrayList<>(pl.pars(excuses));  //коллекция, в который мы будем заносить предлоги/местоимения/союзы

        /*HashMap<String, Integer> repeats = new HashMap<>();   //мне друг сказал, что вам, возможно, нужны люди, которые знают программирование
        Integer count;                                          //на базовом уровне. Типа нужно сделать разбитие через циклы. Если это потребуется,
        for (String i : wordsList) {                            //то я могу реализовать через регулярные выражения.
            if(Collections.frequency(excusesList, i) == 1)    //Это моя перва реализация подсчёта повторов слов в тексте
            continue;                                         //Здесь тоже самое, что и ниже 1 в 1.
            count = repeats.get(i);                             //Мы проходим по коллекции, если итый элемент встречался, т.е. == 1, мы добавляем 1, если нет, устанавливаем
            repeats.put(i, count == null ? 1 : count + 1);      //значение на 1. Также идёт проверка, на присутствие элемента в списке предлогов, если он встречался там, то
        }*/                                                     //цикл идёт на следующую итерацию

        Map<String, Long> repeats = wordsList.parallelStream()  //Только здесь более ОПП. Способ появился относительно недавно, в 8 версии, вроде бы))
                .filter(s -> !excusesList.contains(s))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        repeats.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEach(System.out::println); //сортировка всей коллекции по убыванию
    }
}