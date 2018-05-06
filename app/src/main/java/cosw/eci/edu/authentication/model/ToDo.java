package cosw.eci.edu.authentication.model;

/**
 * Created by LauraRB on 28/04/2018.
 */

public class ToDo {
    private String description;
    private int priority;
    private boolean completed;

    public ToDo(String description, int priority, boolean completed){
        this.description = description;
        this.priority = priority;
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
