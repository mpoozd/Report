package app.xda.report;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class NewUser extends Activity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button signUpButton;
    protected String arr;
    protected String arr2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        usernameEditText=(EditText)findViewById(R.id.semail);
        passwordEditText=(EditText)findViewById(R.id.spass);
        signUpButton=(Button)findViewById(R.id.sibut);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spnrr, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spnrr2, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spinr = spinner.getSelectedItem().toString();
                arr = getResources().getStringArray(R.array.arr1)[spinner.getSelectedItemPosition()];
                String spinr2 = spinner2.getSelectedItem().toString();
                arr2 = getResources().getStringArray(R.array.arr2)[spinner2.getSelectedItemPosition()];
                SignUp();



            }
        });

    }
    private void SignUp (){
        ParseUser user = new ParseUser();
        ParseACL acl = new ParseACL(ParseUser.getCurrentUser());
        acl.setRoleReadAccess("ad",true);

       user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());
        user.put(Pid.S_Pnr, arr);
        user.put(Pid.S_Pnr2, arr2);




        // Validate the log in data
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
        if (usernameEditText.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }
        if (passwordEditText.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_password));
        }
        validationErrorMessage.append(getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(NewUser.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(NewUser.this);
        dialog.setMessage(getString(R.string.progress_signup));
        dialog.show();

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                if (e!=null){
                   //Error
                    Toast.makeText(NewUser.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }
                else {
                    // Success SignUp
                    StringBuilder stringBuilder = new StringBuilder(getString(R.string.newuser));
                    Toast.makeText(NewUser.this,stringBuilder.toString(),Toast.LENGTH_LONG).show();
                    finish();

                }


            }
        });
    }

}
