package com.example.mull.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mull.Dao.Coletas;
import com.example.mull.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.mull.Activities.MainActivity.active;
import static com.example.mull.Activities.MainActivity.fragment5;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MullFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MullFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MullFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton btnFloat;
    private RecyclerView MinhaListaColetas;
    private View ColetasView;
    private DatabaseReference ColetasReference, usersRef;
    private FirebaseAuth mAuth;
    private String currentUserID="";

    // TODO: Rename and change types of parameters


    public MullFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MullFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MullFragment newInstance(String param1, String param2) {
        MullFragment fragment = new MullFragment();
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

        ColetasView = inflater.inflate(R.layout.fragment_mull, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        ColetasReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Coletas");

        MinhaListaColetas = ColetasView.findViewById(R.id.listaColetas);
        MinhaListaColetas.setHasFixedSize(true);
        MinhaListaColetas.setLayoutManager(new LinearLayoutManager(getContext()));

        btnFloat = ColetasView.findViewById(R.id.fab);

        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().hide(active).show(fragment5).commit();
                active = fragment5;
            }
        });

        return ColetasView;
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

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Coletas>()
                .setQuery(ColetasReference, Coletas.class)
                .build();

        FirebaseRecyclerAdapter<Coletas, MullViewHolder> adapter = new FirebaseRecyclerAdapter<Coletas, MullViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MullViewHolder mullViewHolder, final int i, @NonNull Coletas coletas) {
                ColetasReference.child("Coletas").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            String key = snap.getKey();
                            ColetasReference.child("Coletas").child(key).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        String DataColeta = dataSnapshot.child("dataColeta").getValue().toString();
                                        String HorarioColetaInicio = dataSnapshot.child("horarioColeta").getValue().toString();
                                        String HorarioColetaFim = dataSnapshot.child("horarioColetaFim").getValue().toString();

                                        mullViewHolder.dia.setText(DataColeta);
                                        mullViewHolder.horarioi.setText(HorarioColetaInicio);
                                        mullViewHolder.horariof.setText(HorarioColetaFim);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public MullViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_coletas_layout, parent, false);
                return new MullViewHolder(view);
            }
        };

        MinhaListaColetas.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        adapter.startListening();
    }

    public static class MullViewHolder extends RecyclerView.ViewHolder {

        TextView horarioi, dia, horariof;

        public MullViewHolder(@NonNull View itemView) {
            super(itemView);

            horariof = itemView.findViewById(R.id.listitemColetaHorarioFinal);
            dia = itemView.findViewById(R.id.listitemColetaDia);
            horarioi = itemView.findViewById(R.id.listitemColetaHorarioInicio);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}