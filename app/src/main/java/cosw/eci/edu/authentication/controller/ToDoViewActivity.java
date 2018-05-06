package cosw.eci.edu.authentication.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cosw.eci.edu.authentication.R;
import cosw.eci.edu.authentication.model.ToDo;
import cosw.eci.edu.authentication.network.NetworkException;
import cosw.eci.edu.authentication.network.RequestCallback;
import cosw.eci.edu.authentication.network.RetrofitNetwork;
import cosw.eci.edu.authentication.ui.adapter.ToDoAdapter;

public class ToDoViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private String token;
    List<ToDo> todos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_view);
        recyclerView = findViewById(R.id.recyclerView);
        token = (String) getIntent().getExtras().get(LoginActivity.TOKEN_NAME);
        configureRecyclerView();
        getAllToDo();
    }

    private void configureRecyclerView() {
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        recyclerView.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );
    }


    private void getAllToDo(){
        RetrofitNetwork rfN = new RetrofitNetwork();
        rfN.getAllToDo(new RequestCallback<List<ToDo>>() {
            @Override
            public void onSuccess(List<ToDo> response) {
                todos = response;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        recyclerView.setAdapter(new ToDoAdapter(todos));
                    }
                });
            }

            @Override
            public void onFailed(NetworkException e) {

            }
        },token );

    }


    public void addNewTodo(View view){
        Intent intent = new Intent(this, ToDoAddActivity.class);
        intent.putExtra(LoginActivity.TOKEN_NAME,token);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    getAllToDo();
            }
        }
    }


}

