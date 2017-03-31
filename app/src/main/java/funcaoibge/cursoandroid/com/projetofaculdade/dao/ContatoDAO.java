package funcaoibge.cursoandroid.com.projetofaculdade.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import funcaoibge.cursoandroid.com.projetofaculdade.R;
import funcaoibge.cursoandroid.com.projetofaculdade.classe.Contato;

/**
 * Created by marcos.j.estagiario on 06/03/2017.
 */

public class ContatoDAO extends SQLiteOpenHelper {

    private final static String BANCO = "Agenda";
    private final static int VERSAO = 3;

    public ContatoDAO(Context context) {
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Contato (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, email TEXT, telefone TEXT, cor INTEGER, sobrenome TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Contato";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Contato contato) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosContato(contato);

        db.insert("Contato", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosContato(Contato contato) {
        ContentValues dados = new ContentValues();
        dados.put("nome", contato.getNome());
        dados.put("sobrenome", contato.getSobrenome());
        dados.put("email", contato.getEmail());
        dados.put("telefone", contato.getTelefone());
        dados.put("cor", contato.getCor());
        return dados;
    }

    public List<Contato> buscaAlunos() {
        String sql = "SELECT * FROM Contato;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<Contato> contatos = new ArrayList<Contato>();
        while (cursor.moveToNext()) {
            Contato contato = new Contato();
            contato.setId(cursor.getLong(cursor.getColumnIndex("id")));
            contato.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            contato.setSobrenome(cursor.getString(cursor.getColumnIndex("sobrenome")));
            contato.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            contato.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            contato.setCor(cursor.getInt(cursor.getColumnIndex("cor")));

            contatos.add(contato);
        }
        cursor.close();

        return contatos;
    }

    public void altera(Contato contato) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosContato(contato);

        String[] params = {String.valueOf(contato.getId())};
        db.update("Contato", dados, "id = ?", params);
    }

    public void remove(Contato contato) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {String.valueOf(contato.getId())};
        db.delete("Contato", "id = ?", params);
    }
}
