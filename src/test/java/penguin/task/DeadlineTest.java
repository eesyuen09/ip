package penguin.task;

import org.junit.jupiter.api.Test;
import penguin.command.DateTimeParser;
import penguin.exception.PenguinException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;

public class DeadlineTest {
    @Test
    public void getByStorage_validDateTime_returnsIsoString() throws PenguinException {
        String deadline = "09/09/2025 1800";
        LocalDateTime dateTime= DateTimeParser.parse(deadline.trim());
        Deadline d = new Deadline("xmas eve", false, dateTime );
        assertEquals("2025-09-09T18:00", d.getByStorage());
    }

    @Test
    public void toString_notDoneTask_returnsFormattedString() throws PenguinException {
        Deadline d = new Deadline("read book", "09/09/2025 1800");
        assertEquals("[D][ ] read book (by: Sep 9 2025, 6:00pm)", d.toString());
    }

    @Test
    public void toString_doneTask_returnsFormattedString() throws PenguinException {
        LocalDateTime dt = LocalDateTime.of(2025, 10, 1, 7, 0);
        Deadline d = new Deadline("finish", true, dt);
        assertEquals("[D][X] finish (by: Oct 1 2025, 7:00am)", d.toString());
    }
}