import java.util.Scanner;

public class Penguin {
    static final String greetings = "Hello! I'm Penguin!\nWhat can I do for you?";
    static final String line = "____________________________________________________________________________";
    static Scanner sc = new Scanner(System.in);

    public static void reply(String msg) {
        System.out.println(line);
        System.out.println(msg);
        System.out.println(line);
    }

    public static void main(String[] args) throws PenguinException {
        Parser parser = new Parser();
        TaskList taskList = new TaskList();
        Storage storage = new Storage(taskList);
        reply(greetings);
        taskList = storage.load();
        while (true) {
            String input = sc.nextLine();
            try {
                Command cmd = parser.parse(input);
                String out = cmd.execute(taskList);
                if (taskList.isModified()) {
                    storage.save(taskList);
                    taskList.resetModification();
                }
                reply(out);
                if (cmd.intent() == Intent.BYE) break;
            } catch (PenguinException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
