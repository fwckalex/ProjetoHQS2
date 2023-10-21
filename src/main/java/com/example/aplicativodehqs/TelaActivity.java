package com.example.aplicativodehqs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class TelaActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    private ListView listViewColecao;
    private Button botao;
    private ArrayList<Integer> arrayIds;
    private Integer idSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewColecao = findViewById(R.id.listViewColecao);
        botao = findViewById(R.id.btnCadastrarColecao);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        listViewColecao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                abrirTelaHQs(idSelecionado); // Passar o ID da coleção selecionada
            }
        });

        listViewColecao.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                abrirTelaAlterarColecao(idSelecionado);
                return true;
            }
        });

        criarBancoDados();
        listarDados();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarDados();
    }

    public void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS colecao(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", nome VARCHAR" +
                    ", autor VARCHAR" +
                    ", genero VARCHAR" +
                    " )");

            // Crie a tabela para HQs
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS hq(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", titulo VARCHAR" +
                    ", autor VARCHAR" +
                    ", genero VARCHAR" +
                    ", colecao_id INTEGER" +
                    " )");

            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarDados() {
        try {
            arrayIds = new ArrayList<>();
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome FROM colecao", null);
            ArrayList<String> linhas = new ArrayList<>();
            ArrayAdapter<String> meuAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listViewColecao.setAdapter(meuAdapter);
            meuCursor.moveToFirst();
            while (meuCursor != null) {
                linhas.add(meuCursor.getString(1));
                arrayIds.add(meuCursor.getInt(0));
                meuCursor.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirTelaCadastro() {
        Intent intent = new Intent(this, CadastroColecaoActivity.class);
        startActivity(intent);
    }

    public void abrirTelaHQs(int idSelecionado) {
        Intent intent = new Intent(this, HQActivity.class);
        intent.putExtra("colecao_id", idSelecionado); // Passar o ID da coleção selecionada para a tela de HQs
        startActivity(intent);
    }

    public void abrirTelaAlterarColecao(int colecaoId){
        Intent intent = new Intent(this, AlterarColecaoActivity.class);
        intent.putExtra("colecao_id", colecaoId);
        startActivity(intent);
    }
}

