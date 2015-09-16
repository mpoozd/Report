package app.xda.report;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Start extends Activity {

    Button but1;
    Button but2;
    Button but3;
    Button but4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        but1 = (Button)findViewById(R.id.but1);
        but2= (Button)findViewById(R.id.but2);
        but3 = (Button)findViewById(R.id.but3);
        but4 = (Button)findViewById(R.id.but4);

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
                Intent i = new Intent(Start.this, FragClass.class);
                startActivity(i);
            }
        });
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Start.this,Sec.class);
                startActivity(i);
            }
        });
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Start.this,NewUser.class);
                startActivity(i);
            }
        });
    }


}
