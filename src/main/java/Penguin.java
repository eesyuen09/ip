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

    public void run() {
        ui.greet();
        while (true) {
            String input = ui.readCommand();
            try {
                Command cmd = parser.parse(input);
                String out = cmd.execute(tasks);
                if (tasks.isModified()) {
                    storage.save(tasks);
                    tasks.resetModification();
                }
                ui.reply(out);
                if (cmd.intent() == Intent.BYE) break;
            } catch (PenguinException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Penguin().run();
    }
}
