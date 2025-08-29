import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Deadline extends Task {
    private LocalDateTime by;

    private static final DateTimeFormatter OUT_DATETIME =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    public Deadline(String description, String byText) throws PenguinException {
        super(description);
        this.by = DateTimeParser.parse(byText.trim());
    }

    public Deadline(String description, boolean done, LocalDateTime by) throws PenguinException {
        super(description, done);
        this.by = by;
    }

    public String getByDisplay() {
        return by.format(OUT_DATETIME);
    }

    public String getByStorage() {
        return by.toString();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getByDisplay() + ")";
    }

}

