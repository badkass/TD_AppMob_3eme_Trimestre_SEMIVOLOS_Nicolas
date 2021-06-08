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

public class EditUserActivity extends AppCompatActivity {

    EditText editUsername;
    EditText editEmail;


    Button btnUpdate;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PersistenceDonnee_BDDMysql);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        if (!SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(EditUserActivity.this,LoginActivity.class));
            return;
        }

        Intent intent = getIntent();
        String currentUser = intent.getStringExtra(Constante.INTENT_USERNAME);

        editUsername = (EditText)findViewById(R.id.edit_UsernameUpdate);
        editUsername.setHint(currentUser);
        editEmail = (EditText)findViewById(R.id.edit_EmailUpdate);
        btnUpdate = (Button)findViewById(R.id.btn_Update);
        progressDialog = new ProgressDialog(this);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUpdate(currentUser);
            }
        });

    }

    private void userUpdate(String currentUser) {
        final String username = editUsername.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,Constante.URL_UPDATE,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //Gestion du Json Object avec la réponse
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Si la connexion a marché : changement de page avec les infos de l'utilisateur
                    Toast.makeText(EditUserActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    if (!jsonObject.getBoolean("error")) {
                        startActivity(new Intent(EditUserActivity.this,SearchActivity.class));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(EditUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                //return super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("currentuser",currentUser);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}