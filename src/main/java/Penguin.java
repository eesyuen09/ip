import java.util.ArrayList;
import java.util.Scanner;

public class Penguin {
    static final String greetings = "Hello! I'm Penguin!\nWhat can I do for you?";
    static final String exit = "Bye. Hope to see you again soon!";
    static final String line = "____________________________________________________________________________";
    static Scanner sc = new Scanner(System.in);
    static ArrayList<String> list = new ArrayList<>();

    public static void reply(String msg) {
        System.out.println(line);
        System.out.println(msg);
        System.out.println(line);
    }

    public static void main(String[] args) {
        TaskList taskList = new TaskList();
        Parser parser = new Parser();
        reply(greetings);
        while (true) {
            String input = sc.nextLine();
            Command cmd = parser.parse(input);
            String out = cmd.execute(taskList);
            reply(out);
            if (cmd.intent() == Intent.BYE) break;
        }
    }
}
