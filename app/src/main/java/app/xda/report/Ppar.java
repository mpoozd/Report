package app.xda.report;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;


public class Ppar extends Application {
    public void onCreate() {

        ParseCrashReporting.enable(this);
        Parse.enableLocalDatastore(getApplicationContext());
        ParseUser.enableAutomaticUser();
        Parse.initialize(this, "yQw1zWytVRzIdVBjoHGxsxwNl9U1IoMKX0hAGxfF", "7eYExyfsV93nmPWO3papMeffhIUBzjt9ra50OHP4");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
        // Create default options which will be used for every
//  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
                .cacheOnDisk(true)
        .build();
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)

                .build();

        ImageLoader.getInstance().init(config);

    }
}
