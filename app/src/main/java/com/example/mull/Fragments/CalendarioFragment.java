package com.example.mull.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mull.Dao.Coletas;
import com.example.mull.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.mull.Activities.MainActivity.active;
import static com.example.mull.Activities.MainActivity.fragment6;
import static com.example.mull.Activities.MainActivity.fragment7;
import static java.util.Calendar.DAY_OF_MONTH;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarioFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText dateText;
    EditText horaInicio;
    EditText horaFinal;
    Coletas coletas;
    DatabaseReference ColetaReference;
    FirebaseAuth authColeta;
    Button btnAddData;
    public final static String DATE_FORMAT = "dd/MM/yyyy";
    public final static String HOUR_FORMAT = "HH:mm";
    private OnFragmentInteractionListener mListener;

    public CalendarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarioFragment newInstance(String param1, String param2) {
        CalendarioFragment fragment = new CalendarioFragment();
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

        View v = inflater.inflate(R.layout.fragment_calendario, container, false);
        dateText = v.findViewById(R.id.date_text);
        dateText = v.findViewById(R.id.date_text);
        horaInicio = v.findViewById(R.id.horainicio);
        horaFinal = v.findViewById(R.id.horafinal);
        btnAddData = v.findViewById(R.id.btnAdicionarDataColeta);
        coletas = new Coletas();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String userid = currentFirebaseUser.getUid();
        ColetaReference = FirebaseDatabase.getInstance().getReference("Users").child(userid).child("Coletas");
        btnAddData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String txt_data = dateText.getText().toString();
                String txt_horainicio = horaInicio.getText().toString();
                String txt_horafinal = horaFinal.getText().toString();

                coletas.setDataColeta(txt_data);
                coletas.setHorarioColeta(txt_horainicio);
                coletas.setHorarioColetaFim(txt_horafinal);

                if (TextUtils.isEmpty(txt_data) || TextUtils.isEmpty(txt_horafinal) || TextUtils.isEmpty(txt_horainicio)) {
                    Toast.makeText(getContext(), "Todos os campos são necessários para a coleta.", Toast.LENGTH_SHORT).show();
                }

                else if (isDateValid(txt_data) && isHorarioValido(txt_horainicio) && isHorarioValido(txt_horafinal)) {

                    ColetaReference.push().setValue(coletas).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Coleta inserida", Toast.LENGTH_SHORT).show();
                                dateText.setText("");
                                horaFinal.getText().clear();
                                horaInicio.getText().clear();
                                FragmentManager manager = getFragmentManager();
                                manager.beginTransaction().hide(active).show(fragment6).commit();
                                active = fragment6;
                            } else {
                                Toast.makeText(getContext(), "Não foi possível inserir a coleta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                else {
                    Toast.makeText(getContext(), "Insira uma data ou um horário válido.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        horaInicio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timePickerDialog();
            }
        });

        horaFinal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timePickerDialogF();
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

    public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isHorarioValido(String horario) {
        try {
            DateFormat hf = new SimpleDateFormat(HOUR_FORMAT);
            hf.setLenient(false);
            hf.parse(horario);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public void registrarColeta(final String DataColeta, final String ColetaHoraInicio, final String ColetaHoraFim) {
        FirebaseUser firebaseUser = authColeta.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();

        ColetaReference = FirebaseDatabase.getInstance().getReference("Users").child(userid).child("Coletas");

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",userid);
        hashMap.put("DataColeta", DataColeta);
        hashMap.put("ColetaHoraInicio", ColetaHoraInicio);
        hashMap.put("ColetaHoraFim", ColetaHoraFim);

        ColetaReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().hide(active).show(fragment7).commit();
                    active = fragment7;
                }
                else {
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().hide(active).show(fragment6).commit();
                    active = fragment6;
                }
            }
        });

    }

    private void showDatePickerDialog () {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.DialogTheme,this,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.YEAR)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void timePickerDialog () {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),R.style.DialogTheme,this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void timePickerDialogF () {
        TimePickerDialog timePickerDialogF = new TimePickerDialog(getContext(),R.style.DialogTheme,this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );
        timePickerDialogF.show();

    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
        mes=mes+1;
        String date = dia + "/" + mes + "/" + ano;
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        dateText.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
        String horainicio = hora + ":" +minuto;
        horaInicio.setText(horainicio);
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