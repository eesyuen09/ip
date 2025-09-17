package penguin.task;

import penguin.exception.PenguinException;
import penguin.command.DateTimeParser;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Represents a task with a deadline(date & time).
 */
public class Deadline extends Task {
    private LocalDateTime by;

    private static final DateTimeFormatter OUT_DATETIME =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    public Deadline(String description, String byText) throws PenguinException {
        super(description);
        this.by = DateTimeParser.parse(byText.trim());
    }

    public Deadline(String description, boolean done, LocalDateTime by) {
        super(description, done);
        this.by = by;
    }

    /**
     * Returns the deadline with the correct output format.
     *
     * @return a String with the deadline of the correct format.
     */
    public String getByDisplay() {
        return by.format(OUT_DATETIME);
    }

    /**
     * Returns the deadline with the correct storage format.
     *
     * @return a String with the deadline of the storage format.
     */
    public String getByStorage() {
        return by.toString();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getByDisplay() + ")";
    }

}

