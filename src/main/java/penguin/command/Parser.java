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
        String trimmedInput = input.trim();

        if (trimmedInput.equals("bye")) {
            return new Command(Action.BYE, "");
        } else if (trimmedInput.equals("list")) {
            return new Command(Action.LIST, "");
        } else if (trimmedInput.startsWith("mark")) {
            return parseCommand("mark", Action.MARK, trimmedInput);
        } else if (trimmedInput.startsWith("unmark")) {
            return parseCommand("unmark", Action.UNMARK, trimmedInput);
        } else if (trimmedInput.startsWith("todo")) {
            return parseCommand("todo", Action.TODO, trimmedInput);
        } else if (trimmedInput.startsWith("deadline")) {
            return parseCommand("deadline", Action.DEADLINE, trimmedInput);
        } else if (trimmedInput.startsWith("event")) {
            return parseCommand("event", Action.EVENT, trimmedInput);
        } else if (trimmedInput.startsWith("delete")) {
            return parseCommand("delete", Action.DELETE, trimmedInput);
        } else if (trimmedInput.startsWith("find")) {
            return parseCommand("find", Action.FIND, trimmedInput);
        } else if (trimmedInput.startsWith("schedule")) {
            return parseCommand("schedule", Action.SCHEDULE, trimmedInput);
        }
        return new Command(Action.UNKNOWN, trimmedInput);
    }

    /**
     * Creates a Command for inputs that begin with a specific keyword.
     * Extracts the substring after the keyword (if any) as the arguments.
     *
     * @param keyword The command keyword (e.g. "todo", "deadline", "event").
     * @param action  The corresponding Action enum for the keyword.
     * @param input   The full user input string.
     * @return A Command containing the action and its extracted arguments.
     */
    private Command parseCommand(String keyword, Action action, String input) {
        return input.length() > keyword.length()
                ? new Command(action, input.substring(keyword.length() + 1).trim())
                : new Command(action, "");
    }

}
