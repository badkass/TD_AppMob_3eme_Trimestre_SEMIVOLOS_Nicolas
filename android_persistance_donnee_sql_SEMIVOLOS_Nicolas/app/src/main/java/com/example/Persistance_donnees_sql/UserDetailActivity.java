package com.example.Persistance_donnees_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class UserDetailActivity extends AppCompatActivity {

    TextView usernameTxt;
    TextView emailTxt;
    Button btnEditUser;
    Button btnDelete;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PersistenceDonnee_BDDMysql);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        if (!SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(UserDetailActivity.this,LoginActivity.class));
            return;
        }

        usernameTxt = (TextView)findViewById(R.id.UsernameDetail);
        emailTxt = (TextView)findViewById(R.id.EmailDetail);
        btnEditUser = (Button)findViewById(R.id.btnEditUser);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        User user = intent.getParcelableExtra(Constante.EXTRA_USER);

        usernameTxt.setText(user.getUsername());
        emailTxt.setText(user.getEmail());


        //Bouton pour mettre à jour les informations de l'utilisateur
        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailActivity.this,EditUserActivity.class);
                intent.putExtra(Constante.INTENT_USERNAME,user.getUsername());
                startActivity(intent);
            }
        });

        //Bouton pour supprimer l'utilisateur
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(user.getUsername());
            }
        });

    }

    private void deleteUser(String user) {
        final String username = user;

        //Afficher la boîte de dialogue
        progressDialog.setMessage("Suppression en cours");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constante.URL_DELETE , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //Gestion du Json Object avec la réponse
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Si la connexion a marché : changement de page avec les infos de l'utilisateur
                    Toast.makeText(UserDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    if (!jsonObject.getBoolean("error")) {
                        startActivity(new Intent(UserDetailActivity.this,SearchActivity.class));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(UserDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                //return super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}