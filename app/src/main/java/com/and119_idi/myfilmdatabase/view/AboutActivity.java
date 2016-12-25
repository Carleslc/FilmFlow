package com.and119_idi.myfilmdatabase.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.and119_idi.myfilmdatabase.R;

/**
 * Created by albert on 23/12/16.
 */
public class AboutActivity extends DialogActivity {

    private ImageButton mMailButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        initCoolStuff();
    }

    private void initCoolStuff() {
        mMailButton = (ImageButton) findViewById(R.id.contact);
        mMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_SUBJECT, "2kg of your best marijuana please");
                intent.putExtra(Intent.EXTRA_EMAIL,
                        new String[] { getApplicationContext().getString(R.string.mail_albert),
                                       getApplicationContext().getString(R.string.mail_carlos) });
                startActivity(Intent.createChooser(intent, ""));
            }
        });
    }

    @Override
    protected boolean checkData(boolean showErrors) {
        return true;
    }
}
