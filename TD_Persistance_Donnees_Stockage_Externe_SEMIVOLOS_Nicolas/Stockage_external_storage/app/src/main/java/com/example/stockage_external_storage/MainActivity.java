package com.example.stockage_external_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnSave, btnLoad;
    EditText etInput;
    TextView tvLoad;
    String filename = "";
    String filepath = "";
    String fileContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
        etInput = findViewById(R.id.etInput);
        tvLoad = findViewById(R.id.tvLoad);
        filename = "monFichier.txt";
        filepath = "monFichierDir";
        if(!isExternalStorageAvailableForRW())
        {
            btnSave.setEnabled(false);
    }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLoad.setText("");
                fileContent = etInput.getText().toString().trim();
                if(!fileContent.equals("")) {
                    File MonFichierExterne = new File(getExternalFilesDir(filepath), filename);
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(MonFichierExterne);
                        fos.write(fileContent.getBytes());


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    etInput.setText("");
                    Toast.makeText(MainActivity.this, "Informations sauvegardées", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Veuillez saisir au moins un caractères", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileReader fr = null;
                File monFichierExterne = new File(getExternalFilesDir(filepath), filename);
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    fr = new FileReader(monFichierExterne);
                    BufferedReader br = new BufferedReader(fr);
                    String line = br.readLine();

                    while(line != null) {
                        stringBuilder.append(line).append('\n');
                        line = br.readLine();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    String fileContents = "File contents\n" + stringBuilder.toString();
                    tvLoad.setText(fileContents);
                }
            }
        });

}

    private boolean isExternalStorageAvailableForRW() {

        String extStorageState = Environment.getExternalStorageState();
        if(extStorageState.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}