// Generated code from Butter Knife. Do not modify!
package edu.upc.whatsapp;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class e_MessagesActivity_ViewBinding implements Unbinder {
  private e_MessagesActivity target;

  @UiThread
  public e_MessagesActivity_ViewBinding(e_MessagesActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public e_MessagesActivity_ViewBinding(e_MessagesActivity target, View source) {
    this.target = target;

    target.title = Utils.findRequiredViewAsType(source, R.id.title, "field 'title'", TextView.class);
    target.input_text = Utils.findRequiredViewAsType(source, R.id.input, "field 'input_text'", EditText.class);
    target.button = Utils.findRequiredViewAsType(source, R.id.mybutton, "field 'button'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    e_MessagesActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.title = null;
    target.input_text = null;
    target.button = null;
  }
}
