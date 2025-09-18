package penguin.command;

import java.time.LocalDate;
import java.util.List;

import penguin.exception.PenguinException;
import penguin.task.Deadline;
import penguin.task.Event;
import penguin.task.Task;
import penguin.task.TaskList;
import penguin.task.Todo;

enum Action {ADD, LIST, MARK, UNMARK, BYE, TODO, DEADLINE, EVENT, DELETE, FIND, SCHEDULE, UNKNOWN}

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
     *
     * @param tasks Tasklist to be executed on.
     * @return Response message for users.
     * @throws PenguinException If there are invalid arguments.
     */
    public String execute(TaskList tasks) throws PenguinException {
        assert tasks != null : "TaskList must not be null";
        assert action != null : "Action must not be null";
        switch (action) {
        case ADD -> { return handleAdd(tasks); }
        case DELETE -> { return handleDelete(tasks); }
        case LIST -> { return handleList(tasks); }
        case MARK -> { return handleMark(tasks); }
        case UNMARK -> { return handleUnmark(tasks); }
        case BYE -> { return handleBye(tasks); }
        case TODO -> { return handleTodo(tasks); }
        case DEADLINE -> { return handleDeadline(tasks); }
        case EVENT -> { return handleEvent(tasks); }
        case FIND -> { return handleFind(tasks); }
        case SCHEDULE -> { return handleSchedule(tasks); }
        default -> throw new PenguinException("Sorry, I don't understand that.");
        }
    }

    public String getSimpleName(Action action) {
        return action.toString().toLowerCase();
    }

    public Action getAction() {
        return action;
    }

    /**
     * Adds a generic Task with the given arguments to the task list.
     *
     * @param tasks Task list to add the task to.
     * @return Response message after adding the task.
     */
    public String handleAdd(TaskList tasks) {
        Task task = new Task(args);
        tasks.add(task);
        return "added: " + args;
    }

    /**
     * Deletes a task from the task list at the given index.
     *
     * @param tasks Task list to operate on.
     * @return Response message after deletion.
     * @throws PenguinException If no index is provided or if the index is invalid.
     */
    public String handleDelete(TaskList tasks) throws PenguinException {
        if (args.isBlank()) {
            throw new PenguinException("Please provide a task number to delete.");
        }
        int index = Integer.parseInt(args) - 1;
        if (index >= tasks.getSize()) {
            throw new PenguinException("Please provide a valid task number.");
        }
        Task t = tasks.get(index);
        tasks.delete(index);
        return "Noted. I've removed this task:\n" + t
                + "\nNow you have " + tasks.getSize() + " tasks in the list.";
    }

    /**
     * Lists all tasks currently in the task list.
     *
     * @param tasks Task list to display.
     * @return String representation of all tasks.
     */
    public String handleList(TaskList tasks) {
        String str = "Here are the tasks in your list:\n";
        return str + tasks.toString();
    }


    /**
     * Marks a task in the task list as completed.
     *
     * @param tasks Task list containing the task to mark.
     * @return Response message after marking the task.
     * @throws PenguinException If no index is provided or the index is invalid.
     */
    public String handleMark(TaskList tasks) throws PenguinException {
        if (args.isBlank()) {
            throw new PenguinException("Please provide a task number to mark.");
        }
        int index = Integer.parseInt(args) - 1;
        Task task = tasks.get(index);
        task.mark();
        return "Nice! I've marked this task as done:\n" + task;
    }

    /**
     * Unmarks a task in the task list, setting it back to not completed.
     *
     * @param tasks Task list containing the task to unmark.
     * @return Response message after unmarking the task.
     * @throws PenguinException If no index is provided or the index is invalid.
     */
    public String handleUnmark(TaskList tasks) throws PenguinException {
        if (args.isBlank()) {
            throw new PenguinException("Please provide a task number to unmark.");
        }
        int idx = Integer.parseInt(args) - 1;
        Task task = tasks.get(idx);
        task.unmark();
        return "OK, I've marked this task as not done yet:\n" + task;
    }

    /**
     * Exits the chatbot.
     *
     * @param tasks Task list (not used here).
     * @return Exit message for the user.
     */
    public String handleBye(TaskList tasks) {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Adds a Todo task to the task list.
     *
     * @param tasks Task list to add the Todo to.
     * @return Response message after adding the Todo.
     * @throws PenguinException If the Todo description is missing.
     */
    public String handleTodo(TaskList tasks) throws PenguinException {
        if (args.isBlank()) {
            throw new PenguinException("Please specify what task you’d like to add as a todo.");
        }
        Task task = new Todo(args);
        tasks.add(task);
        return "Got it. I've added this task:\n" + task + "\nNow you have " + tasks.getSize() + " tasks in the list.";
    }

    /**
     * Adds a Deadline task with a description and due date to the task list.
     *
     * @param tasks Task list to add the deadline to.
     * @return Response message after adding the deadline.
     * @throws PenguinException If the description or deadline date is missing or formatted incorrectly.
     */
    public String handleDeadline(TaskList tasks) throws PenguinException {
        if (args.isBlank()) {
            throw new PenguinException("Please specify what task you’d like to add as a deadline task.");
        }
        int index = args.indexOf("by");
        if (index == -1) {
            throw new PenguinException("OOPS! A deadline task must include 'by <dd/MM/yyyy HHmm>'.");
        }
        String desc = args.substring(0, index - 1);
        if (desc.isBlank()) {
            throw new PenguinException("OOPS! A deadline task must include a description before 'by'.");
        }
        String by = args.substring(index + 3);
        Task task = new Deadline(desc, by);
        tasks.add(task);
        return "Got it. I've added this task:\n" + task
                + "\nNow you have " + tasks.getSize() + " tasks in the list.";
    }

    /**
     * Adds an Event task with a description, start time, and end time to the task list.
     *
     * @param tasks Task list to add the event to.
     * @return Response message after adding the event.
     * @throws PenguinException If description, start time, or end time is missing or incorrectly formatted.
     */
    public String handleEvent(TaskList tasks) throws PenguinException {
        if (args == null || args.isBlank()) {
            throw new PenguinException("The event description cannot be empty.");
        }
        int fromIdx = args.indexOf("from");
        int toIdx = args.indexOf("to");
        if (fromIdx == -1 || toIdx == -1) {
            throw new PenguinException("An event must include both 'from <start>' and 'to <end>'.");
        }
        Task task = getTask(fromIdx, toIdx);
        tasks.add(task);
        return "Got it. I've added this task:\n" + task
                + "\nNow you have " + tasks.getSize() + " tasks in the list.";
    }

    private Task getTask(int fromIdx, int toIdx) throws PenguinException {
        if (fromIdx > toIdx) {
            throw new PenguinException("'from' must come before 'to'.");
        }

        String desc = args.substring(0, fromIdx).trim();
        String from = args.substring(fromIdx + 4, toIdx).trim();
        String to = args.substring(toIdx + 2).trim();
        if (desc.isBlank()) throw new PenguinException("The event description cannot be empty.");
        if (from.isBlank()) throw new PenguinException("The event start time cannot be empty.");
        if (to.isBlank()) throw new PenguinException("The event end time cannot be empty.");
        return new Event(desc, from, to);
    }

    /**
     * Finds and lists tasks containing the given keyword.
     *
     * @param tasks Task list to search within.
     * @return String of matching tasks or a message if none found.
     * @throws PenguinException If the keyword is missing.
     */
    public String handleFind(TaskList tasks) throws PenguinException {
        if (args.isBlank()) {
            throw new PenguinException("Please provide a keyword to search for.");
        }
        String keyword = args.trim().toLowerCase();
        StringBuilder result = new StringBuilder("Here are the matching results in your list: \n");
        boolean isFound = false;
        for (int i = 0; i < tasks.getSize(); i++) {
            Task task = tasks.get(i);
            if (task.toString().toLowerCase().contains(keyword)) {
                result.append(i).append(". ").append(task).append("\n");
                isFound = true;
            }
        }
        if (isFound) {
            return result.toString();
        } else {
            return "There are no matching results in your list.";
        }
    }

    public String handleSchedule(TaskList tasks) throws PenguinException {
        if (args.isBlank() || args == null) {
            List<Task> schedule = tasks.getUpcomingSchedules();
            if (schedule.isEmpty()) {
                return "You have no upcoming schedules.";
            }
            TaskList scheduleList = new TaskList(schedule);
            return "Here are your upcoming schedules: \n" + scheduleList;
        } else {
            LocalDate date;
            try {
                date = LocalDate.parse(args.trim());
            } catch (Exception e) {
                throw new PenguinException("Invalid date format. Please use yyyy-MM-dd.");
            }
            List<Task> schedule = tasks.getScheduleOnDate(date);
            TaskList scheduleList = new TaskList(schedule);
            return "Here are your upcoming schedules at " + args + "\n" + scheduleList;
        }
    }
}

