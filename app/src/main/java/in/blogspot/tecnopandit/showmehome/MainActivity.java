package in.blogspot.tecnopandit.showmehome;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    Button renter,tenant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        renter=findViewById(R.id.renter);
        tenant=findViewById(R.id.tenant);
        getSupportActionBar().hide();
        /*if (ParseUser.getCurrentUser()!=null)
        {
            if (ParseUser.getCurrentUser().get("typeofuser").toString().equals("renter"))
            {
                Intent intent=new Intent(getApplicationContext(),RenterAct.class);
                startActivity(intent);
            }
            else
            {
                Intent intent=new Intent(getApplicationContext(),TenantAct.class);
                startActivity(intent);
            }

        }*/
    }
    public void gotologin(View view)
    {
        Intent intent=new Intent(getApplicationContext(),LoginAct.class);
        startActivity(intent);
    }
}
