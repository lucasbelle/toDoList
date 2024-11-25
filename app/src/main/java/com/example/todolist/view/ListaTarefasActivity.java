package com.example.todolist.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.todolist.R;
import com.example.todolist.controller.TarefaController;
import com.example.todolist.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class ListaTarefasActivity extends AppCompatActivity {

    private ListView listViewTarefas;
    private FloatingActionButton btnAdicionarTarefa;
    private TarefaController tarefaController;
    private List<Tarefa> listaDeTarefas;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> titulosTarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tarefas);

        listViewTarefas = findViewById(R.id.listViewTarefas);
        btnAdicionarTarefa = findViewById(R.id.btnAdicionarTarefa);

        tarefaController = new TarefaController(this);
        carregarTarefas();

        btnAdicionarTarefa.setOnClickListener(view -> {
            Intent intent = new Intent(ListaTarefasActivity.this, AdicionarEditarTarefaActivity.class);
            startActivity(intent);
        });

        Button btnConfiguracoes = findViewById(R.id.btnConfiguracoes);

        btnConfiguracoes.setOnClickListener(view -> {
            Intent intent = new Intent(ListaTarefasActivity.this, ConfiguracoesActivity.class);
            startActivity(intent);
        });


        listViewTarefas.setOnItemClickListener((adapterView, view, position, id) -> {
            Tarefa tarefaSelecionada = listaDeTarefas.get(position);
            Intent intent = new Intent(ListaTarefasActivity.this, DetalhesTarefaActivity.class);
            intent.putExtra("tarefa_id", tarefaSelecionada.getId());
            startActivity(intent);
        });

        listViewTarefas.setOnItemLongClickListener((adapterView, view, position, id) -> {
            Tarefa tarefaSelecionada = listaDeTarefas.get(position);
            new AlertDialog.Builder(ListaTarefasActivity.this)
                    .setTitle("Excluir Tarefa")
                    .setMessage("Tem certeza de que deseja excluir esta tarefa?")
                    .setPositiveButton("Sim", (dialogInterface, i) -> {
                        tarefaController.excluirTarefa(tarefaSelecionada.getId());
                        carregarTarefas();
                        Toast.makeText(ListaTarefasActivity.this, "Tarefa excluída", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Não", null)
                    .show();
            return true;
        });
    }

    private void carregarTarefas() {
        listaDeTarefas = tarefaController.listarTarefas();
        titulosTarefas = new ArrayList<>();
        for (Tarefa tarefa : listaDeTarefas) {
            String titulo = tarefa.getTitulo() + " - " + tarefa.getDataConclusao() + " (" + tarefa.getStatus() + ")";
            titulosTarefas.add(titulo);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titulosTarefas);
        listViewTarefas.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarTarefas();
    }
}

