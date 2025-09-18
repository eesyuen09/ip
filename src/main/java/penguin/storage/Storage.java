package penguin.storage;

import penguin.exception.PenguinException;
import penguin.task.TaskList;
import penguin.task.Task;
import penguin.task.TaskCode;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Handles loading and storing task list to storage.
 */
public class Storage {
    private final TaskList tasks;
    private final TaskCode code = new TaskCode();
    private static final Path DATA_FILE = Paths.get("data", "penguin.txt");

    public Storage (TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Saves a given tasklist to a storage file.
     *
     * @param tasks Tasklist to be stored.
     * @throws PenguinException if an I/O error occurs while storing the file.
     */
    public void save (TaskList tasks) throws PenguinException {
        try {
            Files.createDirectories(DATA_FILE.getParent());
            if (!Files.exists(DATA_FILE)) {
                Files.createFile(DATA_FILE);
            }
            FileWriter file  = new FileWriter(DATA_FILE.toFile());
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < tasks.getSize(); i++) {
                Task task = tasks.get(i);
                text.append(code.encode(task)).append("\n");
            }
            file.write(text.toString());
            file.close();
        }  catch (IOException e) {
            throw new PenguinException("Something went wrong: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return TaskList stored in storage or empty TaskList if no file exists.
     * @throws PenguinException if an I/O error occurs while storing the file.
     */
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
