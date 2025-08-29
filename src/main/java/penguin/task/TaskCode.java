package penguin.task;

import penguin.exception.PenguinException;
import java.time.LocalDateTime;

public class TaskCode {
    private static final String SEP = " | ";

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

    public static Task decode(String line) throws PenguinException {
        if (line == null) return null;
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#")) return null;

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

    private static boolean parseDone(String s) {
        if ("0".equals(s)) return false;
        if ("1".equals(s)) return true;
        throw new IllegalArgumentException("done must be 0 or 1");
    }


}
