package com.example.mathieu.custommathkeyboard;

import android.content.Context;
import android.graphics.LightingColorFilter;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by Mathieu on 2017-03-13.
 */

public class mathematicalKeyboard extends GridLayout{

    private Button[] key;
    private Button confirmBtn;
    private Button limBtn;
    private ImageButton backspaceBtn;
    private EditText typingZone;
    private int screenWidth;
    private Handler backspaceHandler;

    private String[] keyText = {

            "1", "2", // 0 to 9 for number
            "3", "4",
            "5", "6",
            "7", "8",
            "9", "0",

            "a", "b", //10 to 27 for letters
            "e", "f",
            "g", "h",
            "i", "j",
            "n", "o",
            "p", "r",
            "s", "t",
            "w", "x",
            "y", "z",

            "+", "-", //28 to 45 for symbols
            "•", "/",
            "=", "÷",
            "≤", "≥",
            "<", ">",
            "±", ",",
            "(", ")",
            "[", "]",
            "!", "√",

            "≈", "≠", //46 to 64 for greek letters and other
            "—", "∞",
            "|", "′",
            "ഽ",
            "α", "π",
            "β", "Δ",
            "μ", "φ",
            "Σ", "θ",
            "λ", "ω",
            "δ", "σ",

            "", "", //empty string
            "", "",
            "", "",
            "", "",
            "",


            //"⟶", "⇀",
};


    public mathematicalKeyboard(Context context) {
        super(context);
    }

    public mathematicalKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);

        setColumnCount(10);

        setBackgroundResource(R.color.Gray);

        //params for common buttons
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT);

        //creating button and adding them to the layout
        key = new Button[31];
        for(int i = 0; i < 28; i++){
            key[i] = new Button(context);
            key[i].getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
            key[i].setLayoutParams(btnParams);
            key[i].setOnClickListener(keyClickListener);
            key[i].setText(keyText[i]);
            key[i].setTransformationMethod(null);
            addView(key[i]);
        }


        //Adding backspace Button
        backspaceBtn = new ImageButton(context);
        backspaceBtn.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
        backspaceBtn.setLayoutParams(btnParams);
        backspaceBtn.setImageResource(R.drawable.backspace);
        //backspaceBtn.setOnClickListener(backspaceListener);
        backspaceBtn.setOnTouchListener(backspaceToutchListener);
        addView(backspaceBtn);

        //button 27 to 30 are button to change between symbol and letters
        for(int i = 28; i < 31; i++){
            key[i] = new Button(context);
            key[i].getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
            key[i].setLayoutParams(btnParams);
            key[i].setOnClickListener(keyClickListener);
            key[i].setTransformationMethod(null);
            addView(key[i]);
        }

        key[28].setText("abc");
        key[29].setText("#+=");
        key[30].setText("βΔഽ");


        //Adding confirm Button
        confirmBtn = new Button(context);
        confirmBtn.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
        confirmBtn.setLayoutParams(btnParams);
        confirmBtn.setText(R.string.confirm);
        addView(confirmBtn);

        //creating lim button
        limBtn = new Button(context);
        limBtn.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
        limBtn.setLayoutParams(btnParams);
        limBtn.setTransformationMethod(null);
        limBtn.setOnClickListener(keyClickListener);



        backspaceHandler = new Handler(); //initialization of the Handler 
    }

    /**
     * This method open the mathematical keyboard
     * @param writingZone The textView where we want to  write
     * @param screenDimensions The screen dimensions
     */
    public void openKeyboard(EditText writingZone, DisplayMetrics screenDimensions){
        screenWidth = screenDimensions.widthPixels;
        setKeyWidith();
        setVisibility(VISIBLE);
        typingZone = writingZone;
    }


    /**
     * Setting the button layout param and the width
     */
    private void setKeyWidith(){
        int keyWidith = screenWidth / 10;


        for(int i = 0; i < 28; i++){
            ViewGroup.LayoutParams params = key[i].getLayoutParams();
            params.width = keyWidith;
            key[i].setLayoutParams(params);
        }

        for(int i = 28; i < 31; i++){
            //setting rowSpan
            GridLayout.LayoutParams LP = (GridLayout.LayoutParams)key[i].getLayoutParams();
            LP.columnSpec = GridLayout.spec((i - 28)*2, 2);
            key[i].setLayoutParams(LP);

            ViewGroup.LayoutParams params = key[i].getLayoutParams();
            params.width = keyWidith * 2;
            key[i].setLayoutParams(params);
        }

        //backspace button
        GridLayout.LayoutParams backspaceLP = (GridLayout.LayoutParams)backspaceBtn.getLayoutParams();
        backspaceLP.columnSpec = GridLayout.spec(8, 2);
        backspaceBtn.setLayoutParams(backspaceLP);

        ViewGroup.LayoutParams params = backspaceBtn.getLayoutParams();
        params.width = keyWidith * 2;
        backspaceBtn.setLayoutParams(params);

        //comfirm button
        GridLayout.LayoutParams confirmLP = (GridLayout.LayoutParams)confirmBtn.getLayoutParams();
        confirmLP.columnSpec = GridLayout.spec(6, 4);
        confirmBtn.setLayoutParams(confirmLP);

        params = confirmBtn.getLayoutParams();
        params.width = keyWidith * 4;
        confirmBtn.setLayoutParams(params);
    }

    /**
     * Listener called when a key is pressed
     */
    final OnClickListener keyClickListener = new OnClickListener() {
        public void onClick(final View v) {
            Button key = (Button) v;
            switch(key.getText().toString()) {
                case "#+=":
                    changeToSymbols();
                    break;
                case "abc":
                    changeToLetters();
                    break;
                case "βΔഽ":
                    changeToGreek();
                    break;
                default:
                    typingZone.getText().insert(typingZone.getSelectionStart(), key.getText().toString());
                    break;
            }
        }
    };

    /**
     * Listener called when the backspace is toutched
     */
   final OnTouchListener backspaceToutchListener = new OnTouchListener() {
       @Override
       public boolean onTouch(View v, MotionEvent event) {
           boolean holding = true;
           while (holding){
               typingZone.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
               SystemClock.sleep(500);
               if(event.getAction() == MotionEvent.ACTION_UP)
                   holding = false;
           }
           return true;
       }
   };


    /**
     * Listener called when the backspace is clicked
     */
    final OnClickListener backspaceListener = new OnClickListener() {
        public void onClick(final View v) {
            typingZone.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        }

    };


    /**
     * change the current keyboard key to the symbols
     */
    private void changeToSymbols(){
        if(limBtn.getText().toString() == "lim"){
            removeViewAt(26);
            addView(key[26], 26);
            addView(key[27], 27);
            limBtn.setText("");
        }

        for(int i = 0; i < 10; i++)
            key[i].setText(keyText[i]);

        for (int i = 10; i < 28; i++)
            key[i].setText(keyText[i +18]);
    }

    /**
     * change the current keyboard key to letters
     */
    private void changeToLetters(){

        if(limBtn.getText().toString() == "lim"){
            removeViewAt(26);
            addView(key[26], 26);
            addView(key[27], 27);
            limBtn.setText("");
        }

        for (int i = 0; i < 28; i++)
            key[i].setText(keyText[i]);
    }

    /**
     * change the current keyboard key to greek letters and other
     */
    private void changeToGreek(){

        if(limBtn.getText().toString() == ""){
            removeViewAt(26);
            removeViewAt(26);

            addView(limBtn, 26);

            //lim Button
            GridLayout.LayoutParams LimLP = (GridLayout.LayoutParams)limBtn.getLayoutParams();
            LimLP.columnSpec = GridLayout.spec(6, 2);
            limBtn.setLayoutParams(LimLP);

            ViewGroup.LayoutParams params = limBtn.getLayoutParams();
            params.width = screenWidth /5;
            limBtn.setLayoutParams(params);

            limBtn.setText("lim");
        }

        for (int i = 0; i < 28; i++)
           key[i].setText(keyText[i +46]);
    }

}
