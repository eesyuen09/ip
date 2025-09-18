package penguin.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ===============================================================
 * A-AiAssisted
 * ===============================================================
 * The following test cases were generated with the assistance of
 * ChatGPT (OpenAI). I provided the Parser.java class and asked
 * for unit tests following the CS2103/T Testing guidelines.
 *
 * ChatGPT helped by:
 * - Identifying test cases for each keyword branch in parse().
 * - Including normal, boundary, and unknown cases.
 * - Using JUnit 5 idioms (assertEquals, descriptive test names).
 *
 * I am responsible for:
 * - Reviewing and adapting these test cases.
 * - Ensuring the expected Command objects match my implementation.
 */
public class ParserTest {

    private final Parser parser = new Parser();

    @Test
    void parse_byeCommand_returnsByeAction() {
        Command c = parser.parse("bye");
        assertEquals(Action.BYE, c.getAction());
        assertEquals("", c.args());
    }

    @Test
    void parse_listCommand_returnsListAction() {
        Command c = parser.parse("list");
        assertEquals(Action.LIST, c.getAction());
        assertEquals("", c.args());
    }

    @Test
    void parse_markCommand_withArgument_returnsMarkAction() {
        Command c = parser.parse("mark 2");
        assertEquals(Action.MARK, c.getAction());
        assertEquals("2", c.args());
    }

    @Test
    void parse_markCommand_withoutArgument_returnsMarkActionEmptyArgs() {
        Command c = parser.parse("mark");
        assertEquals(Action.MARK, c.getAction());
        assertEquals("", c.args());
    }

    @Test
    void parse_unmarkCommand_withArgument_returnsUnmarkAction() {
        Command c = parser.parse("unmark 3");
        assertEquals(Action.UNMARK, c.getAction());
        assertEquals("3", c.args());
    }

    @Test
    void parse_todoCommand_returnsTodoAction() {
        Command c = parser.parse("todo read book");
        assertEquals(Action.TODO, c.getAction());
        assertEquals("read book", c.args());
    }

    @Test
    void parse_deadlineCommand_returnsDeadlineAction() {
        Command c = parser.parse("deadline submit report by 2025-12-01");
        assertEquals(Action.DEADLINE, c.getAction());
        assertEquals("submit report by 2025-12-01", c.args());
    }

    @Test
    void parse_eventCommand_returnsEventAction() {
        Command c = parser.parse("event project meeting from 2pm to 4pm");
        assertEquals(Action.EVENT, c.getAction());
        assertEquals("project meeting from 2pm to 4pm", c.args());
    }

    @Test
    void parse_deleteCommand_returnsDeleteAction() {
        Command c = parser.parse("delete 5");
        assertEquals(Action.DELETE, c.getAction());
        assertEquals("5", c.args());
    }

    @Test
    void parse_findCommand_returnsFindAction() {
        Command c = parser.parse("find homework");
        assertEquals(Action.FIND, c.getAction());
        assertEquals("homework", c.args());
    }

    @Test
    void parse_scheduleCommand_returnsScheduleAction() {
        Command c = parser.parse("schedule 2025-10-10");
        assertEquals(Action.SCHEDULE, c.getAction());
        assertEquals("2025-10-10", c.args());
    }

    @Test
    void parse_unknownCommand_returnsUnknownAction() {
        Command c = parser.parse("foobar something");
        assertEquals(Action.UNKNOWN, c.getAction());
        assertEquals("foobar something", c.args());
    }

    @Test
    void parse_commandWithExtraSpaces_trimsInput() {
        Command c = parser.parse("   todo   buy milk  ");
        assertEquals(Action.TODO, c.getAction());
        assertEquals("buy milk", c.args());
    }
}