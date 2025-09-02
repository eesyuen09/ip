package penguin.command;

/**
 * Parses the user input into command.
 */
public class Parser {
    /**
     * Parses the user input.
     *
     * @param input
     * @return Command
     */
    public Command parse(String input) {
        String s = input.trim();
        if (s.equals("bye")) {
            return new Command(Action.BYE, "");
        } else if (s.equals("list")) {
            return new Command(Action.LIST, "");
        } else if (s.startsWith("mark")) {
            return new Command(Action.MARK, s.length() > 4 ? s.substring(5).trim() : "".trim());
        } else if (s.startsWith("unmark")) {
            return new Command(Action.UNMARK, s.length() > 6 ? s.substring(7).trim() : "");
        } else if (s.startsWith("todo")) {
            return new Command(Action.TODO, s.length() > 4 ? s.substring(5).trim() : "".trim());
        } else if (s.startsWith("deadline")) {
            return new Command(Action.DEADLINE, s.length() > 8 ? s.substring(9).trim() : "".trim());
        } else if (s.startsWith("event")) {
            return new Command(Action.EVENT, s.length() > 5 ? s.substring(6).trim() : "".trim());
        } else if (s.startsWith("delete")) {
            return new Command(Action.DELETE, s.length() > 6 ? s.substring(7).trim() : "".trim());
        }

        return new Command(Action.UNKNOWN, s.trim());

    }
}
