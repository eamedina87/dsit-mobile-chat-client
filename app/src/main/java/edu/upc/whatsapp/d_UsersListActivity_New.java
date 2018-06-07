package edu.upc.whatsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.upc.whatsapp.REST_API.UserInfo_REST_API;
import edu.upc.whatsapp.adapter.MyAdapter_users;
import edu.upc.whatsapp.adapter.MyAdapter_users_new;
import entity.UserInfo;

//import edu.upc.whatsapp.comms.RPC;

public class d_UsersListActivity_New extends Activity implements MyAdapter_users_new.UserAdapterCallbacks {

  _GlobalState globalState;
  MyAdapter_users_new adapter;


  @BindView(R.id.usersView)
  RecyclerView usersRecyclerView;
    @BindView(R.id.errorMessage)
    TextView errorMessage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    globalState = (_GlobalState) getApplication();
    setContentView(R.layout.d_userslist_new);
      ButterKnife.bind(this);

  }

    @Override
    protected void onResume() {
        super.onResume();
        getUsersFromAPI();
    }



  @OnClick(R.id.errorMessage)
  public void onErrorClick(){
    getUsersFromAPI();
  }

    @OnClick(R.id.logout)
    public void onLogoutClick(){
        globalState.my_user = null;
        globalState.user_to_talk_to = null;
        finish();
    }


    private void getUsersFromAPI() {
        new DownloadUsers_Task().execute();
    }

    @Override
    public void OnUserClick(UserInfo userInfo) {
      globalState.user_to_talk_to = userInfo;
        Intent intent = new Intent(this, e_MessagesActivity_New.class);
        startActivity(intent);
    }

    private class DownloadUsers_Task extends AsyncTask<Void, Void, List<UserInfo>> {

    @Override
    protected void onPreExecute() {
      /*progressDialog = ProgressDialog.show(d_UsersListActivity.this, "UsersListActivity",
        "downloading the users...");*/
      progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected List<UserInfo> doInBackground(Void... nothing) {
      return UserInfo_REST_API.allUserInfos(globalState.my_user);
    }

    @Override
    protected void onPostExecute(List<UserInfo> users) {
      //progressDialog.dismiss();
        progressBar.setVisibility(View.INVISIBLE);
      if (users == null) {
        setupError();
      } else {
        //Show Users List
          if (users.isEmpty()){
              setupUsersListEmpty();
          } else {
              setupUsersList(users);
          }
      }
    }
  }

    private void setupError() {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(getString(R.string.users_msg_error));
        errorMessage.setEnabled(true);
        usersRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void setupUsersListEmpty() {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(getString(R.string.users_msg_empty));
        errorMessage.setEnabled(false);
        usersRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void setupUsersList(List<UserInfo> users) {
        errorMessage.setVisibility(View.INVISIBLE);
        usersRecyclerView.setVisibility(View.VISIBLE);
        if (adapter==null) {
            adapter = new MyAdapter_users_new(users, this);
            usersRecyclerView.setAdapter(adapter);
        } else {
            adapter.swapUsers(users);
        }
    }

    /*
    private void toastShow(String text) {
    Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
    toast.setGravity(0, 0, 200);
    toast.show();
  }*/

        /*
    @Override
  public void onItemClick(AdapterView<?> l, View v, int position, long id) {

    //Open Message Activity with this user
    globalState.user_to_talk_to = (UserInfo) adapter.getItem(position);
    Intent intent = new Intent(d_UsersListActivity_New.this, e_MessagesActivity.class);
    startActivity(intent);
  }
  */

}
