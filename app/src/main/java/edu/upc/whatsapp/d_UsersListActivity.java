package edu.upc.whatsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
//import edu.upc.whatsapp.comms.RPC;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.upc.whatsapp.REST_API.UserInfo_REST_API;
import edu.upc.whatsapp.adapter.MyAdapter_users;
import entity.UserInfo;
import java.util.List;

public class d_UsersListActivity extends Activity implements ListView.OnItemClickListener {

  _GlobalState globalState;
  MyAdapter_users adapter;
  //ProgressDialog progressDialog;

  @BindView(R.id.listView)
  ListView usersListView;
    @BindView(R.id.errorMessage)
    TextView errorMessage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    globalState = (_GlobalState) getApplication();
    setContentView(R.layout.d_userslist);
      ButterKnife.bind(this);

  }

    @Override
    protected void onResume() {
        super.onResume();
        getUsersFromAPI();
    }

    @Override
  public void onItemClick(AdapterView<?> l, View v, int position, long id) {

    //Open Message Activity with this user
    globalState.user_to_talk_to = (UserInfo) adapter.getItem(position);
    Intent intent = new Intent(d_UsersListActivity.this, e_MessagesActivity.class);
    startActivity(intent);
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

  private class DownloadUsers_Task extends AsyncTask<Void, Void, List<UserInfo>> {

    @Override
    protected void onPreExecute() {
      /*progressDialog = ProgressDialog.show(d_UsersListActivity.this, "UsersListActivity",
        "downloading the users...");*/
      progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected List<UserInfo> doInBackground(Void... nothing) {
      return UserInfo_REST_API.allUserInfos();
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
        usersListView.setVisibility(View.INVISIBLE);
    }

    private void setupUsersListEmpty() {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(getString(R.string.users_msg_empty));
        errorMessage.setEnabled(false);
        usersListView.setVisibility(View.INVISIBLE);
    }

    private void setupUsersList(List<UserInfo> users) {
        errorMessage.setVisibility(View.INVISIBLE);
        usersListView.setVisibility(View.VISIBLE);
        if (adapter==null) {
            adapter = new MyAdapter_users(this, users);
            usersListView.setAdapter(adapter);
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

}
