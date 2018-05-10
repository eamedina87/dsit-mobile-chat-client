// Generated code from Butter Knife. Do not modify!
package edu.upc.whatsapp;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class b_LoginActivity_ViewBinding implements Unbinder {
  private b_LoginActivity target;

  private View view2131230811;

  @UiThread
  public b_LoginActivity_ViewBinding(b_LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public b_LoginActivity_ViewBinding(final b_LoginActivity target, View source) {
    this.target = target;

    View view;
    target.mLogin = Utils.findRequiredViewAsType(source, R.id.login_login, "field 'mLogin'", TextInputEditText.class);
    target.mPassword = Utils.findRequiredViewAsType(source, R.id.login_password, "field 'mPassword'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.login_Button, "method 'onLoginClick'");
    view2131230811 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLoginClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    b_LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLogin = null;
    target.mPassword = null;

    view2131230811.setOnClickListener(null);
    view2131230811 = null;
  }
}
