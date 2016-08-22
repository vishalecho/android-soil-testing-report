package disq.farmss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class UserProfileActivity extends ActionBarActivity {

    /*EditText ETName,ETState,ETDistrict,ETtehsil,ETVillage,ETPincode,ETMobile;
    DatabaseHelper dbhelper = new DatabaseHelper(this);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        /*ETName = (EditText)findViewById(R.id.Edit_name);
        ETState = (EditText)findViewById(R.id.Edit_state);
        ETDistrict = (EditText)findViewById(R.id.Edit_district);
        ETtehsil = (EditText)findViewById(R.id.Edit_tehsil);
        ETVillage = (EditText)findViewById(R.id.Edit_village);
        ETPincode = (EditText)findViewById(R.id.Edit_pincode);
        ETMobile = (EditText)findViewById(R.id.Edit_mobile);
        UserDataDBMethod c =  new UserDataDBMethod();*/

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
}
