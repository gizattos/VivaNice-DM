package com.example.giovanni.agenda;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MsgBroad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_broad);

/*
        new AlertDialog.Builder(this)
                .setTitle("VivaNice")
                .setMessage("Viva sua vida de forma nice !")
                .setPositiveButton("ㅇㅇ", null)
                .setCancelable(false)
                .show();
*/
        Toast.makeText(this, "Não Fique mal ! VivaNice!", Toast.LENGTH_LONG).show();

    }
}
