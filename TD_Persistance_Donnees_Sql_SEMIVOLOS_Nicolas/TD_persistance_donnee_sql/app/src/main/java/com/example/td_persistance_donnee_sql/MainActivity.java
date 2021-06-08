package com.example.td_persistance_donnee_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username, password, email;
    private Button register;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.usernameEdit);
        password = findViewById(R.id.passwordEdit);
        email = findViewById(R.id.emailEdit);
        register = findViewById(R.id.buttonRegistre);
        progressDialog = new ProgressDialog(this);

    }

    private void registerUser() {

        final String nomUser = username.getText().toString().trim();
        final String mdpUser = password.getText().toString().trim();
        final String mailUser = email.getText().toString().trim();

        progressDialog.setMessage("Création utilisateurs en cours...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constantes.url_register,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        progressDialog.dismiss();
                        Log.e("Resp", response);
                        try{
                            Log.e("Resp", response);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            Log.e("tagA", jsonObject.getString("message"));
                        }catch(JSONException e){
                            e.printStackTrace();
                            Log.e("Resp", response);
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        progressDialog.hide();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("tagB", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", nomUser);
                params.put("password", mdpUser);
                params.put("mail", mailUser);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    @Override
    public void onClick(View v) {
        if (v == register) {
            registerUser();
        }
    }
}