package com.example.Persistance_donnees_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editUsername;
    private EditText editPassword;
    private EditText editEmail;
    private Button btnRegister;
    private Button btnLogin;


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PersistenceDonnee_BDDMysql);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            return;
        }

        //Récupérer les éléments de la vue
        editUsername = (EditText)findViewById(R.id.edit_Username);
        editPassword = (EditText)findViewById(R.id.edit_Password);
        editEmail = (EditText)findViewById(R.id.edit_Email);
        btnRegister = (Button)findViewById(R.id.btn_Register);
        btnLogin = (Button)findViewById(R.id.btn_Login);


        progressDialog = new ProgressDialog(this);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnRegister) {
            registerUser();
        }
        if (v == btnLogin) {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }

    }

    private void registerUser() {
        //Récupérer les valeurs des 3 champs et ôter les espaces avec .trim()
        final String username = editUsername.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();

        //Afficher la boîte de dialogue
        progressDialog.setMessage("Création d'utilisateur en cours");
        progressDialog.show();

        //Créer la requête POST vu qu'on insère en base
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constante.URL_REGISTER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //Gestion du Json Object avec la réponse
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                //return super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                params.put("email",email);
                return params;
            }
        };


        // V2 : "singleton" request queue
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}