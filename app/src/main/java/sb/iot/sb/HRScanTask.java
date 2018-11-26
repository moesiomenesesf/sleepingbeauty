package sb.iot.sb;

import android.os.AsyncTask;
import android.util.Log;

public class HRScanTask extends AsyncTask<Void,Void,Void> {

    private MiBandHRHandleListener hrListener;

    HRScanTask(MiBandHRHandleListener hrListener){
        super();

        this.hrListener = hrListener;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void integer) {
        super.onPostExecute(integer);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Boolean continuar = true;

        Integer hr = 81;

        Integer counter = 0;

        while(continuar) {

            this.hrListener.onReceive(hr);
            hr--;


            try {

                Thread.sleep(1000);

            } catch (InterruptedException iex){

                iex.printStackTrace();

            }




        }

        return null;
    }
}

