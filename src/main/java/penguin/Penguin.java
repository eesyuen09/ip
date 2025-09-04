package penguin;

import penguin.command.Command;
import penguin.exception.PenguinException;
import penguin.storage.Storage;
import penguin.task.TaskList;
import penguin.command.Parser;
import penguin.ui.Ui;

/**
 * The Penguin application.
 */
public class Penguin {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private final Parser parser;

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
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command cmd = parser.parse(input);
            String out = cmd.execute(tasks);
            if (tasks.isModified()) {
                storage.save(tasks);
                tasks.resetModification();
            }
            return ui.reply(out);

        } catch (PenguinException e) {
            return(e.getMessage());
        }
    }

}
