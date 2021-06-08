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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editUsername;
    EditText editPassword;
    Button btnLogin;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PersistenceDonnee_BDDMysql);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
            return;
        }

        editUsername = (EditText)findViewById(R.id.edit_LUsername);
        editPassword = (EditText)findViewById(R.id.edit_LPassword);
        btnLogin = (Button)findViewById(R.id.btn_LLogin);

        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            userLogin();
        }
    }

    private void userLogin() {
        //Récupérer les valeurs des 3 champs et ôter les espaces avec .trim()
        final String username = editUsername.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();

        //Afficher la boîte de dialogue
        progressDialog.setMessage("Connexion en cours");
        progressDialog.show();

        String getUrl = Constante.URL_LOGIN+"?username="+username+"&password="+password;

        //Créer la requête GET
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getUrl , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //Gestion du Json Object avec la réponse
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Si la connexion a marché : changement de page avec les infos de l'utilisateur
                    Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    if (!jsonObject.getBoolean("error")) {
                        Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
                        /*
                        intent.putExtra(Constante.INTENT_USERNAME,jsonObject.getString("username"));
                        intent.putExtra(Constante.INTENT_EMAIL,jsonObject.getString("email"));
                         */

                        SharedPreferencesManager.getInstance(getApplicationContext()).userLogin(jsonObject.getString("username"),jsonObject.getString("email"));

                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                //return super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };

        // V2 : "singleton" request queue
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}