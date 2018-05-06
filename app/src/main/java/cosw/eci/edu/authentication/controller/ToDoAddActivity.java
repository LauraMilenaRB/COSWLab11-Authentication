package cosw.eci.edu.authentication.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cosw.eci.edu.authentication.R;
import cosw.eci.edu.authentication.model.ToDo;
import cosw.eci.edu.authentication.model.Token;
import cosw.eci.edu.authentication.network.NetworkException;
import cosw.eci.edu.authentication.network.RequestCallback;
import cosw.eci.edu.authentication.network.RetrofitNetwork;
import cosw.eci.edu.authentication.ui.adapter.ToDoAdapter;
import okhttp3.ResponseBody;

public class ToDoAddActivity extends AppCompatActivity {
    private String token;
    public EditText txt_description, txt_priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_add);
        token = (String) getIntent().getExtras().get(LoginActivity.TOKEN_NAME);
        txt_description = findViewById(R.id.txt_description);
        txt_priority = findViewById(R.id.txt_priority);
    }


    public void addToDo(View view){
        if(validForm()) {
            ToDo todo = new ToDo(txt_description.getText().toString(), Integer.valueOf(txt_priority.getText().toString()), false);
            RetrofitNetwork rfN = new RetrofitNetwork();
            rfN.addToDo(todo, new RequestCallback<ResponseBody>() {
                @Override
                public void onSuccess(ResponseBody response) {
                    Intent result = new Intent(getApplicationContext(), ToDoViewActivity.class);
                    setResult(RESULT_OK, result);
                    finish();
                }

                @Override
                public void onFailed(NetworkException e) {

                }
            }, token);
        }
    }

    private boolean validForm(){
        if(txt_description.length()==0){
            txt_description.setError("Please enter either a description");
            return false;
        }
        else if(txt_priority.length()==0){
            txt_priority.setError("Please enter either a priority");
            return false;
        }
        else{
            return true;
        }
    }

    public void cancelToDoAdd(View view){
        Intent result = new Intent(getApplicationContext(), ToDoViewActivity.class);
        setResult(RESULT_CANCELED, result);
        finish();
    }
}
