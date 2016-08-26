package disq.farmss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Timestamp;
import java.util.Date;

public class TestResultActivity extends ActionBarActivity {

    private static Button button_selectcrop;
    EditText pH, N, P, K;
    String TAG = "disq.farmss:";
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        sharedPref = this.getSharedPreferences("MyPref", 0);

        //Generating Current TimeStamp
        Date date= new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        String Time_Stamp = ts.toString();
        Log.i(TAG, Time_Stamp);

        //Generating unique Transaction id
        String mob = sharedPref.getString("Login_Mobile",null);
        String Transaction_Id = ts.toString()+" "+mob;
        SharedPreferences.Editor editor1 = sharedPref.edit();
        Log.i(TAG, Transaction_Id);
        editor1.putString("Transaction_ID",Transaction_Id);
        editor1.putString("TimeStamp",Time_Stamp);
        editor1.commit();

        pH = (EditText) findViewById(R.id.phvalue);
        N = (EditText) findViewById(R.id.nvalue);
        P = (EditText) findViewById(R.id.pvalue);
        K = (EditText) findViewById(R.id.kvalue);
        String temp1 = sharedPref.getString("BTValue",null);
        String Zero = "0";
        pH.setText(Zero);
        N.setText(Zero);
        P.setText(temp1);
        K.setText(Zero);
        button_selectcrop = (Button) findViewById(R.id.btn_selectCrop);
        button_selectcrop.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedPref.edit();

                        int int_pH = Integer.parseInt(pH.getText().toString());
                        Log.i(TAG, "ph= "+String.valueOf(int_pH));
                        editor.putInt("pH",int_pH);

                        int int_n= Integer.parseInt(N.getText().toString());
                        Log.i(TAG, "n= "+String.valueOf(int_n));
                        editor.putInt("N",int_n);

                        int int_p= Integer.parseInt(P.getText().toString());
                        Log.i(TAG, "p= "+String.valueOf(int_p));
                        editor.putInt("P",int_p);

                        int int_k= Integer.parseInt(K.getText().toString());
                        Log.i(TAG, "k= "+String.valueOf(int_k));
                        editor.putInt("K",int_k);

                        Intent intent = new Intent(TestResultActivity.this,SelectCropActivity.class);

                        editor.commit();
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
        alertDialog.setIcon(R.mipmap.alert);
        alertDialog.setTitle(getString(R.string.leave_application));
        alertDialog.setMessage(getString(R.string.Leave_application_msg));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.Yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.No), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                    }
                }
        );
        alertDialog.show();
    }
}
