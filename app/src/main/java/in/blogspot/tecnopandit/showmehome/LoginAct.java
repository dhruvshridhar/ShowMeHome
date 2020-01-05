package in.blogspot.tecnopandit.showmehome;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginAct extends AppCompatActivity {
EditText name,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginact);
        name=findViewById(R.id.name);
        password=findViewById(R.id.pass);
        getSupportActionBar().hide();
    }
    public void loginuser(View view)
    {
        if (TextUtils.isEmpty(name.getText().toString())||TextUtils.isEmpty(password.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Username/Password cannot be blank!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            ParseUser.logInInBackground(name.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user!=null&&e==null)
                    {
                        Toast.makeText(getApplicationContext(),"Welcome: "+user.getUsername(),Toast.LENGTH_SHORT).show();
                        if ((user.get("typeofuser").toString()).equals("renter"))
                        {
                            Intent intent=new Intent(getApplicationContext(),RenterAct.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent=new Intent(getApplicationContext(),TenantAct.class);
                            startActivity(intent);
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
    public void signup(View view)
    {
        Intent intent=new Intent(getApplicationContext(),SignUp.class);
        startActivity(intent);
    }
}
