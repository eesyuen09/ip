public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String status() {
        return isDone
                ? "[X]"
                : "[ ]";
    }

    @Override
    public String toString() {
        return status() + " " + this.description;
    }
}
