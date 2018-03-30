package com.example.giovanni.agenda;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TelaPrincipalActivity extends AppCompatActivity {

    EditText edtNome;
    EditText edtDatNas;
    EditText edtIdentifica;
    Spinner edtSex;
    Spinner edtTipo;
    EditText edtRegistro;

    FirebaseDatabase database;
    FirebaseAuth auth;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final int requestCode = 20;
    private ImageView imageHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telaprincipal);

        edtNome = findViewById(R.id.edtNome);
        edtDatNas = findViewById(R.id.edtDatNas);
        edtSex = findViewById(R.id.edtSex);
        edtTipo = findViewById(R.id.edtTipo);
        edtIdentifica = findViewById(R.id.edtIdentifica);
        edtRegistro = findViewById(R.id.edtRegistro);
       Button b5 = findViewById(R.id.button2);


        String[] sexo = new String[]{"Homem", "Mulher"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sexo);
//set the spinners adapter to the previously created one.
        edtSex.setAdapter(adapter);

        String[] tipoUser = new String[]{"Pessoa", "Ajudante"};
        ArrayAdapter<String> tipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipoUser);
        edtTipo.setAdapter(tipo);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        Button btn = findViewById(R.id.btnGravar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gravar();
            }
        });

        Button btn2 = findViewById(R.id.btnLogoff);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), ListaAlunosActivity.class);
                startActivity(i);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TelaPrincipalActivity.this, mapas.class); // Intent it = new Intent(this, TelaPrincipalActivity.class); tive que coloacr a classe no primeiro this pq ela ta dentro do oncompletelistener
                startActivity(it);
            }
        });





        //      Button btn3 = findViewById(R.id.btnFoto);
        //     btn3.setOnClickListener(new View.OnClickListener() {
        //        @Override
        //       public void onClick(View view) {
        //          Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        //         startActivityForResult(takePictureIntent, requestCode);
        //    }
        // });



        imageHolder = (ImageView)findViewById(R.id.imageView7);
        Button capturedImageButton = (Button)findViewById(R.id.btnFoto);
        capturedImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, requestCode);
            }
        });




/*

        if(edtTipo.getSelectedItem().toString().equals("Pessoa")){
            edtIdentifica.setVisibility(View.INVISIBLE);

        } if(edtTipo.getSelectedItem().toString().equals("Ajudante")){
            edtIdentifica.setVisibility(View.VISIBLE);
        }

*/


        edtTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,View selectedItemView, int position, long id) {
                if ("Pessoa".equals(edtTipo.getSelectedItem().toString())) {
                    edtIdentifica.setVisibility(View.VISIBLE);
                    edtRegistro.setVisibility(View.INVISIBLE);
                    edtRegistro.setText(" ");

                    //     edtIdentifica.setHint("Coloque seu CPF");

                } else if ("Ajudante".equals(edtTipo.getSelectedItem().toString())) {
                    edtRegistro.setVisibility(View.VISIBLE);
                    edtIdentifica.setVisibility(View.INVISIBLE);
                    edtIdentifica.setText(" ");
                    //edtIdentifica.setHint("Coloque seu cadastro médico");
                }}
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        ImageView Im7 = findViewById(R.id.imageView7);


    }

    //TENTANDO MOSTRAR IMAGEM


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageHolder.setImageBitmap(bitmap);
        }
    }


// FINAL TENTANDO MOSTRAR IMAGEM



    private void gravar(){

        String nome = edtNome.getText().toString();
        String dataNasc = edtDatNas.getText().toString();
        String sexo = edtSex.getSelectedItem().toString();
        String tipo = edtTipo.getSelectedItem().toString();
        String CPF = edtIdentifica.getText().toString();
        String codmed = edtRegistro.getText().toString();

        if(CPF.equals(null)){
            CPF = "não informado";

        }
        if(codmed.equals(null)){
            codmed = "não informado";

        }


        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();

        DatabaseReference alunos = database.getReference("/Alunos");
        alunos.child(user.getUid()).child("nome").setValue(nome); // colocando valor dentro do child ele vai direto pro banco
        alunos.child(user.getUid()).child("dt_nasc").setValue(dataNasc);
        alunos.child(user.getUid()).child("sexo").setValue(sexo);
        alunos.child(user.getUid()).child("tipo").setValue(tipo); // colocando valor dentro do child ele vai direto pro banco
        alunos.child(user.getUid()).child("CPF").setValue(CPF);
        alunos.child(user.getUid()).child("codmed").setValue(codmed);


    }
}
