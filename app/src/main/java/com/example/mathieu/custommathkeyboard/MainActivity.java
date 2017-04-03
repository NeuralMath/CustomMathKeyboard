package com.example.mathieu.custommathkeyboard;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    mathematicalKeyboard mathKeyboard;
    RelativeLayout mainLayout;
    customEditText writingZone;
    DisplayMetrics screenDimensions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenDimensions = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenDimensions);

        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        writingZone = (customEditText) findViewById(R.id.writingZone);


        /* ------------------------------------------------
         * code from: http://stackoverflow.com/a/13975236
         * author: Eddie Sullivan
         * consulted date: 22 March 2017
         */
            // Update the EditText so it won't popup Android's own keyboard, since I have my own.
            writingZone.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.onTouchEvent(event);
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    return true;
                }
            });
        //--------------------------------------------------

        mathKeyboard = (mathematicalKeyboard) findViewById(R.id.keyboard);

        writingZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mathKeyboard.openKeyboard(writingZone, screenDimensions);
            }
        });
        }
}
