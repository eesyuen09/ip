package penguin.task;

/**
 * Represents a generic task with a description and a completion status.
 *
 * Subclasses such as Todo, Deadline and Event
 * extend this class to represent more specific task types.
 * @author eesyuen
 */
public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the status symbol of this task.
     *
     * @return {@code "[X]"} if the task is done, otherwise "[ ]".
     */
    public String status() {
        return isDone
                ? "[X]"
                : "[ ]";
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of this task, including its status
     * and description.
     *
     * Example: {@code [X] finish assignment}
     *
     * @return String representation of this task.
     */
    @Override
    public String toString() {
        return status() + " " + this.description;
    }
}
