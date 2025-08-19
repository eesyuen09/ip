import java.util.ArrayList;
import java.util.Scanner;

public class Penguin {
    static String greetings = "Hello! I'm Penguin!\nWhat can I do for you?";
    static String exit = "Bye. Hope to see you again soon!";
    static String line = "____________________________________________________________________________";
    static String current = "";
    static Scanner sc = new Scanner(System.in);
    static ArrayList<String> list = new ArrayList<>();

    public static void echo(String input) {
        if (input.equals("bye")) {
            System.out.println(line);
            System.out.println(exit);
            System.out.println(line);
        } else {
            System.out.println(line);
            System.out.println(current);
            System.out.println(line);
            current = sc.nextLine();
            echo(current);
        }
    }

    public static void setList(String input) {
        if (input.equals("bye")) {
            System.out.println(line);
            System.out.println(exit);
            System.out.println(line);
        } else if (input.equals("list")) {
            System.out.println(line);
            for (int i = 0; i < list.size(); i++) {
                int num = i + 1;
                System.out.println(num + ". " + list.get(i) + "\n");
            }
            System.out.println(line);
            current = sc.nextLine();
            setList(current);
        } else {
            list.add(input);
            System.out.println(line);
            System.out.println("added: " + input);
            System.out.println(line);
            current = sc.nextLine();
            setList(current);
        }
    }

    public static void main(String[] args) {
        System.out.println(line);
        System.out.println(greetings);
        current = sc.nextLine();
        setList(current);
    }
}
