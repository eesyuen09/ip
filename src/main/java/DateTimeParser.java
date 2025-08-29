import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateTimeParser {
    private static final DateTimeFormatter IN_DATETIME =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    public static LocalDateTime parse(String text) throws PenguinException {
        try {
            return LocalDateTime.parse(text, IN_DATETIME);
        } catch (DateTimeParseException e) {
            throw new PenguinException("Invalid date/time format: " + text
                    + "Expected dd/MM/yyyy HHmm, e.g., 09/09/2025 1800");
        }
    }
}