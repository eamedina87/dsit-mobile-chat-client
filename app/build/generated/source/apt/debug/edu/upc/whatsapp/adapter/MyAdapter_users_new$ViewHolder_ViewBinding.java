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

public class MyAdapter_users_new$ViewHolder_ViewBinding implements Unbinder {
  private MyAdapter_users_new.ViewHolder target;

  @UiThread
  public MyAdapter_users_new$ViewHolder_ViewBinding(MyAdapter_users_new.ViewHolder target,
      View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.user_name, "field 'name'", TextView.class);
    target.surname = Utils.findRequiredViewAsType(source, R.id.user_lastname, "field 'surname'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyAdapter_users_new.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.surname = null;
  }
}
