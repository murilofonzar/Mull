package com.example.mull.BackgroundWorkers;

import android.content.Context;
import android.text.TextWatcher;
import android.text.Editable;

import com.example.mull.Activities.RegisterActivity;


public class ZipCodeListener implements TextWatcher {
    private Context context;

    public ZipCodeListener( Context context ){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String zipCode = editable.toString();

        if( zipCode.length() == 9 ){
            new AddressRequest( (RegisterActivity) context ).execute();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
}
