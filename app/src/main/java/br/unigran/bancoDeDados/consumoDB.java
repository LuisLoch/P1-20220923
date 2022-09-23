package br.unigran.bancoDeDados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import br.unigran.p1.Consumo;

public class consumoDB {
    private DBHelper db;
    private SQLiteDatabase conexao;

    public consumoDB(DBHelper db) { this.db = db; }

    public void inserir(Consumo consumo){
        conexao = db.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("kmAtual", consumo.getKmAtual());
        valores.put("qtdAbastecida", consumo.getQtdAbastecida());
        valores.put("valor", consumo.getValor());
        valores.put("data", consumo.getData().toString());

        conexao.insertOrThrow("abastecimentos", null, valores);

        conexao.close();
    }

    public void remover(int id){
        conexao = db.getWritableDatabase();
        conexao.delete("abastecimentos", "id = ?", new String[]{id+""});
    }

    public void atualizar(ListView lista){
        lista.invalidateViews();
    }

    public void listarDados(List dados){
        dados.clear();
        conexao = db.getReadableDatabase();
        String names[] ={"id", "kmAtual", "qtdAbastecida", "valor", "data"};
        Cursor query =conexao.query("abastecimentos", names, null, null, null, null, "id");

        while(query.moveToNext()){
            Consumo consumo = new Consumo();
            consumo.setId(Integer.parseInt(query.getString(0)));
            consumo.setKmAtual(Integer.parseInt(query.getString(1)));
            consumo.setQtdAbastecida(Integer.parseInt(query.getString(2)));
            consumo.setValor(Float.valueOf(query.getString(3)));
            consumo.setData(query.getString(4));
            dados.add(consumo);
        }

        conexao.close();
    }
}
