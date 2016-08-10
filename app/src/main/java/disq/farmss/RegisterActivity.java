package disq.farmss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {

    EditText name,mobile,password1,password2;
    DatabaseHelper dbhelper = new DatabaseHelper(this);
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name =(EditText)findViewById(R.id.TFname);
        mobile =(EditText)findViewById(R.id.TFmobile);
        password1 =(EditText)findViewById(R.id.TFpass1);
        password2 =(EditText)findViewById(R.id.TFpass2);
        sharedPref = this.getSharedPreferences("MyPref", 0);
    }
    public void onRegisterBtnClick(View view){
        if(view.getId()==R.id.ButtonRegister){
            if(ValidateRegister()){
                if(checkUser()){
                    AddUser();
                }
                else{
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                    alertDialog.setIcon(R.mipmap.alert);
                    alertDialog.setTitle(getString(R.string.Alert));
                    alertDialog.setMessage(getString(R.string.MobAlreadyReg));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.Ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        }
    }

    private boolean checkUser() {
        String new_mobile = mobile.getText().toString();
        boolean validate_mobile = dbhelper.validateMobile(new_mobile);
        return validate_mobile;
    }

    private boolean ValidateRegister() {
        boolean valid=true;
        if(name.getText().toString().length()==0){
            name.setError(getString(R.string.EnterName));
            valid=false;
        }else{
            Pattern ps= Pattern.compile("^[a-zA-Z ]+$");
            Matcher ms= ps.matcher(name.getText().toString());
            boolean bs =ms.matches();
            if(bs==false){
                name.setError(getString(R.string.Alphabetic_Only));
                valid=false;
            }
            else name.setError(null);
        }

        if(mobile.getText().toString().length()<10){
            mobile.setError(getString(R.string.EnterMobNo));
            valid=false;
        }else{
            mobile.setError(null);
        }

        if (password1.getText().toString().length()==0){
            password1.setError(getString(R.string.EnterPass));
            valid=false;
        }else {
            password1.setError(null);
        }
        return valid;
    }

    private void AddUser() {
        String str_name = name.getText().toString();
        String str_mobile = mobile.getText().toString();
        String str_password1 = password1.getText().toString();
        String str_password2 = password2.getText().toString();

        if (!str_password1.equals(str_password2)){
            Toast t1 = Toast.makeText(RegisterActivity.this,getString(R.string.MatchPass),Toast.LENGTH_SHORT);
            t1.show();
        }else{
            UserDataDBMethod c =  new UserDataDBMethod();
            c.setMobile(str_mobile);
            c.setName(str_name);
            c.setPass(str_password1);

            dbhelper.insertData(c);
            Toast t2 = Toast.makeText(RegisterActivity.this,getString(R.string.RegSuccess), Toast.LENGTH_SHORT);
            t2.show();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Register_Mobile",str_mobile);
            Intent intent = new Intent(RegisterActivity.this,RegSuccessfulActivity.class);
            startActivity(intent);
            finish();

        }
    }

}
