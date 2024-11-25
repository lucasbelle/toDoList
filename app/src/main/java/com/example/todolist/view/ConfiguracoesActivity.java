package com.example.todolist.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.database.TarefaDatabase;

public class ConfiguracoesActivity extends AppCompatActivity {

    private Button btnSobre, btnLimparTarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        btnSobre = findViewById(R.id.btnSobre);
        btnLimparTarefas = findViewById(R.id.btnLimparTarefas);

        TarefaDatabase tarefaDBHelper = new TarefaDatabase(this);

        btnSobre.setOnClickListener(view -> Toast.makeText(this, "App ToDoList - Versão 1.0", Toast.LENGTH_LONG).show());

        btnLimparTarefas.setOnClickListener(view -> new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Limpar todas as tarefas")
                .setMessage("Tem certeza de que deseja excluir todas as tarefas?")
                .setPositiveButton("Sim", (dialogInterface, i) -> {

                    tarefaDBHelper.excluirTodasTarefas();

                    Toast.makeText(this, "Todas as tarefas foram excluídas!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Não", null)
                .show());
    }
}
