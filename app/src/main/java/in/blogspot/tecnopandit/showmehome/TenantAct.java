package in.blogspot.tecnopandit.showmehome;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.List;

public class TenantAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String states[]={"Andra Pradesh","Arunachal Pradesh","Rajasthan","Bihar","Chattisgarh","Assam","Gujarat","Haryana","Maharashtra"};
    String cityraj[]={"Jaipur","Ajmer","Jodhpur","Bikaner","Pilani","Udaipur","Kota"};
    String cityandp[]={"Visakhapatnam","Guntur","Vijayawada","Tirupati","Machilipatnam","Madanapalle","Ongole"};
    String type[]={"Residential","Commercial","Industrial","Agriculture"};
    String state,city,proptype;
    Spinner spstate,spcity,sptype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        spstate=findViewById(R.id.spinnerstate);
        getSupportActionBar().setTitle("Show Me Home!");
        spcity=findViewById(R.id.spinnercity);
        sptype=findViewById(R.id.spinnertype);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,states);
        spstate.setAdapter(arrayAdapter);
        spstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,cityandp);
                        spcity.setAdapter(arrayAdapter);break;
                    case 1:
                        break;
                    case 2:ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,cityraj);
                        spcity.setAdapter(arrayAdapter1);break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city=spcity.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> typeof=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,type);
        sptype.setAdapter(typeof);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tenant, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void search(View view)
    {
        ParseQuery<ParseObject> parseQuery=new ParseQuery<>("Properties");
        parseQuery.whereEqualTo("city",city);
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (int i=0;i<objects.size();i++)
                {
                    DataStructures.propnames.add(objects.get(i).getObjectId());
                }
            }
        });
        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
    }
}
