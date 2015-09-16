package app.xda.report;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by MPooZD on 26/08/15.
 */
public class FragClass extends AppCompatActivity {

    ViewPager viewPager;
    TabsPagerAdapter mAdapter;
    ActionBar actionBar;

    public class TabsPagerAdapter extends FragmentPagerAdapter {

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int index) {

            switch (index) {
                case 0:
                    // Top Rated fragment activity
                    return new DispatchActivity();
                case 1:
                    // Games fragment activity
                    return new ViewD();


            }

            return null;
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return 2;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tap1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tap2));

        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
