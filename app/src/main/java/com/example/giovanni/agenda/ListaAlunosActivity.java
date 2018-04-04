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
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Arrays;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

public class ListaAlunosActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient ;
    FirebaseAuth auth;
    CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_lista_alunos);
        Button b = findViewById(R.id.btnLogin);
        Button b2 = findViewById(R.id.btn_forgot_password);
        SignInButton gLoginButton = findViewById(R.id.sign_in_button);
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


        //botao do google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(getString(R.string.server_client_id)).build();
        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        gLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });



        //botao do facebook

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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account;
            try {
                account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }
        }
        else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = auth.getCurrentUser();
                            Intent it = new Intent(ListaAlunosActivity.this, TelaPrincipalActivity.class);
                            startActivity(it);

                        } else {

                        }

                        // ...
                    }
                });
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getEmail();
            Toast.makeText(this, idToken, Toast.LENGTH_SHORT).show();



        } catch (ApiException e) {
            Toast.makeText(this, "exception", Toast.LENGTH_SHORT).show();
        }
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
