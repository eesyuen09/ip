package penguin.task;

import penguin.exception.PenguinException;
import java.time.LocalDateTime;


/**
 * Provides encoding and decoding of {@link Task} objects to and from
 * their string representation for persistent storage.
 */
public class TaskCode {
    private static final String SEP = " | ";

    /**
     * Encodes a Task into a storage string.
     *
     * @param t The task to encode.
     * @return Encoded string representation of the task.
     * @throws IllegalArgumentException If the task type is not recognized.
     */
    public static String encode(Task t) {
        String done = t.isDone() ? "1" : "0";
        String desc = t.getDescription();

        if (t instanceof Todo) {
            return "T" + SEP + done + SEP + desc;
        } else if (t instanceof Deadline d) {
            return "D" + SEP + done + SEP + desc + SEP + d.getByStorage();     // by as String
        } else if (t instanceof Event e) {
            return "E" + SEP + done + SEP + desc + SEP + e.getFromStorage() + SEP + e.getToStorage();
        }
        throw new IllegalArgumentException("Unknown task type: " + t.getClass());
    }

    /**
     * Decodes a line of text into a Task.
     * Ignores null lines and empty lines."|" and reconstructs the
     * appropriate Todo, Deadline or Event.
     *
     * @param line A storage line representing a task.
     * @return A corresponding Task or null if the line is empty or a comment.
     * @throws PenguinException If the line is malformed, the task type is unknown,
     *                          or the field count is incorrect.
     * @throws IllegalArgumentException If the "done" flag is not 0 or 1.
     */
    public static Task decode(String line) throws PenguinException {
        if (line == null) return null;
        line = line.trim();
        if (line.isEmpty()) return null;

        String[] p = line.split(" \\| ");
        if (p.length < 3) {
            throw new PenguinException("Wrong format: " + line);
        }

        String type = p[0];
        boolean done = parseDone(p[1]);
        String desc = p[2];

        switch (type) {
            case "T":
                if (p.length != 3) throw new PenguinException("Todo task needs 3 fields: " + line);
                return new Todo(desc, done);

            case "D":
                if (p.length != 4) throw new PenguinException("Deadline task needs 4 fields: " + line);
                LocalDateTime by = LocalDateTime.parse(p[3]);
                return new Deadline(desc, done, by);

            case "E":
                if (p.length != 5) throw new PenguinException("Event task needs 5 fields: " + line);
                LocalDateTime from = LocalDateTime.parse(p[3]);
                LocalDateTime to = LocalDateTime.parse(p[4]);
                return new Event(desc, done, from, to);

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
