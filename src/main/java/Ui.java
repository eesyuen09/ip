import java.util.Scanner;

public class Ui {
    static final String greetings = "Hello! I'm Penguin!\nWhat can I do for you?";
    static final String line = "____________________________________________________________________________";
    static Scanner sc = new Scanner(System.in);

    public static void reply(String msg) {
        System.out.println(line);
        System.out.println(msg);
        System.out.println(line);
    }

    public static void greet() {
        System.out.println(line);
        System.out.println(greetings);
        System.out.println(line);
    }

    public static String readCommand() {
        return sc.nextLine();
    }



}
