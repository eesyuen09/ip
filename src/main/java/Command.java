enum Intent { ADD, LIST, MARK, UNMARK, BYE, TODO, DEADLINE, EVENT}

public record Command(Intent intent, String args) {
    String execute(TaskList tasks) {
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
                int idx = Integer.parseInt(args) - 1;
                Task task = tasks.get(idx);
                task.mark();
                return "Nice! I've marked this task as done:\n" + task;
            }
            case UNMARK -> {
                int idx = Integer.parseInt(args) - 1;
                Task task2 = tasks.get(idx);
                task2.unmark();
                return "OK, I've marked this task as not done yet:\n" + task2;
            }
            case BYE -> {
                return "Bye. Hope to see you again soon!";
            }
            case TODO -> {
                Task t = new Todo(args);
                tasks.add(t);
                return "Got it. I've added this task:\n" + t + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            case DEADLINE -> {
                int idx = args.indexOf("/");
                String desc = args.substring(0, idx - 1);
                String by = args.substring(idx + 4);
                Task t = new Deadline(desc, by);
                tasks.add(t);
                return "Got it. I've added this task:\n" + t + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            case EVENT -> {
                int firstIdx = args.indexOf("/");
                int lastIdx = args.lastIndexOf("/");
                String desc = args.substring(0, firstIdx - 1);
                String from = args.substring(firstIdx + 6, lastIdx);
                String to = args.substring(lastIdx + 3);
                Task t = new Event(desc, from, to);
                tasks.add(t);
                return "Got it. I've added this task:\n" + t + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            default -> {
                return "Sorry, I don't understand that!";
            }
        }

    }
}
