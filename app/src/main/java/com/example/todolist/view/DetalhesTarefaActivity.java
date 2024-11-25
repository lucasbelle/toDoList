package com.example.todolist.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.controller.TarefaController;
import com.example.todolist.model.Tarefa;

public class DetalhesTarefaActivity extends AppCompatActivity {

    private TextView textTitulo, textDescricao, textDataConclusao, textStatus;
    private Button btnEditar, btnExcluir;

    private TarefaController tarefaController;
    private Tarefa tarefaAtual;

    private int tarefaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_tarefa);

        textTitulo = findViewById(R.id.textTitulo);
        textDescricao = findViewById(R.id.textDescricao);
        textDataConclusao = findViewById(R.id.textDataConclusao);
        textStatus = findViewById(R.id.textStatus);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);

        tarefaController = new TarefaController(this);

        tarefaId = getIntent().getIntExtra("tarefa_id", -1);

        if (tarefaId != -1) {
            carregarTarefa();
        } else {
            Toast.makeText(this, "Erro ao carregar tarefa!", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnEditar.setOnClickListener(view -> {
            Intent intent = new Intent(DetalhesTarefaActivity.this, AdicionarEditarTarefaActivity.class);
            intent.putExtra("tarefa_id", tarefaId);
            startActivity(intent);
        });

        btnExcluir.setOnClickListener(view -> new AlertDialog.Builder(this)
                .setTitle("Excluir Tarefa")
                .setMessage("Tem certeza de que deseja excluir esta tarefa?")
                .setPositiveButton("Sim", (dialogInterface, i) -> {
                    tarefaController.excluirTarefa(tarefaAtual.getId());
                    Toast.makeText(this, "Tarefa excluída com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Não", null)
                .show());
    }

    private void carregarTarefa() {
        tarefaAtual = tarefaController.listarTarefas().stream()
                .filter(tarefa -> tarefa.getId() == tarefaId)
                .findFirst()
                .orElse(null);

        if (tarefaAtual != null) {
            textTitulo.setText(tarefaAtual.getTitulo());
            textDescricao.setText(tarefaAtual.getDescricao().isEmpty() ? "Sem descrição" : tarefaAtual.getDescricao());
            textDataConclusao.setText(tarefaAtual.getDataConclusao());
            textStatus.setText(tarefaAtual.getStatus());
        } else {
            Toast.makeText(this, "Erro ao carregar dados!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarTarefa();
    }
}

