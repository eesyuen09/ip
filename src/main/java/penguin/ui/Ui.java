package penguin.ui;

import java.util.Scanner;

public class Ui {
    static final String greetings = "Hello! I'm Penguin!\nWhat can I do for you?";

    public static String reply(String msg) {
        return msg;
    }

    public static String greet() {
        return reply(greetings);
    }


}
