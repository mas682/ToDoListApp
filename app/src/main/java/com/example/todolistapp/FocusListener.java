package com.example.todolistapp;

import android.view.View;

public class FocusListener implements View.OnFocusChangeListener {

        private int index;

        public FocusListener(int index)
        {
            super();
            this.index = index;
        }


        public int getIndex()
        {
            return index;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus)
        {

        }
}
