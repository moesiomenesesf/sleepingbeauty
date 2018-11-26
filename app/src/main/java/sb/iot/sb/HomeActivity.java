package sb.iot.sb;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.khmelenko.lab.miband.MiBand;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomeActivity extends AppCompatActivity {

    private TextView mUserNameView;

    private MiBand miBand;

    private FirebaseAuth mAuth;

    private HashMap<String,BluetoothDevice> devices = new HashMap();

    private ArrayAdapter<String> adapter;

    private CompositeDisposable disposables = new CompositeDisposable();


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

        final Intent i = new Intent(HomeActivity.this, LampActivity.class);
        Button mLampada = (Button) findViewById(R.id.lampada);
        mLampada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });

        this.miBand = new MiBand(this);

        this.adapter = new ArrayAdapter(this,R.layout.item, new ArrayList());

        Button startScanButton = (Button) findViewById(R.id.starScanButton);

        startScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("scanstarted","Scan started ... ");

                Disposable disposable = miBand.startScan().subscribe(handleScanResult(), handleScanError());
                disposables.add(disposable);

            }
        });

        Button stopScanButton = (Button) findViewById(R.id.stopScanButton);

        stopScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("scanstopped","Scan started ... ");

                Disposable disposable = miBand.stopScan().subscribe(handleScanResult(), handleScanError());
                disposables.add(disposable);

            }
        });

        ListView lv = (ListView) findViewById(R.id.listView);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView)view;

                String item = textView.getText().toString();

                if(devices.containsKey(item)){

                    Disposable disposable = miBand.stopScan().subscribe(handleScanResult(), handleScanError());

                    disposables.add(disposable);

                    Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
                    intent.putExtra("device", devices.get(item));
                    startActivity(intent);
                    finish();

                }


            }
        });

    }


    private Consumer<ScanResult> handleScanResult(){

        return new Consumer<ScanResult>() {
            @Override
            public void accept(ScanResult scanResult) throws Exception {

                BluetoothDevice device = scanResult.getDevice();

                Log.d("scanres","Scan results: name:" +  device.getAddress() + ", uuid: " +
                Arrays.toString(device.getUuids()) +", add:" +  device.getAddress() + ", type:" + device.getType() +", bondState:" + device.getBondState() + ", rssi:" + scanResult.getRssi());

                String item = device.getName() + "|" + device.getAddress();

                if (!devices.containsKey(item)) {
                    devices.put(item, device);
                    adapter.add(item);
                }

            }
        };

    }

    private Consumer<Throwable> handleScanError(){

        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

                Log.d("scanerror",throwable.getMessage());

            }
        };

    }

    private void signOut(){

        mAuth.signOut();

        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }


}
