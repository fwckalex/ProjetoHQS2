package com.example.aplicativodehqs;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class CadastroColecaoActivity extends AppCompatActivity {
    EditText edtNome;
    EditText edtAutor;
    EditText edtGenero;
    Button btnSalvar;
    SQLiteDatabase bancoDados;
    Spinner spinnerHQ; // Adicione um Spinner para selecionar a HQ relacionada à coleção
    String idHQSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_colecao);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtAutor = (EditText) findViewById(R.id.edtAutor);
        edtGenero = (EditText) findViewById(R.id.edtGenero);
        btnSalvar = (Button) findViewById(R.id.btnAlterar);
       // spinnerHQ = (Spinner) findViewById(R.id.spinnerHQ); // Adicione o Spinner no layout

        // Configure o Spinner com a lista de HQs disponíveis
       // ArrayAdapter<String> hqAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDeHQs());
       // hqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      //  spinnerHQ.setAdapter(hqAdapter);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    public void cadastrar() {
        if (!TextUtils.isEmpty(edtNome.getText().toString())) {
            try {
                bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
                String sql = "INSERT INTO colecao (nome, autor, genero) VALUES (?,?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, edtNome.getText().toString());
                stmt.bindString(2, edtAutor.getText().toString());
                stmt.bindString(3, edtGenero.getText().toString());
                // Obtenha o ID da HQ selecionada no Spinner
             //   int hqIndex = spinnerHQ.getSelectedItemPosition();
              //  String idHQ = obterIdHQSelecionada(hqIndex);
             //   stmt.bindString(4, idHQ);
                stmt.executeInsert();
                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



