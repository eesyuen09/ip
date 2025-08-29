package penguin.task;

import penguin.exception.PenguinException;
import penguin.command.DateTimeParser;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

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

    public Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to) throws PenguinException {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    public String getFromDisplay() {
        return from.format(OUT_DATETIME);
    }

    public String getFromStorage() {
        return from.toString();
    }

    public String getToDisplay() {
        return to.format(OUT_DATETIME);
    }

    public String getToStorage() {
        return to.toString();
    }


    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + getFromDisplay() + " to: " + getToDisplay() + ")";
    }
}


