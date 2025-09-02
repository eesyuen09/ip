package penguin.task;

/**
 * Represents a Task of type "Todo".
 * A Todo task only has a description and completion status,
 * without any date/time fields.
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean done) {
        super(description, done);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
