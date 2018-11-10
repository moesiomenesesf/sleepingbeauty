package sb.iot.sb;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zhaoxiaodan.miband.ActionCallback;
import com.zhaoxiaodan.miband.MiBand;
import com.zhaoxiaodan.miband.listeners.HeartRateNotifyListener;
import com.zhaoxiaodan.miband.listeners.NotifyListener;
import com.zhaoxiaodan.miband.model.UserInfo;
import com.zhaoxiaodan.miband.model.BatteryInfo;

public class HeartRateScanner {

    private MiBand miband;

    private final ScanCallback scanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result)
        {
            BluetoothDevice device = result.getDevice();
            Log.d("hrs",
                    "name:" + device.getName() +
                    ",uuid:"+ device.getUuids() + ",add:"
                    + device.getAddress() + ",type:"
                    + device.getType() + ",bondState:"
                    + device.getBondState() + ",rssi:" + result.getRssi());
        }

    };

    public HeartRateScanner(Context context, Intent intent){

        this.miband = new MiBand(context);

        MiBand.startScan(scanCallback);
        MiBand.stopScan(scanCallback);

        final BluetoothDevice device = intent.getParcelableExtra("device");


        miband.connect(device, new ActionCallback() {

            @Override
            public void onSuccess(Object data)
            {
                Log.d("connectionopen","connect success");
            }

            @Override
            public void onFail(int errorCode, String msg)
            {
                Log.d("connectionfail","connect fail, code:"+errorCode+",mgs:"+msg);
            }
        });

        miband.setDisconnectedListener(new NotifyListener(){

            @Override
            public void onNotify(byte[] data) {
                Log.d("disconnected","disconnected");
            }

        });


        miband.setUserInfo(new UserInfo(20111111, 1, 32, 180, 55, "Feixe de gordura ", 0));

        miband.setHeartRateScanListener(new HeartRateNotifyListener()
        {
            @Override
            public void onNotify(int heartRate){
                Log.d("heart rate", "heart rate: "+ heartRate);
            }
        });






        miband.setSensorDataNotifyListener(new NotifyListener() {
            @Override
            public void onNotify(byte[] data){
                int i = 0;

                int index = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;  // Serial number
                int d1 = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;
                int d2 = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;
                int d3 = (data[i++] & 0xFF) | (data[i++] & 0xFF) << 8;

            }
        });



        miband.pair(new ActionCallback() {
            @Override
            public void onSuccess(Object data) {
                //changeStatus("pair succ");
                //Do stuff, success case
                startScan();
            }

            @Override
            public void onFail(int errorCode, String msg) {
                //changeStatus("pair fail");
                //Handle error, fail case
            }
        });

    }

    public void startScan(){

        miband.startHeartRateScan();



    }

    public void stopScan(){



    }

    //Signal strength
    public void readRssi(){

        miband.readRssi(new ActionCallback() {

            @Override
            public void onSuccess(Object data)
            {
                Log.d("rssisuccess", "rssi:"+(int)data);
            }

            @Override
            public void onFail(int errorCode, String msg)
            {
                Log.d("rssifail", "readRssi fail");
            }
        });

    }

    public void getBatteryInfo(){


        miband.getBatteryInfo(new ActionCallback() {

            @Override
            public void onSuccess(Object data)
            {
                BatteryInfo info = (BatteryInfo)data;
                Log.d("batteryreadsuccess", info.toString());
                //cycles:4,level:44,status:unknow,last:2015-04-15 03:37:55
            }

            @Override
            public void onFail(int errorCode, String msg)
            {
                Log.d("batteryreadfail", "battery read fail");
            }
        });
    }

    public void enableDataNotify(){

        miband.enableSensorDataNotify();

    }

}
