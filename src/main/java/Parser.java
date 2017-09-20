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


    Path fileExcuses = Paths.get("src\\main\\resources\\excuses.txt"); //файл с предлогами/местоимения/союзы на английском и русском языке
    Scanner in = new Scanner(System.in);

    public void parseLine() throws IOException {
        System.out.print("Введите путь: ");
        String path = in.nextLine(); //получаем путь и затем его просваиваем переменной file
        Path file = Paths.get(path);

        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(file); //сюда закидываем массив с текстом
        ArrayList<String> excuses = (ArrayList<String>) Files.readAllLines(fileExcuses); //здесь хранятся предлоги
        ArrayList<String> wordsList = new ArrayList<>();    //коллекция, в который мы будем разбивать текст
        ArrayList<String> excusesList = new ArrayList<>();  //коллекция, в который мы будем заносить предлоги/местоимения/союзы

        for (String line : lines) { //цикл разбивает нашу коллекцию с текстом на элементы по знакам препинания и заносит обратно в обработанном варианте
            StringTokenizer st = new StringTokenizer(line, " \t\n\r,.");
            while (st.hasMoreTokens()) {
                wordsList.add(st.nextToken());
            }
        }

        for (String line : excuses) {  //аналогичем предыдущему, но сюда закидываем файл с предлогами
            StringTokenizer st = new StringTokenizer(line, " \t\n\r,.");
            while (st.hasMoreTokens()) {
                excusesList.add(st.nextToken());
            }
        }

        /*HashMap<String, Integer> repeats = new HashMap<>();   //мне друг сказал, что вам, возможно, нужны люди, которые знают программирование
        Integer count;                                          //на базовом уровне. Типа нужно сделать разбитие через циклы. Если это потребуется,
        for (String i : wordsList) {                            //то я могу реализовать через регулярные выражения.
            //if(Collections.frequency(excusesList, i) == 1)    //Это моя перва реализация подсчёта повторов слов в тексте
            //continue;                                         //Здесь тоже самое, что и ниже 1 в 1.
            count = repeats.get(i);                             //Мы проходим по коллекции, если итый элемент встречался, т.е. == 1, мы добавляем 1, если нет, устанавливаем
            repeats.put(i, count == null ? 1 : count + 1);      //значение на 1. Также идёт проверка, на присутствие элемента в списке предлогов, если он встречался там, то
        }*/                                                     //цикл идёт на следующую итерацию

        Map<String, Long> repeats = wordsList.parallelStream()  //Только здесь более ОПП. Способ появился относительно недавно, в 8 версии, вроде бы))
                .filter(s -> !excusesList.contains(s))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        repeats.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEach(System.out::println); //сортировка всей коллекции по убыванию
    }
}