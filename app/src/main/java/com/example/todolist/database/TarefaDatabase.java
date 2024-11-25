package com.example.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TarefaDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tarefa_db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TAREFAS = "tarefas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_DATA_CONCLUSAO = "dataConclusao";
    public static final String COLUMN_STATUS = "status";

    public TarefaDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TAREFAS_TABLE = "CREATE TABLE " + TABLE_TAREFAS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITULO + " TEXT NOT NULL, "
                + COLUMN_DESCRICAO + " TEXT, "
                + COLUMN_DATA_CONCLUSAO + " TEXT NOT NULL, "
                + COLUMN_STATUS + " TEXT NOT NULL)";
        db.execSQL(CREATE_TAREFAS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAREFAS);
        onCreate(db);

    }

        public void excluirTodasTarefas() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_TAREFAS);  // Exclui todas as tarefas da tabela
            db.close();
            
    }
}

