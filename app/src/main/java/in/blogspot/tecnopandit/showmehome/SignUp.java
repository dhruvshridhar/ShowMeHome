package in.blogspot.tecnopandit.showmehome;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
public class SignUp extends AppCompatActivity {
EditText name,password,emailid,mobileno;
String type="";
RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        getSupportActionBar().hide();
        emailid=findViewById(R.id.emailid);
        mobileno=findViewById(R.id.mobileno);
        rg=findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.renterbutt:
                            // Pirates are the best
                            type="renter";
                        break;
                    case R.id.tenantbutt:
                            // Ninjas rule
                            type="tenant";
                        break;
                }
            }
        });
    }

    public void signup(View view)
    {
        if (TextUtils.isEmpty(name.getText().toString())||TextUtils.isEmpty(password.getText().toString())||TextUtils.isEmpty(emailid.getText().toString())||TextUtils.isEmpty(mobileno.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Username/Password/Email cannot be empty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            ParseUser parseUser=new ParseUser();
            parseUser.setUsername(name.getText().toString());
            parseUser.setPassword(password.getText().toString());
            parseUser.setEmail(emailid.getText().toString());
            parseUser.put("phoneno",mobileno.getText().toString());
            parseUser.put("typeofuser",type);
            parseUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null)
                    {
                        Toast.makeText(getApplicationContext(),"Sign up successful",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
