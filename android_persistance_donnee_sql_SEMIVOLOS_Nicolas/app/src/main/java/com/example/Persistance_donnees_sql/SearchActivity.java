package com.example.Persistance_donnees_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editUsername;
    EditText editEmail;


    Button btnSearch;
    ProgressDialog progressDialog;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PersistenceDonnee_BDDMysql);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (!SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(SearchActivity.this,LoginActivity.class));
            return;
        }

        editUsername = (EditText)findViewById(R.id.edit_UsernameSearch);
        editEmail = (EditText)findViewById(R.id.edit_EmailSearch);
        btnSearch = (Button)findViewById(R.id.btn_Search);
        listView = (ListView)findViewById(R.id.listView);

        progressDialog = new ProgressDialog(this);

        btnSearch.setOnClickListener(this);
        userRequests();

    }

    @Override
    public void onClick(View v) {
        if (v == btnSearch) {
            userRequests();
        }
    }

    private void userRequests() {
        final String username = editUsername.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();


        String getUrl = Constante.URL_SEARCH+"?username="+username+"&email="+email;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,getUrl,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //Gestion du Json Object avec la réponse
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Si la connexion a marché : changement de page avec les infos de l'utilisateur
                    Toast.makeText(SearchActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    //Si pas d'erreur, stocker les données dans une liste
                    if (!jsonObject.getBoolean("error")) {
                        //Tableau des données
                        JSONArray jsonUsers = jsonObject.getJSONArray("users");

                        //Liste de users pour la liste view
                        ArrayList<User> userList = new ArrayList<>();

                        for(int i =0; i < jsonUsers.length();i++) {
                            //Pour chaque user du tableau, on stock dans la liste
                            JSONObject object = jsonUsers.getJSONObject(i);
                            User user = new User(object.getString("username"),object.getString("email"));
                            userList.add(user);
                        }

                        //Mise à jour de la liste view
                        AndroidAdapter adapter = new AndroidAdapter(SearchActivity.this,R.layout.item,userList);
                        listView.setAdapter(adapter);

                        //Click sur un item de la liste : afficher la page de détails
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(SearchActivity.this,UserDetailActivity.class);

                                User user = new User(userList.get(position).getUsername(),userList.get(position).getEmail());
                                intent.putExtra(Constante.EXTRA_USER,user);
                                startActivity(intent);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}