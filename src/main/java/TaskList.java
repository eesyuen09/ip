import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks = new ArrayList<>();

    void add(Task task){
        tasks.add(task);
    }

    Task get(int index){
        return tasks.get(index);
    }

    int size(){
        return tasks.size();
    }

    public void delete(int index){
        tasks.remove(index);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            int num = i + 1;
            sb.append(num).append(". ").append(tasks.get(i)).append("\n");
        }
       return sb.toString().trim();
    }

}
