enum Intent { ADD, LIST, MARK, UNMARK, BYE, TODO, DEADLINE, EVENT, UNKNOWN}

public record Command(Intent intent, String args) {
    String execute(TaskList tasks) throws PenguinException {
        switch (intent) {
            case ADD -> {
                Task t = new Task(args);
                tasks.add(t);
                return "added: " + args;
            }
            case LIST -> {
                return tasks.toString();
            }
            case MARK -> {
                if (args.isBlank()) {
                    throw new PenguinException("Please provide a task number to mark.");
                }
                int idx = Integer.parseInt(args) - 1;
                Task task = tasks.get(idx);
                task.mark();
                return "Nice! I've marked this task as done:\n" + task;
            }
            case UNMARK -> {
                if (args.isBlank()) {
                    throw new PenguinException("Please provide a task number to unmark.");
                }
                int idx = Integer.parseInt(args) - 1;
                Task task2 = tasks.get(idx);
                task2.unmark();
                return "OK, I've marked this task as not done yet:\n" + task2;
            }
            case BYE -> {
                return "Bye. Hope to see you again soon!";
            }
            case TODO -> {
                if (args.isBlank()) {
                    throw new PenguinException("Please specify what task you’d like to add as a todo.");
                }
                Task t = new Todo(args);
                tasks.add(t);
                return "Got it. I've added this task:\n" + t + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            case DEADLINE -> {
                if (args.isBlank()) {
                    throw new PenguinException("Please specify what task you’d like to add as a deadline task.");
                }
                int idx = args.indexOf("/");
                if (idx == -1) {
                    throw new PenguinException("OOPS! A deadline task must include '/by <time>'. Example: deadline return book /by Sunday");
                }
                String desc = args.substring(0, idx - 1);
                if (desc.isBlank()) {
                    throw new PenguinException("OOPS! A deadline task must include a description before '/by <time>'. Example: deadline return book /by Sunday");
                }
                String by = args.substring(idx + 4);
                if (by.equals("Sunday")) {
                    throw new PenguinException("OOPS! A deadline task must include '/by <time>'. Example: deadline return book /by Sunday");
                }
                Task t = new Deadline(desc, by);
                tasks.add(t);
                return "Got it. I've added this task:\n" + t + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            case EVENT -> {
                if (args == null || args.isBlank()) {
                    throw new PenguinException("The event description cannot be empty.");
                }
                int fromIdx = args.indexOf("/from");
                int toIdx = args.indexOf("/to");
                if (fromIdx == -1 || toIdx == -1) {
                    throw new PenguinException(
                            "An event must include both '/from <start>' and '/to <end>'.\n" +
                                    "Example: event project meeting /from Mon 2pm /to Mon 4pm"
                    );
                }
                if (fromIdx > toIdx) {
                    throw new PenguinException("'/from' must come before '/to'.");
                }

                String desc = args.substring(0, fromIdx);
                String from = args.substring(fromIdx + 6, toIdx);
                String to = args.substring(toIdx + 3);
                if (desc.isBlank()) throw new PenguinException("The event description cannot be empty.");
                if (from.isBlank()) throw new PenguinException("The event start time after '/from' cannot be empty.");
                if (to.isBlank())   throw new PenguinException("The event end time after '/to' cannot be empty.");
                Task t = new Event(desc, from, to);
                tasks.add(t);
                return "Got it. I've added this task:\n" + t + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            default -> {
                throw new PenguinException("Sorry, I don't understand that.");
            }
        }

    }
}
