package cosw.eci.edu.authentication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cosw.eci.edu.authentication.R;
import cosw.eci.edu.authentication.model.ToDo;

/**
 * Created by JuanArevaloMerchan on 19/04/2018.
 */

public class ToDoAdapter  extends RecyclerView.Adapter<ToDoAdapter.ViewHolder>{

    private final List<ToDo> todos;
    private Context context;

    public ToDoAdapter(List<ToDo> response) {
        this.todos = response;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.todo_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDo todo = todos.get(position);
        holder.description.setText(todo.getDescription());
        holder.completed.setText(todo.isCompleted()+"");
        holder.priority.setText(todo.getPriority()+"");
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView description, priority, completed;

        public ViewHolder(View view) {
            super(view);
            description = (TextView) view.findViewById(R.id.description);
            priority = (TextView) view.findViewById(R.id.priority);
            completed = (TextView) view.findViewById(R.id.completed);
        }
    }

}
