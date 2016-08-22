package disq.farmss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class RegSuccessfulActivity extends ActionBarActivity {

    private static Button button_findMachine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsuccessful);
        onClickButtonListener();
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
    private void onClickButtonListener() {
        button_findMachine =(Button)findViewById(R.id.btn_deviceFound);
        button_findMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegSuccessfulActivity.this,AddPlotSizeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
