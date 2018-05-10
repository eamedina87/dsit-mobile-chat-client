package edu.upc.whatsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class a_WelcomeActivity extends Activity implements View.OnClickListener {

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
            Intent intent = new Intent(a_WelcomeActivity.this, d_UsersListActivity.class);
            startActivity(intent);

            finish();
        }

    }
    public void onClick(View arg0) {

      if (arg0 == findViewById(R.id.welcomeLoginButton)) {
        Intent intent = new Intent(this, b_LoginActivity.class);
        startActivity(intent);
      }
      if (arg0 == findViewById(R.id.welcomeRegisterButton)) {
        Intent intent = new Intent(this, c_RegistrationActivity.class);
        startActivity(intent);
      }
    }
    
}
