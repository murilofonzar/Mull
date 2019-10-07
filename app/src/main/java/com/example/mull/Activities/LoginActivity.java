package com.example.mull.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mull.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity  {

    private TextView btnRegistro;
    private ImageView btnInstagram;
    private ImageView btnFacebook;
    private ImageView btnTwitter;
    private ImageView btnLinkedin;
    private Button btnLogin;
    private TextInputEditText txtUsuario;
    private TextInputEditText txtSenha;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsuario = findViewById(R.id.edEmail);
        txtSenha = findViewById(R.id.edSenha);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnInstagram = findViewById(R.id.btnInstagram);
        btnInstagram.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                paginaInstagram();
            }
        });

        btnFacebook = findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                paginaFacebook();
            }
        });

        btnTwitter = findViewById(R.id.btnTwitter);
        btnTwitter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                paginaTwitter();
            }
        });

        btnLinkedin = findViewById(R.id.btnLinkedin);
        btnLinkedin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                paginaLinkedin();
            }
        });

        btnRegistro = findViewById(R.id.edRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paginaRegistro();
            }
        });

        auth = FirebaseAuth.getInstance();
        txtUsuario = findViewById(R.id.edEmail);
        txtSenha = findViewById(R.id.edSenha);
    }

    public void login() {
        String txt_email = txtUsuario.getText().toString();
        String txt_senha = txtSenha.getText().toString();

        if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_senha)){
            Toast.makeText(LoginActivity.this, "Campos em branco.", Toast.LENGTH_SHORT).show();
        }
        else {
            auth.signInWithEmailAndPassword(txt_email,txt_senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Login e/ou Senha inválidos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void paginaRegistro(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void paginaInstagram () {

        Uri uri = Uri.parse("http://instagram.com/_u/meinmull");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (
                ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/meinmull")));
        }

    }

    public void paginaTwitter () {

        Uri uri = Uri.parse("http://twitter.com/meinmull");
        Intent Twitt = new Intent(Intent.ACTION_VIEW, uri);

        Twitt.setPackage("com.twitter.android");

        try {
            startActivity(Twitt);
        } catch (
                ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://twitter.com/meinmull")));
        }

    }

    public void paginaLinkedin () {

        Uri uri = Uri.parse("http://linkedin.com/meinmull");
        Intent Linked = new Intent(Intent.ACTION_VIEW, uri);

        Linked.setPackage("com.linkedin.android");

        try {
            startActivity(Linked);
        } catch (
                ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://linkedin.com/meinmull")));
        }

    }

    public void paginaFacebook () {

        Uri uri = Uri.parse("http://facebook.com/meinmull");
        Intent Face = new Intent(Intent.ACTION_VIEW, uri);

        Face.setPackage("com.facebook.android");

        try {
            startActivity(Face);
        } catch (
                ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://facebook.com/meinmull")));
        }

    }



}
