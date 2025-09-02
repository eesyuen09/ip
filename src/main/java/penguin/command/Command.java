package penguin.command;

import penguin.exception.PenguinException;
import penguin.task.Deadline;
import penguin.task.Event;
import penguin.task.Task;
import penguin.task.TaskList;
import penguin.task.Todo;

enum Action {ADD, LIST, MARK, UNMARK, BYE, TODO, DEADLINE, EVENT, DELETE, UNKNOWN}

/**
 * Represents a command that is parsed from user's input.
 *
 * @param action indicates the type of action requested
 * @param args   description of the task
 */
public record Command(Action action, String args) {
    /**
     * Execute the action on the tasklist.
     * The behavior depends on the Action:
     * ADD: Adds a task to the tasklist.
     * LIST: List all task in the tasklist.
     * MARK: Mark the task on the index as done.
     * UNMARK: Mark the task on the index as undone.
     * BYE: Terminates the chatbot.
     * TODO: Adds an undone TODO task to the tasklist.
     * DEADLINE: Adds an undone DEADLINE task to the tasklist.
     * EVENT: Adds an undone EVENT task to the tasklist.
     *
     * @param tasks Tasklist to be executed on.
     * @return Response message for users.
     * @throws PenguinException
     */
    public String execute(TaskList tasks) throws PenguinException {
        switch (action) {
        case ADD -> {
            Task t = new Task(args);
            tasks.add(t);
            return "added: " + args;
        }
        case DELETE -> {
            if (args.isBlank()) {
                throw new PenguinException("Please provide a task number to delete.");
            }
            int idx = Integer.parseInt(args) - 1;
            if (idx >= tasks.getSize()) {
                throw new PenguinException("Please provide a valid task number.");
            }
            Task t = tasks.get(idx);
            tasks.delete(idx);
            return "Noted. I've removed this task:\n" + t + "\nNow you have " + tasks.getSize() + " tasks in the list.";
        }
        case LIST -> {
            String str = "Here are the tasks in your list:\n";
            return str + tasks.toString();
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
            return "Got it. I've added this task:\n" + t + "\nNow you have " + tasks.getSize() + " tasks in the list.";
        }
        case DEADLINE -> {
            if (args.isBlank()) {
                throw new PenguinException("Please specify what task you’d like to add as a deadline task.");
            }
            int idx = args.indexOf("by");
            if (idx == -1) {
                throw new PenguinException("OOPS! A deadline task must include 'by <dd/MM/yyyy HHmm>'. Example: deadline return book by 09/09/2025 1800");
            }
            String desc = args.substring(0, idx - 1);
            if (desc.isBlank()) {
                throw new PenguinException("OOPS! A deadline task must include a description before 'by by <dd/MM/yyyy HHmm>'. Example: deadline return book by 09/09/2025 1800");
            }
            String by = args.substring(idx + 3);
            if (by.equals("Sunday")) {
                throw new PenguinException("OOPS! A deadline task must include 'by 09/09/2025 1800'. Example: deadline return book by 09/09/2025 1800");
            }
            Task t = new Deadline(desc, by);
            tasks.add(t);
            return "Got it. I've added this task:\n" + t + "\nNow you have " + tasks.getSize() + " tasks in the list.";
        }
        case EVENT -> {
            if (args == null || args.isBlank()) {
                throw new PenguinException("The event description cannot be empty.");
            }
            int fromIdx = args.indexOf("from");
            int toIdx = args.indexOf("to");
            if (fromIdx == -1 || toIdx == -1) {
                throw new PenguinException(
                        "An event must include both 'from <start>' and 'to <end>'.\n" +
                                "Example: event project meeting from 09/09/2025 1800 to 09/10/2025 1800"
                );
            }
            if (fromIdx > toIdx) {
                throw new PenguinException("'from' must come before 'to'.");
            }

            String desc = args.substring(0, fromIdx).trim();
            String from = args.substring(fromIdx + 4, toIdx).trim();
            String to = args.substring(toIdx + 2).trim();
            if (desc.isBlank()) throw new PenguinException("The event description cannot be empty.");
            if (from.isBlank()) throw new PenguinException("The event start time after 'from' cannot be empty.");
            if (to.isBlank()) throw new PenguinException("The event end time after 'to' cannot be empty.");
            Task t = new Event(desc, from, to);
            tasks.add(t);
            return "Got it. I've added this task:\n" + t + "\nNow you have " + tasks.getSize() + " tasks in the list.";
        }
        default -> {
            throw new PenguinException("Sorry, I don't understand that.");
        }
        }

    }
}
