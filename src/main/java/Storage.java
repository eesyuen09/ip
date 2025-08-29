import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Storage {
    private final TaskList tasks;
    private final TaskCode code = new TaskCode();
    private final Path DATA_FILE = Paths.get("data", "penguin.txt");

    public Storage (TaskList tasks) {
        this.tasks = tasks;
    }

    public void save (TaskList tasks) throws PenguinException {
        try {
            Files.createDirectories(DATA_FILE.getParent());
            if (!Files.exists(DATA_FILE)) {
                Files.createFile(DATA_FILE);
            }
            FileWriter file  = new FileWriter(DATA_FILE.toFile());
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                text.append(code.encode(task)).append("\n");
            }
            file.write(text.toString());
            file.close();
        }  catch (IOException e) {
            throw new PenguinException("Something went wrong: " + e.getMessage());
        }
    }

    public TaskList load () throws PenguinException {
        TaskList tasks = new TaskList();
        try {
            File file = new File("data/penguin.txt");
            if (!file.exists()) {
                return tasks;
            } else {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    Task actualTask = code.decode(line);
                    tasks.add(actualTask);
                }
                sc.close();
                return tasks;
            }
        } catch (IOException e) {
            throw new PenguinException("Failed to load: " + e.getMessage());
        }
    }
}
