package app.report;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseObject;


public class Student extends ActionBarActivity {

    public static final String POSTS = "Post";
    public static final String BULD = "build";
    public static final String PLAT = "plate";
    public static final String PARRK = "park";

    protected EditText mbuild;
    protected EditText mpark;
    protected EditText mplate;
    protected Button mbut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        mbuild = (EditText) findViewById(R.id.bulding1);
        mpark = (EditText) findViewById(R.id.parking1);
        mplate = (EditText) findViewById(R.id.plate1);
        mbut = (Button) findViewById(R.id.butsend);

        mbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String build = mbuild.getText().toString();
                String park = mpark.getText().toString();
                String plate = mplate.getText().toString();

                if (!build.equals("")){

                    ParseObject Post = new ParseObject("Post");
                    Post.put("build", build);
                    Post.put("plate", plate);
                    Post.put("park", park);
                    Post.saveInBackground();

                    finish();
                }
            }
        });

    }




}
