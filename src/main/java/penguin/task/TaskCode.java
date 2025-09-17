package penguin.task;

import penguin.exception.PenguinException;
import java.time.LocalDateTime;


/**
 * Provides encoding and decoding of {@link Task} objects to and from
 * their string representation for persistent storage.
 */
public class TaskCode {
    private static final String SEP = " | ";
    private static final int TODO_FIELDS = 3;
    private static final int DEADLINE_FIELDS = 4;
    private static final int EVENT_FIELDS = 5;

    /**
     * Encodes a Task into a storage string.
     *
     * @param task The task to encode.
     * @return Encoded string representation of the task.
     * @throws IllegalArgumentException If the task type is not recognized.
     */
    public static String encode(Task task) {
        String done = task.isDone() ? "1" : "0";
        String desc = task.getDescription();

        if (task instanceof Todo) {
            return "T" + SEP + done + SEP + desc;
        } else if (task instanceof Deadline d) {
            return "D" + SEP + done + SEP + desc + SEP + d.getByStorage();     // by as String
        } else if (task instanceof Event e) {
            return "E" + SEP + done + SEP + desc + SEP + e.getFromStorage() + SEP + e.getToStorage();
        }
        throw new IllegalArgumentException("Unknown task type: " + task.getClass());
    }

    /**
     * Decodes a line of text into a Task.
     * Ignores null lines and empty lines. Split the line by "|" and reconstructs the
     * appropriate Todo, Deadline or Event.
     *
     * @param line A storage line representing a task.
     * @return A corresponding Task or null if the line is empty or a comment.
     * @throws PenguinException If the line is malformed, the task type is unknown,
     *                          or the field count is incorrect.
     * @throws IllegalArgumentException If the "done" flag is not 0 or 1.
     */
    public static Task decode(String line) throws PenguinException {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] fields = line.split(" \\| ");
        if (fields.length < TODO_FIELDS) {
            throw new PenguinException("Wrong format: " + line);
        }

        String type = fields[0];
        boolean isDone = parseDone(fields[1]);
        String desc = fields[2];

        switch (type) {
            case "T":
                if (fields.length != TODO_FIELDS) throw new PenguinException("Todo task needs 3 fields: " + line);
                return new Todo(desc, isDone);

            case "D":
                if (fields.length != DEADLINE_FIELDS) throw new PenguinException("Deadline task needs 4 fields: " + line);
                LocalDateTime by = LocalDateTime.parse(fields[3]);
                return new Deadline(desc, isDone, by);

            case "E":
                if (fields.length != EVENT_FIELDS) throw new PenguinException("Event task needs 5 fields: " + line);
                LocalDateTime from = LocalDateTime.parse(fields[3]);
                LocalDateTime to = LocalDateTime.parse(fields[4]);
                return new Event(desc, isDone, from, to);

            default:
                throw new PenguinException("Unknown task type: " + type);
        }
    }

    /**
     * Parses the done flag from storage.
     *
     * @param s The string flag ("0" or "1").
     * @return true if the flag is "1", false if "0".
     * @throws IllegalArgumentException If the flag is not "0" or "1".
     */
    private static boolean parseDone(String s) {
        if ("0".equals(s)) return false;
        if ("1".equals(s)) return true;
        throw new IllegalArgumentException("done must be 0 or 1");
    }


}
