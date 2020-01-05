package in.blogspot.tecnopandit.showmehome;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class RenterAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LocationManager locationManager;
    Location loca;
    LocationListener locationListener;
    Spinner spinnerstate,spinnercity,spinnerlocality;
    EditText pincode,rent,description,title;
    String states[]={"Andra Pradesh","Arunachal Pradesh","Rajasthan","Bihar","Chattisgarh","Assam","Gujarat","Haryana","Maharashtra"};
    String cityraj[]={"Jaipur","Ajmer","Jodhpur","Bikaner","Pilani","Udaipur","Kota"};
    String cityandp[]={"Visakhapatnam","Guntur","Vijayawada","Tirupati","Machilipatnam","Madanapalle","Ongole"};
    String locality[]={"Jawahar nagar","Rajapark","Vaishali Nagar","Pratap Nagar","C-Scheme","Agra Road","Ajmer Road","Delhi Road","Sikar Road","Sitapura","Jagatpura"};
    String address="",citystr,statestr,localstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Hey! "+ParseUser.getCurrentUser().getUsername());
        spinnerstate=findViewById(R.id.spinnerstate);
        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        spinnercity=findViewById(R.id.spinnercity);
        spinnerlocality=findViewById(R.id.spinnerlocality);
        pincode=findViewById(R.id.pincode);
        rent=findViewById(R.id.rent);
        description=findViewById(R.id.description);
        title=findViewById(R.id.editText6);
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,states);
        spinnerstate.setAdapter(arrayAdapter);
        spinnerstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statestr=spinnerstate.getItemAtPosition(position).toString();
                switch (position)
                {
                    case 0:ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,cityandp);
                        spinnercity.setAdapter(arrayAdapter);break;
                    case 1:
                        break;
                    case 2:ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,cityraj);
                        spinnercity.setAdapter(arrayAdapter1);break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnercity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                citystr=spinnercity.getItemAtPosition(position).toString();
                if (citystr.equals("Jaipur")||citystr.equals("Ajmer"))
                {
                    ArrayAdapter<String> localityadap=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,locality);
                    spinnerlocality.setAdapter(localityadap);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerlocality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                localstr=spinnerlocality.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }
    }

    public void getlocation(View view)
    {
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                loca=location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (loca!=null)
        {
            Toast.makeText(getApplicationContext(),"Got Your Location!!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"An Unknown Error Occurred :(",Toast.LENGTH_SHORT).show();
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }

    }
    public void post(View view)
    {
        address=localstr+" "+citystr+" "+statestr;
        ParseObject parseObject=new ParseObject("Properties");
        parseObject.put("propname",title.getText().toString());
        parseObject.put("phoneno",ParseUser.getCurrentUser().get("phoneno").toString());
        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
        parseObject.put("rent",rent.getText().toString());
        parseObject.put("description",description.getText().toString());
        parseObject.put("address",address);
        parseObject.put("city",citystr);
        ParseGeoPoint parseGeoPoint=new ParseGeoPoint(loca.getLatitude(),loca.getLongitude());
        parseObject.put("loc",parseGeoPoint);
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null)
                {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Property listed successfully!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        getMenuInflater().inflate(R.menu.renter, menu);
        return true;
    }
public void logout()
{
    ParseUser.logOut();
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
            logout();
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

}
