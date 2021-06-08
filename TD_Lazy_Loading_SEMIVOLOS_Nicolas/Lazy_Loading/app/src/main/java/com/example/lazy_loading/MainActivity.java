package com.example.lazy_loading;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lazy);

        final View stub = findViewById(R.id. Mon_viewstub);
        Button afficher = (Button) findViewById(R.id.Btn_affiche) ;
        afficher.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                                                                    //au clic on va rendre visible le bouton 2 et en cliquant sur le bouton 2,
                                                                    //ce dernier devient invisible et un toast apparait

                stub.setVisibility(View.VISIBLE);                   //va rendrre l'élément visible
                // stub.setVisibility(View.INVISIBLE);                va rendre l'élément invisible mais il occupe toujours l'espace
                // stub.setVisibility(View.GONE);                     va rendre l'élément invisible et ce dernier n'occupe plus d'espace

                Button affiche = (Button) findViewById(R.id.Mon_viewstubvisible) ;
                affiche.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View t) {

                        //stub.setVisibility(View.VISIBLE);
                        stub.setVisibility(View.INVISIBLE);
                        // stub.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(),"Bouton 2",Toast.LENGTH_LONG).show();
                    }
                });

            }

        });


}
}
