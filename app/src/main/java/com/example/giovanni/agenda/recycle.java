package com.example.giovanni.agenda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class recycle extends AppCompatActivity {

    public class Aluno{
        public String matricula;
        public String nome;
        public String curso;

        public Aluno(String matricula, String curso, String nome){
            this.matricula = matricula;
            this.nome = nome;
            this.curso = curso;


        }

    }

    private List<Aluno> listaAlunos = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        Aluno aluno1 = new Aluno("1234","COO","joão");
        Aluno aluno2 = new Aluno("666","CCO","pedro");
        Aluno aluno3 = new Aluno("1522130010","CCO","Giovanni");
        Aluno aluno4 = new Aluno("1522130004","CCO","Vitor");
        Aluno aluno5 = new Aluno("1234","COO","joão");
        Aluno aluno6 = new Aluno("666","CCO","pedro");
        Aluno aluno7 = new Aluno("1522130010","CCO","Giovanni");
        Aluno aluno8 = new Aluno("1522130004","CCO","Vitor");
        Aluno aluno9 = new Aluno("1234","COO","joão");
        Aluno aluno10 = new Aluno("666","CCO","pedro");
        Aluno aluno11 = new Aluno("1522130010","CCO","Giovanni");
        Aluno aluno12 = new Aluno("1522130004","CCO","Vitor");
        listaAlunos.add(aluno1);
        listaAlunos.add(aluno2);
        listaAlunos.add(aluno3);
        listaAlunos.add(aluno4);
        listaAlunos.add(aluno5);
        listaAlunos.add(aluno6);
        listaAlunos.add(aluno7);
        listaAlunos.add(aluno8);
        listaAlunos.add(aluno9);
        listaAlunos.add(aluno10);
        listaAlunos.add(aluno11);
        listaAlunos.add(aluno12);




        RecyclerView lista = findViewById(R.id.Lista);
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setItemAnimator(new DefaultItemAnimator());


        AlunoAdapter adapter = new AlunoAdapter(this);
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

            holder.nome.setText(a.nome);
            holder.matricula.setText(a.matricula);
            holder.curso.setText(a.curso);

        }

        @Override
        public int getItemCount() {
            return listaAlunos.size();
        }
    }

}


// adicionar os campos nome, matricula e curso no xmllayoutfile, layout height tem que ser wrap content