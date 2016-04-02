package app.xda.report;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseACL;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;


public class Student extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
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
    TextView txtr,txtu, textll,textgg;
    TextInputLayout inbuld , inpark , inplate;

    protected EditText mbuild;
    protected EditText mpark;
    protected EditText mplate;
    protected Button mbut;
    protected Button mbup,lbut;
    protected Spinner spinner;
    protected Uri decodeUri;
    double dist;
    byte[] fileBytes ;

    public GoogleApiClient mgoogleApiClient;
    private LocationServices mlocationServices;

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
                // if an image selected
                if (decodeUri!=null){
                    fileBytes = FileHelper.getByteArrayFromFile(Student.this, decodeUri);


                    ImageView iii = (ImageView)findViewById(R.id.imgbut);
                    iii.setImageBitmap(BitmapFactory.decodeFile(getRealPathFromURI(decodeUri)));


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
    protected void displayLocation() {

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double unlat = 24.70044326800795;
            double unlon = 46.80518299341202;
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            double newlat = Math.toRadians(latitude - unlat);
            double newlon = Math.toRadians(longitude - unlon);
            double a = (Math.sin(newlat / 2) * Math.sin(newlon / 2)) +
                    (Math.cos(Math.toRadians(latitude))) *
                            (Math.cos(Math.toRadians(unlat))) *
                            (Math.sin(newlon / 2)) *
                            (Math.sin(newlon / 2));

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            dist = 6371 * c;


            textll.setText(dist  + ", ");
            textgg.setText(latitude + " " + longitude);



        } else {

            textll
                    .setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
          lbut = (Button)findViewById(R.id.butlocal);
        textll = (TextView)findViewById(R.id.textloc1);
        textgg = (TextView)findViewById(R.id.textView2);



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

        lbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
displayLocation();
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
        ImageView iii = (ImageView)findViewById(R.id.imgbut);


        iii.setOnClickListener(new View.OnClickListener() {
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
                if (mLastLocation == null) {
                    textgg.setText("Please make sure GPS is enabled");
                }



                else  {

                String build = mbuild.getText().toString();
                String park = mpark.getText().toString();
                String plate = mplate.getText().toString();
                String spinr = spinner.getSelectedItem().toString();
                String arr = getResources().getStringArray(R.array.arr1)[spinner.getSelectedItemPosition()];
                String spinr2 = spinner2.getSelectedItem().toString();
                String arr2 = getResources().getStringArray(R.array.arr2)[spinner2.getSelectedItemPosition()];





                if (decodeUri==null || build.isEmpty() || park.isEmpty() || plate.isEmpty()) {
                    Toast.makeText(Student.this,"Please fill all fields ", Toast.LENGTH_LONG)
                            .show();
                }










                else {


                    fileBytes=FileHelper.reduceImageForUpload(fileBytes);

                    ParseFile imageFile = new ParseFile("img.jpeg", fileBytes);
                    imageFile.saveInBackground();
                    ParseGeoPoint geoPoint = new ParseGeoPoint(mLastLocation.getLatitude(),mLastLocation.getLongitude());


                    ParseACL acl = new ParseACL();
                    acl.setPublicWriteAccess(true);
                    acl.setPublicReadAccess(true);
                    acl.setRoleWriteAccess("ad", true);
                    ParseObject Post = new ParseObject(Pid.P_POST);
                    Post.put(Pid.B_BULD, build);
                    Post.put(Pid.P_PARK, park);
                    Post.put(Pid.P_PLAT, plate);
                    Post.put(Pid.I_IMG, imageFile);
                    Post.put(Pid.S_Pnr, arr);
                    Post.put(Pid.S_Pnr2, arr2);
                    Post.put(Pid.D_Done, false);
                    Post.setACL(acl);
                    Post.put("Loc", geoPoint);

                    Intent i = new Intent(Student.this,CustomAd.class);
                    i.putExtra("l",mLastLocation.getLatitude());
                    i.putExtra("g",mLastLocation.getLongitude());


                    Post.saveInBackground();
                    finish();}
                }}




        });
        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }


    }

    /**
     * Method to display the location on UI
     * */


    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }

    @Override
    public void onConnected(Bundle bundle) {
displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}