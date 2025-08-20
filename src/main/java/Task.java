public class Task {
    private final String description;
    private boolean isDone;

    Task(String description) {
        this.description = description;
    }

    void mark() {
        this.isDone = true;
    }

    void unmark() {
        this.isDone = false;
    }

    String status() {
        return isDone ? "[X]" : "[ ]";
    }

    @Override
    public String toString() {
        return status() + " " + this.description;
    }
}
