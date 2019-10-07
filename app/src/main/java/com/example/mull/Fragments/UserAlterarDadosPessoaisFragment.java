package com.example.mull.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mull.BackgroundWorkers.Mask;
import com.example.mull.BackgroundWorkers.Util;
import com.example.mull.BackgroundWorkers.ZipCodeListener;
import com.example.mull.Dao.Alterar;
import com.example.mull.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.example.mull.Activities.MainActivity.active;
import static com.example.mull.Activities.MainActivity.fragment1;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserAlterarDadosPessoaisFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserAlterarDadosPessoaisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAlterarDadosPessoaisFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public DatabaseReference reference;
    private Spinner edUF;
    private EditText edCEP;
    private EditText edComplemento;
    private EditText edCidade;
    private EditText etZipCode;
    private EditText edEndereco;
    private EditText edNumero;
    private Button btnAlterar;
    public Alterar alterar;
    public FirebaseAuth auth;

    private OnFragmentInteractionListener mListener;

    public UserAlterarDadosPessoaisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAlterarDadosPessoaisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAlterarDadosPessoaisFragment newInstance(String param1, String param2) {
        UserAlterarDadosPessoaisFragment fragment = new UserAlterarDadosPessoaisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_alterar_dados_pessoais, container, false);

        edCEP = v.findViewById(R.id.edCEP);
        edComplemento = v.findViewById(R.id.edComplemento);
        etZipCode = v.findViewById(R.id.edCEP);
        edCidade = v.findViewById(R.id.edCidade);
        edEndereco = v.findViewById(R.id.edEndereco);
        edNumero = v.findViewById(R.id.edNumero);
        edUF = v.findViewById(R.id.edUF);
        edCEP.addTextChangedListener(Mask.insert(Mask.MaskType.CEP, edCEP));

        btnAlterar = v.findViewById(R.id.btnAlterar);
        alterar = new Alterar();

        Spinner spStates = v.findViewById(R.id.edUF);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getContext(),
                        R.array.states,
                        R.layout.spin_layout);
        spStates.setAdapter(adapter);

        TextView Login = v.findViewById(R.id.edEmail);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        String useremail = firebaseUser.getEmail();
        Login.setText(useremail);

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_numero = edNumero.getText().toString();
                String txt_cep = edCEP.getText().toString();
                String txt_endereco = edEndereco.getText().toString();
                String txt_cidade = edCidade.getText().toString();
                String txt_UF = edUF.getSelectedItem().toString();
                String txt_complemento = edComplemento.getText().toString();

                if (TextUtils.isEmpty(txt_cidade)|| TextUtils.isEmpty(txt_cep) || TextUtils.isEmpty(txt_endereco) || TextUtils.isEmpty(txt_numero)){
                    Toast.makeText(getContext(), "Todos os campos são necessários para alterar o cadastro", Toast.LENGTH_SHORT).show();
                }
                else {
                    alterar(txt_endereco,txt_complemento,txt_cep,txt_UF,txt_numero);
                }
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void alterar(final String edEndereco,final String edComplemento, final String edCEP, final String edUF, final String edNumero) {
        String txt_endereco = edEndereco;
        String txt_complemento = edComplemento;
        String txt_cep = edCEP;
        String txt_UF = edUF;
        String txt_numero = edNumero;

        if (TextUtils.isEmpty(txt_endereco) || TextUtils.isEmpty(txt_UF) || TextUtils.isEmpty(txt_cep) || TextUtils.isEmpty(txt_numero)) {
            Toast.makeText(getContext(), "Todos os campos são necessários para alterar os dados.", Toast.LENGTH_SHORT).show();
        }

        else {
            auth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = auth.getCurrentUser();
            assert firebaseUser != null;
            String userid = firebaseUser.getUid();
            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

            Map<String,String> hashMap = new HashMap<>();
            hashMap.put("id",userid);
            hashMap.put("imageURL", "default");
            hashMap.put("Endereço", txt_endereco);
            hashMap.put("Numero",txt_numero);
            hashMap.put("Complemento", txt_complemento);
            hashMap.put("Estado", txt_UF);
            hashMap.put("CEP", txt_cep);
            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Dados Alterados", Toast.LENGTH_SHORT).show();
                        FragmentManager manager = getFragmentManager();
                        manager.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                    } else {
                        Toast.makeText(getContext(), "Não foi possível alterar os dados", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
