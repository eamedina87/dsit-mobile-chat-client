package edu.upc.whatsapp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import edu.upc.whatsapp.REST_API.User_REST_API;
import entity.User;
import entity.UserInfo;

public class b_LoginActivity extends Activity{

  @BindView(R.id.login_login)
  TextInputEditText mLogin;
  @BindView(R.id.login_password)
  TextInputEditText mPassword;

  _GlobalState globalState;
  ProgressDialog progressDialog;
  User user;
  OperationPerformer operationPerformer;
  private User mUser;

    @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    globalState = (_GlobalState)getApplication();
    setContentView(R.layout.b_login);
    ButterKnife.bind(this);

  }

  @OnClick(R.id.login_Button)
  public void onLoginClick() {
      if (isInfoComplete()){
          //TODO CHANGE for ProgressBar
        progressDialog =
                ProgressDialog.show(this,
                        "LoginActivity",
                        "Logging into the server...");
        // if there's still a running thread doing something,
          // we don't create a new one
        if (operationPerformer == null) {
          operationPerformer = new OperationPerformer(handler);
          operationPerformer.start();
        }

      }

  }

  private boolean isInfoComplete() {
    boolean result = true;
    View view = null;
    mUser = new User();

    if (TextUtils.isEmpty(mPassword.getText().toString())){
      result = false;
      view = mPassword;
      mPassword.setError(getString(R.string.view_incomplete));
    } else {
      mUser.setPassword(mPassword.getText().toString());
    }


    if (TextUtils.isEmpty(mLogin.getText().toString())){
      result = false;
      view = mLogin;
      mLogin.setError(getString(R.string.view_incomplete));
    } else {
      mUser.setLogin(mLogin.getText().toString());
    }

    if (view!=null)
      view.requestFocus();

    return result;
  }

  Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {

      operationPerformer = null;
      progressDialog.dismiss();

      switch (msg.getData().getInt("result")) {
        case 1:
          toastShow("Login successful");
          //Go to Messages Activity
          Intent intent =
                  new Intent(
                          b_LoginActivity.this,
                          e_MessagesActivity.class);
          startActivity(intent);
          
          finish();
          break;
        case -1:
          toastShow("Login unsuccessful, try again please");
          break;
        case -2:
          toastShow("Not logged in, " +
                  "connection problem due to: "
                  + msg.getData().getString("error"));
          break;
      }
    }
  };

  private class OperationPerformer extends Thread {

    Handler handler;

    OperationPerformer(Handler h) {
      handler = h;
    }

    @Override
    public void run() {
      Message msg = handler.obtainMessage();
      Bundle b = new Bundle();
      try {

        UserInfo userInfo = User_REST_API.loginUser(mUser);
        globalState.my_user = userInfo;

        if (globalState.my_user != null) {
          b.putInt("result", 1);
        } else {
          b.putInt("result", -1);
        }
      } catch (Exception e) {
        b.putInt("result", -2);
        b.putString("error", e.getMessage());
      }
      msg.setData(b);
      handler.sendMessage(msg);
    }
  }

  private void toastShow(String text) {
    Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
    toast.setGravity(0, 0, 200);
    toast.show();
  }
}
