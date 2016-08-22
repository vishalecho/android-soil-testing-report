package disq.farmss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends ActionBarActivity implements OnItemSelectedListener {

    EditText name,mobile,password1,password2,village,pincode;
    Spinner district,tehsil;
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
        district = (Spinner)findViewById(R.id.spDistrict);
        tehsil = (Spinner)findViewById(R.id.spTehsil);
        village =(EditText)findViewById(R.id.TFvillage);
        pincode =(EditText)findViewById(R.id.TFpincode);

        district.setOnItemSelectedListener(this);
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
        if(name.getText().toString().trim().length()==0){
            name.setError(getString(R.string.EnterName));
            valid=false;
        }else{
            Pattern ps= Pattern.compile("^[a-zA-Z ]+$");
            Matcher ms= ps.matcher(name.getText().toString().trim());
            boolean bs =ms.matches();
            if(bs==false){
                name.setError(getString(R.string.Alphabetic_Only));
                valid=false;
            }
            else name.setError(null);
        }

        if(mobile.getText().toString().trim().length()<10){
            mobile.setError(getString(R.string.EnterMobNo));
            valid=false;
        }else{
            mobile.setError(null);
        }

        if (password1.getText().toString().trim().length()==0){
            password1.setError(getString(R.string.EnterPass));
            valid=false;
        }else {
            password1.setError(null);
        }

        if(district.getSelectedItem().toString().trim() == "Select District"){
            Toast.makeText(RegisterActivity.this, getString(R.string.Select_District), Toast.LENGTH_SHORT).show();
            valid=false;
        }

        if(tehsil.getSelectedItem().toString().trim() == "Select Tehsil"){
            Toast.makeText(RegisterActivity.this, getString(R.string.Select_Tehsil), Toast.LENGTH_SHORT).show();
            valid=false;
        }

        if (village.getText().toString().trim().length()==0){
            village.setError(getString(R.string.Enter_Village));
            valid=false;
        }else{
            Pattern ps= Pattern.compile("^[a-zA-Z ]+$");
            Matcher ms= ps.matcher(village.getText().toString().trim());
            boolean bs =ms.matches();
            if(bs==false){
                village.setError(getString(R.string.Enter_only_Alphabets));
                valid=false;
            }
            else village.setError(null);
        }

        if(pincode.getText().toString().trim().length()<6){
            pincode.setError(getString(R.string.Enter_Valid_Pincode));
            valid=false;
        }else{
            pincode.setError(null);
        }

        return valid;
    }

    private void AddUser() {
        String str_name = name.getText().toString().trim();
        String str_mobile = mobile.getText().toString().trim();
        String str_password1 = password1.getText().toString().trim();
        String str_password2 = password2.getText().toString().trim();
        String str_district = district.getSelectedItem().toString().trim();
        String str_tehsil = tehsil.getSelectedItem().toString().trim();
        String str_village = village.getText().toString().trim();
        String str_pincode = pincode.getText().toString().trim();

        if (!str_password1.equals(str_password2)){
            Toast t1 = Toast.makeText(RegisterActivity.this,getString(R.string.MatchPass),Toast.LENGTH_SHORT);
            t1.show();
        }else{
            boolean isInserted;
            UserDataDBMethod c =  new UserDataDBMethod();
            c.setMobile(str_mobile);
            c.setName(str_name);
            c.setPass(str_password1);
            c.setDistrict(str_district);
            c.setTehsil(str_tehsil);
            c.setVillage(str_village);
            c.setPincode(str_pincode);

            isInserted = dbhelper.insertData(c);
            if (isInserted) {
                Toast t2 = Toast.makeText(RegisterActivity.this, getString(R.string.RegSuccess), Toast.LENGTH_SHORT);
                t2.show();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Login_Mobile", str_mobile);
                editor.commit();
                Intent intent = new Intent(RegisterActivity.this, RegSuccessfulActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast t3 = Toast.makeText(RegisterActivity.this, getString(R.string.Try_again), Toast.LENGTH_SHORT);
                t3.show();
                name.setText("");
                mobile.setText("");
                password1.setText("");
                password2.setText("");
                village.setText("");
                pincode.setText("");
            }

        }
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        // TODO Auto-generated method stub
        String sp1 = String.valueOf(district.getSelectedItem());
        //Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        if(sp1.contentEquals("Select District")){
            List<String> list = new ArrayList<String>();
            list.add("Select Tehsil");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            tehsil.setAdapter(dataAdapter);
        }

        if (sp1.contentEquals("Nashik")) {
            List<String> list = new ArrayList<String>();
            list.add("Select Tehsil");
            list.add("Baglan");
            list.add("Chandvad");
            list.add("Deola");
            list.add("Dindori");
            list.add("Igatpuri");
            list.add("Kalwan");
            list.add("Malegaon");
            list.add("Nandgaon");
            list.add("Nashik");
            list.add("Niphad");
            list.add("Peint");
            list.add("Sinnar");
            list.add("Surgana");
            list.add("Trimbakeshwar");
            list.add("Yevla");


            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            tehsil.setAdapter(dataAdapter2);
        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
