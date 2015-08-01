package app.report;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;


public class Ppar extends Application {
    public void onCreate() {
        Parse.initialize(this, "yQw1zWytVRzIdVBjoHGxsxwNl9U1IoMKX0hAGxfF", "7eYExyfsV93nmPWO3papMeffhIUBzjt9ra50OHP4");
    }
}
