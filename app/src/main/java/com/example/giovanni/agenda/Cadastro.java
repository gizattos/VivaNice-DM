package com.example.giovanni.agenda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Cadastro extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    EditText editLogin;
    EditText editPassword;
    Button btnLoginRegister;
    ProgressDialog progressLogin ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);
        firebaseAuth = FirebaseAuth.getInstance();
        editLogin = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        btnLoginRegister = (Button) findViewById(R.id.email_sign_in_button);
       // btnLoginRegister.setText(R.string.action_register);
        progressLogin = new ProgressDialog(this);
        btnLoginRegister.setOnClickListener((View.OnClickListener) this);
    }

    private void registerUser(){
        String email = editLogin.getText().toString().trim();
        String password  = editPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "coloque seu email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "entre com sua senha",Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Cadastro.this, "funcionou",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), TelaPrincipalActivity.class);
                            intent.putExtra("nome", editLogin.getText().toString().trim());
                            startActivity(intent);
                        }else{
                            Toast.makeText(Cadastro.this, "funcionou",Toast.LENGTH_LONG).show();
                        }
                        progressLogin.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        registerUser();
    }
}
