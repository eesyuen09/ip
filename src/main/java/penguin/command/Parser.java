package penguin.command;

public class Parser {
    public Command parse(String input) {
        String s = input.trim();
        if (s.equals("bye")) {
            return new Command(Intent.BYE, "");
        } else if (s.equals("list")) {
            return new Command(Intent.LIST, "");
        } else if (s.startsWith("mark")) {
            return new Command(Intent.MARK, s.length() > 4 ? s.substring(5).trim() : "".trim());
        } else if (s.startsWith("unmark")) {
            return new Command(Intent.UNMARK, s.length() > 6 ? s.substring(7).trim() : "");
        } else if (s.startsWith("todo")) {
            return new Command(Intent.TODO, s.length() > 4 ? s.substring(5).trim() : "".trim());
        } else if (s.startsWith("deadline")) {
            return new Command(Intent.DEADLINE, s.length() > 8 ? s.substring(9).trim() : "".trim());
        } else if (s.startsWith("event")) {
            return new Command(Intent.EVENT, s.length() > 5 ? s.substring(6).trim() : "".trim());
        } else if (s.startsWith("delete")) {
            return new Command(Intent.DELETE, s.length() > 6 ? s.substring(7).trim() : "".trim());
        }

        return new Command(Intent.UNKNOWN, s.trim());

    }
}
