import java.util.Scanner;

public class Penguin {
    static String greetings = "Hello! I'm Penguin!\nWhat can I do for you?";
    static String exit = "Bye. Hope to see you again soon!";
    static String line = "____________________________________________________________________________";
    static String input = "";
    static Scanner sc = new Scanner(System.in);

    public static void echo(String string) {
        if (string.equals("bye")) {
            System.out.println(line);
            System.out.println(exit);
            System.out.println(line);
        } else {
            System.out.println(line);
            System.out.println(input);
            System.out.println(line);
            input = sc.nextLine();
            echo(input);
        }
    }

    public static void main(String[] args) {
        System.out.println(line);
        System.out.println(greetings);
        input = sc.nextLine();
        echo(input);
    }
}
