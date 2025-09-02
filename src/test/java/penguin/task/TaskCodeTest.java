package penguin.task;

import org.junit.jupiter.api.Test;
import penguin.exception.PenguinException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TaskCodeTest {
    @Test
    public void encode_todo_returnsCorrectStorageLine() {
        Todo t = new Todo("read book", false);
        assertEquals("T | 0 | read book", TaskCode.encode(t));
    }

    @Test
    public void encode_deadline_returnsCorrectStorageLine() throws PenguinException {
        LocalDateTime by = LocalDateTime.of(2025, 9, 7, 18, 0);
        Deadline d = new Deadline("submit", true, by);
        assertEquals("D | 1 | submit | 2025-09-07T18:00", TaskCode.encode(d));
    }

    @Test
    public void encode_event_returnsCorrectStorageLine() throws PenguinException {
        LocalDateTime from = LocalDateTime.of(2025, 10, 1, 9, 0);
        LocalDateTime to   = LocalDateTime.of(2025, 10, 1, 11, 30);
        Event e = new Event("standup", false, from, to);
        assertEquals("E | 0 | standup | 2025-10-01T09:00 | 2025-10-01T11:30", TaskCode.encode(e));
    }

    @Test
    public void decode_todoValidLine_returnsTodoWithFields() throws PenguinException {
        Task t = TaskCode.decode("T | 1 | wash dishes");
        assertTrue(t instanceof Todo);
        assertTrue(t.isDone());
        assertEquals("wash dishes", t.getDescription());
    }

    @Test
    public void decode_deadlineValidLine_returnsDeadlineWithFields() throws PenguinException {
        Task t = TaskCode.decode("D | 0 | submit | 2025-12-25T23:59");
        assertTrue(t instanceof Deadline);
        Deadline d = (Deadline) t;
        assertFalse(d.isDone());
        assertEquals("submit", d.getDescription());
        assertEquals("2025-12-25T23:59", d.getByStorage());
    }

    @Test
   public void decode_eventValidLine_returnsEventWithFields() throws PenguinException {
        Task t = TaskCode.decode("E | 1 | meeting | 2025-10-01T09:00 | 2025-10-01T10:00");
        assertTrue(t instanceof Event);
        Event e = (Event) t;
        assertTrue(e.isDone());
        assertEquals("meeting", e.getDescription());
        assertEquals("2025-10-01T09:00", e.getFromStorage());
        assertEquals("2025-10-01T10:00", e.getToStorage());
    }
    @Test
    public void decode_wrongFieldCountTodo_throwsPenguinException() {
        // Todo should have exactly 3 fields
        assertThrows(PenguinException.class, () -> TaskCode.decode("T | 0 | one | extra"));
    }

    @Test
    public void decode_wrongFieldCountDeadline_throwsPenguinException() {
        // Deadline should have exactly 4 fields
        assertThrows(PenguinException.class, () -> TaskCode.decode("D | 0 | submit"));
    }

    @Test
    public void decode_wrongFieldCountEvent_throwsPenguinException() {
        // Event should have 5 fields
        assertThrows(PenguinException.class, () -> TaskCode.decode("E | 0 | meet | 2025-10-01T09:00"));
    }
}
