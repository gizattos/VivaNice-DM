package com.example.giovanni.agenda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Arrays;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

public class ListaAlunosActivity extends AppCompatActivity {

    FirebaseAuth auth;
    CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Button b = findViewById(R.id.btnLogin);
        Button b2 = findViewById(R.id.btn_forgot_password);
        final AVLoadingIndicatorView progress = (AVLoadingIndicatorView) findViewById(R.id.avi);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*
                final ProgressDialog pd = new ProgressDialog(ListaAlunosActivity.this);
                pd.setIndeterminate(true);
                pd.setMessage("Carregando...");
                pd.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(2000);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        pd.dismiss();
                    }
                });
                */
                login();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ListaAlunosActivity.this, EsqueceSenha.class);
                startActivity(it);
            }
        });

        //botao do facebook
        mCallbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "callback... error", Toast.LENGTH_LONG).show();
            }


        });

        if (user != null) {
            Intent it = new Intent(ListaAlunosActivity.this, TelaPrincipalActivity.class);
            startActivity(it);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();

                            Intent it = new Intent(ListaAlunosActivity.this, TelaPrincipalActivity.class);
                            startActivity(it);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "handle... error", Toast.LENGTH_LONG).show();
                        }
                        // ...
                    }
                });
    }


    private void login() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtSenha = findViewById(R.id.edtSenha);


        if (!edtEmail.getText().toString().isEmpty() && !edtSenha.getText().toString().isEmpty()) {
            Task<AuthResult> processo = auth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString());
            processo.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent it = new Intent(ListaAlunosActivity.this, TelaPrincipalActivity.class); // Intent it = new Intent(this, TelaPrincipalActivity.class); tive que coloacr a classe no primeiro this pq ela ta dentro do oncompletelistener
                        startActivity(it);
                    } else {
                        Toast.makeText(getApplicationContext(), "E-mail ou senha invalidos", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Favor preencher os campos de LOGIN", Toast.LENGTH_LONG).show();
        }

    }
}
