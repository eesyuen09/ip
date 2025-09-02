package penguin;

import penguin.command.Command;
import penguin.exception.PenguinException;
import penguin.storage.Storage;
import penguin.task.TaskList;
import penguin.command.Parser;
import penguin.ui.Ui;

/**
 * The main entry point the Penguin application.
 */
public class Penguin {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private final Parser parser;
    private final String BYE = "Bye. Hope to see you again soon!";

    public Penguin() {
        ui = new Ui();
        tasks = new TaskList();
        storage = new Storage(tasks);
        parser = new Parser();

        try {
            tasks = storage.load();
        } catch (PenguinException e) {
            tasks = new TaskList();
        }

    }

    /**
     * Runs the main interaction of Penguin chatbot.
     * It first greets the user, then continuously reads user input,
     * parses it into command, execute the command
     * and replies with the result. If tasks are modified,
     * it will save the current task list to storage.
     * The chatbot can be terminated by running bye.
     */
    public void run() {
        ui.greet();
        boolean isExit = false;
        while (!isExit) {
            String input = ui.readCommand();
            try {
                Command cmd = parser.parse(input);
                String out = cmd.execute(tasks);
                if (tasks.isModified()) {
                    storage.save(tasks);
                    tasks.resetModification();
                }
                if (out == BYE) {
                    ui.reply(BYE);
                    isExit = true;
                } else {
                    ui.reply(out);
                }
            } catch (PenguinException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Penguin().run();
    }
}
