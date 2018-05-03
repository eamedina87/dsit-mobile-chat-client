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

public class c_RegistrationActivity_ViewBinding implements Unbinder {
  private c_RegistrationActivity target;

  private View view2131230840;

  @UiThread
  public c_RegistrationActivity_ViewBinding(c_RegistrationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public c_RegistrationActivity_ViewBinding(final c_RegistrationActivity target, View source) {
    this.target = target;

    View view;
    target.mEmail = Utils.findRequiredViewAsType(source, R.id.register_email, "field 'mEmail'", TextInputEditText.class);
    target.mPassword = Utils.findRequiredViewAsType(source, R.id.register_password, "field 'mPassword'", TextInputEditText.class);
    target.mName = Utils.findRequiredViewAsType(source, R.id.register_name, "field 'mName'", TextInputEditText.class);
    target.mSurname = Utils.findRequiredViewAsType(source, R.id.register_surname, "field 'mSurname'", TextInputEditText.class);
    target.mLogin = Utils.findRequiredViewAsType(source, R.id.register_login, "field 'mLogin'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.registration_Button, "method 'onRegisterClick'");
    view2131230840 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRegisterClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    c_RegistrationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEmail = null;
    target.mPassword = null;
    target.mName = null;
    target.mSurname = null;
    target.mLogin = null;

    view2131230840.setOnClickListener(null);
    view2131230840 = null;
  }
}
