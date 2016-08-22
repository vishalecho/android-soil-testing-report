package disq.farmss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
public class FoundDeviceActivity extends ActionBarActivity {
    private static Button button_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_founddevice);
        onClickButtonListner();
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

    private void onClickButtonListner() {
        button_result=(Button)findViewById(R.id.reFindMachine);
        button_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoundDeviceActivity.this,TestResultActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
