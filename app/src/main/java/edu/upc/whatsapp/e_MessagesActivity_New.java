package edu.upc.whatsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.upc.whatsapp.REST_API.Message_REST_API;
import edu.upc.whatsapp.adapter.MyAdapter_messages;
import edu.upc.whatsapp.adapter.MyAdapter_messages_new;
import entity.Message;

//import edu.upc.whatsapp.comms.RPC;

public class e_MessagesActivity_New extends Activity {

  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.input)
  EditText input_text;
  @BindView(R.id.mybutton)
  Button button;
  @BindView(R.id.conversation)
  RecyclerView messageRecyclerView;
    @BindView(R.id.errorMessage)
    TextView mErrorMessage;
    @BindView(R.id.progressBar)
    ProgressBar mProgressbar;

  _GlobalState globalState;


  private MyAdapter_messages_new adapter;
  //private boolean enlarged = false, shrunk = true;

  private Timer timer;
    private fetchAllMessages_Task mFetchAllMessagesTask;
    private boolean isFetchAllMessagesTaskRunning = false;
    private Message mLastMessage;
    private Integer mRemoteUserId;
    private Integer mLocalUserId;

    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    globalState = (_GlobalState) getApplication();
    mLocalUserId = globalState.my_user.getId();
    mRemoteUserId = globalState.user_to_talk_to.getId();
    setContentView(R.layout.e_messages_new);
    ButterKnife.bind(this);

  }

  @Override
  protected void onResume() {
    super.onResume();
      title.setText("Talking with: " + globalState.user_to_talk_to.getName());
      setup_input_text();
    getMessagesFromAPI();
  }

    @Override
  protected void onPause() {
    super.onPause();
    if (mFetchAllMessagesTask !=null
            && isFetchAllMessagesTaskRunning){
        mFetchAllMessagesTask.cancel(true);
    }
    stopNewMessagesTimer();
  }

    private void setNewMessagesTimer() {
        timer = new Timer();
        timer.schedule(new fetchNewMessagesTimerTask(), 5000, 5000);
    }

    private void stopNewMessagesTimer() {
        if (timer!=null){
            timer.cancel();
        }
    }

    private void getMessagesFromAPI() {
        if (!isFetchAllMessagesTaskRunning) {
            mFetchAllMessagesTask = new fetchAllMessages_Task();
            mFetchAllMessagesTask.execute(mLocalUserId, mRemoteUserId);
        }
    }

  private class fetchAllMessages_Task extends AsyncTask<Integer, Void, List<Message>> {

    @Override
    protected void onPreExecute() {
      //progressDialog = ProgressDialog.show(e_MessagesActivity_New.this,
      //    "MessagesActivity", "downloading messages...");
        mProgressbar.setVisibility(View.VISIBLE);
      isFetchAllMessagesTaskRunning = true;
    }

    @Override
    protected List<Message> doInBackground(Integer... userIds) {
      //return Message_REST_API.retrieveMessages(0, 100);
        return Message_REST_API.retrieveMessages(userIds[0], userIds[1]);
    }

    @Override
    protected void onPostExecute(List<Message> all_messages) {
      //progressDialog.dismiss();
        mProgressbar.setVisibility(View.GONE);
      isFetchAllMessagesTaskRunning = false;
      if (all_messages == null) {
        toastShow("There's been an error downloading the messages");
      } else {

          if (all_messages.size()==0){
            mErrorMessage.setVisibility(View.VISIBLE);
            messageRecyclerView.setVisibility(View.INVISIBLE);
          } else {
              mErrorMessage.setVisibility(View.GONE);
              messageRecyclerView.setVisibility(View.VISIBLE);
              toastShow(all_messages.size()+" messages downloaded");
              mLastMessage = all_messages.get(all_messages.size()-1);
              if (adapter==null){
                  adapter = new MyAdapter_messages_new(all_messages, globalState.my_user);
                  messageRecyclerView.setAdapter(adapter);
              } else {
                  adapter.swapMessage(all_messages);
              }
              setNewMessagesTimer();
          }


      }
    }
  }

  private class fetchNewMessages_Task extends AsyncTask<Integer, Void, List<Message>> {

    @Override
    protected List<Message> doInBackground(Integer... userIds) {
      return Message_REST_API.retrieveNewMessages(userIds[0], userIds[1], mLastMessage);
    }

    @Override
    protected void onPostExecute(List<Message> new_messages) {
      if (new_messages == null) {
        //toastShow("There's been an error downloading new messages");
      } else {
        if (new_messages.size()>0) {
            toastShow(new_messages.size()+" new message/s downloaded");
            mLastMessage = new_messages.get(new_messages.size() - 1);
            if (adapter == null) {
                adapter = new MyAdapter_messages_new(new_messages, globalState.my_user);
                messageRecyclerView.setAdapter(adapter);
            } else {
                adapter.swapMessage(new_messages);
            }
        }

      }
    }
  }

  public void addText(final View view) {
    String content = input_text.getText().toString();

    if (content!=null && !content.isEmpty()){
        Message newMessage = new Message();
        newMessage.setContent(content);
        newMessage.setDate(new Date());
        newMessage.setUserReceiver(globalState.user_to_talk_to);
        newMessage.setUserSender(globalState.my_user);
        new SendMessage_Task().execute(newMessage);

        input_text.setText("");

        //to hide the soft keyboard after sending the message:
        InputMethodManager inMgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inMgr.hideSoftInputFromWindow(input_text.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }



  }
  private class SendMessage_Task extends AsyncTask<Message, Void, Boolean> {

    @Override
    protected void onPreExecute() {
      toastShow("sending message");
    }

    @Override
    protected Boolean doInBackground(Message... messages) {
        try {
            Message_REST_API.myCreateMessage(messages[0]);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean resultOk) {
      if (resultOk) {
        toastShow("message sent");
          new fetchNewMessages_Task().execute(mLocalUserId, mRemoteUserId);

      } else {
        toastShow("There's been an error sending the message");
      }
    }
  }

  private class fetchNewMessagesTimerTask extends TimerTask {
    @Override
    public void run() {
      new fetchNewMessages_Task().execute(mLocalUserId, mRemoteUserId);
    }
  }

  private void setup_input_text(){

    input_text = (EditText) findViewById(R.id.input);
    button = (Button) findViewById(R.id.mybutton);
    button.setEnabled(false);

    //to be notified when the content of the input_text is modified:
    input_text.addTextChangedListener(new TextWatcher() {

      public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
      }

      public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
      }

      public void afterTextChanged(Editable arg0) {
        if (arg0.toString().equals("")) {
          button.setEnabled(false);
        } else {
          button.setEnabled(true);
        }
      }
    });


    //to program the send soft key of the soft keyboard:
    input_text.setOnEditorActionListener(new OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND) {
          addText(null);
          handled = true;
        }
        return handled;
      }
    });

  /*
    //to detect a change on the height of the window on the screen:
    input_text.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        int screenHeight = input_text.getRootView().getHeight();
        Rect r = new Rect();
        input_text.getWindowVisibleDisplayFrame(r);
        int visibleHeight = r.bottom - r.top;
        int heightDifference = screenHeight - visibleHeight;
        if (heightDifference > 50 && !enlarged) {
          LayoutParams layoutparams = input_text.getLayoutParams();
          layoutparams.height = layoutparams.height * 2;
          input_text.setLayoutParams(layoutparams);
          enlarged = true;
          shrunk = false;
          conversation.post(new Runnable() {
            @Override
            public void run() {
              conversation.setSelection(conversation.getCount() - 1);
            }
          });
        }
        if (heightDifference < 50 && !shrunk) {
          LayoutParams layoutparams = input_text.getLayoutParams();
          layoutparams.height = layoutparams.height / 2;
          input_text.setLayoutParams(layoutparams);
          shrunk = true;
          enlarged = false;
        }
      }
    });
    */
  }

  private void toastShow(String text) {
    Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
    toast.setGravity(0, 0, 200);
    toast.show();
  }

}
