package com.example.todolistapp;

import android.text.Editable;
import android.text.TextWatcher;

public class TextWatcherExtended implements TextWatcher {

    private int index;

    public TextWatcherExtended(int index)
    {
        super();
        this.index = index;

    }

    public int getIndex()
    {
        return index;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
