package app.report;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


public class Student extends Activity {

    public static final String POSTS = "Post";
    public static final String BULD = "build";
    public static final String PLAT = "plate";
    public static final String PARRK = "park";
    private static final int PICK_PHOTO = 1;
    private ParseFile photoFile;

    protected EditText mbuild;
    protected EditText mpark;
    protected EditText mplate;
    protected Button mbut;
    protected Button mbup;
    protected Spinner msp;

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String UsetPhoto = "user"+System.currentTimeMillis()+"image";
        ParseFile file = new ParseFile(UsetPhoto, byteArray);

        ParseObject gameScore = new ParseObject("Post");
        gameScore.put("picture", file);
        gameScore.saveInBackground();

        return stream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == PICK_PHOTO ) && resultCode == RESULT_OK) {


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        mbuild = (EditText) findViewById(R.id.bulding1);
        mpark = (EditText) findViewById(R.id.parking1);
        mplate = (EditText) findViewById(R.id.plate1);
        mbut = (Button) findViewById(R.id.butsend);
        mbup = (Button)findViewById(R.id.butup);
        mbup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choosepic = new Intent(Intent.ACTION_GET_CONTENT);
                choosepic.setType("image/*");
                startActivityForResult(choosepic, PICK_PHOTO);
                byte[] data = "Working at Parse is great!".getBytes();
                ParseFile file = new ParseFile("resume.jpeg", data);
                file.saveInBackground();
                ParseObject jobApplication = new ParseObject("Post");
                jobApplication.put("picture", file);
                jobApplication.saveInBackground();


            }
        });





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
