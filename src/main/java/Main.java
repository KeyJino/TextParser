import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) throws IOException {
        System.out.print("Выберите задание(1 или 2):" );
        Scanner in = new Scanner(System.in);
        Scanner it = new Scanner(System.in);
        int taskNum = in.nextInt(); //выбираем номер задания

        switch (taskNum){
            case 1: //анализатор текста
                Parser pr = new Parser();
                pr.parseLine();
                break;



            case 2: //анализатор скобок
                BracketsValidator bv = new BracketsValidator();

                System.out.print("Введите путь: ");
                String path = it.nextLine();
                Path file = Paths.get(path);

                String st = String.valueOf(Files.readAllLines(file));
                boolean correct = bv.validate(st);
                System.out.println(correct ? "correct" : "incorrect");

                break;



            default:
                System.out.println("error");
        }


    }
}
