package edu.upc.whatsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class a_WelcomeActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_CODE_LOGIN = 100;
    private static final int REQUEST_CODE_REGISTER = 101;
    private _GlobalState globalState;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        setContentView(R.layout.a_welcome);
        
        ((Button)findViewById(R.id.welcomeLoginButton)).setOnClickListener(this);
        ((Button)findViewById(R.id.welcomeRegisterButton)).setOnClickListener(this);

        globalState = (_GlobalState)getApplication();
        if (globalState.my_user!=null){
            //Is Logged In
            showUsersList();
        }

    }

    private void showUsersList() {
        Intent intent = new Intent(a_WelcomeActivity.this, d_UsersListActivity_New.class);
        startActivity(intent);
        finish();
    }

    public void onClick(View arg0) {
        switch (arg0.getId()){
            case R.id.welcomeLoginButton:{
                Intent intent = new Intent(this, b_LoginActivity.class);
                startActivityForResult(intent, REQUEST_CODE_LOGIN);
                break;
            }
            case R.id.welcomeRegisterButton:{
                Intent intent = new Intent(this, c_RegistrationActivity.class);
                startActivityForResult(intent, REQUEST_CODE_REGISTER);
                break;
            }
        }

        /*
      if (arg0 == findViewById(R.id.welcomeLoginButton)) {
        Intent intent = new Intent(this, b_LoginActivity.class);
        startActivity(intent);
      }
      if (arg0 == findViewById(R.id.welcomeRegisterButton)) {
        Intent intent = new Intent(this, c_RegistrationActivity.class);
        startActivity(intent);
      }
      */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_LOGIN:{
                showUsersList();
                break;
            }
            case REQUEST_CODE_REGISTER:{
                showUsersList();
                break;
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
