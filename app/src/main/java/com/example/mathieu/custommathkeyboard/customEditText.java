package com.example.mathieu.custommathkeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Mathieu on 2017-03-29.
 */

public class customEditText extends android.support.v7.widget.AppCompatEditText {

    public customEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        /* ------------------------------------------------
         * code from: http://stackoverflow.com/a/12331404
         * author: Zain Ali
         * consulted date: 1 April 2017
         */
        //disable the copy, cut and other context menu
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        //--------------------------------------------------

    }

    /**
     * This override function to disable the text drag and drop
     * @param event
     * @return
     */
    @Override
    public boolean onDragEvent(DragEvent event) {
        return  true;
    }
}
