package penguin.task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks = new ArrayList<>();
    private boolean isModified = false;

    public void add(Task task) {
        tasks.add(task);
        isModified = true;
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int getSize() {
        return tasks.size();
    }

    public void delete(int index){
        tasks.remove(index);
        isModified = true;
    }

    public void resetModification() {
        isModified = false;
    }

    public boolean isModified() {
        return isModified;
    }

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
