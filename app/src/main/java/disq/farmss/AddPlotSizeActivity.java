package disq.farmss;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;

public class AddPlotSizeActivity extends ActionBarActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText plotSize;
    int selectedID;
    HashMap<String ,String> convert = new HashMap<String,String>();
    Context context;
    String rb1;
    String TAG = "disq.farmss:";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plot_size);
        plotSize = (EditText)findViewById(R.id.PlotSizeValue);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        try{
            switch (item.getItemId()) {
                case R.id.EditProfile:
                    startActivity(new Intent(this,UserProfileActivity.class));
                    break;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }catch(Exception e){

        }
        return true;
    }
    public void FindDevice(View view){
        if(view.getId()==R.id.addPlotSize){
            if(Validation()){
                radioGroup = (RadioGroup)findViewById(R.id.plotType);
                selectedID = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton)findViewById(selectedID);
                rb1 = (String) radioButton.getText();
                Log.d(TAG, "AreaType = "+rb1);

                if (rb1.equals(getString(R.string.Guntha)))
                {
                    convert.put(rb1,getString(R.string.guntha));
                }else if (rb1.equals(getString(R.string.Acre))){
                    convert.put(rb1,getString(R.string.acre));
                }
                context = this;

                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("land_type",convert.get(rb1));
                editor.putFloat("plot_size",Float.parseFloat(plotSize.getText().toString()));
                editor.commit();
                Intent intent = new Intent(AddPlotSizeActivity.this,FindDeviceActivity.class);
                startActivity(intent);
                finish();
            }



        }
    }

    private boolean Validation() {
        boolean valid=true;
        if(plotSize.getText().toString().length()==0){
            plotSize.setBackgroundResource(R.drawable.edittextstyle);
            valid = false;
        }else{
            plotSize.setError(null);
        }
        return valid;
    }
}
