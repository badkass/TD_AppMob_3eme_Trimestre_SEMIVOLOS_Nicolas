package com.example.Persistance_donnees_sql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    TextView textUsername;
    TextView textEmail;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PersistenceDonnee_BDDMysql);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            return;
        }

        textUsername = (TextView)findViewById(R.id.textUsername);
        textEmail = (TextView)findViewById(R.id.textEmail);
        btnSearch = (Button)findViewById(R.id.btn_Search);

        //Récupérer les données de l'intent
        Intent intent = getIntent();
        //String strUsername = intent.getStringExtra(Constante.INTENT_USERNAME);
        //String strEmail = intent.getStringExtra(Constante.INTENT_EMAIL);
        String strUsername = SharedPreferencesManager.getInstance(getApplicationContext()).getUsername();
        String strEmail = SharedPreferencesManager.getInstance(getApplicationContext()).getUserEmail();

        textUsername.setText(strUsername);
        textEmail.setText(strEmail);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout :
                SharedPreferencesManager.getInstance(getApplicationContext()).logout();
                finish();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                break;
            case R.id.settings:
                Toast.makeText(ProfileActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}