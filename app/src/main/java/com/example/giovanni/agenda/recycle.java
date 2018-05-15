package com.example.giovanni.agenda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class recycle extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth auth;
    AlunoAdapter adapter;

    public static class Aluno{
        public String tipo;
        public String nome;
        public String dt_nasc;

        public Aluno (){ }


        public Aluno(String tipo,String nome,String dt_nasc){
            this.tipo = tipo;
            this.nome = nome;
            this.dt_nasc = dt_nasc;
        }
    }


    private List<Aluno> listaAlunos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        DatabaseReference alunos = database.getReference("/Alunos");

        alunos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    Aluno aluno = ds.getValue(Aluno.class);
                    listaAlunos.add(aluno);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        RecyclerView lista = findViewById(R.id.Lista);
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setItemAnimator(new DefaultItemAnimator());

        adapter = new AlunoAdapter(this);
        lista.setAdapter(adapter);
    }

    public class AlunoViewHolder extends RecyclerView.ViewHolder{
        public TextView nome;
        public TextView matricula;
        public TextView curso;
        public AlunoViewHolder(View view){
            super(view);

            nome = view.findViewById(R.id.nome);
            matricula = view.findViewById(R.id.matricula);
            curso = view.findViewById(R.id.curso);
        }
    }


    public class AlunoAdapter extends RecyclerView.Adapter<AlunoViewHolder>{
        private Context mContext;
        public AlunoAdapter(Context context){
            mContext = context;
        }
        @NonNull
        @Override
        public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.xmllayoufile,parent,false);
            AlunoViewHolder holder = new AlunoViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {

            Aluno a = listaAlunos.get(position);

            holder.nome.setText(a.tipo);
            holder.matricula.setText(a.nome);
            holder.curso.setText(a.dt_nasc);

        }

        @Override
        public int getItemCount() {
            return listaAlunos.size();
        }
    }

}




// adicionar os campos nome, matricula e curso no xmllayoutfile, layout height tem que ser wrap content