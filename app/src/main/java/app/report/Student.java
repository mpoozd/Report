package app.report;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Student extends Activity {

    public static final String POSTS = "Post";
    public static final String BULD = "build";
    public static final String PLAT = "plate";
    public static final String PARRK = "park";
    static final int PICK_PHOTO = 1;
    private ParseFile photoFile;
    private byte[]byteArray;
    protected Uri fileUri;
    protected Uri decodeUri18;
    Bitmap newbitmap;
    TextView txtr,txtu;

    protected EditText mbuild;
    protected EditText mpark;
    protected EditText mplate;
    protected Button mbut;
    protected Button mbup;
    protected Spinner spinner;

    protected Uri decodeUri;



    public String getRealPathFromURI(Uri contentUri){

        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void setTextViews(int sdk, String uriPath, String realPath) {

        Uri uriFromPath = Uri.fromFile(new File(realPath));

        fileUri = uriFromPath;

        try {
            newbitmap = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(fileUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("Status", "Build.VERSION.SDK_INT:" + sdk);
        Log.d("Status", "URI Path:" + fileUri);
        Log.d("Status", "Real Path: " + realPath);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == PICK_PHOTO ) && resultCode == RESULT_OK) {
            if(requestCode==PICK_PHOTO){
                String realPath;
                // SDK < API11
                if (Build.VERSION.SDK_INT < 11) {
                    decodeUri = data.getData();
                    realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

                }

                // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19) {
                    decodeUri = data.getData();
                    realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

                }

                // SDK > 19 (Android 4.4)
                else {
                    try {
                        decodeUri = data.getData();

                        realPath = RealPathUtil.getRealPathFromURI_API19(
                                Student.this,
                                data.getData());

                        setTextViews(Build.VERSION.SDK_INT, data.getData()
                                .getPath(), realPath);

                    } catch (Exception e) {

                        decodeUri = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver()
                                .query(decodeUri, filePathColumn, null,
                                        null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor
                                .getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();
                        Bitmap bitmap=BitmapFactory.decodeFile(filePath);


                    }

                }

            }

        }
    }
    public void decodeUri(Uri uri) {
        ParcelFileDescriptor parcelFD = null;
        try {
            parcelFD = getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor imageSource = parcelFD.getFileDescriptor();

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(imageSource, null, o);

            // the new size we want to scale to
            final int REQUIRED_SIZE = 1024;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);


        } catch (FileNotFoundException e) {
            // handle errors
        } catch (IOException e) {
            // handle errors
        } finally {
            if (parcelFD != null)
                try {
                    parcelFD.close();
                } catch (IOException e) {
                    // ignored
                }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        final Spinner spinner = (Spinner) findViewById(R.id.spnr);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spnrr, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner spinner2 = (Spinner)findViewById(R.id.spnr2);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spnrr2, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);



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


            }
        });


        mbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String build = mbuild.getText().toString();
                String park = mpark.getText().toString();
                String plate = mplate.getText().toString();
                String spinr = spinner.getSelectedItem().toString();
                String arr = getResources().getStringArray(R.array.arr1)[spinner.getSelectedItemPosition()];
                String spinr2 = spinner2.getSelectedItem().toString();
                String arr2 = getResources().getStringArray(R.array.arr2)[spinner2.getSelectedItemPosition()];





                if (build.isEmpty()||park.isEmpty()||plate.isEmpty()||decodeUri==null ){
                    StringBuilder pic = new StringBuilder(getString(R.string.subtimto));

                    Toast.makeText(Student.this, pic.toString(), Toast.LENGTH_LONG)
                            .show();


                }
                else {

                    byte[] fileBytes = FileHelper.getByteArrayFromFile(Student.this, decodeUri);
                    fileBytes=FileHelper.reduceImageForUpload(fileBytes);

                    ParseFile imageFile = new ParseFile("img.jpeg", fileBytes);
                    imageFile.saveInBackground();

                    ParseObject Post = new ParseObject(Pid.P_POST);
                    Post.put(Pid.B_BULD, build);
                    Post.put(Pid.P_PARK, park);
                    Post.put(Pid.P_PLAT, plate);
                    Post.put(Pid.I_IMG, imageFile);
                    Post.put(Pid.S_Pnr,arr);
                    Post.put(Pid.S_Pnr2,arr2);



                    Post.saveInBackground();
                    finish();
                }

            }

        });

    }



}