package in.blogspot.tecnopandit.showmehome;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        for (int i=0;i<DataStructures.propnames.size();i++)
        {
            ParseQuery<ParseObject> prop=new ParseQuery<ParseObject>("Proterties");
            //prop.whereEqualTo("objectId",DataStructures.propnames.get(i));
            try {
               LatLng loca=new LatLng(prop.get(DataStructures.propnames.get(i)).getParseGeoPoint("loc").getLatitude(),prop.get(DataStructures.propnames.get(i)).getParseGeoPoint("loc").getLongitude());
               //mMap.addMarker(new MarkerOptions().position(loca).title(prop.get(DataStructures.propnames.get(i)).get("propname").toString()));
                // mMap.moveCamera(CameraUpdateFactory.newLatLng(loca));
                Log.i("ObjectsLoc:::::   ",Double.toString(loca.latitude+loca.longitude));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}
