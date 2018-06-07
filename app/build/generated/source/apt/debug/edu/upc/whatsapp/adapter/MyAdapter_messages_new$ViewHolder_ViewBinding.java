// Generated code from Butter Knife. Do not modify!
package edu.upc.whatsapp.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import edu.upc.whatsapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyAdapter_messages_new$ViewHolder_ViewBinding implements Unbinder {
  private MyAdapter_messages_new.ViewHolder target;

  @UiThread
  public MyAdapter_messages_new$ViewHolder_ViewBinding(MyAdapter_messages_new.ViewHolder target,
      View source) {
    this.target = target;

    target.date = Utils.findRequiredViewAsType(source, R.id.date, "field 'date'", TextView.class);
    target.message = Utils.findRequiredViewAsType(source, R.id.texto, "field 'message'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyAdapter_messages_new.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.date = null;
    target.message = null;
  }
}
