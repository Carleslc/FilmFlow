package com.and119_idi.myfilmdatabase.view;

import android.os.Bundle;
import android.widget.TextView;

import com.and119_idi.myfilmdatabase.R;

/**
 * Created by albert on 9/12/16.
 */
public abstract class CheckableDialogActivity extends DialogActivity {

    protected ConfirmDialog mDialog;
    protected boolean mUnsavedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);
        initAlertDialog();
    }

    protected void initAlertDialog() {
        mDialog = new ConfirmDialog(CheckableDialogActivity.this,
                getString(R.string.cancel_warning),
                (dialog, which) -> {
                    dialog.dismiss();
                    mUnsavedData = false;
                    fi();
                },
                (dialog, which) -> dialog.dismiss());
        mDialog.create();
    }

    protected abstract boolean checkData(boolean showErrors);

    protected boolean isEmpty(TextView text) {
        return text.getText().toString().trim().isEmpty();
    }

    protected void fi() {
        if (mUnsavedData) mDialog.show();
        else super.fi();
    }

}
