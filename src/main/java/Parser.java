public class Parser {
    Command parse(String input) {
        String s = input.trim();
        if (s.equals("bye")) {
            return new Command(Intent.BYE, "");
        } else if (s.equals("list")) {
            return new Command(Intent.LIST, "");
        } else if (s.startsWith("mark ")) {
            return new Command(Intent.MARK, s.substring(5).trim());
        } else if (s.startsWith("unmark ")) {
            return new Command(Intent.UNMARK, s.substring(7).trim());
        }

        return new Command(Intent.ADD, s.trim());

    }
}
