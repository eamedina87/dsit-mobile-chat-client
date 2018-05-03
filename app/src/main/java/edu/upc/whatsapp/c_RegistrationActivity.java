package edu.upc.whatsapp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.upc.whatsapp.REST_API.User_REST_API;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import entity.User;
import entity.UserInfo;

public class c_RegistrationActivity extends Activity {

  @BindView(R.id.register_email)
  TextInputEditText mEmail;
  @BindView(R.id.register_password)
  TextInputEditText mPassword;
  @BindView(R.id.register_name)
  TextInputEditText mName;
  @BindView(R.id.register_surname)
  TextInputEditText mSurname;
  @BindView(R.id.register_login)
  TextInputEditText mLogin;

  _GlobalState globalState;
  ProgressDialog progressDialog;
  User mUser = null;
  OperationPerformer operationPerformer;


  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    globalState = (_GlobalState)getApplication();
    setContentView(R.layout.c_registration);
    ButterKnife.bind(this);
  }



  @OnClick(R.id.registration_Button)
  public void onRegisterClick() {

    if (isUserComplete()){
        progressDialog = ProgressDialog.show(this, "RegistrationActivity", "Registering for service...");
        // if there's still a running thread doing something, we don't create a new one
        if (operationPerformer == null) {
            operationPerformer = new OperationPerformer(handler);
            operationPerformer.start();
        }
    }



  }

  private boolean isUserComplete() {
      //Create User and UserInfo
    boolean result = true;
    View view = null;
    UserInfo mUserInfo = new UserInfo();
    mUser = new User();

    if (TextUtils.isEmpty(mEmail.getText().toString())){
      result = false;
      view = mEmail;
      mEmail.setError(getString(R.string.view_incomplete));
    } else {
      mUser.setEmail(mEmail.getText().toString());
    }

    if (TextUtils.isEmpty(mName.getText().toString())){
      result = false;
      view = mName;
        mName.setError(getString(R.string.view_incomplete));
    } else {
      mUserInfo.setName(mName.getText().toString());
      mUser.setUserInfo(mUserInfo);
    }

    if (TextUtils.isEmpty(mSurname.getText().toString())){
      result = false;
      view = mSurname;
      mSurname.setError(getString(R.string.view_incomplete));
    } else {
      mUserInfo.setSurname(mSurname.getText().toString());
      mUser.setUserInfo(mUserInfo);
    }

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
          toastShow("Registration successful");
          //closeActivity
          finish();
          break;
        case -1:
          toastShow("Registration unsuccessful,\nemail account already in use");
          break;
        case -2:
          toastShow("Not registered, connection problem due to: " + msg.getData().getString("error"));
          System.out.println("--------------------------------------------------");
          System.out.println("error!!!");
          System.out.println(msg.getData().getString("error"));
          System.out.println("--------------------------------------------------");
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

        //TODO Comunicacion con la API
        UserInfo userInfo = User_REST_API.createUser_return_UserInfo(mUser);

        //Assign globalstate myuser
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
