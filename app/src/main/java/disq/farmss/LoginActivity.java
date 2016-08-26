package disq.farmss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity{

    DatabaseHelper dbhelper = new DatabaseHelper(this);
    EditText editTextMobile;
    EditText editTextPassword;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextMobile = (EditText)findViewById(R.id.ETmobile);
        editTextPassword = (EditText)findViewById(R.id.ETpassword);
        sharedPref = this.getSharedPreferences("MyPref", 0);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LoginActivity_layout);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ActionBar.LayoutParams.FILL_PARENT, // Width of TextView
                ActionBar.LayoutParams.FILL_PARENT); // Height of TextView

        // Apply the layout parameters to TextView widget
        tv.setLayoutParams(lp);

        // Set text to display in TextView
        tv.setText(ab.getTitle());
        tv.setTextSize(20);
        tv.setTextColor(getResources().getColor(R.color.actionbar_text));

        // Set TextView text alignment to center
        tv.setGravity(Gravity.CENTER);

        // Set the ActionBar display option
        ab.setDisplayOptions(ab.DISPLAY_SHOW_CUSTOM);

        // Finally, set the newly created TextView as ActionBar custom view
        ab.setCustomView(tv);
    }

    public void OnButtonClick(View view){
        if (view.getId()==R.id.btnlogin){
            if(Validate()){
                FindUser();
            }else {
                editTextMobile.setText("");
                editTextPassword.setText("");
                //Toast t = Toast.makeText(LoginActivity.this,getString(R.string.EnterMobPass),Toast.LENGTH_SHORT);
                //t.show();
            }
        }
        if (view.getId()==R.id.btnregister){
            Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(i);
        }
    }

    private void FindUser() {
        String mobile = editTextMobile.getText().toString();
        String pass = editTextPassword.getText().toString();
        String password1 = dbhelper.validateUser(mobile);
        if(pass.equals(password1)){
            Toast toast = Toast.makeText(LoginActivity.this, getString(R.string.LoginSuccess),Toast.LENGTH_SHORT);
            toast.show();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Login_Mobile", mobile);
            editor.commit();
            Intent i = new Intent(LoginActivity.this,AddPlotSizeActivity.class);
            startActivity(i);
            finish();
        }else {
            Toast toast = Toast.makeText(LoginActivity.this,getString(R.string.WrongMobPass),Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    Boolean Validate() {
            boolean valid=true;
            if(editTextMobile.getText().toString().trim().length()<10){
            editTextMobile.setError(getString(R.string.EnterValidMob));
            valid = false;
            }else{
            editTextMobile.setError(null);
            }

            if(editTextPassword.getText().toString().trim().length()==0){
            editTextPassword.setError(getString(R.string.EnterValidPass));
            valid = false;
            }else{
            editTextPassword.setError(null);
            }
        return valid;
    }


}
