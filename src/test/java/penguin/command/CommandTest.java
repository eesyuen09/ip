package penguin.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import penguin.exception.PenguinException;
import penguin.task.TaskList;
import penguin.task.Todo;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ===============================================================
 * A-AiAssisted
 * ===============================================================
 * The following test cases were generated with the assistance of
 * ChatGPT (OpenAI). I provided the Command.java class and asked
 * for unit tests following the CS2103/T Testing guidelines.
 *
 * ChatGPT helped by:
 * - Suggesting a systematic breakdown of test cases per handler.
 * - Ensuring coverage of normal, boundary, and exception cases.
 * - Using JUnit 5 annotations and idiomatic assertThrows usage.
 *
 * I reviewed, adapted, and validated the code:
 * - Adjusted assertions (e.g., assertTrue vs assertEquals).
 * - Modified test data to fit my own TaskList implementation.
 */
public class CommandTest {

    private TaskList tasks;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        tasks.add(new Todo("study")); // index 0
    }

    @Test
    void handleList_nonEmpty_success() {
        Command cmd = new Command(Action.LIST, "");
        String result = cmd.handleList(tasks);
        assertTrue(result.contains("study"));
    }

    @Test
    void handleList_empty_success() {
        tasks = new TaskList();
        Command cmd = new Command(Action.LIST, "");
        assertEquals("Yay! There are no tasks in your list!", cmd.handleList(tasks));
    }

    // ==== DELETE ====
    @Test
    void handleDelete_validIndex_success() throws PenguinException {
        Command cmd = new Command(Action.DELETE, "1");
        String reply = cmd.handleDelete(tasks);
        assertTrue(reply.contains("removed this task"));
    }

    @Test
    void handleDelete_missingArg_exception() {
        Command cmd = new Command(Action.DELETE, "");
        assertThrows(PenguinException.class, () -> cmd.handleDelete(tasks));
    }

    @Test
    void handleDelete_invalidIndex_exception() {
        Command cmd = new Command(Action.DELETE, "99");
        assertThrows(PenguinException.class, () -> cmd.handleDelete(tasks));
    }

    // ==== MARK ====
    @Test
    void handleMark_success() throws PenguinException {
        Command cmd = new Command(Action.MARK, "1");
        String reply = cmd.handleMark(tasks);
        assertTrue(reply.contains("marked this task as done"));
    }

    @Test
    void handleMark_missingArg_exception() {
        Command cmd = new Command(Action.MARK, "");
        assertThrows(PenguinException.class, () -> cmd.handleMark(tasks));
    }

    // ==== UNMARK ====
    @Test
    void handleUnmark_success() throws PenguinException {
        tasks.get(0).mark();
        Command cmd = new Command(Action.UNMARK, "1");
        String reply = cmd.handleUnmark(tasks);
        assertTrue(reply.contains("unmarked this task"));
    }

    // ==== TODO ====
    @Test
    void handleTodo_success() throws PenguinException {
        Command cmd = new Command(Action.TODO, "read");
        String reply = cmd.handleTodo(tasks);
        assertTrue(reply.contains("added this task"));
    }

    @Test
    void handleTodo_missingArg_exception() {
        Command cmd = new Command(Action.TODO, "");
        assertThrows(PenguinException.class, () -> cmd.handleTodo(tasks));
    }

    // ==== DEADLINE ====
    @Test
    void handleDeadline_success() throws PenguinException {
        Command cmd = new Command(Action.DEADLINE, "submit by 12/12/2025 1800");
        String reply = cmd.handleDeadline(tasks);
        assertTrue(reply.contains("added this task"));
    }

    @Test
    void handleDeadline_missingArg_exception() {
        Command cmd = new Command(Action.DEADLINE, "");
        assertThrows(PenguinException.class, () -> cmd.handleDeadline(tasks));
    }

    // ==== EVENT ====
    @Test
    void handleEvent_success() throws PenguinException {
        Command cmd = new Command(Action.EVENT, "party from 12/12/2025 1800 to 12/12/2025 2000");
        String reply = cmd.handleEvent(tasks);
        assertTrue(reply.contains("added this task"));
    }

    @Test
    void handleEvent_invalidArgs_exception() {
        Command cmd = new Command(Action.EVENT, "party to 2025-12-12");
        assertThrows(PenguinException.class, () -> cmd.handleEvent(tasks));
    }

    // ==== FIND ====
    @Test
    void handleFind_found() throws PenguinException {
        Command cmd = new Command(Action.FIND, "study");
        String reply = cmd.handleFind(tasks);
        assertTrue(reply.contains("study"));
    }

    @Test
    void handleFind_notFound() throws PenguinException {
        Command cmd = new Command(Action.FIND, "xyz");
        String reply = cmd.handleFind(tasks);
        assertTrue(reply.contains("no matching results"));
    }

    // ==== SCHEDULE ====
    @Test
    void handleSchedule_upcoming_success() throws PenguinException {
        Command cmd = new Command(Action.SCHEDULE, "");
        String reply = cmd.handleSchedule(tasks);
        assertTrue(reply.contains("schedules"));
    }

    @Test
    void handleSchedule_invalidDate_exception() {
        Command cmd = new Command(Action.SCHEDULE, "invalid-date");
        assertThrows(PenguinException.class, () -> cmd.handleSchedule(tasks));
    }

    // ==== BYE ====
    @Test
    void handleBye_success() {
        Command cmd = new Command(Action.BYE, "");
        assertEquals("Byebye! Hope to see you again!", cmd.handleBye(tasks));
    }
}