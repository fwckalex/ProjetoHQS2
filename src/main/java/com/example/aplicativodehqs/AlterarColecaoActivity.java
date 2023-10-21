package com.example.aplicativodehqs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AlterarColecaoActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public Button btnAlterar;
    public EditText edtNome;
    public EditText edtAutor;
    public EditText edtGenero;
    public Button btnExcluir;

    public Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_colecao);

        btnAlterar = (Button) findViewById(R.id.btnAlterar);
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtAutor = (EditText) findViewById(R.id.edtAutor);
        edtGenero = (EditText) findViewById(R.id.edtGenero);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);

        Intent intent = getIntent();
        id = intent.getIntExtra("colecao_id", 0);

        carregarDados();

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterar();
            }
        });


        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluir();
            }
        });


    }

    public void carregarDados(){
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id, nome, autor, genero FROM colecao WHERE id = " +id.toString(), null);
            cursor.moveToFirst();
            edtNome.setText(cursor.getString(1));
            edtAutor.setText(cursor.getString(2));
            edtGenero.setText(cursor.getString(3));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void alterar(){
        String valueNome;
        String valueAutor;
        String valueGenero;

        valueNome = edtNome.getText().toString();
        valueAutor = edtAutor.getText().toString();
        valueGenero = edtGenero.getText().toString();

        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            String sql = "UPDATE colecao SET nome=?, autor=?, genero=? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1, valueNome);
            stmt.bindString(2, valueAutor);
            stmt.bindString(3, valueGenero);
            stmt.bindLong(4, id);
            stmt.executeUpdateDelete();
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        finish();
    }

    public void excluir(){
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            String sql = "DELETE FROM colecao WHERE id = ?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindLong(1, id);
            stmt.executeUpdateDelete();
            // listarDados();
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        finish();
    }

}