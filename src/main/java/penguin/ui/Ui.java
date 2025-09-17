package penguin.ui;

import java.util.Scanner;

public class Ui {
    static final String greetings = "Hello! I'm Penguin!\nWhat can I do for you?";
    static final String line = "____________________________________________________________________________";
    static Scanner sc = new Scanner(System.in);

    public static String reply(String msg) {
        return msg;
    }

    public static String greet() {
        return reply(greetings);
    }

    public static String readCommand() {
        return sc.nextLine();
    }

}
