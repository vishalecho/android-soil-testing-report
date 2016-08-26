package disq.farmss.cloud;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class RestReaderAsync extends AsyncTask<Void, Void, Void> {
    String TAG = "RestReader";

    Context context = null;

    public RestReaderAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        long obsCount = getObservationCount();
        SharedPreferences sharedPref;
        sharedPref = context.getSharedPreferences("MyPref", 0);
        String time = getCurrentSystemTime(); //This should be sent from the raspberry pi along with soil parameters
        String Str_ph = String.valueOf(sharedPref.getInt("pH",0));
        String Str_n = String.valueOf(sharedPref.getInt("N",0));
        String Str_p = String.valueOf(sharedPref.getInt("P",0));
        String Str_k = String.valueOf(sharedPref.getInt("K",0));
        //String Str_suggested_ph = String.valueOf(sharedPref.getInt("SpH",0));
        String Str_suggested_n = String.valueOf(sharedPref.getInt("Suggested_N",0));
        String Str_suggested_p = String.valueOf(sharedPref.getInt("Suggested_P",0));
        String Str_suggested_k = String.valueOf(sharedPref.getInt("Suggested_K",0));
        pushToCloud(++obsCount, time, "N", "nvalue", Str_n, "double");
        pushToCloud(++obsCount, time, "P", "pvalue", Str_p, "double");
        pushToCloud(++obsCount, time, "K", "kvalue", Str_k, "double");
        pushToCloud(++obsCount, time, "pH", "phvalue", Str_ph, "double");
        pushToCloud(++obsCount, time, "SN", "nvalue", Str_suggested_n, "double");
        pushToCloud(++obsCount, time, "SP", "pvalue", Str_suggested_p, "double");
        pushToCloud(++obsCount, time, "SK", "kvalue", Str_suggested_k, "double");
       // pushToCloud(++obsCount, time, "SpH", "phvalue", Str_ph, "double");
        setObservationCount(obsCount);
        return null;
    }

    private void pushToCloud(long observationID, String timeStamp, String soilParameter, String unit, String value, String dataType) {
        if (Constants.isProxyRequired) {
            System.setProperty("http.proxyHost", Constants.proxyIP);
            System.setProperty("http.proxyPort", Constants.proxyPort);
        }
        URL url = null;
        try {
            url = new URL(Constants.cloudURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            String postURL = "key=dGNzLUludGVHcmEtRGlnaXRhbCBGYXJtaW5nLUt3aWskZW5zZS12MQ==&jsonData={\"request\":\"InsertObservation\",\"service\":\"SOS\",\"version\":\"2.0.0\",\"offering\":\"off-SoilHealthTest\",\"observation\":{\"query_id\":\"" + "SoilHealthTestObs"+observationID + "\",\"sensor\":\"SoilHealthTestParams\",\"feature\":\"SoilHealthTest\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[51.935101100104916,7.651968812254194]},\"phenomenonTime\":\"" + timeStamp + "\",\"resultTime\":\"" + timeStamp + "\",\"output\":[{\"property\": \"" + soilParameter + "\",\"unit\": \"" + unit + "\",\"value\": \"" + value + "\",\"type\": \"" + dataType + "\"}]}}";
            writer.write(postURL);
            writer.flush();
            String line = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, line);
            }
            writer.close();
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setObservationCount(long count) {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("OBSERVATIONCOUNT", count);
        editor.commit();
    }

    private long getObservationCount() {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        //SharedPreferences.Editor editor = preferences.edit();
        return preferences.getLong("OBSERVATIONCOUNT", 0);
    }

    private String getCurrentSystemTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new Date());
    }
}
