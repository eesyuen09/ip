package penguin.task;

import penguin.exception.PenguinException;
import penguin.command.DateTimeParser;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Represents an event task with a start and end LocalDateTime.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;
    private static final DateTimeFormatter OUT_DATETIME =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    public Event(String description, String from, String to) throws PenguinException {
        super(description);
        this.from = DateTimeParser.parse(from);
        this.to = DateTimeParser.parse(to);
    }

    public Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time formatted for display.
     *
     * @return A string in the format {@code "MMM d yyyy, h:mma"}.
     */
    public String getFromDisplay() {
        return from.format(OUT_DATETIME);
    }

    /**
     * Returns the start time in ISO-8601 storage format.
     *
     * @return A string such as {@code "2025-09-09T18:00"}.
     */
    public String getFromStorage() {
        return from.toString();
    }

    /**
     * Returns the end time formatted for display.
     *
     * @return A string in the format {@code "MMM d yyyy, h:mma"}.
     */
    public String getToDisplay() {
        return to.format(OUT_DATETIME);
    }

    /**
     * Returns the end time in ISO-8601 storage format.
     *
     * @return A string such as {@code "2025-09-09T20:00"}.
     */
    public String getToStorage() {
        return to.toString();
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns a string representation of this event, including its description,
     * completion status, and start/end times.
     *
     * @return String representation of this event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + getFromDisplay() + " to: " + getToDisplay() + ")";
    }
}


