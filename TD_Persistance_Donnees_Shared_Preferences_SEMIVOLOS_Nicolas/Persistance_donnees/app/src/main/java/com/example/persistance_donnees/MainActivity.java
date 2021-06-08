package com.example.persistance_donnees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    private Button Button_save;

    private int Saves_count = 0;

    private EditText Nom = null;
    private EditText Prenom = null;
    private EditText Mail = null;
    private EditText Phone = null;
    private TextView Decompte = null;

    private TextView Nom_saved = null;
    private TextView Prenom_saved = null;
    private TextView Mail_saved = null;
    private TextView Phone_saved = null;

    private SharedPreferences mySharedPreferences;

    private static final String MyPREFERENCES = "MyPrefs";
    private static final String Nom_string="SauvegardeNom";
    private static final String Prenom_string="SauvegardePrenom";
    private static final String Mail_string="SauvegardeMail";
    private static final String Phone_string="SauvegardePhone";
    private static final String Sauvegarde_string= "SauvegardeCompteur";
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Saves_count = (mySharedPreferences.getString(Sauvegarde_string, ""));

        Button_save = (Button)findViewById(R.id.Save);

        Nom = (EditText)findViewById(R.id.Nom_saisie);
        Prenom = (EditText)findViewById(R.id.Prenom_saisie);
        Mail = (EditText)findViewById(R.id.Email_saisie);
        Phone = (EditText)findViewById(R.id.Phone_saisie);
        Decompte = (TextView)findViewById(R.id.Compteur_saisie);

        Nom_saved = (TextView) findViewById(R.id.Saved_name);
        Prenom_saved = (TextView)findViewById(R.id.Saved_surname);
        Mail_saved = (TextView)findViewById(R.id.Saved_mail);
        Phone_saved = (TextView)findViewById(R.id.Saved_phone);

        mySharedPreferences= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Nom_saved.setText(mySharedPreferences.getString(Nom_string,""));
        Prenom_saved.setText(mySharedPreferences.getString(Prenom_string,""));
        Mail_saved.setText(mySharedPreferences.getString(Mail_string,""));
        Phone_saved.setText(mySharedPreferences.getString(Phone_string,""));
Button_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Saves_count ++;
                SharedPreferences.Editor myeditor = mySharedPreferences.edit();
                myeditor.putString(Nom_string, Nom.getText().toString());
                myeditor.putString(Prenom_string, Prenom.getText().toString());
                myeditor.putString(Mail_string, Mail.getText().toString());
                myeditor.putString(Phone_string, Phone.getText().toString());
                myeditor.commit(); // Synchrone
                //myeditor.apply(); --- Asynchrone

                Toast.makeText(getApplicationContext(),"Sauvegarde effectuée",LENGTH_LONG).show();

                }

             }

        );

    }

}