package com.example.mull.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mull.Dao.Address;
import com.example.mull.BackgroundWorkers.Util;
import com.example.mull.BackgroundWorkers.ZipCodeListener;
import com.example.mull.BackgroundWorkers.Mask;
import com.example.mull.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText edNome;
    private EditText edUser;
    private EditText edCPF;
    private TextInputLayout layCPF;
    private EditText edCNPJ;
    private TextInputLayout layCNPJ;
    private Spinner edUF;
    private EditText edCEP;
    private EditText edComplemento;
    private EditText edCidade;
    private EditText edSenha;
    private EditText edRepSenha;
    private EditText edEmail;
    private EditText etZipCode;
    private EditText edEndereco;
    private EditText edNumero;
    private Util util;
    private RadioButton rbCPF;
    private RadioButton rbCNPJ;
    private RadioGroup rgCPFCNPJ;
    private TextView tvHide;
    private Button btnRegistrar;
    public FirebaseAuth auth;
    public DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edNome = findViewById(R.id.edNome);
        edCPF = findViewById(R.id.edCPF);

        edCNPJ = findViewById(R.id.edCNPJ);
        rbCNPJ = findViewById(R.id.rbCNPJ);
        rbCPF = findViewById(R.id.rbCPF);
        rgCPFCNPJ = findViewById(R.id.rbCPFCNPJ);
        layCNPJ = findViewById(R.id.txtinputCNPJ);
        layCPF = findViewById(R.id.txtinputCPF);

        edUser = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
        edRepSenha = findViewById(R.id.edrepSenha);
        edEmail = findViewById(R.id.edEmail);

        edCEP = findViewById(R.id.edCEP);
        edComplemento = findViewById(R.id.edComplemento);
        etZipCode = findViewById(R.id.edCEP);
        edCidade = findViewById(R.id.edCidade);
        edEndereco = findViewById(R.id.edEndereco);
        edNumero = findViewById(R.id.edNumero);
        edUF = findViewById(R.id.edUF);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        auth = FirebaseAuth.getInstance();


        Spinner spStates = findViewById(R.id.edUF);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this,
                        R.array.states,
                        R.layout.spin_layout);
        spStates.setAdapter(adapter);

        etZipCode.addTextChangedListener(new ZipCodeListener(this));

        edCNPJ.setVisibility(View.GONE);
        layCNPJ.setVisibility(View.GONE);
        edCEP.addTextChangedListener(Mask.insert(Mask.MaskType.CEP, edCEP));
        edCPF.addTextChangedListener(Mask.insert(Mask.MaskType.CPF, edCPF));
        edCNPJ.addTextChangedListener(Mask.insert(Mask.MaskType.CNPJ, edCNPJ));

        rgCPFCNPJ.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.rbCPF) {
                    edCNPJ.setVisibility(View.GONE);
                    layCNPJ.setVisibility(View.GONE);
                    edCPF.setVisibility(View.VISIBLE);
                    layCPF.setVisibility(View.VISIBLE);


                } else if (checkedId == R.id.rbCNPJ) {
                    edCNPJ.setVisibility(View.VISIBLE);
                    edCPF.setVisibility(View.GONE);
                    layCPF.setVisibility(View.GONE);
                    layCNPJ.setVisibility(View.VISIBLE);

                }
            }
        });


        util = new Util(this, R.id.edCEP,
                R.id.edCidade,
                R.id.edBairro,
                R.id.edComplemento,
                R.id.edEndereco,
                R.id.edUF,
                R.id.edNumero
        );

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_numero = edNumero.getText().toString();
                String txt_nome = edNome.getText().toString();
                String txt_usuario = edEmail.getText().toString();
                String txt_user = edUser.getText().toString();
                String txt_email = edEmail.getText().toString();
                String txt_senha = edSenha.getText().toString();
                String txt_repsenha = edRepSenha.getText().toString();
                String txt_cep = edCEP.getText().toString();
                String txt_endereco = edEndereco.getText().toString();
                String txt_CPF = edCPF.getText().toString();
                String txt_CNPJ = edCNPJ.getText().toString();
                String txt_cidade = edCidade.getText().toString();
                String txt_UF = edUF.getSelectedItem().toString();
                String txt_complemento = edComplemento.getText().toString();

                if (TextUtils.isEmpty(txt_CPF) || TextUtils.isEmpty(txt_CNPJ) || TextUtils.isEmpty(txt_nome) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_usuario)
                        || TextUtils.isEmpty(txt_senha) || TextUtils.isEmpty(txt_repsenha) || TextUtils.isEmpty(txt_cidade)|| TextUtils.isEmpty(txt_cep)
                        || TextUtils.isEmpty(txt_endereco) || TextUtils.isEmpty(txt_numero)){
                    Toast.makeText(RegisterActivity.this, "Todos os campos são necessários para o cadastro", Toast.LENGTH_SHORT).show();
                }

                else if (txt_senha.length()<6 && txt_senha!=txt_repsenha){
                    Toast.makeText(RegisterActivity.this, "A senha precisa conter mais do que 6 digitos", Toast.LENGTH_SHORT).show();
                }

                else {
                    register(txt_nome,txt_email,txt_user,txt_senha,txt_endereco,txt_complemento,txt_cep,txt_CPF,txt_CNPJ,txt_UF,txt_numero);
                }
            }
        });

    }

    public String getUriZipCode() {
        return "https://viacep.com.br/ws/" + etZipCode.getText() + "/json/";
    }

    public void lockFields(boolean isToLock) {
        util.lockFields(isToLock);
    }

    public void setDataViews(Address address) {
        setField(R.id.edEndereco, address.getLogradouro());
        setField(R.id.edNumero, address.getComplemento());
        setField(R.id.edCidade, address.getLocalidade());
        setField(R.id.edBairro, address.getBairro());
        setField(R.id.edComplemento, address.getUnidade());
        setSpinner(R.id.edUF, R.array.states, address.getUf());

    }

    private void setField(int id, String data) {
        ((EditText) findViewById(id)).setText(data);
    }

    private void setSpinner(int id, int arrayId, String data) {
        String[] itens = getResources().getStringArray(arrayId);

        for (int i = 0; i < itens.length; i++) {

            if (itens[i].endsWith("(" + data + ")")) {
                ((Spinner) findViewById(id)).setSelection(i);
                return;
            }
        }
        ((Spinner) findViewById(id)).setSelection(0);
    }

    private void register(final String edNome, final String edEmail, final String edUser, String edSenha, final String edEndereco, final String edComplemento, final String edCEP, final String edCPF, final String edCNPJ, final String edUF, final String edNumero) {
        auth.createUserWithEmailAndPassword(edEmail, edSenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                FirebaseUser firebaseUser = auth.getCurrentUser();
                assert firebaseUser != null;
                String userid = firebaseUser.getUid();

                reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("id",userid);
                hashMap.put("Nome", edNome);
                hashMap.put("Login", edUser);
                hashMap.put("username", edEmail);
                hashMap.put("imageURL", "default");
                hashMap.put("CPF", edCPF);
                hashMap.put("CNPJ", edCNPJ);
                hashMap.put("Endereço", edEndereco);
                hashMap.put("Numero",edNumero);
                hashMap.put("Complemento", edComplemento);
                hashMap.put("Estado", edUF);
                hashMap.put("CEP", edCEP);

                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            } else {
                Toast.makeText(RegisterActivity.this, "Não foi possível se registrar", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}