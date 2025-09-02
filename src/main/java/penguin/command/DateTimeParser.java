package penguin.command;

import penguin.exception.PenguinException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing user-provided date & time strings into a LocalDateTime object.
 */
public final class DateTimeParser {
    private static final DateTimeFormatter IN_DATETIME =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    /**
     * Parses the user-provided date & time strings into a LocalDateTime object.
     *
     * @param text Input of date & time, expected format: dd/MM/yyy
     * @return a LocalDateTime representing the parsed date & time.
     * @throws PenguinException if the input is not in the expected format.
     */
    public static LocalDateTime parse(String text) throws PenguinException {
        try {
            return LocalDateTime.parse(text, IN_DATETIME);
        } catch (DateTimeParseException e) {
            throw new PenguinException("Invalid date/time format: " + text
                    + "Expected dd/MM/yyyy HHmm, e.g., 09/09/2025 1800");
        }
    }
}