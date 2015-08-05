package app.report;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class Ppar extends Application {
    public void onCreate() {

        ParseUser.enableAutomaticUser();
        Parse.initialize(this, "yQw1zWytVRzIdVBjoHGxsxwNl9U1IoMKX0hAGxfF", "7eYExyfsV93nmPWO3papMeffhIUBzjt9ra50OHP4");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }
}
