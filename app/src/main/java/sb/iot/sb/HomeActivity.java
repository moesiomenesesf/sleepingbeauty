package sb.iot.sb;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zhaoxiaodan.miband.MiBand;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    private TextView mUserNameView;


    private FirebaseAuth mAuth;

    private HeartRateScanner heartRateScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.mUserNameView = (TextView) findViewById(R.id.username);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        mUserNameView.setText("Usuario logado é o : " + user.getEmail());

        Button mEmailSignInButton = (Button) findViewById(R.id.signout);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });


        //heartRateScanner = new HeartRateScanner(getApplicationContext(),getIntent());


    }

    private void signOut(){

        mAuth.signOut();

        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }


}
