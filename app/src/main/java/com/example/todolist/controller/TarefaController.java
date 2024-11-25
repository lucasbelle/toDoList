package com.example.todolist.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todolist.database.TarefaDatabase;
import com.example.todolist.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaController {
    private TarefaDatabase dbHelper;

    public TarefaController(Context context) {
        this.dbHelper = new TarefaDatabase(context);
    }

    public long adicionarTarefa(Tarefa tarefa) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TarefaDatabase.COLUMN_TITULO, tarefa.getTitulo());
        values.put(TarefaDatabase.COLUMN_DESCRICAO, tarefa.getDescricao());
        values.put(TarefaDatabase.COLUMN_DATA_CONCLUSAO, tarefa.getDataConclusao());
        values.put(TarefaDatabase.COLUMN_STATUS, tarefa.getStatus());
        long id = db.insert(TarefaDatabase.TABLE_TAREFAS, null, values);
        db.close();
        return id;
    }

    public List<Tarefa> listarTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + TarefaDatabase.TABLE_TAREFAS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(cursor.getInt(0));
                tarefa.setTitulo(cursor.getString(1));
                tarefa.setDescricao(cursor.getString(2));
                tarefa.setDataConclusao(cursor.getString(3));
                tarefa.setStatus(cursor.getString(4));
                tarefas.add(tarefa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tarefas;
    }

    public int atualizarTarefa(Tarefa tarefa) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TarefaDatabase.COLUMN_TITULO, tarefa.getTitulo());
        values.put(TarefaDatabase.COLUMN_DESCRICAO, tarefa.getDescricao());
        values.put(TarefaDatabase.COLUMN_DATA_CONCLUSAO, tarefa.getDataConclusao());
        values.put(TarefaDatabase.COLUMN_STATUS, tarefa.getStatus());
        int rows = db.update(TarefaDatabase.TABLE_TAREFAS, values, TarefaDatabase.COLUMN_ID + " = ?",
                new String[]{String.valueOf(tarefa.getId())});
        db.close();
        return rows;
    }

    public int excluirTarefa(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(TarefaDatabase.TABLE_TAREFAS, TarefaDatabase.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }
}
