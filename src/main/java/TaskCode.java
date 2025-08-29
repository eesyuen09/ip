public class TaskCode {
    private static Task task;
    private static TaskList taskList;
    private static final String SEP = " | ";

    public static String encode(Task t) {
        String done = t.isDone() ? "1" : "0";
        String desc = t.getDescription();

        if (t instanceof Todo) {
            return "T" + SEP + done + SEP + desc;
        } else if (t instanceof Deadline d) {
            return "D" + SEP + done + SEP + desc + SEP + d.getBy();     // by as String
        } else if (t instanceof Event e) {
            return "E" + SEP + done + SEP + desc + SEP + e.getFrom() + SEP + e.getTo();
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
                return new Deadline(desc, done, p[3]); // by as String

            case "E":
                if (p.length != 5) throw new PenguinException("Event task needs 5 fields: " + line);
                return new Event(desc, done, p[3], p[4]); // from, to as String

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
