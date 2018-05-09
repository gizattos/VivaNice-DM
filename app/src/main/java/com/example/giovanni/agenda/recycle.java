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
    String nome;
    String tipo;
    String nascimento;
    String[] testando = new String[10];
    String[] testando2 = new String[10];
    String[] testando3 = new String[10];
    int v=0;
    int s=0;
    int f=0;
    int contador=0;



    public class Aluno{
        public String tipo;
        public String nome;
        public String nascimento;

        public Aluno(String tipo,String nome,String nascimento){
            this.tipo = tipo;
            this.nome = nome;
            this.nascimento = nascimento;



        }

    }

    private List<Aluno> listaAlunos = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
     //   DatabaseReference alunos = database.getReference("/Alunos");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference alunos = rootRef.child("/Alunos");






        alunos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                nascimento = snapshot.getValue().toString();
                for(DataSnapshot ds : snapshot.getChildren()) {
                   testando3[f] = ds.child("dt_nasc").getValue(String.class);
                    testando2[f] = ds.child("nome").getValue(String.class);
                    testando[f] = ds.child("tipo").getValue(String.class);
                    Log.d("TAG","o nome é" + testando3[f]);



                    Aluno aluno1 = new Aluno(testando[f],testando2[f],testando3[f]);
                    listaAlunos.add(aluno1);

                    contador++;
                    f++;
                }




                //Aluno aluno1 = new Aluno(testando[0],testando2[0],testando3[0]);
               // listaAlunos.add(aluno1);
               // Aluno aluno2 = new Aluno(testando[1],testando2[1],testando3[1]);
              //  listaAlunos.add(aluno2);
             //   Aluno aluno3 = new Aluno(testando[2],testando2[2],testando3[2]);
            //    listaAlunos.add(aluno3);
           //     Aluno aluno4 = new Aluno(testando[3],testando2[3],testando3[3]);
          //      listaAlunos.add(aluno4);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        Collection allListObject = new ArrayList();
        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
         /* operation with list */
            list.add(i);
            allListObject.add(list);
        }

        //iterate through the ArrayList values using Iterator's hasNext and next methods
        for (Iterator it = allListObject.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }


        //Aluno aluno1 = new Aluno(tipo,nome,nascimento);
      //  Aluno aluno1 = new Aluno(tipo,nome,nascimento);










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

            holder.nome.setText(a.tipo);
            holder.matricula.setText(a.nome);
            holder.curso.setText(a.nascimento);

        }

        @Override
        public int getItemCount() {
            return listaAlunos.size();
        }
    }

}


// adicionar os campos nome, matricula e curso no xmllayoutfile, layout height tem que ser wrap content