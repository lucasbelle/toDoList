package com.example.todolist.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.controller.TarefaController;
import com.example.todolist.model.Tarefa;

public class AdicionarEditarTarefaActivity extends AppCompatActivity {

    private EditText editTitulo, editDescricao, editDataConclusao;
    private Spinner spinnerStatus;
    private Button btnSalvar, btnCancelar;

    private TarefaController tarefaController;
    private Tarefa tarefaAtual;
    private int tarefaId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_editar_tarefa);

        editTitulo = findViewById(R.id.editTitulo);
        editDescricao = findViewById(R.id.editDescricao);
        editDataConclusao = findViewById(R.id.editDataConclusao);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnCancelar = findViewById(R.id.btnCancelar);

        tarefaController = new TarefaController(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        tarefaId = getIntent().getIntExtra("tarefa_id", -1);
        if (tarefaId != -1) {
            carregarTarefa();
        }

        btnSalvar.setOnClickListener(view -> salvarTarefa());
        btnCancelar.setOnClickListener(view -> finish());
    }

    private void carregarTarefa() {
        tarefaAtual = tarefaController.listarTarefas().stream()
                .filter(tarefa -> tarefa.getId() == tarefaId)
                .findFirst()
                .orElse(null);
        if (tarefaAtual != null) {
            editTitulo.setText(tarefaAtual.getTitulo());
            editDescricao.setText(tarefaAtual.getDescricao());
            editDataConclusao.setText(tarefaAtual.getDataConclusao());
            int spinnerPosition = ((ArrayAdapter) spinnerStatus.getAdapter())
                    .getPosition(tarefaAtual.getStatus());
            spinnerStatus.setSelection(spinnerPosition);
        }
    }

    private void salvarTarefa() {
        String titulo = editTitulo.getText().toString().trim();
        String descricao = editDescricao.getText().toString().trim();
        String dataConclusao = editDataConclusao.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();

        if (titulo.isEmpty() || dataConclusao.isEmpty()) {
            Toast.makeText(this, "São obrigatórios título e data de conclusão!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tarefaAtual == null) {
            tarefaAtual = new Tarefa(titulo, descricao, dataConclusao, status);
            tarefaController.adicionarTarefa(tarefaAtual);
        } else {
            tarefaAtual.setTitulo(titulo);
            tarefaAtual.setDescricao(descricao);
            tarefaAtual.setDataConclusao(dataConclusao);
            tarefaAtual.setStatus(status);
            tarefaController.atualizarTarefa(tarefaAtual);
        }

        Toast.makeText(this, "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}

