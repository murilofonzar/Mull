package com.example.mull.Activities;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.mull.Fragments.CalendarioFragment;
import com.example.mull.Fragments.ChatFragment;
import com.example.mull.Fragments.FeedFragment;
import com.example.mull.Fragments.MullFragment;
import com.example.mull.Fragments.TipoColetaFragment;
import com.example.mull.Fragments.UserAlterarDadosCadastraisFragment;
import com.example.mull.Fragments.UserAlterarDadosPessoaisFragment;
import com.example.mull.Fragments.UserFragment;
import com.example.mull.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
implements FeedFragment.OnFragmentInteractionListener,
        MullFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener,
        UserFragment.OnFragmentInteractionListener,
        CalendarioFragment.OnFragmentInteractionListener,
        TipoColetaFragment.OnFragmentInteractionListener,
        UserAlterarDadosPessoaisFragment.OnFragmentInteractionListener,
        UserAlterarDadosCadastraisFragment.OnFragmentInteractionListener{

    public TextView mTextMessage;
    public final static Fragment fragment1 = new FeedFragment();
    public final static Fragment fragment2 = new MullFragment();
    public final static Fragment fragment3 = new ChatFragment();
    public final static Fragment fragment4 = new UserFragment();
    public final static Fragment fragment5 = new CalendarioFragment();
    public final static Fragment fragment6 = new TipoColetaFragment();
    public final static Fragment fragment7 = new UserAlterarDadosPessoaisFragment();
    public final static Fragment fragment8 = new UserAlterarDadosCadastraisFragment();

    public final FragmentManager fm = getSupportFragmentManager();

    public static Fragment active = fragment1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;
                case R.id.navigation_mull:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;
                case R.id.navigation_chat:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    return true;
                case R.id.navigation_conta:
                    fm.beginTransaction().hide(active).show(fragment4).commit();
                    active = fragment4;
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm.beginTransaction().add(R.id.main_container, fragment1,"1").commit();
        fm.beginTransaction().add(R.id.main_container, fragment2,"2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container, fragment3,"3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment4,"4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.main_container, fragment5,"5").hide(fragment5).commit();
        fm.beginTransaction().add(R.id.main_container, fragment6,"6").hide(fragment6).commit();
        fm.beginTransaction().add(R.id.main_container, fragment7,"7").hide(fragment7).commit();
        fm.beginTransaction().add(R.id.main_container, fragment8,"7").hide(fragment8).commit();
    }

}