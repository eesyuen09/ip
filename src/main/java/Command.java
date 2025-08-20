enum Intent { ADD, LIST, MARK, UNMARK, BYE, ECHO}

public record Command(Intent intent, String args) {
    String execute(TaskList tasks) {
        switch (intent) {
            case ADD:
                Task t = new Task(args);
                tasks.add(t);
                return "added: "  + args;
            case LIST:
                return tasks.toString();
            case MARK:
                int index = Integer.parseInt(args) - 1;
                Task task = tasks.get(index);
                task.mark();
                return "Nice! I've marked this task as done:\n" + task;
            case UNMARK:
                int idx = Integer.parseInt(args) - 1;
                Task task2 = tasks.get(idx);
                task2.unmark();
                return "OK, I've marked this task as not done yet:\n" + task2;
            case BYE:
                return "Bye. Hope to see you again soon!";
            default:
                return "Sorry, I don't understand that!";
        }

    }
}
