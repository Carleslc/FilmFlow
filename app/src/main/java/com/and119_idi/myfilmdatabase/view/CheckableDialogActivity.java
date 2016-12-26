package com.and119_idi.myfilmdatabase.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

/**
 * Created by albert on 9/12/16.
 */
public abstract class CheckableDialogActivity extends DialogActivity {

    protected AlertDialog alertDialog;
    protected boolean unsavedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);
        initAlertDialog();
    }

    protected void initAlertDialog() {
        alertDialog = new AlertDialog.Builder(CheckableDialogActivity.this).create();
        alertDialog.setTitle("Confirm");
        alertDialog.setMessage("Are you sure you want to cancel? \nIntroduced information will be lost");
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        fi();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    protected abstract boolean checkData(boolean showErrors);

    protected boolean isEmpty(TextView text) {
        return text.getText().toString().trim().isEmpty();
    }

    @Override
    public void onBackPressed() {
        fi();
    }

    protected void fi() {
        if (unsavedData) alertDialog.show();
        else super.fi();
    }

}
