package com.and119_idi.myfilmdatabase.view;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Carlos Lázaro Costa on 5/1/17.
 */
class ConfirmDialog extends AlertDialog {

    ConfirmDialog(@NonNull Context context, @NonNull String message,
                  @NonNull OnClickListener yesListener, @NonNull OnClickListener noListener) {
        super(context);
        setCanceledOnTouchOutside(false);
        setTitle("Confirm");
        setMessage(message);
        setButton(BUTTON_POSITIVE, context.getString(android.R.string.yes), yesListener);
        setButton(BUTTON_NEGATIVE, context.getString(android.R.string.no), noListener);
    }
}
