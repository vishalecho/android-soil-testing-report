package disq.farmss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;

public class AddPlotSizeActivity extends Activity {

    RadioGroup PlotType;
    RadioButton rb;
    EditText plotSize;
    int selectedID;
    HashMap<String ,String> convert = new HashMap<String,String>();
    Context context;
    String rb1;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plot_size);
        convert.put(getString(R.string.Guntha),getString(R.string.guntha));
        convert.put(getString(R.string.Acre),getString(R.string.acre));
        context = this;
        PlotType = (RadioGroup)findViewById(R.id.plotType);
        selectedID = PlotType.getCheckedRadioButtonId();
        rb = (RadioButton)findViewById(selectedID);
        plotSize = (EditText)findViewById(R.id.PlotSizeValue);
        rb1 = rb.getText().toString();
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
            if(plotSize.getText().toString().length()==0){
                plotSize.setBackgroundResource(R.drawable.edittextstyle);
            }else{
                plotSize.setError(null);
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
}
