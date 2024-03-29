package com.example.mull.BackgroundWorkers;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import com.example.mull.Activities.RegisterActivity;
import com.example.mull.Dao.Address;
import com.google.gson.Gson;

public class AddressRequest extends AsyncTask <Void, Void, Address> {

    private WeakReference<RegisterActivity> activity;

    public AddressRequest( RegisterActivity activity ){
        this.activity = new WeakReference<>( activity );
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.get().lockFields( true );
    }

    @Override
    protected Address doInBackground(Void... voids) {

        try{
            String jsonString = JsonRequest.request( activity.get().getUriZipCode() );
            Gson gson = new Gson();

            return gson.fromJson(jsonString, Address.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Address address) {
        super.onPostExecute(address);

        if( activity.get() != null ){
            activity.get().lockFields( false );

            if( address != null ){
                activity.get().setDataViews(address);
            }
        }
    }
}
