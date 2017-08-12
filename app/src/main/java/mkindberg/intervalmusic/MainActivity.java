package mkindberg.intervalmusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void editWork(View v){
        EditText field = (EditText) findViewById(R.id.et_work);
        if(field.getText().length()==0)
            field.setText(0);
        if(((Button) v).getText().equals("+"))
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())+1));
        else
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())-1));
    }

    public void editRest(View v){
        Log.i("Clicked", "Clicked");
        EditText field = (EditText) findViewById(R.id.et_rest);
        if(field.getText().length()==0)
            field.setText(0);
        if(((Button) v).getText().equals("+"))
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())+1));
        else
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())-1));
    }

    public void editRounds(View v){
        EditText field = (EditText) findViewById(R.id.et_rounds);
        if(field.getText().length()==0)
            field.setText(0);
        if(((Button) v).getText().equals("+"))
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())+1));
        else
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())-1));
    }
}
