package penguin.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mutable list of Task objects.
 */
public class TaskList {
    private final List<Task> tasks = new ArrayList<>();
    private boolean isModified = false;

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
