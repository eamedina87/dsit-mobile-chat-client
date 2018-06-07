// Generated code from Butter Knife. Do not modify!
package edu.upc.whatsapp;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class d_UsersListActivity_New_ViewBinding implements Unbinder {
  private d_UsersListActivity_New target;

  private View view2131230785;

  private View view2131230815;

  @UiThread
  public d_UsersListActivity_New_ViewBinding(d_UsersListActivity_New target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public d_UsersListActivity_New_ViewBinding(final d_UsersListActivity_New target, View source) {
    this.target = target;

    View view;
    target.usersRecyclerView = Utils.findRequiredViewAsType(source, R.id.usersView, "field 'usersRecyclerView'", RecyclerView.class);
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
    d_UsersListActivity_New target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.usersRecyclerView = null;
    target.errorMessage = null;
    target.progressBar = null;

    view2131230785.setOnClickListener(null);
    view2131230785 = null;
    view2131230815.setOnClickListener(null);
    view2131230815 = null;
  }
}
