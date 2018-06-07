// Generated code from Butter Knife. Do not modify!
package edu.upc.whatsapp;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class d_UsersListActivity_ViewBinding implements Unbinder {
  private d_UsersListActivity target;

  private View view2131230785;

  private View view2131230815;

  @UiThread
  public d_UsersListActivity_ViewBinding(d_UsersListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public d_UsersListActivity_ViewBinding(final d_UsersListActivity target, View source) {
    this.target = target;

    View view;
    target.usersListView = Utils.findRequiredViewAsType(source, R.id.listView, "field 'usersListView'", ListView.class);
    view = Utils.findRequiredView(source, R.id.errorMessage, "field 'errorMessage' and method 'onErrorClick'");
    target.errorMessage = Utils.castView(view, R.id.errorMessage, "field 'errorMessage'", TextView.class);
    view2131230785 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onErrorClick();
      }
    });
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.logout, "method 'onLogoutClick'");
    view2131230815 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLogoutClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    d_UsersListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.usersListView = null;
    target.errorMessage = null;
    target.progressBar = null;

    view2131230785.setOnClickListener(null);
    view2131230785 = null;
    view2131230815.setOnClickListener(null);
    view2131230815 = null;
  }
}
