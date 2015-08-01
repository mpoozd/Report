package app.report;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Start extends ActionBarActivity {

    Button but1;
    Button but2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        but1 = (Button)findViewById(R.id.but1);
        but2= (Button)findViewById(R.id.but2);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Start.this, Student.class);
                startActivity(i);

            }
        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Start.this, DispatchActivity.class);
                startActivity(ii);
            }
        });
    }


}
