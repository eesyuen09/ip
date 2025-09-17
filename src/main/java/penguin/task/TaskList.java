package penguin.task;

import java.time.LocalDateTime;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

/**
 * Represents a mutable list of Task objects.
 */
public class TaskList {
    private final List<Task> tasks = new ArrayList<>();
    private boolean isModified = false;

    public TaskList(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    public TaskList() {

    }

    /**
     * Adds a task to the list and marks the list as modified.
     *
     * @param task The Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
        isModified = true;
    }

    /**
     * Retrieves a task at the given index.
     *
     * @param index Zero-based index of the task to retrieve.
     * @return The Task at the specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the total number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Deletes the task at the given index and marks the list as modified.
     *
     * @param index Zero-based index of the task to remove.
     */
    public void delete(int index){
        tasks.remove(index);
        isModified = true;
    }

    /**
     * Resets the isModified flag to false.
     * Typically called after saving the task list to storage.
     */
    public void resetModification() {
        isModified = false;
    }

    public boolean isModified() {
        return isModified;
    }

    public List<Task> getUpcomingSchedules() {
        LocalDateTime now = LocalDateTime.now();
        return tasks.stream().filter(t -> t instanceof Deadline || t instanceof Event)
                .filter(t -> {
                    if (t instanceof Deadline deadline) {
                        return !deadline.getBy().isBefore(now);
                    } else if (t instanceof Event event) {
                        return !event.getFrom().isBefore(now);
                    }
                    return false;
                })
                .filter(t -> !t.isDone())
                .sorted(Comparator.comparing(t -> {
                    if (t instanceof Deadline deadline) {
                        return deadline.getBy();
                    } else {
                        return ((Event) t).getFrom();
                    }
                })).collect(Collectors.toList());
    }

    public List<Task> getScheduleOnDate(LocalDate date) {
        assert date != null : "Date must not be null";

        return tasks.stream().filter(t -> t instanceof Deadline || t instanceof Event)
                .filter(t -> {
                    if (t instanceof Deadline deadline) {
                        return deadline.getBy().toLocalDate().isEqual(date);
                    } else if (t instanceof Event event) {
                        return event.getFrom().toLocalDate().isEqual(date);
                    }
                    return false;
                }).filter(t -> !t.isDone())
                .sorted(Comparator.comparing(t -> {
                    if (t instanceof Deadline deadline) {
                        return deadline.getBy();
                    } else {
                        return ((Event) t).getFrom();
                    }
                })).collect(Collectors.toList());
    }

    /**
     * Returns a string representation of the task list.
     *
     * @return Numbered string listing of all tasks.
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            int num = i + 1;
            sb.append(num).append(". ").append(tasks.get(i)).append("\n");
        }
       return sb.toString().trim();
    }

}
